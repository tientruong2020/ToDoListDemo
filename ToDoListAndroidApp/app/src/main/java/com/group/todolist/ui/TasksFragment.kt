package com.group.todolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.todolist.R
import com.group.todolist.adapter.CategoryWithTaskAdapter
import com.group.todolist.adapter.TaskAdapter
import com.group.todolist.data.entities.Category
import com.group.todolist.data.entities.Task
import com.group.todolist.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_task.*

@AndroidEntryPoint
class TaskFragment : Fragment(),View.OnClickListener {

    private lateinit var categoryWithTaskAdapter: CategoryWithTaskAdapter
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAction()
        initRecyclerView()
        fetchDefaultData()
    }

    private fun setAction(){
        btn_get_all_tasks.setOnClickListener(this)
    }
    private fun initRecyclerView(){
        initCategorRCV()
        initTaskRCV()
    }
    private fun fetchDefaultData(){
        getAllTask()
        viewModel.getAllCategories()
    }

    private fun getAllTask(){
        viewModel.getAllTask()
        viewModel.tasksListLiveData.observe(viewLifecycleOwner,{
            taskAdapter.setData(it)
        })
    }

    private fun initTaskRCV() {
        taskAdapter = TaskAdapter()
        rcv_tasks.adapter = taskAdapter
        rcv_tasks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        taskAdapter.setItemActionListener(object : TaskAdapter.ItemActionListener{
            override fun clickDelete(task: Task) {
                viewModel.deleteTask(task)
            }

            override fun checkCompleteSwitch(task: Task) {
                viewModel.updateTask(task)
            }

        })
    }

    private fun initCategorRCV() {
        categoryWithTaskAdapter = CategoryWithTaskAdapter()
        rcv_categories.adapter = categoryWithTaskAdapter
        rcv_categories.layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        viewModel.categoryLiveData.observe(viewLifecycleOwner,{
            categoryWithTaskAdapter.setData(it)
        })
        categoryWithTaskAdapter.setItemActionListener(object : CategoryWithTaskAdapter.ItemActionListener{
            override fun clickItem(category: Category) {
                taskAdapter.clearData()
                viewModel.getTasksInCategory(categoryId = category.id)
                viewModel.taskLiveData.observe(viewLifecycleOwner,{
                    taskAdapter.setData(it.tasksList)
                })
            }

        })

    }

    override fun onClick(view: View?) {
        when(view){
            (btn_get_all_tasks) -> {
                getAllTask()
            }
        }
    }


}