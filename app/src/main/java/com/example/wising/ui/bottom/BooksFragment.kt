package com.example.wising.ui.bottom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wising.databinding.FragmentBooksBinding
import com.example.wising.databinding.ItemViewpagerBinding
import com.example.wising.ui.page.DataPage
import com.example.wising.ui.page.ViewPagerAdapter


class BooksFragment : Fragment() {
    private val binding by lazy { FragmentBooksBinding.inflate(layoutInflater) }
    private val bindingItem by lazy { ItemViewpagerBinding.inflate(layoutInflater) }

    var viewPager2: ViewPager2? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        viewPager2 = binding.viewPager

        val list = ArrayList<DataPage>()
        list.add(DataPage("화면을 위로 쓸어\n삻에 한줄을 추가하세요.\n\\ (,,>_<,,) /", "개발자"))
        list.add(DataPage("\"이 이강현이 짱센 주제에 너무 신중하다\"", "이성은1"))
        list.add(DataPage("\"내 이강현은 역시 잘못 되었다\"", "이성은2"))
        list.add(DataPage("\"흔해빠진 이강현으로 세계최강\"", "이성은3"))
        list.add(DataPage("\"노 이강현 노 라이프\"", "이성은4"))
        list.add(DataPage("\"이 멋진 이강현에게 축복을!\"", "이성은5"))
        list.add(DataPage("\"주술강현\"", "이성은6"))
        list.add(DataPage("\"월간소녀 이강현군\"", "이성은7"))
        list.add(DataPage("\"강현몬스터\"", "이성은8"))
        list.add(DataPage("\"귀멸의 강현\"", "이성은9"))
        list.add(DataPage("\"던전에서 이강현을 추구하면 안되는걸까\"", "이성은10"))
        list.add(DataPage("\"명탐정 강현\"", "이성은11"))
        list.add(DataPage("\"5등분의 강현\"", "이성은12"))
        list.add(DataPage("\"주말의 강현\"", "이성은13"))
        list.add(DataPage("\"강현 아카이브\"", "이성은15"))
        list.add(DataPage("\"옆집 이강현 때문에 인간적으로 타락한 사연\"", "이성은16"))
        list.add(DataPage("\"쏘아올린 이강현, 밑에서 볼까? 옆에서 볼까?\"", "이성은17"))
        list.add(DataPage("\"강현종말여헹\"", "이성은18"))
        list.add(DataPage("\"이강현 컴퍼니\"", "이성은19"))
        list.add(DataPage("\"최애의 강현\"", "이성은20"))
        list.add(DataPage("\"이강현\"", "이성은21"))
        list.add(DataPage("\"너의 강현은\"", "이성은22"))
        list.add(DataPage("\"이강현의 문단속\"", "이성은23"))
        list.add(DataPage("\"내 뇌 속의 이강현이 대소고를 전력으로 방해하고 있다.\"", "이성은24"))
        list.add(DataPage("\"전생했더니 서승훈이었던 건에 대하여\"", "이성은25"))




        viewPager2!!.adapter = ViewPagerAdapter(list)

        viewPager2?.setOrientation(ViewPager2.ORIENTATION_VERTICAL);



        return binding.root
//        return inflater.inflate(R.layout.fragment_books, container, false)
    }


}