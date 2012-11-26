package com.ant.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ant.timetable.db.MainDB;

public class DetailEdit extends Activity {

	private int week;
	private int section;
	private MainDB myMainDB = new MainDB(this);
	private AutoCompleteTextView tv_course_name;
	private EditText tv_teacher;
	private EditText tv_classroom;
	private String courseName;
	private String teacher;
	private String classroom;
	private Button btn_ok;
	private Button btn_back;
	private TextView tv_nav_week;
	private TextView tv_nav_time;
	private TextView tv_nav_section;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_course);
		myMainDB.getWritableDatabase();
		week = getIntent().getIntExtra("week", 0);
		section = getIntent().getIntExtra("section", 0);
		Log.i("my", "����" + week);
		Log.i("my", "�ڼ���" + section);
		tv_course_name = (AutoCompleteTextView) findViewById(R.id.course_name);
		tv_teacher = (EditText) findViewById(R.id.teacher);
		tv_classroom = (EditText) findViewById(R.id.classroom);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_nav_week = (TextView) findViewById(R.id.tv_nav_week);
		tv_nav_time = (TextView) findViewById(R.id.tv_nav_time);
		tv_nav_section = (TextView) findViewById(R.id.tv_nav_section);
		
		tv_nav_week.setText(getWeek(week));
		tv_nav_time.setText(getTime(section));
		tv_nav_section.setText(getSection(section));
		
		tv_course_name.setText(myMainDB.queryCourse(week, section).getCourseName());
		tv_teacher.setText(myMainDB.queryCourse(week, section).getTeacher());
		tv_classroom.setText(myMainDB.queryCourse(week, section).getClassroom());

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,myMainDB.queryCourseName());
		tv_course_name.setAdapter(arrayAdapter);
		
		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				courseName = tv_course_name.getText().toString();
				teacher = tv_teacher.getText().toString();
				classroom = tv_classroom.getText().toString();
				if (!courseName.equals("")) {
					myMainDB.addNewCourseName(courseName);
				}
				//Log.i("my", "��Ϣ��" + courseName + teacher + classroom + section);
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

	public String getWeek(int week) {
		switch(week) {
		case 0 : return "������";
		case 1 : return "����һ";
		case 2 : return "���ڶ�";
		case 3 : return "������";
		case 4 : return "������";
		case 5 : return "������";
		case 6 : return "������";
		}
		return "";
	}
	
	public String getTime(int section) {
		switch(section) {
		case 1 : return "����";
		case 2 : return "����";
		case 3 : return "����";
		case 4 : return "����";
		case 5 : return "����";
		case 6 : return "����";
		}
		return "";
	}
	
	public String getSection(int section) {
		switch(section) {
		case 1 : return "��һ��";
		case 2 : return "�ڶ���";
		case 3 : return "��һ��";
		case 4 : return "�ڶ���";
		case 5 : return "��һ��";
		case 6 : return "�ڶ���";
		}
		return "";
	}
	
	private void updateCourse() {
		boolean b;
		myMainDB.getWritableDatabase();
		Intent aintent = new Intent(DetailEdit.this, MainActivity.class);
		aintent.putExtra("result", 1);
		if (myMainDB.isCourseExist(week, section)) {
			b = myMainDB.editCourse(week, section, courseName, classroom, teacher);
			Log.i("my", "��Ϊ��");
			if(b) {
				Log.i("my", "��ӳɹ�");
				setResult(1, aintent);
			} else {
				Log.i("my", "���ʧ��");
			}
		} else {
			b = myMainDB.addCourse(week, section, courseName, classroom, teacher);
			Log.i("my", "Ϊ��");
			if(b) {
				Log.i("my", "��ӳɹ�");
				setResult(1, aintent);
			} else {
				Log.i("my", "���ʧ��");
			}
		}
	}
}
