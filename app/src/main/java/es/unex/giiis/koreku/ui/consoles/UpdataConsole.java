package es.unex.giiis.koreku.ui.consoles;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Date;

import es.unex.giiis.koreku.R;

public class UpdataConsole extends AppCompatActivity {

    private static String dateString;
    private static TextView dateView;
    boolean bandera;
    private EditText mTitle;
    private Date mBuydate;
    private EditText mCompany;
    private static final int LOAD_IMAGE_REQUEST = 0;
    private static final int PERMISSION_CODE = 1;
    private Button mImageSelect;
    private Uri imageUri;
    ImageView foto;
    String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update_console);

        mTitle = findViewById(R.id.title);
        dateView = findViewById(R.id.date);
        mCompany = findViewById(R.id.desc);
        mImageSelect = findViewById(R.id.image_picker_button);
        foto = (ImageView) findViewById(R.id.imageView2);

        String titulo = getIntent().getExtras().getString("titulo");
        String company = getIntent().getExtras().getString("company");
        String date = getIntent().getExtras().getString("date");
        String image = getIntent().getExtras().getString("image");
        // Set the default date and time
         bandera = true;
        mTitle.setText(titulo);
        mCompany.setText(company);
        dateView.setText(date);
        foto.setImageBitmap(BitmapFactory.decodeFile(image));

        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog

        final Button datePickerButton =  findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        mImageSelect = findViewById(R.id.image_picker_button);

        if (ContextCompat.checkSelfPermission(UpdataConsole.this,READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mImageSelect.setEnabled(false);
            ActivityCompat.requestPermissions(
                    UpdataConsole.this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }

        mImageSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bandera=false;
                getImageFromAlbum();
            }
        });
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
                foto.setImageURI(Uri.parse(""));
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
                String buyDate = dateView.getText().toString();

                String company = mCompany.getText().toString();

                String image = "";
                if (imageUri != null)
                    image = imagen;

                // Package ToDoItem data into an Intent
                Intent data = new Intent();
                Consolas.packageIntent(data, titleString, company, image, buyDate);

                // - return data Intent and finish
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == LOAD_IMAGE_REQUEST){
            imageUri = data.getData();
            imagen = getRealPathFromURI(imageUri);
            foto.setImageURI(imageUri);
        }
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

    // Do not modify below here

    // Use this method to set the default date and time

    private void setDefaultDateTime() {

        // Default is current time + 7 days
        mBuydate = new Date();

        Calendar c = Calendar.getInstance();

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
