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
import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.consoles.UpdataConsole;
import es.unex.giiis.koreku.ui.profile.BuscarPerfiles;
import es.unex.giiis.koreku.ui.profile.NuevoComentario;


public class GameDetailFragment extends Fragment {
    Button addbugs, deletegame;
    TextView mBugs;
    TextView bugTitle;
    TextView mTitle;
    TextView mDesc;
    TextView mBuyDate;
    ImageView image;
    TextView bug_details;
    TextView mStatus;
    TextView mGenre;
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
        args.putString("bugs",g.getBugs());
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
                mGa = new Games(args.getLong(("id")), args.getString("title"), Games.Status.FINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),args.getString("genre"),args.getString("bugs"));
            else
                mGa = new Games(args.getLong(("id")), args.getString("title"), Games.Status.NOTFINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),args.getString("genre"),args.getString("bugs"));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.game_detail, container, false);
        // Show item content
        mBugs = v.findViewById(R.id.bug_details);
        mTitle = v.findViewById(R.id.titleGameDetail);
        mDesc = v.findViewById(R.id.descGameDetail);
        mBuyDate = v.findViewById(R.id.editTextDate);
        image = v.findViewById(R.id.imageViewGame);
        bug_details = v.findViewById(R.id.bug_details);
        mStatus = v.findViewById(R.id.statusDetail);
        mGenre = v.findViewById(R.id.genreDetail);
        bugTitle = v.findViewById(R.id.bugstitle);
        if (mGa.getBugs()!=null){
            bug_details.setText(mGa.getBugs());
            bugTitle.setVisibility(View.VISIBLE);
            bug_details.setVisibility(View.VISIBLE);
        }

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

        Button updategame = (Button) v.findViewById(R.id.update_game);
        updategame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateGame.class);

                intent.putExtra("titulo", mGa.getTitle());
                intent.putExtra("desc", mGa.getDesc());
                intent.putExtra("date", mGa.getBuydate().toInstant());
                intent.putExtra("image", mGa.getImage());
                intent.putExtra("genre", mGa.getGenero());
                intent.putExtra("bugs", mGa.getBugs());
                startActivityForResult(intent, 0);
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
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        db.getDao1().update(mGa.getTitle(),mGa.getBugs());
                    }
                });
                mBugs.setVisibility(View.VISIBLE);
                bugTitle.setVisibility(View.VISIBLE);
                mBugs.setText(mGa.getBugs());
            }
        }
        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                Games g = new Games(data);
                mGa.setTitle(g.getTitle());
                mGa.setDesc(g.getDesc());
                mGa.setBuydate(g.getBuydate());
                mGa.setImage(g.getImage());
                mGa.setStatus(g.getStatus());
                mGa.setGenero(g.getGenero());
                mGa.setBugs(g.getBugs());

                AppExecutors.getInstance().diskIO().execute(() -> KorekuDatabase.getInstance(getActivity()).getDao1().update(mGa));
                mTitle.setText(mGa.getTitle());
                mDesc.setText(mGa.getDesc());
                //mBuyDate.setText(mGa.getBuydate());
                //mStatus.setText(mGa.getStatus());
                mGenre.setText(mGa.getGenero());
                mBugs.setText(mGa.getBugs());

                String imagePath = mGa.getImage();
                if (imagePath!=null)
                    image.setImageBitmap(BitmapFactory.decodeFile(imagePath));


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