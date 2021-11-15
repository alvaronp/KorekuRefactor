package es.unex.giiis.koreku.ui.consoles;

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
            mCon = new Consolas(args.getString("title"), DateConverter.toDate(args.getLong("dateLong")),args.getString("comp"),args.getString("image"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.console_detail, container, false);
        // Show item content
        TextView mTitle = v.findViewById(R.id.titleGameDetail);
        TextView mCompany = v.findViewById(R.id.descGameDetail);
        EditText mBuyDate = v.findViewById(R.id.editTextDate);
        ImageView image = v.findViewById(R.id.imageViewGame);
        mTitle.setText(mCon.getTitle());
        mCompany.setText(mCon.getCompany());
        mBuyDate.setText(mCon.getDate().toString());
        image.setImageURI(Uri.parse(mCon.getImage()));
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