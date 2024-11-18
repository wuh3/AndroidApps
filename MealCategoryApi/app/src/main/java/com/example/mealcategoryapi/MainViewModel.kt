package com.example.mealcategoryapi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _mealResponseState = mutableStateOf(MealResponse(meals = emptyList()))
    val mealResponseState: State<MealResponse> = _mealResponseState

    private var categoryMap: MutableMap<String, MutableList<Meal>> = mutableMapOf()

    private val _filteredListState = mutableStateOf(listOf<Meal>())
    val filteredListState:State<List<Meal>> = _filteredListState


    fun fetchMealByFirstLetter(letter: String) {
        viewModelScope.launch {
            try {
                val response = retrofitService.getMealByFirstLetter(letter)
                _mealResponseState.value = _mealResponseState.value.copy(
                    meals = response.meals
                )
                for (meal:Meal in response.meals) {
                    if (categoryMap.containsKey(meal.strCategory)) {
                        categoryMap[meal.strCategory]?.add(meal)
                    } else {
                        categoryMap[meal.strCategory] = mutableListOf(meal)
                    }
                }
            } catch (e: Exception) {
                _mealResponseState.value = _mealResponseState.value.copy(
                    meals = emptyList()
                )
                println("Error fetching meal: ${e.message}")
            }
        }
    }

    fun searchMealsByCategory(category: String) {
        _filteredListState.value = categoryMap[category]?.toList() ?: listOf()
    }
}