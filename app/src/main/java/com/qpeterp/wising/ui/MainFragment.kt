package com.qpeterp.wising.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.databinding.FragmentMainBinding
import com.qpeterp.wising.ui.bottom.qoutes.BooksFragment
import com.qpeterp.wising.ui.bottom.bookmark.BookMarkFragment
import com.qpeterp.wising.ui.bottom.home.HomeFragment
import com.qpeterp.wising.ui.bottom.widget.WidgetFragment

class MainFragment : Fragment() {
    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    // 각 Fragment를 초기화 시점에 생성하여 재사용
    private val homeFragment = HomeFragment()
    private val booksFragment = BooksFragment()
    private val bookMarkFragment = BookMarkFragment()
    private val widgetFragment = WidgetFragment()

    private var currentFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 초기 화면을 HomeFragment로 설정
        changeFragment(homeFragment)

        binding.bottomBar.setOnNavigationItemSelectedListener {
            val selectedFragment = when (it.itemId) {
                R.id.home -> homeFragment
                R.id.books -> booksFragment
                R.id.bookMarks -> bookMarkFragment
                R.id.widget -> widgetFragment
                else -> null
            }

            if (selectedFragment != null && selectedFragment == currentFragment) {
                // 현재 프래그먼트와 선택된 프래그먼트가 동일하면 아무런 동작도 하지 않음
                Log.d(Constant.TAG, "MainFragment onCreateView 중복 클릭")
                return@setOnNavigationItemSelectedListener false
            }

            // 선택된 프래그먼트로 변경
            if (selectedFragment != null) {
                changeFragment(selectedFragment)
            }
            true
        }

        return binding.root
    }

    private fun changeFragment(fragment: Fragment) {
        // 프래그먼트 변경 시 현재 프래그먼트 업데이트
        currentFragment = fragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, fragment).commit()
    }
}
