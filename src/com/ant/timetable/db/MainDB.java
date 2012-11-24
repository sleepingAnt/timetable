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
	 * ��ѯһ���γ̵������Ϣ�������γ����ơ���ʦ���Ͽεص㣩
	 * @param week ��ǰ�γ����ڵ���
	 * @param section ��ǰ�γ��ǵڼ���
	 * @return ����һ���γ�(Course)����
	 */
	public Course queryCourse(int week, int section) {
		Course myCourse = new Course();
		myCourse.week = week - 1;
		Log.i("my", "week:::::" + week);
		myCourse.section = section;
		Log.i("my", "section:::::" + section);
		try {
			Log.i("my", "queried");
			db = this.getWritableDatabase();
			Log.i("my", "queried2");
			String sql = "select * from table_course where t_week="
					+ week + " and t_section=" + section;
			Cursor cursor = db.rawQuery(sql, null);
			while(cursor.moveToNext()) {
				myCourse.classroom = cursor.getString(3);
				myCourse.courseName = cursor.getString(2);
				myCourse.teacher = cursor.getString(4);
				Log.i("my", "courseName" + cursor.getString(2));
			}
			closeDB();
		} catch (Exception e) {
			myCourse.classroom = "������";
			myCourse.courseName = "������";
			myCourse.teacher = "������";
		} finally {
			
		}
		return myCourse;
	}
	
	/**
	 * �ж�Ҫ���µĿγ���Ϣ�ڸ���֮ǰ�Ƿ�Ϊ��
	 * 
	 * @param week
	 *            ׼�����µĿγ��ǵڼ���
	 * @param section
	 *            ׼�����µĿγ��ǵڼ���
	 * @return ���Ϊ�շ���false,��Ϊ�շ���true
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
			Log.i("my", "count" + cursor.getCount());
			closeDB();
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * ��ӿγ���Ϣ
	 * 
	 * @param week
	 *            �ڼ���
	 * @param section
	 *            �ڼ���
	 * @param name
	 *            �γ�����
	 * @param room
	 *            �Ͽν���
	 * @param teacher
	 *            �Ͽν�ʦ
	 * @return �ɹ��򷵻�true
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
		}
		return result;

	}

	/**
	 * �޸Ŀγ���Ϣ
	 * 
	 * @param week
	 *            �ڼ���
	 * @param section
	 *            �ڼ���
	 * @param name
	 *            �γ�����
	 * @param room
	 *            �Ͽν���
	 * @param teacher
	 *            �Ͽν�ʦ
	 * @return �ɹ��򷵻�true
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
		}
		return result;
	}

	/**
	 * �رմ򿪵����ݿ�
	 */
	public void closeDB() {
		if (db.isOpen()) {
			db.close();
		}
	}

}
