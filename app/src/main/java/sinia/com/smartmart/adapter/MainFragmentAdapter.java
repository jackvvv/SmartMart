package sinia.com.smartmart.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sinia.com.smartmart.base.BaseFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<BaseFragment> fragmentList;

	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public MainFragmentAdapter(FragmentManager fm,
			ArrayList<BaseFragment> fragmentList) {
		super(fm);
		this.fragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
}
