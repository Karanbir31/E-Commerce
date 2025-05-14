package com.example.ecommerce.productslist.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import com.example.ecommerce.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.ecommerce.productslist.domain.modules.Product
import retrofit2.http.Query

@Composable
fun ProductsScreen(productsListViewModel: ProductsListViewModel = hiltViewModel()) {

    val productsUiState = productsListViewModel.products.collectAsState().value


    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            ProductsSearchBar()
        }
    ) { innerPadding ->

    }

    when (productsUiState) {
        is ProductsUiState.Error -> {

        }

        ProductsUiState.Loading -> {

        }

        is ProductsUiState.Success -> {

        }
    }




}

@Composable
fun ProductsList(products: List<Product>) {
    LazyColumn{
        items(products) { product ->
            ProductPreviewCard(
                product = product,
                onCLickProduct = {

                }
            )
        }
    }
}


@Composable
fun ProductPreviewCard(product: Product, onCLickProduct: () -> Unit) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {

        AsyncImage(
            modifier = Modifier.weight(1f),
            model = product.thumbnail,
            contentDescription = product.title,
            placeholder = painterResource(R.drawable.ic_launcher_background)
        )

        Column(modifier = Modifier.weight(1.5f)) {
            Text(product.title)
            Text("Price-${product.price}")
            Text("Discount ${product.discountPercentage}")
            Text(product.shippingInformation)
        }
    }
}


