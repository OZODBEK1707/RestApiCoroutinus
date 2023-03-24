package com.rosh.restapicoroutinus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosh.restapicoroutinus.models.ApiClient
import com.rosh.restapicoroutinus.models.MyData
import com.rosh.restapicoroutinus.models.MyTodoRequest
import com.rosh.restapicoroutinus.models.MyTodoResponse
import com.rosh.restapicoroutinus.repository.ToDoRepository
import com.rosh.restapicoroutinus.retrofit.ApiService
import com.rosh.restapicoroutinus.utils.Resource
import com.rosh.restapicoroutinus.utils.Status
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TodoViewModel(val repository: ToDoRepository) : ViewModel() {
    private val liveData = MutableLiveData<Resource<List<MyData>>>()


    fun getAllTodo(): MutableLiveData<Resource<List<MyData>>> {
        viewModelScope.launch {
            liveData.postValue(Resource.loading("loading"))
            try {

                coroutineScope {
                    val list: List<MyData> =async {
                        repository.getAllTodo()
                    }.await()

                    liveData.postValue(Resource.success(list))
                }

            }catch (e: Exception){
                liveData.postValue(Resource.error(e.message))
            }
        }
        return liveData
    }
    private val postLiveData = MutableLiveData<Resource<MyTodoResponse>>()
    fun addMyTodo(myTodoRequest: MyTodoRequest): MutableLiveData<Resource<MyTodoResponse>>{
        viewModelScope.launch {
            postLiveData.postValue(Resource.loading("Loading post"))
            try {
                coroutineScope {
                    val response = async {
                        repository.addTodo(myTodoRequest)
                    }.await()
                    postLiveData.postValue(Resource.success(response))
                    getAllTodo()
                }
            }catch (e: Exception){
                postLiveData.postValue(Resource.error(e.message ))
            }
        }
        return postLiveData
    }
}