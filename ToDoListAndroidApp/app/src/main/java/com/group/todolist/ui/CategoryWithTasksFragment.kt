package com.group.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.todolist.R
import com.group.todolist.adapter.TaskAdapter
import com.group.todolist.data.entities.Category
import com.group.todolist.data.entities.Task
import com.group.todolist.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category_with_tasks.*

@AndroidEntryPoint
class CategoryWithTasksFragment : Fragment(), View.OnClickListener {
    private lateinit var category:Category

    private var taskAdapter: TaskAdapter? = null

    private val viewmodel by activityViewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val bundle = CategoryWithTasksFragmentArgs.fromBundle(it)
            category = bundle.categoryObject
            Log.d("category_name", category.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_with_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initAction()
        initRecyclerView()
    }

    private fun initData(){
        tw_category_name.text = category.name
    }

    private fun initAction(){
        floatingActionButton.setOnClickListener(this)
    }

    private fun initRecyclerView(){
        taskAdapter = TaskAdapter()
        rcv_category_with_tasks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewmodel.getTasksInCategory(category.id)
        viewmodel.taskLiveData.observe(viewLifecycleOwner,{categoryWithTask ->
            taskAdapter?.setData(categoryWithTask.tasksList)
        })
        rcv_category_with_tasks.adapter = taskAdapter
        taskAdapter?.setItemActionListener(object : TaskAdapter.ItemActionListener{
            override fun clickDelete(task: Task) {
                viewmodel.deleteTask(task)
            }

            override fun checkCompleteSwitch(task: Task) {
                viewmodel.updateTask(task)
            }

        })
    }

    override fun onClick(view: View?) {
        when(view){
            (floatingActionButton) -> {
                toAddTask()
            }
        }
    }

    private fun toAddTask() {
        val action = CategoryWithTasksFragmentDirections.actionCategoryWithTasksFragmentToAddingTaskFragment(category)
        findNavController().navigate(action)
    }

    override fun onDetach() {
        super.onDetach()
        taskAdapter = null
    }


}