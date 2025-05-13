package com.example.ecommerce.navigation

sealed class NavScreens(val route: String) {
    object Products : NavScreens("products_screen")
    object UserDetails : NavScreens("user_details_screen")
    object Cart : NavScreens("cart_screen")
    object Authentication : NavScreens("authentication_screen")
    object ProductDetails : NavScreens("product_details_screen")
}