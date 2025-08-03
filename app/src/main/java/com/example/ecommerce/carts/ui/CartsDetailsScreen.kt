package com.example.ecommerce.carts.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce.R
import com.example.ecommerce.carts.domain.modules.Cart
import com.example.ecommerce.carts.domain.modules.CartItem
import com.example.ecommerce.navigation.NavScreens
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.ui.ProductActionButton
import com.example.ecommerce.productslist.ui.ProductItem

@Composable
fun CartsDetailsScreen(
    navController: NavController,
    cartsViewModel: CartsViewModel = hiltViewModel()
) {

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
            UserCartsDetailsUi(state.data, onClickProduct = {id->
                navController.navigate(
                    NavScreens.ProductDetails.createRoute(productId = id)
                )
            })
        }
    }
}

@Composable
fun UserCartsDetailsUi(data: Cart, onClickProduct: (Long) -> Unit) {
    val cartItem = CartItem()

    Scaffold(
        bottomBar = {
            CheckOutBottomBar()
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            items(cartItem.products) { product ->
                CartProductItem(product = product, onClickProduct = { id ->
                    onClickProduct.invoke(id)
                })

            }

        }
    }

}

@Composable
fun CheckOutBottomBar() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.75f),
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {

            }
    ) {
        Row(

        ) {
            Text(
                text = "Total Price : ",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .weight(1f, fill = false)
            )
            Text(
                text = "$2345",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .weight(1f, fill = true)
            )

        }

        ProductActionButton(
            text = "Check out now",
            buttonIcon = null,
            modifier = Modifier
        ) { }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsCartAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Apni Dhukan",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            )
        },
        navigationIcon = {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backs")
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,

            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun CartProductItem(product: Product, onClickProduct: (Long) -> Unit) {
    Column {
        Box {
            ProductItem(
                product = product,

                onClickProduct = {
                    onClickProduct.invoke(product.id)
                },
                onClickNavigateToCart = {

                },
                )

            Checkbox(
                checked = true,
                onCheckedChange = {

                },
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

        Row {

            ProductActionNegativeButton(
                text = "Remove",
                modifier = Modifier.weight(1f)
            ) {

            }


            ProductActionButton(
                text = "Buy Now",
                buttonIcon = R.drawable.ic_buy,
                modifier = Modifier.weight(1f)
            ) {

            }


        }


    }
}


@Composable
fun ProductActionNegativeButton(
    text: String = "Remove",
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedButton(
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f),
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        onClick = onClick,
        modifier = modifier.padding(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_buy),
            contentDescription = "buy now",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}




