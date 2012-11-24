package com.ant.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ant.timetable.db.MainDB;

public class DetailEdit extends Activity {

	private int week;
	private int section;
	private MainDB myMainDB = new MainDB(this);
	private TextView tv_course_name;
	private TextView tv_teacher;
	private TextView tv_classroom;
	private String courseName;
	private String teacher;
	private String classroom;
	private Button btn_ok;
	private Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_course);

		week = getIntent().getIntExtra("week", 0);
		section = getIntent().getIntExtra("section", 0);
		Log.i("my", "星期" + week);
		Log.i("my", "第几节" + section);
		tv_course_name = (TextView) findViewById(R.id.course_name);
		tv_teacher = (TextView) findViewById(R.id.teacher);
		tv_classroom = (TextView) findViewById(R.id.classroom);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_back = (Button) findViewById(R.id.btn_back);
		
		tv_course_name.setText(myMainDB.queryCourse(week, section).getCourseName());
		tv_teacher.setText(myMainDB.queryCourse(week, section).getTeacher());
		tv_classroom.setText(myMainDB.queryCourse(week, section).getClassroom());


		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				courseName = tv_course_name.getText().toString();
				teacher = tv_teacher.getText().toString();
				classroom = tv_classroom.getText().toString();
				if (courseName.equals("") || teacher.equals("")
						|| classroom.equals("")) {

				} else {
					
				}
				Log.i("my", "信息：" + courseName + teacher + classroom + section);
				updateCourse();
				finish();
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DetailEdit.this.finish();
			}
		});

	}

	private void updateCourse() {
		boolean b;
		myMainDB.getWritableDatabase();
		Intent aintent = new Intent(DetailEdit.this, MainActivity.class);
		aintent.putExtra("result", 1);
		if (myMainDB.isCourseExist(week, section)) {
			b = myMainDB.editCourse(week, section, courseName, classroom, teacher);
			Log.i("my", "不为空");
			if(b) {
				Log.i("my", "添加成功");
				setResult(1, aintent);
			} else {
				Log.i("my", "添加失败");
			}
		} else {
			b = myMainDB.addCourse(week, section, courseName, classroom, teacher);
			Log.i("my", "为空");
			if(b) {
				Log.i("my", "添加成功");
				setResult(1, aintent);
			} else {
				Log.i("my", "添加失败");
			}
		}
	}
}
