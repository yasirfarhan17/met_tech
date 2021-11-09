package com.met.tech.ui.splash.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.met.tech.ui.splash.fragments.CategoryFragment

class TabAapter(
    fragmentManger: FragmentManager,
    internal var totalTabs: Int
) : FragmentPagerAdapter(fragmentManger) {

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("position", position)
        val frag: Fragment = CategoryFragment.newInstance(position)
        frag.arguments = bundle
        return frag
    }

    override fun getCount(): Int {
        return totalTabs
    }
}