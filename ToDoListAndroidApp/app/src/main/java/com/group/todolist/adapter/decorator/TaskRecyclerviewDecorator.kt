package com.group.todolist.adapter.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TaskRecyclerviewDecorator(
    private val sidePadding: Float, private val topPadding: Float
) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = topPadding.toInt()
        outRect.top = topPadding.toInt()

        outRect.left = sidePadding.toInt()
        outRect.right = sidePadding.toInt()

    }
}