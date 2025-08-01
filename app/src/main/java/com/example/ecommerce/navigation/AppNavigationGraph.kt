package com.example.ecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecommerce.authentication.ui.AuthenticationScreen
import com.example.ecommerce.carts.ui.CartsDetailsScreen
import com.example.ecommerce.productslist.ui.ProductsScreen
import com.example.ecommerce.productslist.ui.productsDetails.ProductDetailsScreen
import com.example.ecommerce.userDetails.ui.UserDetailsScreen

@Composable
fun AppNavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavScreens.Products.route,
        modifier = modifier
    ) {

        composable(NavScreens.Products.route) {
            ProductsScreen(navController = navController)
        }
        composable(NavScreens.UserDetails.route) {
            UserDetailsScreen(navController = navController)
        }
        composable(NavScreens.Cart.route) {
            CartsDetailsScreen(navController = navController)
        }
        composable(NavScreens.Authentication.route) {
            AuthenticationScreen(navController = navController)
        }
        composable(
            NavScreens.ProductDetails.route,
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) {backStackEntry->
            val productId = backStackEntry.arguments?.getLong("productId") ?: 1L
            ProductDetailsScreen(productId = productId, navController = navController)
        }
    }
}