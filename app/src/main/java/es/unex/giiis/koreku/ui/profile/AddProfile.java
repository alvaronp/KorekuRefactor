package es.unex.giiis.koreku.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;

public class AddProfile extends AppCompatActivity {

    private EditText mTitle,mTelefono,mCorreo;
    private Button mImageSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_profile);

        mTitle = findViewById(R.id.nombretxt);
        mTelefono = findViewById(R.id.telefonotxt);
        mCorreo = findViewById(R.id.correotxt);
        mImageSelect = findViewById(R.id.imageProfile);

        // Set the default date and time





// OnClickListener for the Cancel Button,

        final Button cancelButton =  findViewById(R.id.cancelProfile);
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
        final Button resetButton =  findViewById(R.id.resetProfile);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered resetButton.OnClickListener.onClick()");

                // - Reset data fields to default values
                mTitle.setText("");
                mTelefono.setText("");
                mCorreo.setText("");

            }
        });
        final Button submitButton =  findViewById(R.id.submitProfile);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");

                // -  Title
                String titleString = mTitle.getText().toString();

                // Date


                String telefono = mTelefono.getText().toString();
                String correo = mTelefono.getText().toString();

                String image = "asdfasdfasd";

                // Package ToDoItem data into an Intent
                Intent data = new Intent();
               Perfil.packageIntent(data, titleString, telefono, correo,image);

                // - return data Intent and finish
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
