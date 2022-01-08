package com.group.todolist.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.group.todolist.R
import com.group.todolist.data.entities.Category
import com.group.todolist.data.entities.Task
import com.group.todolist.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_adding_task.*
import java.util.*

@AndroidEntryPoint
class AddingTaskFragment : Fragment(), View.OnClickListener {
    private lateinit var category: Category
    private val viewmodel by activityViewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val data = AddingTaskFragmentArgs.fromBundle(it)
            category = data.categoryObject
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adding_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        btn_pick_date_time.setOnClickListener(this)
        btn_add_task.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(view: View?) {
        when (view) {
            (btn_pick_date_time) -> {
                showPickDateTime()
            }
            (btn_add_task) -> {
                addTask()
            }
            (btn_cancel) -> {
                toCategoryWithTaskFragment()
            }
        }
    }

    private fun addTask() {
        val taskName = et_task_name.text.toString()
        val dueDate = et_task_due_date.text.toString()
        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(dueDate)){
            Toast.makeText(requireContext(),"Please!!! Enter full fields", Toast.LENGTH_SHORT).show()
        }
        else{
            val task = Task(0,taskName,dueDate,category.id,0)
            viewmodel.addTask(task)
            toCategoryWithTaskFragment()
        }
    }

    private fun toCategoryWithTaskFragment() {
        val action = AddingTaskFragmentDirections.actionAddingTaskFragmentToCategoryWithTasksFragment(category)
        findNavController().navigate(action)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun showPickDateTime() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(requireContext())
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        datePickerDialog.setOnDateSetListener(object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, mothOfYear: Int, dayOfMonth: Int) {
                calendar.set(year, mothOfYear, dayOfMonth)
                val timeSetListner =
                    TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                        calendar.apply {
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, minute)
                        }
                        et_task_due_date.text = Editable.Factory.getInstance().newEditable(simpleDateFormat.format(calendar.time))
                    }
                TimePickerDialog(
                    requireContext(), timeSetListner, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }

        })
        datePickerDialog.show()
    }
}

