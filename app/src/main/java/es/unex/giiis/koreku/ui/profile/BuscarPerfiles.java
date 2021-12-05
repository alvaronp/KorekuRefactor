package es.unex.giiis.koreku.ui.profile;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import es.unex.giiis.koreku.AppContainer;
import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.MyApplication;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.databinding.FragmentProfileBinding;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;

public class BuscarPerfiles extends AppCompatActivity {

    private static Perfil perfil= null;
    EditText edtCodigo,edtTelefono,edtCorreo,edtTitle;
    ImageFilterView Imagen;
    private FragmentProfileBinding binding;
    private static final int MENU_DELETE = Menu.FIRST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda_profile);



        edtCodigo = findViewById(R.id.edtCodigo);
        edtTelefono = findViewById(R.id.Telefono);
        edtCorreo = findViewById(R.id.Correo);
        edtTitle = findViewById(R.id.Nombre);
        Imagen = findViewById(R.id.imagen);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        ProfileViewModel mViewModel = new ViewModelProvider(this, appContainer.pfactory).get(ProfileViewModel.class);

        // OnClickListener for the Cancel Button,

         Button buscar = (Button) findViewById(R.id.btnBuscar);
         buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo=edtCodigo.getText().toString();
                perfil=mViewModel.getPerfil(codigo);
                boolean bandera = false;
                if( perfil !=null){

                        edtTelefono.setVisibility(View.VISIBLE);
                        edtCorreo.setVisibility(View.VISIBLE);
                        edtTitle.setVisibility(View.VISIBLE);
                        Imagen.setVisibility(View.VISIBLE);
                        edtTelefono.setText(perfil.getPhone());
                        edtTitle.setText(perfil.getTitle());
                        edtCorreo.setText(perfil.getMail());
                        Imagen.setImageBitmap(BitmapFactory.decodeFile(perfil.getImage()));
                       bandera =true;

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

