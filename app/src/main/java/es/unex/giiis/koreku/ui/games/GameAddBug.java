package es.unex.giiis.koreku.ui.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.giiis.koreku.R;

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

