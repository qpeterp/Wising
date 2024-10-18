package com.qpeterp.wising.ui

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.activity.addCallback
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.databinding.FragmentMainBinding
import com.qpeterp.wising.ui.base.BaseFragment
import com.qpeterp.wising.ui.main.qoutes.BooksFragment
import com.qpeterp.wising.ui.main.bookmark.BookMarkFragment
import com.qpeterp.wising.ui.main.home.HomeFragment
import com.qpeterp.wising.ui.main.widget.WidgetFragment

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    // 각 Fragment를 초기화 시점에 생성하여 재사용
    private val homeFragment = HomeFragment()
    private val booksFragment = BooksFragment()
    private val widgetFragment = WidgetFragment()

    private var currentFragment: Fragment? = null

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackPressed()
        }
        changeFragment(homeFragment)

        binding.bottomBar.setOnNavigationItemSelectedListener {
            val selectedFragment = when (it.itemId) {
                R.id.home -> homeFragment
                R.id.books -> booksFragment
                R.id.bookMarks -> BookMarkFragment()
                R.id.widget -> widgetFragment
                else -> return@setOnNavigationItemSelectedListener false
            }

            if (selectedFragment == currentFragment) {
                // 현재 프래그먼트와 선택된 프래그먼트가 동일하면 아무런 동작도 하지 않음
                Log.d(Constant.TAG, "MainFragment onCreateView 중복 클릭")
                return@setOnNavigationItemSelectedListener false
            }

            // 선택된 프래그먼트로 변경
            changeFragment(selectedFragment)
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainContent, fragment)

        currentFragment = fragment
        fragmentTransaction.commit()
    }

    private fun handleBackPressed() {
        requireActivity().finish()
    }
}
