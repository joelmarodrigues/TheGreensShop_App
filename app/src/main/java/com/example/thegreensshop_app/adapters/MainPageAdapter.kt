package com.example.thegreensshop_app.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thegreensshop_app.fragments.CartFragment
import com.example.thegreensshop_app.fragments.MyAccountFragment
import com.example.thegreensshop_app.fragments.OrdersFragment
import com.example.thegreensshop_app.fragments.ShopFragment

class MainPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        //Returns the number of fragments to be displayed in the ViewPager2
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        //Returns the fragment associated with a specified position.
        return when (position) {
            0 -> ShopFragment()
            1 -> CartFragment()
            2 -> OrdersFragment()
            3 -> MyAccountFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
