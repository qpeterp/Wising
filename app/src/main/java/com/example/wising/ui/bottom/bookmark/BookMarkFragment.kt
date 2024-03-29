package com.example.wising.ui.bottom.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wising.R
import com.example.wising.data.BookMarkData
import com.example.wising.databinding.FragmentBookMarkBinding

class BookMarkFragment : Fragment() {
    private val binding by lazy { FragmentBookMarkBinding.inflate(layoutInflater) }
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val wisingList = ArrayList<BookMarkData>()

        wisingList.add(BookMarkData("성은성은 테스트", true))
        wisingList.add(BookMarkData("안드로이드 테스트", true))
        wisingList.add(BookMarkData("정신나간 테스트", true))


        if (wisingList.isEmpty()) {
            adapter = CustomAdapter(wisingList)

            binding.bookmarkRecyclerView.adapter = adapter
            binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(activity)

            adapter.itemClick = object : CustomAdapter.ItemClick {  //클릭이벤트추가부분
                override fun onClick(view: View, position: Int) {
                    Toast.makeText(activity, wisingList[position].content, Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {

        }




        return binding.root
    }
}