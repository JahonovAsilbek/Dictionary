package uz.pdp.dictionary.start.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.pdp.dictionary.start.StartViewPagerFragment

class StartVPAdapter(
    var title: ArrayList<String>,
    var text: ArrayList<String>,
    fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int = title.size

    override fun getItem(position: Int): Fragment {
        return StartViewPagerFragment.newInstance(title[position], text[position])
    }

}