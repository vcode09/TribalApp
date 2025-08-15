package com.capullo.tribalapp.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capullo.tribalapp.core.domain.usecase.GetAllChuckUseCase
import com.capullo.tribalapp.core.presentation.model.StateCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoriesViewModel @Inject constructor(
    private val getAllChuckUseCase: GetAllChuckUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StateCategories())
    val state: StateFlow<StateCategories> = _state

    init {
        startList()
    }

    /**
     * This function is responsible for starting the list of categories.
     * It updates the state to loading, fetches the categories from the use case,
     * and updates the state with the result.
     * If an error occurs, it updates the state with the error message.
     * <3 Vmoreno
     */
    fun startList() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true,
                    error = null
                )
            }
            runCatching { getAllChuckUseCase.getCategories() }
                .onSuccess { list ->
                    _state.update {
                        it.copy(
                            loading = false,
                            items = list
                        )
                    }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            loading = false,
                            error = e.message ?: "Error"
                        )
                    }
                }
        }
    }
}
