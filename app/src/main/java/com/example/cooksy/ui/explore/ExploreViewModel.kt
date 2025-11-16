package com.example.cooksy.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cooksy.data.model.Category
import com.example.cooksy.data.model.Meal
import com.example.cooksy.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    // Areas
    private val _areas = MutableStateFlow<List<String>>(emptyList())
    val areas: StateFlow<List<String>> = _areas

    private val _selectedArea = MutableStateFlow<String?>(null)
    val selectedArea: StateFlow<String?> = _selectedArea

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCategories()
        loadAreas()
        loadMeals()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getCategories()
                _categories.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadMeals(category: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _meals.value = repository.getMeals(
                    category = _selectedCategory.value,
                    area = _selectedArea.value
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadAreas() = viewModelScope.launch {
        _isLoading.value = true
        try {
            _areas.value = repository.getAreas()
        } catch (e: Exception) {
            _errorMessage.value = e.message
        } finally {
            _isLoading.value = false
        }
    }

    fun selectCategory(categoryName: String?) {
        _selectedCategory.value = if (_selectedCategory.value == categoryName) null else categoryName
        loadMeals(_selectedCategory.value)
    }

    fun selectArea(areaName: String?) {
        _selectedArea.value = if (_selectedArea.value == areaName) null else areaName
        loadMeals()
    }
}
