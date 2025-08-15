package com.capullo.tribalapp.ui.cuckcategories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capullo.tribalapp.R
import com.capullo.tribalapp.core.presentation.MainCategoriesViewModel
import com.capullo.tribalapp.core.presentation.model.StateCategories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: MainCategoriesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.text_categories)) },
                actions = {
                    IconButton(onClick = { viewModel.startList() }, enabled = !state.loading) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.text_refresh))
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.loading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            state.error != null -> Column(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Ocurrió un error:\n${state.error}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(12.dp))
                Button(onClick = viewModel::startList) { Text(stringResource(R.string.text_try)) }
            }

            else ->
                ContentCategories(state, padding = padding)
        }
    }
}

@Composable
fun ContentCategories(
    state: StateCategories,
    padding: PaddingValues,
    onCategoryClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.items, key = { it }) { category ->
            CategoryCard(
                title = category.replaceFirstChar { ch ->
                    if (ch.isLowerCase()) ch.titlecase() else "$ch"
                },
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Preview
@Composable
fun CategoriesScreenPreview() {
    val listCategory: List<String> = listOf(
        "animal", "career", "celebrity", "dev", "explicit",
        "fashion", "food", "history", "money", "movie",
        "music", "political", "religion", "science", "sport",
        "travel"
    )
    ContentCategories(StateCategories(items = listCategory), PaddingValues())
}
