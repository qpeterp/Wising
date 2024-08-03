package com.qpeterp.wising.ui.bottom.bookmark

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.data.BookMarkData
import com.qpeterp.wising.data.Quote
import com.qpeterp.wising.databinding.FragmentBookMarkBinding
import com.qpeterp.wising.ui.bottom.qoutes.BookmarkManager
import com.qpeterp.wising.utils.shortToast

class BookMarkFragment : Fragment() {
    private val binding by lazy { FragmentBookMarkBinding.inflate(layoutInflater) }
    private lateinit var adapter: CustomAdapter
    private lateinit var bookmarkManager: BookmarkManager
    private val wisingList = ArrayList<Quote>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun initView() {
        // Check if the fragment is attached to the activity
        if (!isAdded) return

        adapter = CustomAdapter(wisingList, requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE).getString("androidId", "").toString())

        binding.bookmarkRecyclerView.adapter = adapter
        binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.bookMarksCount.text = "${adapter.itemCount}개"

        adapter.itemClick = object : CustomAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(activity, wisingList[position].quote, Toast.LENGTH_SHORT).show()
                clickWising(wisingList[position].name, wisingList[position].quote)
            }
        }
    }

    private fun loadData() {
        val androidId = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
            .getString("androidId", "").toString()
        Log.d(Constant.TAG, "BookMarkFragment onCreateView androidId: $androidId")
        bookmarkManager = BookmarkManager(androidId)

        bookmarkManager.getBookmarks { bookmarks ->
            if (bookmarks.isNotEmpty()) {
                Log.d(Constant.TAG, "북마크 목록: $bookmarks")
                for (quoteId in bookmarks) {
                    bookmarkManager.getQuotes(quoteId = quoteId) { quote: BookMarkData? ->
                        activity?.runOnUiThread {
                            if (quote == null) {
                                shortToast("북마크를 불러오지 못했습니다")
                                return@runOnUiThread
                            }
                            Log.d(Constant.TAG, "북마크 명언 데이터: ${quote.name} ${quote.quote}")
                            wisingList.add(Quote(id = quoteId, name = quote.name, quote = quote.quote))

                            // Update the UI only when the fragment is attached
                            if (wisingList.size == bookmarks.size) {
                                initView() // 모든 데이터 로드 후에 한 번만 호출
                            }
                        }
                    }
                }
            } else {
                Log.d(Constant.TAG, "북마크가 없습니다.")
            }
        }
    }

    private fun clickWising(name: String, quote: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_book_mark_detail)

        // Dialog 내부의 View를 참조합니다.
        val contentDetail = dialog.findViewById<TextView>(R.id.contentDetail)
        contentDetail.text = quote

        val authorDetail = dialog.findViewById<TextView>(R.id.authorDetail)
        authorDetail.text = name

        dialog.findViewById<ImageView>(R.id.share).setOnClickListener {
            share(name, quote)
        }

        dialog.findViewById<ImageView>(R.id.copy).setOnClickListener {
            copy(quote)
        }

        dialog.show()
    }

    private fun share(author: String, content: String) {
        Log.d(Constant.TAG, "BookMarkDialogFragment share is run")
        // 공유할 텍스트 생성
        val shareText = "$content - $author"
        Log.d(Constant.TAG, "BookMarkDialogFragment shareText is $shareText")

        // 공유 인텐트 생성
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        // 인텐트 선택기를 통해 공유하기
        val chooser = Intent.createChooser(shareIntent, "공유하기")
        requireContext().startActivity(chooser)
    }

    private fun copy(content: String) {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clipData = ClipData.newPlainText("WiSing", content)
        clipboardManager?.setPrimaryClip(clipData)
    }
}