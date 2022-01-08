package com.group.todolist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.group.todolist.ui.CategoryFragment
import com.group.todolist.ui.TaskFragment

class ViewpagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                CategoryFragment()
            }
            1 -> {
                TaskFragment()
            }
            else -> {
                CategoryFragment()
            }
        }
    }

}