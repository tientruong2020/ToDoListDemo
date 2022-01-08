package com.group.todolist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.group.todolist.R
import com.group.todolist.data.entities.Task
import kotlinx.android.synthetic.main.task_item_layout.view.*
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

class TaskAdapter:RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()

    private var mItemActionListener:ItemActionListener? = null

    fun setItemActionListener(listener: ItemActionListener){
        this.mItemActionListener = listener
    }

    fun setData(taskList:List<Task>){
        this.taskList = taskList
        notifyDataSetChanged()
    }

    fun clearData(){
        this.taskList = emptyList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.setUi(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        fun setUi(task: Task){
            itemView.tw_task_name.text = task.taskName
            itemView.tw_due_date.text = task.dueDate
            if (task.isCompleted == 1){
                itemView.switch1.visibility = View.GONE
                itemView.iw_done.visibility = View.VISIBLE
            }else{
                itemView.switch1.visibility = View.VISIBLE
                itemView.iw_done.visibility = View.GONE
            }
            itemView.btn_delete.setOnClickListener(this)

            itemView.switch1.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(view: CompoundButton?, isChecked: Boolean) {
                    val taskTmp = task.copy()
                    taskTmp.isCompleted = 1
                    mItemActionListener?.checkCompleteSwitch(taskTmp)
                }

            })

        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                val task = taskList[position]
                when(view){
                    (itemView.btn_delete) -> {
                        mItemActionListener?.clickDelete(task)
                    }
                }
            }
        }
    }

    interface ItemActionListener{
        fun clickDelete(task: Task)
        fun checkCompleteSwitch(task: Task)
    }
}