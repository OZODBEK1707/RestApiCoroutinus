package com.rosh.restapicoroutinus.repository

import com.rosh.restapicoroutinus.models.MyTodoRequest
import com.rosh.restapicoroutinus.retrofit.ApiService

class ToDoRepository(val apiService: ApiService) {
    suspend fun getAllTodo() = apiService.getAllTodo()
    suspend fun addTodo(myTodoRequest: MyTodoRequest) = apiService.addTodo(myTodoRequest)

}