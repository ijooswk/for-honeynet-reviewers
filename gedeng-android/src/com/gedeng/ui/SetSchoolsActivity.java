package com.gedeng.ui;
import com.gedeng.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;

public class SetSchoolsActivity extends Activity {
	
	private ImageView collageStudent;
	private ImageView middleSchoolStudent;
	private ImageView primarySchoolStudent;
	private EditText schoolName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_schools);
		// Show the Up button in the action bar.
		setupActionBar();
		
		collageStudent = (ImageView) findViewById(R.id.imageView_collageStudent);
		middleSchoolStudent = (ImageView) findViewById(R.id.imageView_middleSchoolStudent);
		primarySchoolStudent = (ImageView) findViewById(R.id.imageView_primarySchoolStudent);
		schoolName = (EditText) findViewById(R.id.editText_schoolName);
		
		collageStudent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collageStudent
						.setImageResource(R.drawable.collage_student_checked);
				middleSchoolStudent
						.setImageResource(R.drawable.middle_school_student);
				primarySchoolStudent
						.setImageResource(R.drawable.primary_school_student);
				schoolName.setText(R.string.register_input_school_collage);
			}
		});
		middleSchoolStudent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collageStudent.setImageResource(R.drawable.collage_student);
				middleSchoolStudent
						.setImageResource(R.drawable.middle_school_student_checked);
				primarySchoolStudent
						.setImageResource(R.drawable.primary_school_student);
				schoolName.setText(R.string.register_school_name_middle_school);
			}
		});
		primarySchoolStudent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				collageStudent.setImageResource(R.drawable.collage_student);
				middleSchoolStudent
						.setImageResource(R.drawable.middle_school_student);
				primarySchoolStudent
						.setImageResource(R.drawable.primary_school_student_checked);
				schoolName.setText(R.string.register_school_name_primary_school);
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_schools, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
