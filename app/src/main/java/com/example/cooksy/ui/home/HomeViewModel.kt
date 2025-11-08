package com.example.cooksy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooksy.data.model.Meal
import com.example.cooksy.data.model.Category
import com.example.cooksy.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    // -----------------------------
    // UI State
    // -----------------------------
    private val _popularMeals = MutableStateFlow<List<Meal>>(emptyList())
    val popularMeals: StateFlow<List<Meal>> = _popularMeals.asStateFlow()

    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal: StateFlow<Meal?> = _randomMeal.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _areas = MutableStateFlow<List<String>>(emptyList())
    val areas: StateFlow<List<String>> = _areas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // -----------------------------
    // Initialization
    // -----------------------------
    init {
        loadHomeData()
    }

    // -----------------------------
    // Load data for Home screen
    // -----------------------------
    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val popular = repository.getPopularMeals()
                val random = repository.getRandomMeal()
                val categories = repository.getCategories()
                val areas = repository.getAreas()

                _popularMeals.value = popular
                _randomMeal.value = random
                _categories.value = categories
                _areas.value = areas
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // -----------------------------
    // Optional: Refresh random meal
    // -----------------------------
    fun refreshRandomMeal() {
        viewModelScope.launch {
            try {
                _randomMeal.value = repository.getRandomMeal()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to load random meal"
            }
        }
    }

    // -----------------------------
    // Filter meals by category or area (if needed)
    // -----------------------------
    suspend fun getMeals(category: String? = null, area: String? = null): List<Meal> {
        return repository.getMeals(category, area)
    }
}
