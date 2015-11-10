package edu.chc.appdev.teama.gradekeeper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Glenn on 9/11/2015.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter
{
    private Context context;

    private ArrayList<ITabbedFragment> tabFragments;

    public TabFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.tabFragments = new ArrayList<ITabbedFragment>();
    }

    public void addTabFragment(ITabbedFragment fragment)
    {
        this.tabFragments.add(fragment);
    }

    @Override
    public int getCount()
    {
        return this.tabFragments.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        return (Fragment) this.tabFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.tabFragments.get(position).getName();
    }
}