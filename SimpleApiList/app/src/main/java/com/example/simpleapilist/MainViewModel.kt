package com.example.simpleapilist

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val _mealResponseState = mutableStateOf(MealResponse(meals = emptyList()))
    val mealResponseState: State<MealResponse> = _mealResponseState
    val _mealMap = mutableMapOf<String, MutableList<Meal>>()
    val _mealList = mutableStateOf(listOf<Meal>())
    val mealList: State<List<Meal>> = _mealList
    fun fetchMealsByFirstLetter(letter: String) {
        viewModelScope.launch {
            try {
                val response = retrofitService.getMealsByFirstLetter(letter)
                _mealResponseState.value = _mealResponseState.value.copy(
                    meals = response.meals
                )
                addMealsToMap(_mealMap, response)
            } catch (e: Exception) {
                println("Error fetching meals by $letter: " + e.message)
            }

        }
    }

    fun fetchMealByCategory(category: String) {
        _mealList.value = _mealMap[category]?.toList() ?: listOf()
    }
    private fun addMealsToMap(map: MutableMap<String, MutableList<Meal>>, response: MealResponse) {
        for (meal: Meal in response.meals) {
            val children = map.getOrDefault(meal.strCategory, mutableListOf())
            children.add(meal)
            map[meal.strCategory] = children
            println("New length: ${map[meal.strCategory]?.size}")
        }
    }
}