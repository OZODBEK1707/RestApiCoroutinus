package com.rosh.restapicoroutinus

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.rosh.restapicoroutinus.adapters.TodoAdapter
import com.rosh.restapicoroutinus.databinding.ActivityMainBinding
import com.rosh.restapicoroutinus.databinding.MyDialogBinding
import com.rosh.restapicoroutinus.models.ApiClient
import com.rosh.restapicoroutinus.models.MyData
import com.rosh.restapicoroutinus.models.MyTodoRequest
import com.rosh.restapicoroutinus.repository.ToDoRepository
import com.rosh.restapicoroutinus.utils.Status
import com.rosh.restapicoroutinus.viewmodel.MyViewModelFactory
import com.rosh.restapicoroutinus.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity(), TodoAdapter.RvClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    private  val TAG = "MainActivity"
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var toDoRepository: ToDoRepository
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toDoRepository = ToDoRepository(ApiClient.getApiService())
        todoViewModel = ViewModelProvider(this, MyViewModelFactory(toDoRepository))[TodoViewModel::class.java]
        todoAdapter = TodoAdapter(rvClick = this)
        binding.rv.adapter = todoAdapter


        todoViewModel.getAllTodo()
            .observe(this){
                when(it.status){
                    Status.LOADING -> {
                        Log.d(TAG, "onCreate: Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR->{
                        Log.d(TAG, "onCreate: Error ${it.message}")
                        Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS ->{
                        Log.d(TAG, "onCreate: ${it.data}")
                        todoAdapter.list = it?.data!!
                        todoAdapter.notifyDataSetChanged()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemMyDialogBinding = MyDialogBinding.inflate(layoutInflater)
            dialog.setView(itemMyDialogBinding.root)

            itemMyDialogBinding.apply {
                btnSaveDialog.setOnClickListener {

                    val myTodoRequest = MyTodoRequest(
                        spinnerStatus.selectedItem.toString(),
                        tvTitle.text.toString().trim(),
                        tvText.text.toString().trim(),
                        tvDeadline.text.toString().trim(),
                    )
                    todoViewModel.addMyTodo(myTodoRequest).observe(this@MainActivity){
                        when(it.status){
                            Status.LOADING->{
                                progresBar.visibility = View.VISIBLE
                            }
                            Status.ERROR->{
                                Toast.makeText(
                                    this@MainActivity,
                                    "Xatolik, ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                progresBar.visibility = View.GONE
                            }
                            Status.SUCCESS->{
                                Toast.makeText(this@MainActivity, "${it.data?.sarlavha} ${it.data?.matn} ga saqlandi", Toast.LENGTH_SHORT).show()
                                dialog.cancel()
                            }
                        }
                    }
                }
            }

            dialog.show()
        }
    }

    override fun menuClick(imageView: ImageView, myData: MyData) {
        val popup = PopupMenu(this, imageView)
        popup.inflate(R.menu.todo_menu)

        popup.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.menu_edit->{

                }
                R.id.menu_delete->{

                }
            }
            true
        }
        popup.show()
    }
}