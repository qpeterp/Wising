package com.qpeterp.wising.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qpeterp.wising.R
import com.qpeterp.wising.databinding.FragmentMainBinding
import com.qpeterp.wising.ui.bottom.BooksFragment
import com.qpeterp.wising.ui.bottom.bookmark.BookMarkFragment
import com.qpeterp.wising.ui.bottom.HomeFragment
import com.qpeterp.wising.ui.bottom.widget.WidgetFragment

class MainFragment : Fragment() {

    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        changeFragment(HomeFragment())

        binding.bottomBar.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> { changeFragment(HomeFragment()) }
                R.id.books -> { changeFragment(BooksFragment()) }
                R.id.bookMarks -> { changeFragment(BookMarkFragment()) }
                R.id.widget -> { changeFragment(WidgetFragment()) }

            }
            true
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun changeFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, fragment).commit()
    }

}