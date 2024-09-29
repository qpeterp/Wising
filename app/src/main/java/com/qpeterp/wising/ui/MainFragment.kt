package com.qpeterp.wising.ui

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.qpeterp.wising.R
import com.qpeterp.wising.common.Constant
import com.qpeterp.wising.databinding.FragmentMainBinding
import com.qpeterp.wising.ui.base.BaseFragment
import com.qpeterp.wising.ui.main.qoutes.BooksFragment
import com.qpeterp.wising.ui.main.bookmark.BookMarkFragment
import com.qpeterp.wising.ui.main.home.HomeFragment
import com.qpeterp.wising.ui.main.home.HomeViewModel
import com.qpeterp.wising.ui.main.home.HomeViewModelFactory
import com.qpeterp.wising.ui.main.widget.WidgetFragment

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    // 각 Fragment를 초기화 시점에 생성하여 재사용
    private val homeFragment = HomeFragment()
    private val booksFragment = BooksFragment()
    private val bookMarkFragment = BookMarkFragment()
    private val widgetFragment = WidgetFragment()

    private lateinit var homeViewModel: HomeViewModel

    private var currentFragment: Fragment? = null

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackPressed()
        }
        changeFragment(homeFragment)

        val sharedPreferences =
            requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        homeViewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(sharedPreferences)).get(HomeViewModel::class.java)

        binding.bottomBar.setOnNavigationItemSelectedListener {
            val selectedFragment = when (it.itemId) {
                R.id.home -> homeFragment
                R.id.books -> booksFragment
                R.id.bookMarks -> bookMarkFragment
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

        // 현재 프래그먼트가 null이 아니고 보여져 있다면 숨김 처리
        currentFragment?.let {
            fragmentTransaction.hide(it)
        }

        // 해당 프래그먼트가 이미 추가된 경우 보여주기, 아니면 추가하기
        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.add(R.id.mainContent, fragment)
        }

        // 프래그먼트 변경 시 현재 프래그먼트 업데이트
        currentFragment = fragment
        fragmentTransaction.commit()
    }

    private fun handleBackPressed() {
        Log.d(Constant.TAG, "MainFragment handleBackPressed: 뒤로가기 클릭")
        requireActivity().finish()
    }
}
