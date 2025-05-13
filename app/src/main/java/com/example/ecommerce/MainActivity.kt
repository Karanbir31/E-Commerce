package com.example.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.navigation.AppNavigationGraph
import com.example.ecommerce.navigation.BottomNavigationBar
import com.example.ecommerce.ui.theme.ECommerceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ECommerceTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                    bottomBar = {
                        BottomNavigationBar(navController)
                    }

                ) { innerPadding ->

                    AppNavigationGraph(navController, Modifier.padding(innerPadding))

                }
            }
        }
    }
}


