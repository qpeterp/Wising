package com.qpeterp.wising.ui.page

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.rive.runtime.kotlin.RiveAnimationView
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.data.Quote
import com.qpeterp.wising.ui.main.qoutes.BookmarkManager

class ViewHolderPage internal constructor(
    itemView: View,
    androidId: String
) : RecyclerView.ViewHolder(itemView) {
    private val content: TextView by lazy { itemView.findViewById(R.id.wisingContent) }
    private val author: TextView = itemView.findViewById(R.id.wisingAuthor)
    private lateinit var qouteId: String
    private var data: Quote? = null
    private var flag = 0
    private var bookMarkChecker = true
    private val bookmarkManager = BookmarkManager(androidId)

    init {
        with(itemView.findViewById<ImageView>(R.id.bookMark)) {
            setOnClickListener {
                Log.d(Constant.TAG, "ViewHolderPage init quote id: ${qouteId}")
                val icon = if (bookMarkChecker) R.drawable.ic_check_ok else R.drawable.ic_check_not
                setImageDrawable(ContextCompat.getDrawable(itemView.context, icon))
                if (bookMarkChecker) {
                    bookmarkManager.addBookmark(qouteId)
                } else {
                    bookmarkManager.removeBookmark(qouteId)
                }
                bookMarkChecker = !bookMarkChecker
            }
        }

        itemView.findViewById<ImageView>(R.id.copy).setOnClickListener {
            val clipboardManager =
                itemView.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clipData = ClipData.newPlainText("WiSing", content.text)
            clipboardManager?.setPrimaryClip(clipData)
        }

        itemView.findViewById<ImageView>(R.id.share).setOnClickListener {
            // 공유할 텍스트 생성
            val shareText = "${content.text} - ${author.text}"
            Log.d("viewHolderPage", shareText)
            // 공유 인텐트 생성
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            // 인텐트 선택기를 통해 공유하기
            val chooser = Intent.createChooser(shareIntent, "공유하기")
            itemView.context.startActivity(chooser)
        }
    }

    fun onBind(data: Quote, position: Int) {
        this.data = data

        qouteId = data.id
        Log.d(Constant.TAG, "ViewHolderPage onBind quote id: ${qouteId}")
        content.text = data.quote
        author.text = data.name

        this.flag = position

        val animationVisibility = if (flag == 0) View.VISIBLE else View.GONE
        val visibility = if (flag == 0) View.GONE else View.VISIBLE
        itemView.findViewById<RiveAnimationView>(R.id.swipeAnimation).visibility = animationVisibility
        itemView.findViewById<ImageView>(R.id.copy).visibility = visibility
        itemView.findViewById<ImageView>(R.id.share).visibility = visibility
        itemView.findViewById<ImageView>(R.id.bookMark).visibility = visibility
    }
}