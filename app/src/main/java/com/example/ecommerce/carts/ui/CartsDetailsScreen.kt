package com.example.ecommerce.carts.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.ecommerce.carts.domain.modules.Cart
import com.example.ecommerce.carts.domain.modules.CartsProducts

@Composable
fun CartsDetailsScreen(cartsViewModel: CartsViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        cartsViewModel.getUsersCart()
    }

    val cartsUiState = cartsViewModel.usersCart.collectAsState().value

    when (val state = cartsUiState) {
        is CartsUiState.Error -> {
            Log.e("tag", "error in carts ui state ${state.exception}")
        }

        CartsUiState.Loading -> {

        }

        is CartsUiState.Success -> {
            UserCartsDetailsUi(state.data)
        }
    }
}

@Composable
fun UserCartsDetailsUi(carts: Cart) {
    val context = LocalContext.current
    LazyColumn {
        item {
            Text("All Items")
        }

        items(carts.products) { cartsProduct ->
            CartPreviewCard(
                cartProduct = cartsProduct,
                onClick = {
                    Toast.makeText(context, "you click ${cartsProduct.title}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        item{
            HorizontalDivider()
            Text("total products - ${carts.totalProducts}")
            Text("total quantity - ${carts.totalQuantity}")
            Text("total price ₹ ${carts.discountedTotal}")

        }
    }
}

@Composable
fun CartPreviewCard(cartProduct: CartsProducts, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(
                onClick = {
                    onClick.invoke()
                }
            )
    ) {
        AsyncImage(
            model = cartProduct.thumbnail,
            contentDescription = cartProduct.title,
            modifier = Modifier.weight(0.4f)
        )

        Column(
            modifier = Modifier.weight(0.6f)
        ) {
            Text(cartProduct.title)
            Text(cartProduct.quantity.toString())
            Text("₹ ${cartProduct.discountedTotal}")
            Text(cartProduct.title)
        }
    }
}