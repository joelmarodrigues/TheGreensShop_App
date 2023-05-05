package com.example.thegreensshop_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.thegreensshop_app.R
import com.example.thegreensshop_app.adapters.MainPageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

//Implementing the fragment that contains the ViewPager2 and the BottomNavigationView
//The fragments inflate the layout of the fragment_main.xml and then set the adapter for the ViewPager2.
//Also register a callback to the ViewPager2 to change the selected item of the BottomNavigationView when the page changes.

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        viewPager.adapter = MainPageAdapter(requireActivity())

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.shopFragment -> viewPager.currentItem = 0
                R.id.cartFragment -> viewPager.currentItem = 1
                R.id.ordersFragment -> viewPager.currentItem = 2
                R.id.myAccountFragment -> viewPager.currentItem = 3
            }
            true
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottomNavigationView.menu.findItem(R.id.shopFragment).isChecked = true
                    1 -> bottomNavigationView.menu.findItem(R.id.cartFragment).isChecked = true
                    2 -> bottomNavigationView.menu.findItem(R.id.ordersFragment).isChecked = true
                    3 -> bottomNavigationView.menu.findItem(R.id.myAccountFragment).isChecked = true
                }
            }
        })
    }
}
