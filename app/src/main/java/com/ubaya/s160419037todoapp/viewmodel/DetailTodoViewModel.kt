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

class DetailTodoViewModel(application:  Application) :AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val todoLD = MutableLiveData<Todo>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun addTodo(list: List<Todo>) {
        launch {
            val db = buildDb(getApplication())

            // * di depan list artinya merubah list ke beberapa item to do independen untuk masuk ke parameter insertAll sesuai yang di model karena vararg
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }

    fun fetch(uuid: Int){
        launch {
            val db = buildDb(getApplication())
            todoLD.value = db.todoDao().selectTodo(uuid)
        }
    }

    fun update(id:Int, title: String, notes: String, priority: Int){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(id, title, notes,priority)
        }
    }
}