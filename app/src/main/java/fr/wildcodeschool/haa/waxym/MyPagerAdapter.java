package fr.wildcodeschool.haa.waxym;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by tuffery on 22/12/16.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;

    public MyPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION_KEY, position);
        return CalendarFragment.newInstance(bundle);


    }


    @Override
    public int getCount() {
        return Constants.TOTAL_SLIDES;
    }

    @Override
    public int getItemPosition(Object object) {
        CalendarFragment f = (CalendarFragment) object;
        f.updateCalendar(context);
        return super.getItemPosition(object);
    }
}

