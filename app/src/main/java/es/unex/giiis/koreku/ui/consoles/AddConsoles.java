package es.unex.giiis.koreku.ui.consoles;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;

import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.R;

public class AddConsoles extends AppCompatActivity {

	// 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
	private static final int SEVEN_DAYS = 604800000;

	private static String dateString;
	private static TextView dateView;
	
	private EditText mTitle;
	private Date mBuydate;
	private EditText mCompany;
	private Button mImageSelect;
	private String pid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_add_console);

        mTitle = findViewById(R.id.title);
        dateView = findViewById(R.id.date);
        mCompany = findViewById(R.id.company);
        mImageSelect = findViewById(R.id.image_picker_button);

		// Set the default date and time

		setDefaultDateTime();

		// OnClickListener for the Date button, calls showDatePickerDialog() to show
		// the Date dialog

		final Button datePickerButton =  findViewById(R.id.date_picker_button);
		datePickerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});


		// OnClickListener for the Cancel Button, 

		final Button cancelButton =  findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered cancelButton.OnClickListener.onClick()");

				// - Implement onClick().
				Intent data = new Intent();
				setResult(RESULT_CANCELED, data);				
				finish();		

			}
		});

		//OnClickListener for the Reset Button

		final Button resetButton =  findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered resetButton.OnClickListener.onClick()");

				// - Reset data fields to default values
				mTitle.setText("");
				mCompany.setText("");
				setDefaultDateTime();
			}
		});

		// OnClickListener for the Submit Button
		// Implement onClick().
		
		final Button submitButton =  findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered submitButton.OnClickListener.onClick()");

				// Gather Console data

				// -  Title
				String titleString = mTitle.getText().toString();

				// Date
				String buyDate = dateString;

				// Package ToDoItem data into an Intent
				Intent data = new Intent();
				Consolas.packageIntent(data, titleString, mCompany.toString(), mImageSelect.toString(), dateString);

				// - return data Intent and finish
				setResult(RESULT_OK, data);				
				finish();

				
				
			}
		});
	}

	// Do not modify below here
	
	// Use this method to set the default date and time
	
	private void setDefaultDateTime() {

		// Default is current time + 7 days
		mBuydate = new Date();
		mBuydate = new Date(mBuydate.getTime() + SEVEN_DAYS);

		Calendar c = Calendar.getInstance();
		c.setTime(mBuydate);

		setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));

		dateView.setText(dateString);
	}

	private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

		// Increment monthOfYear for Calendar/Date -> Time Format setting
		monthOfYear++;
		String mon = "" + monthOfYear;
		String day = "" + dayOfMonth;

		if (monthOfYear < 10)
			mon = "0" + monthOfYear;
		if (dayOfMonth < 10)
			day = "0" + dayOfMonth;

		dateString = year + "-" + mon + "-" + day;
	}

	// DialogFragment used to pick a Console deadline date

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Use the current date as the default date in the picker

			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
			setDateString(year, monthOfYear, dayOfMonth);

			dateView.setText(dateString);
		}

	}


	private void showDatePickerDialog() {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	private void log(String msg) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
