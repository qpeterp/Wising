package com.qpeterp.wising.ui.bottom.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qpeterp.wising.R
import com.qpeterp.wising.data.Quote
import com.qpeterp.wising.databinding.ItemBookmarkBinding
import com.qpeterp.wising.ui.bottom.qoutes.BookmarkManager

class CustomAdapter(private val wisingList : ArrayList<Quote>, androidId: String): RecyclerView.Adapter<CustomAdapter.Holder>() {

    interface ItemClick {  //클릭이벤트추가부분
        fun onClick(view : View, position : Int)
    }
    var itemClick : ItemClick? = null  //클릭이벤트추가부분
    val bookmarkManager = BookmarkManager(androidId)

    inner class Holder(val binding: ItemBookmarkBinding): RecyclerView.ViewHolder(binding.root) {
        val bookmarkContent = binding.itemBookmarkContent
        val bookmark = binding.itemBookmarkCheck
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.Holder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: CustomAdapter.Holder, position: Int) {
        holder.bookmarkContent.text = wisingList[position].quote

        var isBookmarked = true // 초기 상태는 북마크되지 않은 상태로 설정

        holder.bookmark.setOnClickListener {
            isBookmarked = !isBookmarked // 클릭할 때마다 상태 변경

            // 상태에 따라서 이미지 리소스 변경
            if (isBookmarked) {
                holder.bookmark.setImageResource(R.drawable.ic_check_ok)
                bookmarkManager.addBookmark(wisingList[position].id)
            } else {
                holder.bookmark.setImageResource(R.drawable.ic_check_not)
                bookmarkManager.removeBookmark(wisingList[position].id)
            }
        }

        holder.itemView.setOnClickListener {  //클릭이벤트추가부분
            itemClick?.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return wisingList.size
    }
}