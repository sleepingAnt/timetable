package com.ant.timetable;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private String a2;
	private TextView textView1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		textView1 = (TextView)findViewById(R.id.tv_course_name); 
		textView1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//startActivity(new Intent(MainActivity.this,DetailEdit.class));
			}
		});
		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(getWeek());
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {

			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (arg0 == 0) {
					mViewPager.setCurrentItem(8, false);
				} else if (arg0 == 8) {
					mViewPager.setCurrentItem(1, false);
				}
			}

			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	/**
	 * 获取当天是星期几
	 * @return week 当天的星期
	 */
	private int getWeek() {
		SimpleDateFormat dateformat2 = new SimpleDateFormat("EEE");
		a2 = dateformat2.format(new Date());
		Log.i("my", a2);
		int week = 1;
		if (a2.equals("周二")) {
			week = 2;
		} else if (a2.equals("周三")) {
			week = 3;
		} else if (a2.equals("周四")) {
			week = 4;
		} else if (a2.equals("周五")) {
			week = 5;
		} else if (a2.equals("周六")) {
			week = 6;
		} else if (a2.equals("周日")) {
			week = 7;
		}
		return week;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section7).toUpperCase();
			case 1:
				return getString(R.string.title_section1).toUpperCase();
			case 2:
				return getString(R.string.title_section2).toUpperCase();
			case 3:
				return getString(R.string.title_section3).toUpperCase();
			case 4:
				return getString(R.string.title_section4).toUpperCase();
			case 5:
				return getString(R.string.title_section5).toUpperCase();
			case 6:
				return getString(R.string.title_section6).toUpperCase();
			case 7:
				return getString(R.string.title_section7).toUpperCase();
			case 8:
				return getString(R.string.title_section1).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		public DummySectionFragment() {
		}

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.table_main, null);
			/*
			 * TextView textView = new TextView(getActivity());
			 * textView.setGravity(Gravity.CENTER); Bundle args =
			 * getArguments();
			 * textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER
			 * )));
			 */
			return view;
		}
	}
}
