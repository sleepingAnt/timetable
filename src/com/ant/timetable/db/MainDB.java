package com.ant.timetable.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MainDB extends SQLiteOpenHelper {
	private static final String dbName = "timetable.db";

	private SQLiteDatabase db;
	public MainDB(Context context) {
		super(context, dbName, null, 1);
	}

	/**
	 * 添加课程信息
	 * 
	 * @param week
	 *            第几周
	 * @param section
	 *            第几节
	 * @param name
	 *            课程名字
	 * @param room
	 *            上课教室
	 * @param teacher
	 *            上课教师
	 * @return 成功则返回true
	 */
	public boolean addCourse(int week, int section, String courseName,
			String classroom, String teacher) {
		boolean result = false;
		try {
			db = this.getWritableDatabase();
			String sql = String
					.format("insert into table_course (t_week,t_section,course_name,course_classroom,course_teacher)"
							+ " values (%d,%d,'%s','%s','%s')", week, section,
							courseName, classroom, teacher);
			db.execSQL(sql);
			closeDB();
			result = true;
		} catch (Exception e) {
			closeDB();
		}
		return result;

	}

	/**
	 * 添加课程信息（用于编辑课程表时的自动提示）
	 * @param courseName
	 * @return
	 */
	public boolean addNewCourseName(String courseName) {
		boolean result = false;
		try {
			db = this.getWritableDatabase();
			String sql = String
					.format("insert into table_course_name (course_name) values ('%s')", courseName);
			if(!isCourseNameExist(courseName)) {
				db.execSQL(sql);
			}
			closeDB();
			result = true;
		} catch (Exception e) {
			result = false;
			closeDB();
		}
		return result;
	}

	@Override
	public synchronized void close() {
		super.close();
		closeDB();
	}

	/**
	 * 关闭打开的数据库
	 */
	public void closeDB() {
		if (db.isOpen()) {
			db.close();
		}
	}
	
	/**
	 * 清空课程表数据
	 * @return
	 */
	public boolean deleteAllCourse() {
		boolean result = true;
		try {
			db = this.getWritableDatabase();
			String sql = String
					.format("DROP TABLE IF EXISTS table_course");
			db.execSQL(sql);
			String sqlTableCourse = "create table table_course(t_week integer,t_section integer,"
					+ "course_name varchar(50),course_classroom varchar(50),course_teacher varchar(50)"
					+ ")";
			db.execSQL(sqlTableCourse);
			closeDB();
		} catch (Exception e) {
			result = false;
			closeDB();
		}
		return result;
	}
	
	/**
	 * 清空课程名称数据
	 * @return
	 */
	public boolean deleteAllCourseName() {
		boolean result = true;
		try {
			db = this.getWritableDatabase();
			String sql = String
					.format("DROP TABLE IF EXISTS table_course_name");
			db.execSQL(sql);
			String sqlTableCourseName = "create table table_course_name(course_name varchar(50))";
			db.execSQL(sqlTableCourseName);
			closeDB();
		} catch (Exception e) {
			result = false;
			closeDB();
		}
		return result;
	}
	
	/**
	 * 修改课程信息
	 * 
	 * @param week
	 *            第几周
	 * @param section
	 *            第几节
	 * @param name
	 *            课程名字
	 * @param room
	 *            上课教室
	 * @param teacher
	 *            上课教师
	 * @return 成功则返回true
	 */
	public boolean editCourse(int week, int section, String courseName,
			String classroom, String teacher) {
		boolean result = false;
		try {
			db = this.getWritableDatabase();
			String sql = String
					.format("update table_course set course_name='%s',course_classroom='%s',course_teacher='%s'"
							+ " where t_week="
							+ week
							+ " and t_section="
							+ section, courseName, classroom, teacher);
			db.execSQL(sql);
			closeDB();
			result = true;
		} catch (Exception e) {
			closeDB();
		}
		return result;
	}
	
	/**
	 * 判断要更新的课程信息在更新之前是否为空
	 * 
	 * @param week
	 *            准备更新的课程是第几周
	 * @param section
	 *            准备更新的课程是第几节
	 * @return 如果为空返回false,不为空返回true
	 */
	public boolean isCourseExist(int week, int section) {
		boolean result = true;
		try {
			db = this.getWritableDatabase();
			String sql = "select course_name from table_course where t_week="
					+ week + " and t_section=" + section;
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.getCount() == 0) {
				result = false;
			}
			cursor.close();
			Log.i("my", "count" + cursor.getCount());
			closeDB();
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 判断课程名称是否已经存在（用于编辑课程表时的自动提示）
	 * @param courseName
	 * @return
	 */
	private boolean isCourseNameExist(String courseName) {
		boolean result = true;
		db = this.getWritableDatabase();
		String sql = "select * from table_course_name where course_name='" + courseName + "'";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.getCount() == 0) {
			result = false;
		}
		cursor.close();
		closeDB();
		return result;
	}
	
	@Override
	public void onCreate(SQLiteDatabase sdb) {
		String sqlTableCourse = "create table table_course(t_week integer,t_section integer,"
				+ "course_name varchar(50),course_classroom varchar(50),course_teacher varchar(50)"
				+ ")";
		String sqlTableCourseName = "create table table_course_name(course_name varchar(50))";
		sdb.execSQL(sqlTableCourse);
		sdb.execSQL(sqlTableCourseName);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * 查询一条课程的相关信息（包括课程名称、老师、上课地点）
	 * @param week 当前课程所在的周
	 * @param section 当前课程是第几节
	 * @return 返回一个课程(Course)类型
	 */
	public Course queryCourse(int week, int section) {
		Course myCourse = new Course();
		myCourse.week = week - 1;
		myCourse.section = section;
		try {
			db = this.getWritableDatabase();
			String sql = "select * from table_course where t_week="
					+ week + " and t_section=" + section;
			Cursor cursor = db.rawQuery(sql, null);
			while(cursor.moveToNext()) {
				myCourse.classroom = cursor.getString(cursor.getColumnIndex("course_classroom"));
				myCourse.courseName = cursor.getString(cursor.getColumnIndex("course_name"));
				myCourse.teacher = cursor.getString(cursor.getColumnIndex("course_teacher"));
				Log.i("my", "courseName" + cursor.getString(cursor.getColumnIndex("course_name")));
			}
			cursor.close();
			closeDB();
		} catch (Exception e) {
			myCourse.classroom = "上课地点";
			myCourse.courseName = "课程名称";
			myCourse.teacher = "上课教师";
			return myCourse;
		} finally {
			closeDB();
		}
		return myCourse;
	}

	/**
	 * 获取课程名称（用于编辑课程表时的自动提示）
	 * @return
	 */
	public List<String> queryCourseName() {
		List<String> list = new ArrayList<String>();
		db = this.getWritableDatabase();
		String sql = "select * from table_course_name";
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		cursor.close();
		closeDB();
		return list;
	}

}
