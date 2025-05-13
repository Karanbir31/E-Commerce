package com.example.ecommerce.productslist.ui

import androidx.compose.foundation.layout.Column
import com.example.ecommerce.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.ecommerce.productslist.domain.modules.Product

@Composable
fun ProductsScreen(){

}

@Composable
fun ProductPreviewCard(product: Product){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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