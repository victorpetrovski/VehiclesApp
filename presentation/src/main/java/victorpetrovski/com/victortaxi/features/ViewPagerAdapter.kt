package victorpetrovski.com.victortaxi.features

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import victorpetrovski.com.victortaxi.base.BaseFragment


class ViewPagerAdapter constructor(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    var mFragmentList = mutableListOf<BaseFragment>()

    override fun getItem(position: Int) = mFragmentList[position]

    override fun getCount() = mFragmentList.size


    fun addFragment(fragment: BaseFragment) {
        mFragmentList.add(fragment)
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val ret = super.instantiateItem(container, position)
        mFragmentList[position] = ret as BaseFragment
        return ret
    }
}