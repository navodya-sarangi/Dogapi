package com.example.dogapiresponse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.dogapiresponse.network.RetrofitClient

data class DogUiState(
    val imageUrls: List<String> = emptyList(),
    val isLoading: Boolean = false
)

class DogViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DogUiState(isLoading = true))
    val uiState: StateFlow<DogUiState> = _uiState.asStateFlow()

    init {
        fetchDogImages()
    }

    private fun fetchDogImages() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true)
            }
            try {
                val response = RetrofitClient.apiService.getRandomDogImages()
                _uiState.update { currentState ->
                    currentState.copy(
                        imageUrls = response.message,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { currentState ->
                    currentState.copy(isLoading = false)
                }
            }
        }
    }
}