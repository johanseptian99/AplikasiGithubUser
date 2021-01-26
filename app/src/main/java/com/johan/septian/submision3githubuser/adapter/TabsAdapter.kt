package com.johan.septian.submision3githubuser.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.fragment.FollowersFragment
import com.johan.septian.submision3githubuser.fragment.FollowingFragment
import com.johan.septian.submision3githubuser.fragment.RepositoryFragment

class TabsAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_repository, R.string.tab_followers,R.string.tab_following)

    override fun getCount(): Int {
       return 3
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RepositoryFragment()
            1 -> fragment = FollowersFragment()
            2 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}