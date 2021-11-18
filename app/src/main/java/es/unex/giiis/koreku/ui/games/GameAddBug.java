package es.unex.giiis.koreku.ui.games;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.AddConsoles;

public class GameAddBug extends AppCompatActivity {
    String bug;
    EditText bug_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_add_bug);

        bug_text = findViewById(R.id.bug_text);

        Button submit_bug = (Button) findViewById(R.id.submit_bug_button);
        submit_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bug =  bug_text.getText().toString();
                Intent data = new Intent();
                data.putExtra("bugs", bug);

                // - return data Intent and finish
                setResult(RESULT_OK, data);
                finish();

            }
        });


    }}

