package es.unex.giiis.koreku.ui.games;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import org.w3c.dom.Text;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.profile.BuscarPerfiles;
import es.unex.giiis.koreku.ui.profile.NuevoComentario;


public class GameDetailFragment extends Fragment {
    Button addbugs, deletegame;
    TextView mBugs;
    private Games mGa;
    private GameAdapter mAdapter;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    public static GameDetailFragment newInstance(Games g) {
        GameDetailFragment fragment = new GameDetailFragment();
        Bundle args = new Bundle();
        args.putString("title",g.getTitle());
        args.putString("desc",g.getDesc());
        args.putString("status",g.getStatus().toString());
        args.putLong("dateLong",DateConverter.toTimestamp(g.getBuydate()));
        args.putString("image",g.getImage());
        args.putString("genre", g.getGenero());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        if (args != null) {
            String status = args.getString("status");
            if (status.equals("FINISHED"))
                mGa = new Games(args.getString("title"), Games.Status.FINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),args.getString("genre"));
            else
                mGa = new Games(args.getString("title"), Games.Status.NOTFINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),args.getString("genre"));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.game_detail, container, false);
        // Show item content
        mBugs = v.findViewById(R.id.bug_details);
        TextView mTitle = v.findViewById(R.id.titleGameDetail);
        TextView mDesc = v.findViewById(R.id.descGameDetail);
        TextView mBuyDate = v.findViewById(R.id.editTextDate);
        ImageView image = v.findViewById(R.id.imageViewGame);
        TextView bug_details = v.findViewById(R.id.bug_details);
        TextView mStatus = v.findViewById(R.id.statusDetail);
        TextView mGenre = v.findViewById(R.id.genreDetail);
        bug_details.setText(mGa.getBugs());

        mTitle.setText(mGa.getTitle());
        mDesc.setText(mGa.getDesc());
        if(mGa.getStatus().toString().equals("FINISHED"))
            mStatus.setText("OK");
        else
            mStatus.setText("NO");
        Instant buyDate = mGa.getBuydate().toInstant();
        Instant correct = buyDate.plus(1, ChronoUnit.DAYS);
        mBuyDate.setText(correct.toString().subSequence(0,10));
        String imagePath = mGa.getImage();
        if (imagePath!=null)
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        mGenre.setText(mGa.getGenero());
        bug_details.setText(mGa.getBugs());


        addbugs = (Button) v.findViewById(R.id.error_button);
        addbugs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), GameAddBug.class);
                startActivityForResult(intent, 1);
            }
        });

        deletegame = (Button) v.findViewById(R.id.delete_game);
        deletegame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        db.getDao1().deleteGame(mGa.getTitle());
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
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                mGa.setBugs(data.getStringExtra("bugs"));
                AppExecutors.getInstance().diskIO().execute(() -> KorekuDatabase.getInstance(getActivity()).getDao1().update(mGa));
                mBugs.setText(mGa.getBugs());
            }
        }
    }

    @Override public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mGa.getTitle());
    }

    @Override public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(((AppCompatActivity)getActivity()).getTitle());
    }
}