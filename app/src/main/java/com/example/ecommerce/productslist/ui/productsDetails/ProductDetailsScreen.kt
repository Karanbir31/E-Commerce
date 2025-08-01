package com.example.ecommerce.productslist.ui.productsDetails

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.ecommerce.R
import com.example.ecommerce.navigation.NavScreens
import com.example.ecommerce.productslist.domain.modules.Product
import com.example.ecommerce.productslist.ui.ProductActionButton
import com.example.ecommerce.productslist.ui.ProductsUiState
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.absoluteValue

@Composable
fun ProductDetailsScreen(
    productId: Long,
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        viewModel.getProductsDetails(productId)
    }

    val productState = viewModel.productDetails.collectAsState().value

    val similarProductsUiState = viewModel.similarProducts

    when (productState) {
        is ProductDetailsUiState.Error -> {

        }
        ProductDetailsUiState.Loading -> {

        }
        is ProductDetailsUiState.Success -> {

            LaunchedEffect(productState.data.category) {
                viewModel.searchProductsByCategory(productState.data.category, 1)
            }
            ProductDetailsScreenUi(productState.data, similarProductsUiState ){
                navController.navigate(it){
                    navController.popBackStack()
                    launchSingleTop = true
                }
            }

        }
    }
}


@Composable
fun ProductDetailsScreenUi(product: Product, similarProductsUiState: StateFlow<ProductsUiState>, onClickNavigate : (String) -> Unit) {
    Scaffold(
       // modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            BuyAndCartsButtons{
                onClickNavigate.invoke(it)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                ProductsImageCarousel(product.images)
            }

            item {
                ProductsDetailsInfo(product)
            }



            item {
                Spacer(Modifier.height(8.dp))

                ProductInfoRow(label = "Brand", value = product.brand)
                ProductInfoRow(label = "Warranty", value = product.warrantInformation)
                ProductInfoRow(label = "Brand", value = product.dimensions.getProductDimensions())
                ProductInfoRow(label = "Shipping", value = product.shippingInformation)
                ProductInfoRow(label = "Return Policy", value = product.returnPolicy)

            }

            item {

                MoreSimilarProducts(similarProductsUiState)
            }


            item {
                Spacer(Modifier.height(120.dp))
            }


        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsDetailsAppBar() {
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
fun MoreSimilarProducts(similarProductsUiState: StateFlow<ProductsUiState>) {

    val similarProducts = similarProductsUiState.collectAsState().value

    when(similarProducts){
        is ProductsUiState.Error -> {

        }
        ProductsUiState.Loading -> {

        }
        is ProductsUiState.Success -> {
            Column {
                Text(
                    text = "Similar Products",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
                )

                LazyRow {
                    items(similarProducts.data.products) {product : Product ->
                        SimilarProductItem(product)
                    }

                    item {
                        Spacer(Modifier.width(16.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun BuyAndCartsButtons(
   onClickNavigate : (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

        ProductActionButton(
            text = "Add to cart",
            buttonIcon = R.drawable.ic_cart,
            modifier = Modifier.weight(1f)
        ) {
            onClickNavigate.invoke(NavScreens.Cart.route)
        }
        ProductActionButton(
            text = "Buy now",
            buttonIcon = R.drawable.ic_buy,
            modifier = Modifier.weight(1f)
        ) {

            onClickNavigate.invoke(NavScreens.Cart.route)
        }

    }

}

@Composable
fun ProductsDetailsInfo(product: Product) {

    Column {
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )

        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {

            Text(
                text = product.rating.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )

            for (i: Int in 1..4) {
                Icon(
                    painter = painterResource(R.drawable.star_button_selected),
                    contentDescription = "rating",
                    tint = Color(0xFFA68E1E),
                    modifier = Modifier.size(24.dp)
                )
            }

        }

        Row {
            val costPrice = (product.price * 100) / (100 - product.discountPercentage)
            Text(
                text = costPrice.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            )
            Text(
                text = product.price.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )

            Text(
                text = "${product.discountPercentage}% off",
                color = Color(0xFF38723A),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
        }

        Text(
            text = "Products details",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
        )

        Text(
            text =product.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
        )

    }
}

@Composable
fun ProductsImageCarousel(productsImages: List<String>) {
    val pagerState = rememberPagerState(pageCount = { productsImages.size })

    Column {
        HorizontalPager(
            state = pagerState
        ) { page ->
            Card(
                shape = RoundedCornerShape(16.dp),

                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                        alpha = lerp(
                            start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }) {

                AsyncImage(
                    modifier = Modifier
                        .height(260.dp)
                        .align(Alignment.CenterHorizontally),
                    model = productsImages[page],
                    contentDescription = "product",
                    contentScale = ContentScale.FillHeight,
                    placeholder = painterResource(R.drawable.ic_launcher_background)
                )

            }

        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp), horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.5f
                    )
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 8.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }

}

@Composable
fun SimilarProductItem(product: Product) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
        ),
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable {

            }

    ) {
        AsyncImage(
            model = product.images?.get(0),
            contentDescription = "product",
            placeholder = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .background(shape = RoundedCornerShape(8.dp), color = Color.Transparent)
        )

        Text(
            text =product.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )

        Row {
            val costPrice = (product.price * 100) / (100 - product.discountPercentage)
            Text(
                text = costPrice.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            )
            Text(
                text = product.price.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )

            Text(
                text = "${product.discountPercentage}% off",
                color = Color(0xFF38723A),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
        }
    }
}


@Composable
fun ProductInfoRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)

    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .weight(0.5f)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .weight(1f)
        )
    }
}

//
//@Preview
//@Composable
//fun ProductsDetailsPreview() {
//    ProductsDetails()
//}
//
