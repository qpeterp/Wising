package com.example.wising.ui.page // <- 파일 이름 같을 때, 충돌방지 & 관리 용이

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wising.R


class ViewHolderPage internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val content: TextView
    private val author: TextView
    var data: DataPage? = null

    var bookMarkChecker = true


    init {
        content = itemView.findViewById(R.id.content)
        author = itemView.findViewById(R.id.author)

        with(itemView.findViewById<ImageView>(R.id.bookMark)) {
            setOnClickListener {
                bookMarkChecker = if (bookMarkChecker) {
                    setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_bookmark_ok))
                    Log.d("ViewHolderPage", "bookmark click")
                    false
                } else {
                    setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_bookmark_not))
                    Log.d("ViewHolderPage", "bookmark click")

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

    fun onBind(data: DataPage) {
        this.data = data
        content.text = data.content
        author.text = data.author

    }
}