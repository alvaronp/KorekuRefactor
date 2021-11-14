package es.unex.giiis.koreku.ui.games;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import es.unex.giiis.koreku.Games;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;


public class GameDetailFragment extends Fragment {

    private Games mGa;

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
        ImageView image = v.findViewById(R.id.imageView);
        mTitle.setText(mGa.getTitle());
        mDesc.setText(mGa.getDesc());
        mBuyDate.setText(mGa.getBuydate().toString());
        image.setImageURI(Uri.parse(mGa.getImage()));
        image.setImageDrawable(getResources().getDrawable(R.drawable.ic_consoles));
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