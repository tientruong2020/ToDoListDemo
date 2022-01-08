package com.group.todolist.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.group.todolist.R
import com.group.todolist.data.entities.Category
import com.group.todolist.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_adding_category.*

@AndroidEntryPoint
class AddingCategoryFragment : DialogFragment(), View.OnClickListener {

    private val viewModel by activityViewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adding_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        btn_add_category.setOnClickListener(this)
        btn_cancel_dialog.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            (btn_add_category) -> {
                addCategory()
            }
            (btn_cancel_dialog) -> {
                this.dismiss()
            }
        }
    }

    private fun addCategory() {
       val categoryName = text_input.text.toString()
        if(!TextUtils.isEmpty(categoryName)){
            val category = Category(0,categoryName)
            viewModel.addCategory(category)
            this.dismiss()
        }
        else{
            Toast.makeText(requireContext(),R.string.empty_category_field_notification,Toast.LENGTH_SHORT).show()
        }
    }
}