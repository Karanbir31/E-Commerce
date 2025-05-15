package com.example.ecommerce.productslist.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import com.example.ecommerce.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.ecommerce.navigation.NavScreens
import com.example.ecommerce.productslist.domain.modules.Product
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    navController: NavController,
    productsListViewModel: ProductsListViewModel = hiltViewModel()
) {
    val productsCategories = productsListViewModel.productCategories.collectAsState().value
    val productsUiState = productsListViewModel.productsUiState.collectAsState().value

    var isSearchBarActive by remember { mutableStateOf(false) }
    var searchedQuery by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }

    LaunchedEffect(searchedQuery) {
        delay(200)

        val trimmedQuery = searchedQuery.trim()
        when {
            trimmedQuery.isEmpty() -> {
                Log.d("tag", "call getAllProducts function")
                productsListViewModel.getAllProducts()
            }

            productsCategories.any { it.equals(trimmedQuery, ignoreCase = true) } -> {
                Log.d("tag", "call searchProductsByCategory function with $trimmedQuery")
                productsListViewModel.searchProductsByCategory(trimmedQuery, 1)
            }

            else -> {
                Log.d("tag", "call searchProducts function with $trimmedQuery")
                productsListViewModel.searchProducts(searchQuery = trimmedQuery)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            if (!isSearchBarActive) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Title",
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    },
                    actions = {
                        Icon(
                            Icons.Default.Search,
                            "search",
                            modifier = Modifier
                                .padding(start = 8.dp, end = 16.dp)
                                .clickable(
                                    onClick = {
                                        isSearchBarActive = true
                                    }
                                )

                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp)
                )
            } else {
                ProductsSearchBar(
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearchQuery = {
                        searchedQuery = it
                        isSearchBarActive = false
                    },
                    isActive = isSearchBarActive,
                    onActiveChange = {
                        isSearchBarActive = it
                    },
                    productsCategories = productsCategories
                )
            }
        }

    ) { innerPadding ->
        when (val state = productsUiState) {
            is ProductsUiState.Error -> {
                Log.d("tag", "Ui state is error and errorCode - ${state.exception}")
            }
            ProductsUiState.Loading -> {
                Log.d("tag", "Ui state is loading")
            }
            is ProductsUiState.Success -> {
                ProductsList(
                    products = state.data.products,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding())
                )
            }
        }
    }
}

@Composable
fun ProductsList(
    products: List<Product>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        items(products) { product ->
            ProductPreviewCard(
                product = product,
                onClickProduct = {
                    Toast.makeText(context, "you click ${product.title}", Toast.LENGTH_SHORT).show()
                    navController.navigate(
                        NavScreens.ProductDetails.createRoute(productId = product.id)
                    )
                }
            )
        }

        item {
            Spacer(modifier.height(200.dp))
        }
    }
}


@Composable
fun ProductPreviewCard(product: Product, onClickProduct: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                onClick = { onClickProduct.invoke() }
            )
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


