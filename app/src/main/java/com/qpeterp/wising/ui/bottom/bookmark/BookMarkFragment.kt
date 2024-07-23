package com.qpeterp.wising.ui.bottom.bookmark

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.data.BookMarkData
import com.qpeterp.wising.databinding.FragmentBookMarkBinding
import com.qpeterp.wising.ui.bottom.qoutes.BookmarkManager
import com.qpeterp.wising.utils.shortToast

class BookMarkFragment : Fragment() {
    private val binding by lazy { FragmentBookMarkBinding.inflate(layoutInflater) }
    private lateinit var adapter: CustomAdapter
    private lateinit var bookmarkManager: BookmarkManager
    private val wisingList = ArrayList<BookMarkData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadData()

        return binding.root
    }

    private fun initView() {
        if (wisingList.isNotEmpty()) {
            adapter = CustomAdapter(wisingList)

            binding.bookmarkRecyclerView.adapter = adapter
            binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(activity)

            binding.bookMarksCount.text = "${adapter.itemCount}개"

            adapter.itemClick = object : CustomAdapter.ItemClick {  //클릭이벤트추가부분
                override fun onClick(view: View, position: Int) {
                    Toast.makeText(activity, wisingList[position].quote, Toast.LENGTH_SHORT).show()
                    clickWising(wisingList[position].name, wisingList[position].quote)
                }
            }
        }
        else {
            Toast.makeText(activity,"저장 명언 없는 거 나중에 뷰에 띄우기" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData() {
        val androidId = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
            .getString("androidId", "").toString()
        Log.d(Constant.TAG, "BookMarkFragment onCreateView androidId: $androidId")
        bookmarkManager = BookmarkManager(androidId)

        bookmarkManager.getBookmarks { bookmarks ->
            if (bookmarks.isNotEmpty()) {
                Log.d(Constant.TAG,"북마크 목록: $bookmarks")
                for (quoteId in bookmarks) {
                    bookmarkManager.getQuote(quoteId = quoteId) { quote: BookMarkData? ->
                        if (quote == null) {
                            shortToast("북마크를 불러오지 못했습니다")
                            return@getQuote
                        }
                        Log.d(Constant.TAG,"북마크 명언 데이터: ${quote.name} ${quote.quote}")
                        wisingList.add(BookMarkData(quote.name, quote.quote))
                        initView()
                    }
                }
            } else {
                Log.d(Constant.TAG, "북마크가 없습니다.")
            }
        }
    }


    private fun clickWising(name : String, quote: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_book_mark_detail)

        // Dialog 내부의 View를 참조합니다.
        val contentDetail = dialog.findViewById<TextView>(R.id.contentDetail)

        contentDetail.text = quote

        dialog.show()
    }
}