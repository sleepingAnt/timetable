package com.ant.timetable.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MainDB extends SQLiteOpenHelper {
	@Override
	public synchronized void close() {
		super.close();
		closeDB();
	}

	private static final String dbName = "timetable.db";
	private SQLiteDatabase db;

	public MainDB(Context context) {
		super(context, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase sdb) {
		String sql = "create table table_course(t_week integer,t_section integer,"
				+ "course_name varchar(50),course_classroom varchar(50),course_teacher varchar(50)"
				+ ")";
		sdb.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * 判断要更新的课程信息在更新之前是否为空
	 * @param week 准备更新的课程是第几周
	 * @param section 准备更新的课程是第几节
	 * @return 如果为空返回false,不为空返回true
	 */
	public boolean isCourseExist(int week, int section) {
		boolean result = true;
		try {
			db = this.getWritableDatabase();
			String sql = "select course_name from table_course where t_week="
					+ week + " and t_section=" + section;
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() == 0) {
				result = false;
			}
			Log.i("my","count"+cursor.getCount());
			closeDB();
		} catch (Exception e) {
			result = false;
		}
		return result;
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
	public boolean addCourse(int week, int section, String courseName, String classroom,
			String teacher) {
		boolean result = false;
		try {
			db = this.getWritableDatabase();
			String sql = String
					.format("insert into table_course (t_week,t_section,course_name,course_classroom,course_teacher)"
							+ " values (%d,%d,'%s','%s','%s')", week, section, courseName,
							classroom, teacher);
			db.execSQL(sql);
			closeDB();
			result = true;
		} catch (Exception e) {
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
	public boolean editCourse(int week, int section, String courseName, String classroom,
			String teacher) {
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
		}
		return result;
	}

	/**
	 * 关闭打开的数据库
	 */
	public void closeDB() {
		if (db.isOpen()) {
			db.close();
		}
	}

}
