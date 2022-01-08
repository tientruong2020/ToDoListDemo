package com.group.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.group.todolist.R
import com.group.todolist.data.entities.Category
import kotlinx.android.synthetic.main.layout_category_item.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoriesList = emptyList<Category>()
    private var mItemActionListener: ItemActionListener? = null

    fun setData(list: List<Category>) {
        this.categoriesList = list
        notifyDataSetChanged()
    }

    fun setItemActionListener(itemActionListener: ItemActionListener) {
        this.mItemActionListener = itemActionListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoriesList.get(position)
        holder.setDataToViewHolder(category)
    }

    override fun getItemCount(): Int {
        if (categoriesList.isEmpty()) {
            return 0
        } else {
            return categoriesList.size
        }
    }

    inner class CategoryViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview),
        View.OnClickListener {
        fun setDataToViewHolder(category: Category) {
            itemView.tw_category_name.text = category.name
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener {
                mItemActionListener?.longItemClick(it,category)
                false
            }


        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val category = categoriesList.get(position)
                when (view) {
                    (itemView) -> {
                        mItemActionListener?.clickItem(category)
                    }
                }
            }

        }
    }

    interface ItemActionListener {
        fun clickItem(category: Category)
        fun longItemClick(view: View,category: Category)
    }
}