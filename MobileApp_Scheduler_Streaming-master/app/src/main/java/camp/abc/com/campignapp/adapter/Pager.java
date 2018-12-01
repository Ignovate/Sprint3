package camp.abc.com.campignapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import camp.abc.com.campignapp.banner.BannerFive;
import camp.abc.com.campignapp.banner.BannerFour;
import camp.abc.com.campignapp.banner.BannerOne;
import camp.abc.com.campignapp.banner.BannerThree;
import camp.abc.com.campignapp.banner.BannerTwo;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                BannerOne tab1 = new BannerOne();
                return tab1;
            case 1:
                BannerTwo tab2 = new BannerTwo();
                return tab2;
            case 2:
               BannerThree tab3 = new BannerThree();
                return tab3;
            case 3:
                BannerFour tab4 = new BannerFour();
                return tab4;
            case 4:
                BannerFive tab5 = new BannerFive();
                return tab5;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}