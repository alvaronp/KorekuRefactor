package es.unex.giiis.koreku.ui.consoles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.List;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;


public class ConsoleDetailFragment extends Fragment {

    private Consolas mCon;
    private String idCon = "id";


    public ConsoleDetailFragment() {
        // Required empty public constructor
    }

    public static ConsoleDetailFragment newInstance(Long id) {
        ConsoleDetailFragment fragment = new ConsoleDetailFragment();
        Bundle args = new Bundle();
        args.putLong(fragment.idCon, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        if (args != null) {
            mCon = new Consolas(args.getLong(idCon), "", new Date(), "","");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.console_detail, container, false);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCon = KorekuDatabase.getInstance(getActivity()).getDao2().get(mCon.getId());
            }
        });
        // Show item content
        TextView mTitle = v.findViewById(R.id.titleConsoleDetail);
        mTitle.setText(mCon.getTitle());
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