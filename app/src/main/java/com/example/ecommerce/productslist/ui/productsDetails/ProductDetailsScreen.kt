package com.example.ecommerce.productslist.ui.productsDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProductDetailsScreen(productId: Long) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.Red)
    ) {


        Text("Product Id - $productId")
    }
}