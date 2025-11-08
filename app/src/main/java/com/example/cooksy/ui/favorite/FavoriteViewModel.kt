package com.example.cooksy.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooksy.data.model.Meal
import com.example.cooksy.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    // Expose favorites as a StateFlow for Compose
    val favorites: StateFlow<List<Meal>> = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Toggle favorite (add/remove)
    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch {
            try {
                val isFav = repository.isFavorite(meal.id)
                if (isFav) {
                    repository.removeFavorite(meal)
                } else {
                    repository.addFavorite(meal)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            }
        }
    }
}
