package es.unex.giiis.koreku.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;


public class PerfilDetailFragment extends Fragment {

    private Perfil mCon;

    public PerfilDetailFragment() {
        // Required empty public constructor
    }

    public static PerfilDetailFragment newInstance(Perfil c) {
        PerfilDetailFragment fragment = new PerfilDetailFragment();
        Bundle args = new Bundle();
        args.putLong("id", c.getId());
        args.putString("title",c.getTitle());
        args.putString("phone",c.getPhone());
        args.putString("correo",c.getMail());
        args.putString("image",c.getImage());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        if (args != null) {
            mCon = new Perfil(args.getLong("id"), args.getString("title"), args.getString("phone"), args.getString("correo"), args.getString("image"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile_detail, container, false);
        // Show item content
        TextView mTitle = v.findViewById(R.id.nombredetailprofile);
        TextView mTelefono = v.findViewById(R.id.telefonodetailprofile);
        TextView mCorreo = v.findViewById(R.id.correodetailprofile);
        //TextView image = v.findViewById(R.id.imagendetailprofile);
        mTitle.setText(mCon.getTitle());
        mTelefono.setText(mCon.getPhone());
        mCorreo.setText(mCon.getMail());
       // image.setText("holahola");

        Button newcomment = (Button) v.findViewById(R.id.comment_button);
        newcomment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), NuevoComentario.class);
                startActivity(intent);
            }
        });

        Button share = (Button) v.findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text="Koreku dice ->Nombre del perfil: "+mCon.getTitle()+" Plataforma: "+mCon.getPhone()+ " Correo: "+mCon.getMail();
                intent.setPackage("org.telegram.messenger");
                intent.putExtra(Intent.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(intent,text));
            }
        });
        return v;
    }
//aa
    @Override public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mCon.getTitle());
    }

    @Override public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(((AppCompatActivity)getActivity()).getTitle());
    }
}