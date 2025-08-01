package com.example.ecommerce.productslist.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil3.compose.AsyncImage
import com.example.ecommerce.R
import com.example.ecommerce.navigation.NavScreens
import com.example.ecommerce.productslist.domain.modules.Product
import kotlinx.coroutines.delay

import java.math.BigDecimal
import java.math.RoundingMode


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

    when (val state = productsUiState) {
        is ProductsUiState.Error -> {
            Log.d("tag", "Ui state is error and errorCode - ${state.exception}")
        }

        ProductsUiState.Loading -> {
            Log.d("tag", "Ui state is loading")
        }

        is ProductsUiState.Success -> {
            Column {

                Box(
                    modifier = Modifier.clickable(
                        onClick = {
                            isSearchBarActive = !isSearchBarActive
                        }
                    )
                ){
                    ProductsSearchBar(
                        query = query,
                        onSearchQuery = {
                            searchedQuery = it
                            isSearchBarActive = !isSearchBarActive
                        },
                        onQueryChange = {
                            query = it
                        },
                        isActive = isSearchBarActive,
                        productsCategories = productsCategories

                    )
                }


                Spacer(Modifier.height(8.dp))

                FilterAndSortingRow()

                ProductsList(
                    products = state.data.products,
                    navController = navController,
                )

            }


        }
    }

}

@Composable
fun ProductsList(
    products: List<Product>,
    navController: NavController,

    ) {
//    val context = LocalContext.current
    LazyColumn {
        items(products) { product ->
            ProductItem(
                product = product,
                onClickProduct = {

                    navController.navigate(route = NavScreens.ProductDetails.createRoute(product.id)) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                onClickNavigateToCart = {
                    navController.navigate(
                        NavScreens.Cart.route
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }

        item {
            Spacer(Modifier.height(200.dp))
        }
    }
}


@Composable
fun FilterAndSortingRow() {

    var shouldShowBottomSheet by remember { mutableStateOf(false) }

    if (shouldShowBottomSheet) {
        FilterSortBottomSheet {
            shouldShowBottomSheet = false
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "All Featured",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .weight(1f)
        )


        FilterChip(
            selected = false,

            label = {
                Text(
                    text = "Sort",
                    style = MaterialTheme.typography.labelLarge
                )
            },
            onClick = {
                shouldShowBottomSheet = true
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_sort),
                    contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )

        FilterChip(
            selected = false,

            label = {
                Text(
                    text = "Filter",
                    style = MaterialTheme.typography.labelLarge
                )
            },
            onClick = {

            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = "filter",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.padding(top = 8.dp, end = 8.dp)
        )


    }
}

@Composable
fun ProductItem(
    product: Product,
    onClickProduct: () -> Unit,
    onClickNavigateToCart: () -> Unit
) {
    var shouldShowAddToCart by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onClickProduct.invoke()
            }
    ) {
        Row {
            ProductImage(
                imageUrl = product.thumbnail,
                modifier = Modifier
                    .weight(1f)
                    .background(shape = RoundedCornerShape(8.dp), color = Color.Transparent)
            )

            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .background(shape = RoundedCornerShape(8.dp), color = Color.Transparent)
            ) {

                ProductTitleAndDescription(title = product.title, description = product.description)

                ProductsPriceRow(product.price, product.discountPercentage)

                ProductsRatingRow(product.rating)

                if (shouldShowAddToCart) {
                    ProductActionButton(
                        text = "Add to cart",
                        buttonIcon = R.drawable.ic_cart,
                        modifier = Modifier
                    ) {
                        //add to cart clicked
                        //  TODO("Add product into room database")
                        shouldShowAddToCart = false
                    }
                } else {
                    ProductActionButton(
                        text = "Go to cart",
                        buttonIcon = R.drawable.ic_cart,
                        modifier = Modifier
                    ) {
                        //navigate to carts screen
                        onClickNavigateToCart.invoke()

                    }
                }
            }
        }

    }
}

@Composable
fun ProductsRatingRow(rating: Float) {
    Row(
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        for (i in 1..5) Icon(
            painter = painterResource(R.drawable.star_button_selected),
            contentDescription = "rating",
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.75f),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ProductsPriceRow(price: Float, discountPercentage: Float) {
    val costPrice = (price * 100) / (100 - discountPercentage)

    val decimal = BigDecimal(3.14159265359).setScale(2, RoundingMode.HALF_EVEN)

    Column {
        Text(
            text = price.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp)
        )

        Row {

            Text(
                text = costPrice.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp)
            )
            Text(
                text = "$discountPercentage % off",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp)
            )
        }

    }


}

@Composable
fun ProductImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "product",
        contentScale = ContentScale.FillWidth,
        placeholder = painterResource(R.drawable.ic_launcher_background),
        modifier = modifier
    )
}

@Composable
fun ProductTitleAndDescription(title: String, description: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(
            top = 4.dp,
            start = 8.dp,
            end = 8.dp
        )
    )

    Text(
        text = description,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(
            top = 4.dp,
            start = 8.dp,
            end = 8.dp
        )
    )
}


@Composable
fun ProductActionButton(
    text: String = "Buy Now",
    modifier: Modifier = Modifier,
    buttonIcon: Int?,
    onClick: () -> Unit
) {
    ElevatedButton(
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        onClick = onClick,
        modifier = modifier.padding(8.dp),
    ) {
        if (buttonIcon != null) {
            Icon(
                painter = painterResource(buttonIcon),
                contentDescription = "buy now",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}


@Composable
fun ProductQuantitySelector(
    onAddProduct: () -> Unit,
    onRemoveProduct: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quantity : ",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 4.dp, start = 8.dp)
                .weight(1f, fill = false)
        )

        IconButton(
            onClick = {
                onRemoveProduct.invoke()
            }
        ) {
            Icon(painterResource(R.drawable.outline_remove_24), "remove")
        }
        Text(
            text = "1",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
        )
        IconButton(
            onClick = {
                onAddProduct.invoke()
            }
        ) {
            Icon(painterResource(R.drawable.baseline_add_24), "Add")
        }
    }
}


//  topBar = {
//            if (!isSearchBarActive) {
//                TopAppBar(
//                    title = {
//                        Text(
//                            text = "Title",
//                            modifier = Modifier.padding(start = 16.dp)
//                        )
//                    },
//                    actions = {
//                        Icon(
//                            Icons.Default.Search,
//                            "search",
//                            modifier = Modifier
//                                .padding(start = 8.dp, end = 16.dp)
//                                .clickable(
//                                    onClick = {
//                                        isSearchBarActive = true
//                                    }
//                                )
//
//                        )
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .shadow(elevation = 8.dp)
//                )
//            } else {
//                ProductsSearchBar(
//                    query = query,
//                    onQueryChange = {
//                        query = it
//                    },
//                    onSearchQuery = {
//                        searchedQuery = it
//                        isSearchBarActive = false
//                    },
//                    isActive = isSearchBarActive,
//                    onActiveChange = {
//                        isSearchBarActive = it
//                    },
//                    productsCategories = productsCategories
//                )
//            }
//        }
