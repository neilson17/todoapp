package com.ubaya.s160419037todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubaya.s160419037todoapp.model.Todo
import com.ubaya.s160419037todoapp.model.TodoDatabase
import com.ubaya.s160419037todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TodoListViewModel(application: Application) :AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    // harus di override (coroutineContext itu sperti properti kyk getter setter) ditambahkan jobnya kita
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false

        // launch ini akan dikerjakan di thread terpisah karena sudah dibuatkan thread sendiri
        launch {
            val db = buildDb(getApplication())

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    // Untuk menghapus sebuah item to do dari database
    fun clearTask(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun updateIsDone(uuid: Int, is_done: Int){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().updateIsDone(uuid, is_done)

            todoLD.value = db.todoDao().selectAllTodo()
        }
    }
}