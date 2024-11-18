package com.example.mealcategoryapi

data class Meal (
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String
)

data class MealResponse(val meals: List<Meal>)