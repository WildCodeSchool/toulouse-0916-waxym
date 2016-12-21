package fr.wildcodeschool.haa.waxym;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by tuffery on 14/12/16.
 */

public class MyViewPager extends FragmentPagerAdapter {

    public MyViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = String.valueOf(position%position);
        return title;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("Month",position);
        return CalendarFragment.newInstance(bundle);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //int virtualPosition = position % position;
        Bundle bundle = new Bundle();
        bundle.putInt("Month",position);
        return CalendarFragment.newInstance(bundle);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int virtualPosition = position % position;
        super.destroyItem(container, virtualPosition, object);
    }

    public void setPrimaryItem(int viewPager, int i, Object o) {
        Bundle bundle = new Bundle();
        bundle.putInt("Month",i);
        CalendarFragment.newInstance(bundle);

    }
}
