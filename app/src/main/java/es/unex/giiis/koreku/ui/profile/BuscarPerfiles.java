package es.unex.giiis.koreku.ui.profile;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentProfileBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;

public class BuscarPerfiles extends AppCompatActivity {
  private static    List<Perfil> perfiles= null;
    EditText edtCodigo,edtTelefono,edtCorreo,edtTitle;
    ImageFilterView Imagen;
    private FragmentProfileBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda_profile);


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                perfiles=  KorekuDatabase.getInstance(BuscarPerfiles.this).getDao3().getAll();

            }
        });

        edtCodigo = findViewById(R.id.edtCodigo);
        edtTelefono = findViewById(R.id.Telefono);
        edtCorreo = findViewById(R.id.Correo);
        edtTitle = findViewById(R.id.Nombre);
        Imagen = findViewById(R.id.imagen);

        // OnClickListener for the Cancel Button,

         Button buscar = (Button) findViewById(R.id.btnBuscar);
         buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i =0;
                String codigo=edtCodigo.getText().toString();
                boolean bandera = false;
                while(i<perfiles.size() && bandera ==false){
                    String titulo=perfiles.get(i).getTitle();
                    if(codigo.equals(titulo)){
                        edtTelefono.setVisibility(View.VISIBLE);
                        edtCorreo.setVisibility(View.VISIBLE);
                        edtTitle.setVisibility(View.VISIBLE);
                        Imagen.setVisibility(View.VISIBLE);
                        edtTelefono.setText(perfiles.get(i).getPhone());
                        edtTitle.setText(perfiles.get(i).getTitle());
                        edtCorreo.setText(perfiles.get(i).getMail());
                        Imagen.setImageBitmap(BitmapFactory.decodeFile(perfiles.get(i).getImage()));
                        bandera = true;
                    }else{i++;}
                }
                if(bandera == false){
                    Snackbar.make(v, R.string.notfound, Snackbar.LENGTH_LONG)
                            .show();
                    edtTelefono.setText(R.string.notfound);
                    edtTitle.setText(R.string.notfound);
                    edtCorreo.setText(R.string.notfound);
                }

            }
        });


}}

