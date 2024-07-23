package com.qpeterp.wising.ui.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qpeterp.wising.R
import com.qpeterp.wising.data.Quote

class ViewPagerAdapter(
    private val quotes: List<Quote>,
    private val userId: String
) :
    RecyclerView.Adapter<ViewHolderPage>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolderPage(
            LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false),
            userId
        )

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        holder.onBind(quotes[position], position)
    }

    override fun getItemCount() = quotes.size
}