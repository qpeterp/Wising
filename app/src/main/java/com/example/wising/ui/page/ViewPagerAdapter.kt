package com.example.wising.ui.page

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wising.R
import com.example.wising.databinding.ItemViewpagerBinding


class ViewPagerAdapter internal constructor(private val listData: ArrayList<DataPage>) :
    RecyclerView.Adapter<ViewHolderPage>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPage {
        val context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_viewpager, parent, false)


        return ViewHolderPage(view)
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        if (holder is ViewHolderPage) {
            val viewHolder: ViewHolderPage = holder
            viewHolder.onBind(listData[position], position)
            Log.d("ViewPagerAdapter", "onBindViewHolder listData ${listData[position].author}")
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}