package es.unex.giiis.koreku.ui.games;

import android.content.Intent;
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

import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;
import es.unex.giiis.koreku.ui.profile.BuscarPerfiles;


public class GameDetailFragment extends Fragment {
    Button addbugs;
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
                mGa = new Games(args.getString("title"), Games.Status.FINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),null);
            else
                mGa = new Games(args.getString("title"), Games.Status.NOTFINISHED, DateConverter.toDate(args.getLong("dateLong")),args.getString("desc"),args.getString("image"),null);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.game_detail, container, false);
        // Show item content
        TextView mTitle = v.findViewById(R.id.titleGameDetail);
        TextView mDesc = v.findViewById(R.id.descGameDetail);
        EditText mBuyDate = v.findViewById(R.id.editTextDate);
        ImageView image = v.findViewById(R.id.imageViewGame);
        TextView bug_details = v.findViewById(R.id.bug_detail);
        TextView mStatus = v.findViewById(R.id.statusDetail);
        mTitle.setText(mGa.getTitle());
        mDesc.setText(mGa.getDesc());
        mBuyDate.setText(mGa.getBuydate().toString());
        if(mGa.getStatus().toString().equals("FINISHED"))
            mStatus.setText("OK");
        else
            mStatus.setText("NO");
        image.setImageURI(Uri.parse(mGa.getImage()));
        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_games));
        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_consoles));
        bug_details.setText(mGa.getBugs());

        addbugs = (Button) v.findViewById(R.id.error_button);
        addbugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // - Attach Listener to FloatingActionButton. Implement onClick()
                Intent intent = new Intent(getActivity(), GameAddBug.class);
                startActivity(intent);

            }
        });

        return v;
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