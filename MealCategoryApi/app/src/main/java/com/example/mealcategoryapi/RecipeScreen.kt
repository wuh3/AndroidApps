package com.example.mealcategoryapi

import android.widget.Space
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
val recipeViewModel = MainViewModel()
@Composable
fun RecipeScreen() {
    val mealState by recipeViewModel.mealResponseState
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            MealScreen(mealState.meals)
            Spacer(modifier = Modifier.height(16.dp))
            SearchScreen()
        }

    }
}

@Composable
fun MealScreen(meals: List<Meal>) {
    Column(modifier = Modifier.fillMaxHeight(0.5f),horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            recipeViewModel.fetchMealByFirstLetter("a")
        }) {
            Text("Fetch")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {
            items(meals) {
                    item: Meal -> MealListItem(meal = item)
            }
        }
    }

}

@Composable
fun SearchScreen() {
    var category by remember { mutableStateOf("Dessert") }
    val filteredList by recipeViewModel.filteredListState
    Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(0.8f),horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.4f),
                value = category,
                onValueChange = {category = it},
                label = {Text("category" )},
            )
            Button(
                onClick = {recipeViewModel.searchMealsByCategory(category)}
            ) {Text("Filter") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {
            items(filteredList) {
                    item: Meal -> MealListItem(meal = item)
            }
        }
    }
}

@Composable
fun MealListItem(meal:Meal) {
    Column {
        Text(meal.idMeal, Modifier.padding(8.dp))
        Text(meal.strMeal, Modifier.padding(8.dp))
        Text(meal.strArea, Modifier.padding(8.dp))
        Text(meal.strCategory, Modifier.padding(8.dp))
    }
}