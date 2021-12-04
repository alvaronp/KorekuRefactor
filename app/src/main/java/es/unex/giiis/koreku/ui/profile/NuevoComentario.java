package es.unex.giiis.koreku.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.giiis.koreku.R;

public class NuevoComentario extends AppCompatActivity {
    String comment;
    EditText comment_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_profile);

        comment_text = findViewById(R.id.comment_text);

        Button submit_comment = (Button) findViewById(R.id.comment_submit);
        submit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment =  comment_text.getText().toString();
                Intent data = new Intent();
                data.putExtra("comment", comment);

                // - return data Intent and finish
                setResult(RESULT_OK, data);
                finish();

            }
        });


    }}

