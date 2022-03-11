package com.example.shoppinglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ShoppingListViewModel: ViewModel() {
    var items = mutableStateOf(listOf<Item>())

    fun addItem(item: Item){
        var newItems = items.value.toMutableList()
        newItems.add(item)
        items.value = newItems
    }

}