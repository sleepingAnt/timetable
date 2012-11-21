package com.ant.timetable;

import android.app.Activity;
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
		Log.i("my", "����" + week);
		Log.i("my", "�ڼ���" + section);
		tv_course_name = (TextView) findViewById(R.id.course_name);
		tv_teacher = (TextView) findViewById(R.id.teacher);
		tv_classroom = (TextView) findViewById(R.id.classroom);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_back = (Button) findViewById(R.id.btn_back);


		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				courseName = tv_course_name.getText().toString();
				teacher = tv_teacher.getText().toString();
				classroom = tv_classroom.getText().toString();
				if (courseName.equals("") || teacher.equals("")
						|| classroom.equals("")) {

				} else {
					
				}
				Log.i("my", "��Ϣ��" + courseName + teacher + classroom + section);
				updateCourse();
				DetailEdit.this.finish();
			}
		});

	}

	private void updateCourse() {
		boolean b;
		myMainDB.getWritableDatabase();
		if (myMainDB.isCourseExist(week, section)) {
			b = myMainDB.editCourse(week, section, courseName, classroom, teacher);
			Log.i("my", "��Ϊ��");
		} else {
			b = myMainDB.addCourse(week, section, courseName, classroom, teacher);
			Log.i("my", "Ϊ��");
			if(b) {
				Log.i("my", "��ӳɹ�");
			} else {
				Log.i("my", "���ʧ��");
			}
		}
	}
}
