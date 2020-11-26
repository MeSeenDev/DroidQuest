package ru.meseen.droidquest.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.meseen.droidquest.data.Quests

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var listData: List<Quests> = listOf()

    fun setData(data: List<Quests>) {
        this.listData = data
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerFragment.getInstance(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}