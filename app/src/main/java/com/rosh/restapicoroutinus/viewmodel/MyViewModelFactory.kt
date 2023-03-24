package com.rosh.restapicoroutinus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rosh.restapicoroutinus.repository.ToDoRepository

class MyViewModelFactory(val toDoRepository: ToDoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TodoViewModel::class.java)){
            return TodoViewModel(toDoRepository) as T
        }


        throw IllegalArgumentException("Error")
    }

}