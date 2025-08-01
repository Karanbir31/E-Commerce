package com.example.ecommerce.userDetails.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.ecommerce.R
import com.example.ecommerce.userDetails.domain.modules.UserDetails
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ecommerce.navigation.NavScreens


@Composable
fun UserDetailsScreen(
    navController: NavController,
    userDetailsViewModel: UserDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val userDetailsUiState = userDetailsViewModel.userDetails.collectAsState().value

    LaunchedEffect(Unit) {
        userDetailsViewModel.navigateAuthScreen.collect {
            Log.d("TAG", "UserDetailsScreen: navigateAuthScreen")
            navController.navigate(NavScreens.Authentication.route){
                launchSingleTop = true
            }
        }
    }

    when (userDetailsUiState) {
        is UserDetailsUiState.Loading -> {
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        }

        is UserDetailsUiState.Error -> {
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
        }

        is UserDetailsUiState.Success -> {
            UsersProfileUi(userDetailsUiState.data)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(horizontal = 16.dp)
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
fun UsersProfileUi(userDetails : UserDetails) {
    LazyColumn {
        item {
            UsersImages(
                name = userDetails.username,
                email = userDetails.email
            )
        }

        item {
            Spacer(Modifier.height((16.dp)))
        }

        item {
            UsersProfileElementsRow(
                icon = R.drawable.ic_profile,
                rowText = "Personal Details"
            ) { }
        }

        item {
            UsersProfileElementsRow(
                icon = R.drawable.outline_orders_24,
                rowText = "Orders"
            ) { }
        }

        item {
            UsersProfileElementsRow(
                icon = R.drawable.outline_heart_check_24,
                rowText = "Wishlist"
            ) { }
        }

        item {
            UsersProfileElementsRow(
                icon = R.drawable.outline_headset_mic_24,
                rowText = "Help Center"
            ) { }
        }


        item {
            UsersProfileElementsRow(
                icon = R.drawable.outline_logout_24,
                rowText = "Log out"
            ) { }
        }


    }
}

@Composable
fun UsersImages(
    name : String,
    email : String,
    imageUrl: String = "https://dummyjson.com/icon/liamg/128",
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = modifier
                .size(120.dp) // Fixed size for profile circle
                .aspectRatio(1f)
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = CircleShape,
            border = BorderStroke(
                2.dp,
                Brush.radialGradient(colors = listOf(Color.Gray, Color.Gray))
            )
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "User image",
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop, // Makes image fill the circle
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp) // Ensure image fits card
            )
        }

        Text(
            text = "Hello, $name",
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            maxLines = 1,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
        )
        Text(
            text = email,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
            maxLines = 1,
            modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun UsersProfileElementsRow(icon: Int, rowText: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
            .clickable {
                onClick.invoke()
            }
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = rowText,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 8.dp)
        )

        Text(
            text = rowText,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )

        Icon(
            painter = painterResource(R.drawable.outline_arrow_forward_ios_24),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = "show more",
            modifier = Modifier.size(24.dp)

        )

    }
}