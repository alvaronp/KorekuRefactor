package es.unex.giiis.koreku.ui.service;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Date;

import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.Service;
import es.unex.giiis.koreku.ui.consoles.UpdataConsole;

public class UpdateService extends AppCompatActivity {

    private static String startDateString;
    private static TextView startDateView;
    private static String dueDateString;
    private static TextView dueDateView;

    private EditText mTitle;
    private Spinner mSubscription;
    private EditText mEmail;
    private EditText mPrice;
    private Date mStartDate;
    private Date mDueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update_service);

        mTitle = findViewById(R.id.update_service_EditName);
        mSubscription = findViewById(R.id.update_service_spinner);
        mEmail = findViewById(R.id.update_service_EditEmail);
        mPrice = findViewById(R.id.update_service_EditPrice);
        startDateView = findViewById(R.id.update_service_EditStartDate);
        dueDateView = findViewById(R.id.update_service_EditDueDate);

        // Spinner options

        String [] opciones = {"EA Access", "GeForce Now ", "Google Stadia", "Humble Monthly",
                "Nintendo Switch Online", "PlayStation Plus", "PlayStation Now", "Twitch Prime",
                "UPlay+","Xbox Game Pass", "Xbox Live Gold", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, opciones);
        mSubscription.setAdapter(adapter);

        String title = getIntent().getExtras().getString("title");
        String subscription = getIntent().getExtras().getString("subscription");

        int selectionPosition = adapter.getPosition(subscription);

        String eMail = getIntent().getExtras().getString("email");
        String price = getIntent().getExtras().getString("price");
        String startDate = getIntent().getExtras().getString("startDate");
        String dueDate = getIntent().getExtras().getString("dueDate");

        mTitle.setText(title);
        mSubscription.setSelection(selectionPosition);
        mEmail.setText(eMail);
        mPrice.setText(price);
        startDateView.setText(startDate);
        dueDateView.setText(dueDate);

        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog

        final Button startDatePickerButton =  findViewById(R.id.update_service_EditStartDate_button);
        startDatePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDatePickerDialog();

            }
        });

        final Button dueDatePickerButton =  findViewById(R.id.update_service_EditStartDate_button);
        dueDatePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDatePickerDialog();

            }

        });

        final Button cancelButton =  findViewById(R.id.update_service_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

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

        final Button resetButton =  findViewById(R.id.update_service_reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                log("Entered resetButton.OnClickListener.onClick()");

                // - Reset data fields to default values

                mTitle.setText("");
                mSubscription.setSelection(0);
                mEmail.setText("");
                mPrice.setText("");
                setDefaultDate();

            }
        });

        // OnClickListener for the Submit Button

        final Button submitButton =  findViewById(R.id.update_service_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                log("Entered submitButton.OnClickListener.onClick()");

                // Gather Console data

                String titleString = mTitle.getText().toString();
                String subscriptionString = mSubscription.getSelectedItem().toString();
                String emailString = mEmail.getText().toString();
                String priceString = mPrice.getText().toString();
                String startString = startDateString;
                String dueString = dueDateString;

                // Package ToDoItem data into an Intent

                Intent data = new Intent();
                Service.packageIntent(data, titleString, subscriptionString, emailString, priceString, startString, dueString);

                // - return data Intent and finish

                setResult(RESULT_OK, data);
                finish();

            }
        });
    }

    private void setDefaultDate() {

        // Default is current time + 7 days

        mStartDate = new Date();
        mDueDate = new Date();

        Calendar c = Calendar.getInstance();
        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        startDateView.setText(startDateString);
        dueDateView.setText(dueDateString);

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

        startDateString = year + "-" + mon + "-" + day;
        dueDateString = year + "-" + mon + "-" + day;

    }

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
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            setDateString(year, monthOfYear, dayOfMonth);

            startDateView.setText(startDateString);
            dueDateView.setText(dueDateString);

        }

    }

    private void showDatePickerDialog() {

        DialogFragment newFragment = new AddService.DatePickerFragment();
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
