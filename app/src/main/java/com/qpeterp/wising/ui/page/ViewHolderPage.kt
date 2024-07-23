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
import com.qpeterp.wising.ui.bottom.qoutes.BookmarkManager


class ViewHolderPage internal constructor(
    itemView: View,
    androidId: String
) : RecyclerView.ViewHolder(itemView) {
    private val content: TextView
    private val author: TextView
    private lateinit var qouteId: String
    private var data: Quote? = null
    private var flag = 0
    private var bookMarkChecker = true
    private val bookmarkManager = BookmarkManager(androidId)

    init {
        content = itemView.findViewById(R.id.wisingContent)
        author = itemView.findViewById(R.id.wisingAuthor)

        with(itemView.findViewById<ImageView>(R.id.bookMark)) {
            setOnClickListener {
                bookMarkChecker = if (bookMarkChecker) {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_bookmark_ok
                        )
                    )
                    Log.d(Constant.TAG, "ViewHolderPage init quote id: ${qouteId}")
                    bookmarkManager.addBookmark(qouteId)
                    false
                } else {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_bookmark_not
                        )
                    )
                    bookmarkManager.removeBookmark(qouteId)
                    true
                }
            }
        }

        itemView.findViewById<ImageView>(R.id.copy).setOnClickListener {
            val clipboardManager = itemView.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
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

        if (flag != 0) {
            itemView.findViewById<RiveAnimationView>(R.id.swipeAnimation).visibility = View.GONE
            itemView.findViewById<ImageView>(R.id.copy).visibility = View.VISIBLE
            itemView.findViewById<ImageView>(R.id.share).visibility = View.VISIBLE
            itemView.findViewById<ImageView>(R.id.bookMark).visibility = View.VISIBLE
        }
        else {
            itemView.findViewById<RiveAnimationView>(R.id.swipeAnimation).visibility = View.VISIBLE
            itemView.findViewById<ImageView>(R.id.copy).visibility = View.GONE
            itemView.findViewById<ImageView>(R.id.share).visibility = View.GONE
            itemView.findViewById<ImageView>(R.id.bookMark).visibility = View.GONE
        }
    }
}