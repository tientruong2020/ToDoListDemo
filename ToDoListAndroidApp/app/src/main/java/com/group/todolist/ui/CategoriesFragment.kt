package com.group.todolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.todolist.R
import com.group.todolist.adapter.CategoryAdapter
import com.group.todolist.data.entities.Category
import com.group.todolist.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category.*
@AndroidEntryPoint
class CategoryFragment : Fragment(),View.OnClickListener {

    private val viewmodel by activityViewModels<MainActivityViewModel>()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        initRecyclerView()
    }

    private fun initAction(){
        btn_to_add_category.setOnClickListener(this)
    }
    private fun initRecyclerView(){
        categoryAdapter = CategoryAdapter()
        rcv_category.layoutManager = GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL, false)
        rcv_category.adapter = categoryAdapter
        getAllCategory()

        categoryAdapter.setItemActionListener(object : CategoryAdapter.ItemActionListener{
            override fun clickItem(category: Category) {
                toCategoryWithTasks(category)
            }

            override fun longItemClick(view: View, category: Category) {
                showPopupMenu(view, category)
            }


        })
    }

    private fun toCategoryWithTasks(category: Category) {
        val action = CategoryFragmentDirections.actionCategoryFragmentToCategoryWithTasksFragment(category)
        findNavController().navigate(action)
    }

    private fun showPopupMenu(view: View, category: Category) {
        val popup = PopupMenu(requireContext(),view)
        popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                if (menuItem != null) {
                    when(menuItem.itemId){
                        (R.id.category_delete_item) -> {
                            deleteTask(category)
                            return true
                        }
                        else -> popup.dismiss()
                    }
                }
                return false
            }

        })
        popup.apply {
            this.inflate(R.menu.task_popup_menu)
            this.show()
        }
    }

    private fun deleteTask(category: Category) {
        viewmodel.deleteCategory(category)
        getAllCategory()
    }

    override fun onClick(view: View?) {
        when(view){
            (btn_to_add_category) -> {
                openAddDialog()
            }
        }
    }

    private fun openAddDialog() {
        val addingCategoryFragment = AddingCategoryFragment()
        activity?.let {
            addingCategoryFragment.show(it.supportFragmentManager,AddingCategoryFragment::class.java.name)
        }
        if(addingCategoryFragment.isCancelable){
            getAllCategory()
        }
    }

    private fun getAllCategory(){
        viewmodel.getAllCategories()
        viewmodel.categoryLiveData.observe(viewLifecycleOwner,{
            categoryAdapter.setData(it)
        })
    }
}