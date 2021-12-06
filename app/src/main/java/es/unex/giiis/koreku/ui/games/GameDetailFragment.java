package es.unex.giiis.koreku.ui.games;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.Instant;

import es.unex.giiis.koreku.AppContainer;
import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.MyApplication;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;


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
    TextView mConsoleAPI;
    TextView mConsole;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    public static GameDetailFragment newInstance(Games g) {
        GameDetailFragment fragment = new GameDetailFragment();
        Bundle args = new Bundle();
        args.putLong("id", g.getId());
        args.putString("title",g.getTitle());
        args.putString("desc",g.getDesc());
        args.putString("status",g.getStatus().toString());
        args.putLong("dateLong",DateConverter.toTimestamp(g.getBuydate()));
        args.putString("image",g.getImage());
        args.putString("genre", g.getGenero());
        args.putString("bugs",g.getBugs());
        args.putString("console", g.getConsole());
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
                mGa = new Games(args.getLong(("id")), args.getString("title"), Games.Status.FINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),args.getString("bugs"),args.getString("genre"),args.getString("console"));
            else
                mGa = new Games(args.getLong(("id")), args.getString("title"), Games.Status.NOTFINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),args.getString("bugs"),args.getString("genre"),args.getString("console"));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.game_detail, container, false);
        TransitionInflater tInf = TransitionInflater.from(this.getActivity());
        setEnterTransition(tInf.inflateTransition(R.transition.slide_right));

        // Show item content
        mBugs = v.findViewById(R.id.bug_details);
        mTitle = v.findViewById(R.id.titleGameDetail);
        mDesc = v.findViewById(R.id.descGameDetail);
        mBuyDate = v.findViewById(R.id.editTextDate);
        image = v.findViewById(R.id.imageViewGame);
        bug_details = v.findViewById(R.id.bug_details);
        mStatus = v.findViewById(R.id.statusDetail);
        mGenre = v.findViewById(R.id.genreDetail);
        mConsoleAPI = v.findViewById(R.id.consoleApiDetail);
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
        mBuyDate.setText(buyDate.toString().subSequence(0,10));
        String imagePath = mGa.getImage();
        if (imagePath!=null)
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        mGenre.setText(mGa.getGenero());
        mConsole = v.findViewById(R.id.console);
        if(mGa.getConsole().length() > 0) {
            mConsoleAPI.setVisibility(View.VISIBLE);
            mConsole.setVisibility(View.VISIBLE);
            mConsoleAPI.setText(mGa.getConsole());
        }
        else {
            mConsole.setVisibility(View.INVISIBLE);
            mConsoleAPI.setVisibility(View.INVISIBLE);
        }


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
                AppContainer appContainer = ((MyApplication) GameDetailFragment.this.getActivity().getApplication()).appContainer;
                GamesViewModel mViewModel = new ViewModelProvider(GameDetailFragment.this, appContainer.gfactory).get(GamesViewModel.class);
                mViewModel.delete(mGa.getTitle());
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
                AppContainer appContainer = ((MyApplication) GameDetailFragment.this.getActivity().getApplication()).appContainer;
                GamesViewModel mViewModel = new ViewModelProvider(GameDetailFragment.this, appContainer.gfactory).get(GamesViewModel.class);
                mViewModel.update(mGa);
                mBugs.setText(mGa.getBugs());
            }
        }
        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                Games g = new Games(data);
                mGa.setTitle(g.getTitle());
                mGa.setDesc(g.getDesc());
                mGa.setBuydate(g.getBuydate());
                if(!g.getImage().equals("")) {
                    mGa.setImage(g.getImage());
                }
                mGa.setStatus(g.getStatus());
                mGa.setGenero(g.getGenero());
                mGa.setBugs(g.getBugs());
                mGa.setConsole(g.getConsole());

                AppContainer appContainer = ((MyApplication) GameDetailFragment.this.getActivity().getApplication()).appContainer;
                GamesViewModel mViewModel = new ViewModelProvider(GameDetailFragment.this, appContainer.gfactory).get(GamesViewModel.class);
                mViewModel.update(mGa);

                mTitle.setText(mGa.getTitle());
                mDesc.setText(mGa.getDesc());
                mGenre.setText(mGa.getGenero());
                mBugs.setText(mGa.getBugs());
                mBuyDate.setText(mGa.getBuydate().toInstant().toString().subSequence(0,10));
                if(mGa.getConsole().length() > 0) {
                    mConsoleAPI.setVisibility(View.VISIBLE);
                    mConsole.setVisibility(View.VISIBLE);
                    mConsoleAPI.setText(mGa.getConsole());
                }
                else {
                    mConsole.setVisibility(View.INVISIBLE);
                    mConsoleAPI.setVisibility(View.INVISIBLE);
                }

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