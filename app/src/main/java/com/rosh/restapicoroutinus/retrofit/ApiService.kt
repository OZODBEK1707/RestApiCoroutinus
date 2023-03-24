package com.rosh.restapicoroutinus.retrofit

import com.rosh.restapicoroutinus.models.MyData
import com.rosh.restapicoroutinus.models.MyTodoRequest
import com.rosh.restapicoroutinus.models.MyTodoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("plan")
    suspend fun getAllTodo(): List<MyData>

    @POST("plan/")
    suspend fun addTodo(@Body myTodoRequest: MyTodoRequest): MyTodoResponse

}