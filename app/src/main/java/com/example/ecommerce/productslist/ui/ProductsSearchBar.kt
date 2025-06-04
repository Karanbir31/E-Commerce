package com.example.ecommerce.productslist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ecommerce.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchQuery: (String) -> Unit,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit,
    productsCategories: List<String>
) {
    Box(
        Modifier
            .fillMaxWidth()
            .offset(x = 0.dp, y = 0.dp)
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = {
                        onQueryChange.invoke(it)
                    },
                    onSearch = {
                        onSearchQuery.invoke(query)
                    },
                    expanded = isActive,
                    onExpandedChange = { },
                    enabled = true,
                    placeholder = {
                        Text("Search by here ....")
                    },
                    trailingIcon = {
                        Icon(Icons.Default.Search, "Search")
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .offset(y = 0.dp)
                        .border(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            width = 2.dp
                        )
                        .clip(RoundedCornerShape(12.dp))
                )
            },
            expanded = isActive,
            onExpandedChange = { },

            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Red),
            content = {
                LazyColumn {
                    items(productsCategories) {
                        SearchBarSuggestionItem(
                            category = it,
                            onClick = {
                                onQueryChange(it)
                            }
                        )
                    }
                }
            },
        )
    }
}


@Composable
fun SearchBarSuggestionItem(category: String, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onClick.invoke(category)
                }
            )
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = category,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                onClick.invoke(category)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.north_east_arrow),
                contentDescription = category
            )
        }
    }
}