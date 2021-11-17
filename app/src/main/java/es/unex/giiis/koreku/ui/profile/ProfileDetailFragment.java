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

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;


public class ProfileDetailFragment extends Fragment {

    private Perfil mProf;
    TextView mComment;
    TextView mTitle ;
    TextView mTelefono ;
    TextView mCorreo ;
    private static final int COMMENT_SET = 1;
    private static final int EDIT_SET = 3;

    public ProfileDetailFragment() {
        // Required empty public constructor
    }

    public static ProfileDetailFragment newInstance(Perfil c) {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        Bundle args = new Bundle();
        args.putLong("id", c.getId());
        args.putString("title",c.getTitle());
        args.putString("phone",c.getPhone());
        args.putString("correo",c.getMail());
        args.putString("image",c.getImage());
        args.putString("comment",c.getComments());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        if (args != null) {
            mProf = new Perfil(args.getLong("id"), args.getString("title"), args.getString("phone"), args.getString("correo"), args.getString("image"),args.getString("comment"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.profile_detail, container, false);
        // Show item content
         mTitle = v.findViewById(R.id.nombredetailprofile);
         mTelefono = v.findViewById(R.id.telefonodetailprofile);
         mCorreo = v.findViewById(R.id.correodetailprofile);
        mComment = v.findViewById(R.id.profile_comment);
        //TextView image = v.findViewById(R.id.imagendetailprofile);
        mTitle.setText(mProf.getTitle());
        mTelefono.setText(mProf.getPhone());
        mCorreo.setText(mProf.getMail());
        mComment.setText(mProf.getComments());
       // image.setText("holahola");

        Button newcomment = (Button) v.findViewById(R.id.comment_button);
        newcomment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), NuevoComentario.class);
                startActivityForResult(intent, COMMENT_SET);
            }
        });

        Button editButton = (Button) v.findViewById(R.id.editarPerfil);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), AddProfile.class);
                startActivityForResult(intent, EDIT_SET);
            }
        });
        Button share = (Button) v.findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String comment = mProf.getComments();
                String text="";
                if (comment.length()>0)
                    text="Nombre del perfil: "+mProf.getTitle()+" \nPlataforma: "+mProf.getPhone()+ " \nCorreo: "+mProf.getMail() + "\nComentario: "+ comment +"\n\n Enviado desde KOREKU©";
                else
                    text="Nombre del perfil: "+mProf.getTitle()+" \nPlataforma: "+mProf.getPhone()+ " \nCorreo: "+mProf.getMail() + "\n\n Enviado desde KOREKU©";
                intent.putExtra(Intent.EXTRA_TEXT,text);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  - Check result code and request code.
        // If user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter
        if (requestCode == COMMENT_SET) {
            if (resultCode == getActivity().RESULT_OK) {
                mProf.setComments(data.getStringExtra("comment"));
                AppExecutors.getInstance().diskIO().execute(() -> KorekuDatabase.getInstance(getActivity()).getDao3().update(mProf));
                mComment.setText(mProf.getComments());
            }

        }
        if(requestCode == EDIT_SET){
        if(resultCode == getActivity().RESULT_OK) {
            Perfil c = new Perfil(data);
            mProf.setPhone(c.getPhone());
            mProf.setTitle(c.getTitle());
            mProf.setMail(c.getMail());
            mProf.setComments(c.getComments());
            mProf.setImage(c.getImage());
            AppExecutors.getInstance().diskIO().execute(() -> KorekuDatabase.getInstance(getActivity()).getDao3().update(mProf));
            mComment.setText(mProf.getComments());
             mTitle.setText(mProf.getTitle());
             mTelefono.setText(mProf.getPhone());
             mCorreo.setText(mProf.getMail());

        }
        }
    }

    @Override public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mProf.getTitle());
    }

    @Override public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(((AppCompatActivity)getActivity()).getTitle());
    }
}