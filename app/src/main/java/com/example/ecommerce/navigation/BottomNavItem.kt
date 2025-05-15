package com.example.ecommerce.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label : String,
    val icon : ImageVector,
    val route: String
)

object BottomNavItems{
    val navItems = listOf(
        BottomNavItem(
            label = "Products",
            icon = Icons.Default.Star,
            route = NavScreens.Products.route
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Default.Person,
            route = NavScreens.UserDetails.route
        ),
        BottomNavItem(
            label = "Cart",
            icon = Icons.Default.ShoppingCart,
            route = NavScreens.Cart.route
        )
    )

    val bottomNavRoutes = listOf(
        NavScreens.Products.route,
        NavScreens.UserDetails.route,
        NavScreens.Cart.route
    )
}