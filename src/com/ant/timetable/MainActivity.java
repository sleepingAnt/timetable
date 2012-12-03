package com.ant.timetable;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.timetable.db.MainDB;

public class MainActivity extends FragmentActivity {
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private String a2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(getWeek() + 7 * 100);
		mViewPager.setHorizontalScrollBarEnabled(true);
//		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//			public void onPageSelected(int arg0) {
//			}
//
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				/*
//				 * if (arg0 == 0) { mViewPager.setCurrentItem(8, false); } else
//				 * if (arg0 == 8) { mViewPager.setCurrentItem(1, false); }
//				 */
//			}
//
//			public void onPageScrollStateChanged(int arg0) {
//
//			}
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(1, Menu.FIRST, 1, "清空课程表").setIcon(
				android.R.drawable.ic_menu_delete); // 3,显式指定菜单项的组号，ID , 排序，标题
		menu.add(1, Menu.FIRST + 1, 2, "清空缓存").setIcon(
				android.R.drawable.ic_menu_delete);
		menu.add(2, Menu.FIRST + 2, 3, "关于").setIcon(
				android.R.drawable.ic_menu_crop);
		menu.add(2, Menu.FIRST + 3, 4, "退出").setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		MainDB mainDB = new MainDB(this);
		boolean result;
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case 1:
			result = mainDB.deleteAllCourse();
			if(result) {
				mViewPager.getAdapter().notifyDataSetChanged();
				Toast.makeText(this, "成功清空所有课程数据", Toast.LENGTH_LONG).show();
			}
			break;
		case 2:
			result = mainDB.deleteAllCourseName();
			if(result) {
				Toast.makeText(this, "成功清空所有缓存的课程名称数据", Toast.LENGTH_LONG).show();
			}
			break;
		case 3:
			startActivity(new Intent(MainActivity.this,About.class));
			break;
		case 4:
			MainActivity.this.finish();
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			refresh();
		}
	}

	public void refresh() {
		mViewPager.getAdapter().notifyDataSetChanged();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_WEEK_NUMBER, i % 7);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			int realPosition = position % 7;
			switch (realPosition) {
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
		public static final String ARG_WEEK_NUMBER = "week_number";
		private MainDB myMainDB;
		Bundle args;
		int week;
		private View tvCourseName1;
		private View tvCourseName2;
		private View tvCourseName3;
		private View tvCourseName4;
		private View tvCourseName5;
		private View tvCourseName6;

		private TextView tv_course_m_1;
		private TextView tv_course_m_2;
		private TextView tv_course_noon_1;
		private TextView tv_course_noon_2;
		private TextView tv_course_night_1;
		private TextView tv_course_night_2;

		private TextView tv_classroom_m_1;
		private TextView tv_classroom_m_2;
		private TextView tv_classroom_noon_1;
		private TextView tv_classroom_noon_2;
		private TextView tv_classroom_night_1;
		private TextView tv_classroom_night_2;

		private TextView tv_teacher_m_1;
		private TextView tv_teacher_m_2;
		private TextView tv_teacher_noon_1;
		private TextView tv_teacher_noon_2;
		private TextView tv_teacher_night_1;
		private TextView tv_teacher_night_2;

		public DummySectionFragment() {
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			myMainDB = new MainDB(getActivity());
			myMainDB.getWritableDatabase();
			tvCourseName1 = (View) getView().findViewById(
					R.id.detail_content_morning_1);
			tvCourseName2 = (View) getView().findViewById(
					R.id.detail_content_morning_2);
			tvCourseName3 = (View) getView().findViewById(
					R.id.detail_content_noon_1);
			tvCourseName4 = (View) getView().findViewById(
					R.id.detail_content_noon_2);
			tvCourseName5 = (View) getView().findViewById(
					R.id.detail_content_night_1);
			tvCourseName6 = (View) getView().findViewById(
					R.id.detail_content_night_2);

			tv_course_m_1 = (TextView) getView().findViewById(
					R.id.tv_course_m_1);
			tv_course_m_2 = (TextView) getView().findViewById(
					R.id.tv_course_m_2);
			tv_course_noon_1 = (TextView) getView().findViewById(
					R.id.tv_course_noon_1);
			tv_course_noon_2 = (TextView) getView().findViewById(
					R.id.tv_course_noon_2);
			tv_course_night_1 = (TextView) getView().findViewById(
					R.id.tv_course_night_1);
			tv_course_night_2 = (TextView) getView().findViewById(
					R.id.tv_course_night_2);

			tv_classroom_m_1 = (TextView) getView().findViewById(
					R.id.tv_classroom_m_1);
			tv_classroom_m_2 = (TextView) getView().findViewById(
					R.id.tv_classroom_m_2);
			tv_classroom_noon_1 = (TextView) getView().findViewById(
					R.id.tv_classroom_noon_1);
			tv_classroom_noon_2 = (TextView) getView().findViewById(
					R.id.tv_classroom_noon_2);
			tv_classroom_night_1 = (TextView) getView().findViewById(
					R.id.tv_classroom_night_1);
			tv_classroom_night_2 = (TextView) getView().findViewById(
					R.id.tv_classroom_night_2);

			tv_teacher_m_1 = (TextView) getView().findViewById(
					R.id.tv_teacher_m_1);
			tv_teacher_m_2 = (TextView) getView().findViewById(
					R.id.tv_teacher_m_2);
			tv_teacher_noon_1 = (TextView) getView().findViewById(
					R.id.tv_teacher_noon_1);
			tv_teacher_noon_2 = (TextView) getView().findViewById(
					R.id.tv_teacher_noon_2);
			tv_teacher_night_1 = (TextView) getView().findViewById(
					R.id.tv_teacher_night_1);
			tv_teacher_night_2 = (TextView) getView().findViewById(
					R.id.tv_teacher_night_2);

			tv_course_m_1
					.setText(myMainDB.queryCourse(week, 1).getCourseName());
			tv_course_m_2
					.setText(myMainDB.queryCourse(week, 2).getCourseName());
			tv_course_noon_1.setText(myMainDB.queryCourse(week, 3)
					.getCourseName());
			tv_course_noon_2.setText(myMainDB.queryCourse(week, 4)
					.getCourseName());
			tv_course_night_1.setText(myMainDB.queryCourse(week, 5)
					.getCourseName());
			tv_course_night_2.setText(myMainDB.queryCourse(week, 6)
					.getCourseName());

			tv_classroom_m_1.setText(myMainDB.queryCourse(week, 1)
					.getClassroom());
			tv_classroom_m_2.setText(myMainDB.queryCourse(week, 2)
					.getClassroom());
			tv_classroom_noon_1.setText(myMainDB.queryCourse(week, 3)
					.getClassroom());
			tv_classroom_noon_2.setText(myMainDB.queryCourse(week, 4)
					.getClassroom());
			tv_classroom_night_1.setText(myMainDB.queryCourse(week, 5)
					.getClassroom());
			tv_classroom_night_2.setText(myMainDB.queryCourse(week, 6)
					.getClassroom());

			tv_teacher_m_1.setText(myMainDB.queryCourse(week, 1).getTeacher());
			tv_teacher_m_2.setText(myMainDB.queryCourse(week, 2).getTeacher());
			tv_teacher_noon_1.setText(myMainDB.queryCourse(week, 3)
					.getTeacher());
			tv_teacher_noon_2.setText(myMainDB.queryCourse(week, 4)
					.getTeacher());
			tv_teacher_night_1.setText(myMainDB.queryCourse(week, 5)
					.getTeacher());
			tv_teacher_night_2.setText(myMainDB.queryCourse(week, 6)
					.getTeacher());

			tvCourseName1.setOnClickListener(detailEditerListener);
			tvCourseName2.setOnClickListener(detailEditerListener);
			tvCourseName3.setOnClickListener(detailEditerListener);
			tvCourseName4.setOnClickListener(detailEditerListener);
			tvCourseName5.setOnClickListener(detailEditerListener);
			tvCourseName6.setOnClickListener(detailEditerListener);
		}

		OnClickListener detailEditerListener = new OnClickListener() {
			public void onClick(View v) {
				int s = 0;
				if (v.getId() == R.id.detail_content_morning_1) {
					s = 1;
				} else if (v.getId() == R.id.detail_content_morning_2) {
					s = 2;
				} else if (v.getId() == R.id.detail_content_noon_1) {
					s = 3;
				} else if (v.getId() == R.id.detail_content_noon_2) {
					s = 4;
				} else if (v.getId() == R.id.detail_content_night_1) {
					s = 5;
				} else if (v.getId() == R.id.detail_content_night_2) {
					s = 6;
				}
				// Log.i("my", "第几节？"+s);
				Intent intent = new Intent();
				intent.putExtra("week", week);
				intent.putExtra("section", s);
				intent.setClass(getActivity(), DetailEdit.class);
				startActivityForResult(intent, 0);
			}
		};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.table_main, container, false);
			args = getArguments();
			week = args.getInt(ARG_WEEK_NUMBER);
			// tvCourseName.getText().toString();
			/*
			 * TextView textView = new TextView(getActivity());
			 * textView.setGravity(Gravity.CENTER); Bundle args =
			 * getArguments();
			 * textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER
			 * )));
			 */
			return v;
		}
	}

	/**
	 * 获取当天是星期几
	 * 
	 * @return week 当天的星期
	 */
	private int getWeek() {
		SimpleDateFormat dateformat2 = new SimpleDateFormat("EEE");
		a2 = dateformat2.format(new Date());
		// Log.i("my", a2);
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
}
