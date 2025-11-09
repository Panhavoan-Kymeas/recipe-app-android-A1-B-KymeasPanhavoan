package com.example.cooksy.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooksy.data.model.Meal
import com.example.cooksy.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _meal = MutableStateFlow<Meal?>(null)
    val meal: StateFlow<Meal?> = _meal.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadMeal(id: String) {
        viewModelScope.launch {
            val localMeal = repository.getFavoriteMealById(id)
            if (localMeal != null) {
                _meal.value = localMeal
                _isFavorite.value = true
            } else {
                val remoteMeal = repository.getMealById(id)
                _meal.value = remoteMeal
                _isFavorite.value = false
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentMeal = _meal.value ?: return@launch
            if (_isFavorite.value) {
                repository.removeFavorite(currentMeal)
            } else {
                repository.addFavorite(currentMeal)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }
}
