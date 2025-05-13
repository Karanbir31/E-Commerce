package com.example.ecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce.authentication.ui.AuthenticationScreen
import com.example.ecommerce.carts.ui.CartsDetailsScreen
import com.example.ecommerce.productslist.ui.ProductsScreen
import com.example.ecommerce.productslist.ui.productsDetails.ProductDetailsScreen
import com.example.ecommerce.userDetails.ui.UserDetailsScreen

@Composable
fun AppNavigationGraph(navController: NavHostController,modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = NavScreens.Products.route ){

        composable(NavScreens.Products.route) {
            ProductsScreen()
        }
        composable(NavScreens.UserDetails.route) {
            UserDetailsScreen()
        }
        composable(NavScreens.Cart.route) {
            CartsDetailsScreen()
        }
        composable(NavScreens.Authentication.route) {
            AuthenticationScreen()
        }
        composable(NavScreens.ProductDetails.route) {
            ProductDetailsScreen()
        }
    }
}