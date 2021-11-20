package es.unex.giiis.koreku.ui.games;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.Games.Status;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.api.OnProductsLoadedListener;
import es.unex.giiis.koreku.api.PriceChartingService;
import es.unex.giiis.koreku.api.Product;
import es.unex.giiis.koreku.api.ProductsLoaderRunnable;
import es.unex.giiis.koreku.api.ProductsNetworkLoaderRunnable;
import es.unex.giiis.koreku.ui.consoles.AddConsoles;

public class AddGames extends AppCompatActivity {

	private static String dateString;
	private static TextView dateView;
	private static final int LOAD_IMAGE_REQUEST = 0;
	private static final int PERMISSION_CODE = 0;

	private EditText mTitle;
	private Date mBuydate;
	private EditText mDesc;
	private Button mImageSelect;
	private RadioGroup mStatusRadioGroup;
	private RadioButton mDefaultStatusButton;
	private EditText mGenre;
	private String imagen;
	private ProductsNetworkLoaderRunnable api;
	private OnProductsLoadedListener listen;
	ImageView check;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_add_game);

		mTitle = findViewById(R.id.title);
		dateView = findViewById(R.id.date);
		mDesc = findViewById(R.id.desc);
		mImageSelect = findViewById(R.id.image_picker_button);
		mDefaultStatusButton = findViewById(R.id.statusNotFinished);
		mStatusRadioGroup = findViewById(R.id.statusGroup);
		mGenre = findViewById(R.id.genero_edit);

		// Set the default date and time

		setDefaultDateTime();

		// OnClickListener for the Date button, calls showDatePickerDialog() to show
		// the Date dialog

		final Button datePickerButton = findViewById(R.id.date_picker_button);
		datePickerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});


		mImageSelect = findViewById(R.id.image_picker_button);
		mImageSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				getImageFromAlbum();
			}
		});

		if (ContextCompat.checkSelfPermission(AddGames.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(
					AddGames.this,
					new String[]{READ_EXTERNAL_STORAGE},
					PERMISSION_CODE
			);
		}
		// OnClickListener for the Cancel Button,
		final Button cancelButton = findViewById(R.id.cancelButton);
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
		final Button resetButton = findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered resetButton.OnClickListener.onClick()");

				// - Reset data fields to default values
				mTitle.setText("");
				mDesc.setText("");
				mGenre.setText("");
				imagen = "";
				mStatusRadioGroup.check(mDefaultStatusButton.getId());
				setDefaultDateTime();
			}
		});

		// OnClickListener for the Submit Button
		// Implement onClick().
		final Button submitButton = findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log("Entered submitButton.OnClickListener.onClick()");

				// Gather Game data
				// -  Title
				String titleString = mTitle.getText().toString();
				// Date
				String buyDate = dateView.getText().toString();

				String desc = mDesc.getText().toString();

				Status status = getStatus();

				String genre = mGenre.getText().toString();

				api = new ProductsNetworkLoaderRunnable(titleString, listen);
				AppExecutors.getInstance().networkIO().execute(api);

				Intent data = new Intent();
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String consolatest = api.getFoundProduct().consolename;
				Games.packageIntent(data, titleString, status, buyDate, desc, imagen, genre, null,consolatest );

				// - return data Intent and finish
				setResult(RESULT_OK, data);
				finish();
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions,
										   int[] grantResults) {
		super.onRequestPermissionsResult(requestCode,permissions,grantResults);
		switch (requestCode) {
			case PERMISSION_CODE:
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 &&
						grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// Permission is granted. Continue the action or workflow
					// in your app.
					mImageSelect.setEnabled(true);
				}  else {
					mImageSelect.setEnabled(false);
				}
				return;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode,resultCode,data);
		if(resultCode == RESULT_OK && requestCode == LOAD_IMAGE_REQUEST){
			imagen = getRealPathFromURI(data.getData());
			check = findViewById(R.id.check_image);
			check.setVisibility(View.VISIBLE);
		}
	}

	// Do not modify below here
	
	// Use this method to set the default date and time
	
	private void setDefaultDateTime() {

		// Default is current time + 7 days
		mBuydate = new Date();

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

	private Status getStatus() {

		switch (mStatusRadioGroup.getCheckedRadioButtonId()) {
			case R.id.statusFinished: {
				return Status.FINISHED;
			}
			default: {
				return Status.NOTFINISHED;
			}
		}
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

	private void getImageFromAlbum(){
		try{
			Intent i = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			i.setType("image/*");
			startActivityForResult(i, LOAD_IMAGE_REQUEST);
		}catch(Exception exp){
			Log.i("Error",exp.toString());
		}
	}

	private String getRealPathFromURI(Uri contentURI) {
		String result;
		Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) {
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
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
