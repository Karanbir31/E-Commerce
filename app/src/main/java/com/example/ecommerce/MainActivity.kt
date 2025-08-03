package com.example.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.navigation.AppNavigationGraph
import com.example.ecommerce.navigation.BottomNavItems
import com.example.ecommerce.navigation.BottomNavigationBar
import com.example.ecommerce.navigation.NavScreens
import com.example.ecommerce.ui.theme.ECommerceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination

            val showBottomBar = currentDestination?.route in BottomNavItems.bottomNavRoutes

            ECommerceTheme {

                Scaffold(
                    topBar = {
                        MainAppBar((currentDestination?.route == NavScreens.ProductDetails.route)){
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.safeContentPadding(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController)
                        }
                    }

                ) { innerPadding ->

                    AppNavigationGraph(navController, Modifier.padding(innerPadding))

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(shouldShowNavIcon: Boolean = false, onClickNavIcon : () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Apni Dhukan",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            )
        },
        navigationIcon = {
            if (shouldShowNavIcon) {
                IconButton(
                    onClick = {
                        onClickNavIcon.invoke()
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "backs")
                }
            }
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
