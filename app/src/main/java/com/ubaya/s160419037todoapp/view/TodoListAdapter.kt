package com.ubaya.s160419037todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.s160419037todoapp.R
import com.ubaya.s160419037todoapp.model.Todo
import kotlinx.android.synthetic.main.layout_item_todo.view.*

class TodoListAdapter(val todoList: ArrayList<Todo>,val adapterOnClick: (Todo) -> Unit): RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
    class TodoListViewHolder(val view:View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_item_todo, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todo = todoList[position]
        with(holder.view) {
            val priority = when(todo.priority){
                1 -> "Low"
                2 -> "Medium"
                else -> "High"
            }
            checkTask.text = "${todo.title} $priority"
            checkTask.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) adapterOnClick(todo)
            }

            buttonEdit.setOnClickListener {
                val action = TodoListFragmentDirections.actionEditTodo(todo.uuid)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = todoList.size

    fun updateTodoList(newTodoList: List<Todo>) {
        //pake arraylist biar bisa di clear bukan pake list biasa
        todoList.clear()

        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }
}
