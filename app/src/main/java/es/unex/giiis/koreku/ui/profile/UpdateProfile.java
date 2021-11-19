package es.unex.giiis.koreku.ui.profile;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;

public class UpdateProfile extends AppCompatActivity {

    private EditText mTitle,mTelefono,mCorreo;
    private Button mImageSelect;
    private Uri imageUri;
    ImageView foto;
    String imagen;
    private static final int LOAD_IMAGE_REQUEST = 0;
    private static final int PERMISSION_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update_profile);

        mTitle = findViewById(R.id.nombretxt);
        mTelefono = findViewById(R.id.telefonotxt);
        mCorreo = findViewById(R.id.correotxt);
        mImageSelect = findViewById(R.id.imageProfile);
        foto = (ImageView) findViewById(R.id.imageViewProfileDetail);

        String titulo = getIntent().getExtras().getString("titulo");
        String phone = getIntent().getExtras().getString("phone");
        String mail = getIntent().getExtras().getString("mail");
        String image = getIntent().getExtras().getString("image");



        mTitle.setText(titulo);
        mTelefono.setText(phone);
        mCorreo.setText(mail);
        foto.setImageBitmap(BitmapFactory.decodeFile(image));

        mImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromAlbum();
            }
        });
        if (ContextCompat.checkSelfPermission(UpdateProfile.this,READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mImageSelect.setEnabled(false);
            ActivityCompat.requestPermissions(
                    UpdateProfile.this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }

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
                foto.setImageURI(Uri.parse(""));
                ;
            }
        });
        final Button submitButton =  findViewById(R.id.submitProfile);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");
                // -  Title
                String titleString = mTitle.getText().toString();
                String telefono = mTelefono.getText().toString();
                String correo = mCorreo.getText().toString();
                String comment = getIntent().getExtras().getString("comment");

                String image = "";
                if (imageUri != null)
                    image = imagen;

                // Package ToDoItem data into an Intent
                Intent data = new Intent();
                Perfil.packageIntent(data, titleString, telefono, correo,image,comment);

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
            imageUri = data.getData();
            imagen = getRealPathFromURI(imageUri);
            foto.setImageURI(imageUri);
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


    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
