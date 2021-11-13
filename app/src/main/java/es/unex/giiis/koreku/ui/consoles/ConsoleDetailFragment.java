package es.unex.giiis.koreku.ui.consoles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;


public class ConsoleDetailFragment extends Fragment {

    private Consolas mCon;


    public ConsoleDetailFragment() {
        // Required empty public constructor
    }

    public static ConsoleDetailFragment newInstance(Consolas c) {
        ConsoleDetailFragment fragment = new ConsoleDetailFragment();
        Bundle args = new Bundle();
        args.putString("title",c.getTitle());
        args.putString("company", c.getCompany());
        args.putLong("date", DateConverter.toTimestamp(c.getDate()));
        args.putString("image",c.getImage());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCon.setTitle(getArguments().getString("title"));
            mCon.setCompany(getArguments().getString("company"));
            mCon.setDate(DateConverter.toDate(getArguments().getLong("date")));
            mCon.setImage(getArguments().getString("image"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.console_detail, container, false);

        // Show item content
        TextView tvDetail = v.findViewById(R.id.item_detail);
        tvDetail.setText(mCon.getTitle());

        return v;
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