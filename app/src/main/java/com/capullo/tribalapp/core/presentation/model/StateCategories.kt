package com.capullo.tribalapp.core.presentation.model

data class StateCategories(
    val loading: Boolean = false,
    val items: List<String> = emptyList(),
    val error: String? = null
)
