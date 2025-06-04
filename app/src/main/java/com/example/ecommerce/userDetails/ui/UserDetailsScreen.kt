package com.example.ecommerce.userDetails.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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

@Composable
fun UserDetailsScreen(
    navController: NavController,
    userDetailsViewModel: UserDetailsViewModel = hiltViewModel()
) {


    val context = LocalContext.current
    val userDetailsUiState = userDetailsViewModel.userDetails.collectAsState().value

    when (userDetailsUiState) {
        is UserDetailsUiState.Loading -> {
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        }

        is UserDetailsUiState.Error -> {
            Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
        }

        is UserDetailsUiState.Success -> {
            UserDetailsUi(userDetailsUiState.data)
        }
    }
}

@Composable
fun UserDetailsUi(userDetails: UserDetails) {

    LazyColumn {

        //header image and full name
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = userDetails.image,
                    contentDescription = "user image",
                    placeholder = painterResource(R.drawable.ic_launcher_background)
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "${userDetails.firstName} ${userDetails.lastName}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Spacer
        item {
            Spacer(Modifier.height(16.dp))
        }

        //basic information
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "User details",
                    style = MaterialTheme.typography.titleMedium
                )

                HorizontalDivider()

                UserDetailsRow(
                    label = "Email",
                    value = userDetails.email
                )

                Spacer(Modifier.height(16.dp))

                UserDetailsRow(
                    label = "Gender",
                    value = userDetails.gender
                )

                Spacer(Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        // delete refreshToken and accessToken from sharedPerf
                        // navigate to authentication screen
                        TODO()

                    }
                ){
                    Text(
                        text = "Sign out"
                    )
                }

            }
        }

    }

}

@Composable
fun UserDetailsRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f)
        )
    }
}