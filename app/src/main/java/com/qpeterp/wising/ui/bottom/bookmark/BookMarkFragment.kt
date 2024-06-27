package com.qpeterp.wising.ui.bottom.bookmark

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.qpeterp.wising.R
import com.qpeterp.wising.data.BookMarkData
import com.qpeterp.wising.databinding.FragmentBookMarkBinding

class BookMarkFragment : Fragment() {
    private val binding by lazy { FragmentBookMarkBinding.inflate(layoutInflater) }
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val wisingList = ArrayList<BookMarkData>()

        wisingList.add(BookMarkData("안드로이드 테스트", true))
        wisingList.add(BookMarkData("정신나간 테스트", true))

        if (!wisingList.isEmpty()) {
            adapter = CustomAdapter(wisingList)

            binding.bookmarkRecyclerView.adapter = adapter
            binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(activity)

            adapter.itemClick = object : CustomAdapter.ItemClick {  //클릭이벤트추가부분
                override fun onClick(view: View, position: Int) {
                    Toast.makeText(activity, wisingList[position].content, Toast.LENGTH_SHORT).show()
                    clickWising(wisingList[position].content)
                }
            }
        }
        else {
            Toast.makeText(activity,"저장 명언 없는 거 나중에 뷰에 띄우기" , Toast.LENGTH_SHORT).show()
        }

        binding.bookMarksCount.text = ("${adapter.itemCount}개")

        return binding.root
    }


    private fun clickWising(text : String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_book_mark_detail)


        // Dialog 내부의 View를 참조합니다.
        val contentDetail = dialog.findViewById<TextView>(R.id.contentDetail)

        contentDetail.text = text

        dialog.show()
    }
}