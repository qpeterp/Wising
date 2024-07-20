package com.qpeterp.wising.ui.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qpeterp.wising.R
import com.qpeterp.wising.data.Quote

class ViewPagerAdapter(
    private val quotes: List<Quote>,
) :
    RecyclerView.Adapter<ViewHolderPage>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolderPage(
            LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        holder.onBind(quotes[position], position)
    }

    override fun getItemCount() = quotes.size
}