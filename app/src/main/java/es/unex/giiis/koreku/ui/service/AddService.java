package es.unex.giiis.koreku.ui.service;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import es.unex.giiis.koreku.R;

public class AddService extends AppCompatActivity {

    private static String dueDateString;
    private static TextView dueDateView;

    private EditText mTitle;
    private Spinner mSubscription;
    private EditText mEmail;
    private EditText mPrice;
    private Date mDueDate;



    // Funcionalidades de los botones y cuadros

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_service);

        mTitle = findViewById(R.id.serviceName);
        mSubscription = findViewById(R.id.serviceSpinner);
        mEmail = findViewById(R.id.serviceEmail);
        mPrice = findViewById(R.id.servicePrice);
        dueDateView = findViewById(R.id.serviceDueDate);

        // Spinner options

        String [] opciones = {"EA Access", "GeForce Now ", "Google Stadia", "Humble Monthly",
                "Nintendo Switch Online", "PlayStation Plus", "PlayStation Now", "Twitch Prime",
                "UPlay+","Xbox Game Pass", "Xbox Live Gold", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, opciones);
        mSubscription.setAdapter(adapter);

        // Set the default date

        setDefaultDate();

        final Button dueDatePickerButton =  findViewById(R.id.serviceDueDateButton);
        dueDatePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDatePickerDialog();

            }

        });

        // OnClickListener for the Cancel Button

        final Button cancelButton =  findViewById(R.id.serviceCancelButton);
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

        final Button resetButton =  findViewById(R.id.serviceResetButton);
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

        final Button submitButton =  findViewById(R.id.serviceSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                log("Entered submitButton.OnClickListener.onClick()");

                // Gather Console data

                String titleString = mTitle.getText().toString();
                String subscriptionString = mSubscription.getSelectedItem().toString();
                String emailString = mEmail.getText().toString();
                String priceString = mPrice.getText().toString();
                String dueString = dueDateString;

                // Package ToDoItem data into an Intent

                Intent data = new Intent();
                Service.packageIntent(data, titleString, subscriptionString, emailString, priceString, dueString);

                // - return data Intent and finish
                setResult(RESULT_OK, data);
                finish();

            }
        });
    }

    // Comentario

    private void setDefaultDate() {

        // Default is current time + 7 days

        mDueDate = new Date();

        Calendar c = Calendar.getInstance();

        setDueDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dueDateView.setText(dueDateString);
    }


    private static void setDueDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting

        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        dueDateString = year + "-" + mon + "-" + day;
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
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDueDateString(year, monthOfYear, dayOfMonth);
            dueDateView.setText(dueDateString);
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
