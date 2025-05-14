package com.example.ecommerce.productslist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import retrofit2.http.Query

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsSearchBar(
    onSearchQuery: (String) -> Unit,
    productsCategories: List<String>
) {
    var query by remember { mutableStateOf("") }
    var searchBarStatus by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val onActiveChange = {
            searchBarStatus = false
        }
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = {
                        query = it
                    },
                    onSearch = {
                        searchBarStatus = false
                        onSearchQuery.invoke(query)
                    },
                    expanded = searchBarStatus,
                    onExpandedChange = {

                    },
                    enabled = searchBarStatus,
                    placeholder = {
                        Text("Search by name or category")
                    },
                    leadingIcon = {

                    },
                    trailingIcon = {
                        Icon(Icons.Default.Search, "Search")
                    }
                )
            },
            expanded = searchBarStatus,
            onExpandedChange = {

            },
            modifier = Modifier.fillMaxWidth(),
            content = {
                LazyColumn {
                    items(productsCategories) {
                        SearchBarSuggestionItem(
                            category = it,
                            onClick = {
                                onSearchQuery(it)
                            }
                        )
                    }
                }
            },
        )//search bar
    }//box
}//fun


@Composable
fun SearchBarSuggestionItem(category: String, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = category,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Default.Search,
            category,
            modifier = Modifier.clickable(
                onClick = {
                    onClick.invoke(category)
                }
            )
        )
    }
}