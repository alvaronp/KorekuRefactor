package es.unex.giiis.koreku.ui.consoles;

import android.content.Intent;
import android.net.Uri;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import es.unex.giiis.koreku.AppExecutors;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.games.GameAddBug;
import es.unex.giiis.koreku.ui.profile.UpdateProfile;


public class ConsoleDetailFragment extends Fragment {

    private Consolas mCon;
    Button deleteCon;
    TextView mTitle;
    TextView mCompany ;
    TextView mBuyDate ;
    ImageView image ;

    public ConsoleDetailFragment() {
        // Required empty public constructor
    }

    public static ConsoleDetailFragment newInstance(Consolas c) {
        ConsoleDetailFragment fragment = new ConsoleDetailFragment();
        Bundle args = new Bundle();
        args.putLong("id", c.getId());
        args.putString("title",c.getTitle());
        args.putString("comp",c.getCompany());
        args.putLong("dateLong",DateConverter.toTimestamp(c.getDate()));
        args.putString("image",c.getImage());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        if (args != null) {
            mCon = new Consolas(args.getLong("id"),args.getString("title"), DateConverter.toDate(args.getLong("dateLong")),args.getString("comp"),args.getString("image"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.console_detail, container, false);
        // Show item content
         mTitle = v.findViewById(R.id.titleGameDetail);
         mCompany = v.findViewById(R.id.descGameDetail);
         mBuyDate = v.findViewById(R.id.editTextDate);
         image = v.findViewById(R.id.imageViewGame);
         mTitle.setText(mCon.getTitle());
         mCompany.setText(mCon.getCompany());
         Instant buyDate = mCon.getDate().toInstant();
         mBuyDate.setText(buyDate.toString().subSequence(0,10));
         String imagePath = mCon.getImage();
         if (imagePath!=null)
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        Button editButton = (Button) v.findViewById(R.id.Edit);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), UpdataConsole.class);
                intent.putExtra("titulo", mCon.getTitle());
                intent.putExtra("company", mCon.getCompany());
                intent.putExtra("date", mCon.getDate().toInstant().toString().subSequence(0,10));
                intent.putExtra("image", mCon.getImage());
                startActivityForResult(intent, 0);
            }
        });
        Button deleteconsole = (Button) v.findViewById(R.id.delete_console);
        deleteconsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        db.getDao2().deleteConsole(mCon.getTitle());
                    }
                });
                getActivity().onBackPressed();

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
        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                Consolas c = new Consolas(data);
                mCon.setTitle(c.getTitle());
                mCon.setCompany(c.getCompany());
                mCon.setDate(c.getDate());
                if(!c.getImage().equals("")) {
                    mCon.setImage(c.getImage());
                }
                AppExecutors.getInstance().diskIO().execute(() -> KorekuDatabase.getInstance(getActivity()).getDao2().update(mCon));
                mTitle.setText(mCon.getTitle());
                mCompany.setText(mCon.getCompany());
                mBuyDate.setText(mCon.getDate().toInstant().toString().subSequence(0,10));
                String imagePath = mCon.getImage();
                if (imagePath!=null)
                    image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            }

        }
    }
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