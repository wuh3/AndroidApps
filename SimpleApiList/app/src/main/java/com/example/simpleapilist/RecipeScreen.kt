package com.example.simpleapilist

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val mealViewModel: MainViewModel = MainViewModel()
@Composable
fun RecipeScreen() {
    val mealState by mealViewModel.mealResponseState
    var firstLetter by remember { mutableStateOf("a") }
    var category by remember { mutableStateOf("Bread") }
    val meals:List<Meal> = mealState.meals
    val mealListState by mealViewModel.mealList
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp)

    ) {
        Box(modifier = Modifier.fillMaxHeight(0.5f)) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        value = firstLetter,
                        onValueChange = {firstLetter = it},
                        label = { Text("Enter the first letter: ")},
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            mealViewModel.fetchMealsByFirstLetter(firstLetter)

                        }
                    ) {
                        Text("Search")
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
                )
                {
                    items(meals) {
                            meal: Meal ->
                        MealListItem(meal = meal)
                    }
                }
            }

        }
        Box(modifier = Modifier.fillMaxHeight(0.5f)) {
            Column {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.3f),
                        value = category,
                        onValueChange = {category = it},
                        label = { Text("Enter the category: ")},
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            mealViewModel.fetchMealByCategory(category)
                        }
                    ) {
                        Text("Search")
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
                )
                {
                    items(mealListState) {
                            meal: Meal ->
                        MealListItem(meal = meal)
                    }
                }
            }

        }
    }
}

@Composable
fun MealListItem(meal: Meal) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        Text(meal.idMeal)
        Text(meal.strMeal)
        Text(meal.strArea)
        Text(meal.strCategory)
    }
}