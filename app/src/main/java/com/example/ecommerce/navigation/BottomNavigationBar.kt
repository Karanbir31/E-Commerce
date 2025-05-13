package com.example.ecommerce.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar{
        navItems.forEach { navItem->

            NavigationBarItem(
                selected = navItem.route == currentRoute,

                onClick = {
                    navController.navigate(navItem.route){
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState= true
                        }
                    }
                },

                icon = { Icon(navItem.icon, navItem.label) },

                label = { Text(text = navItem.label) }
            )

        }
    }

}