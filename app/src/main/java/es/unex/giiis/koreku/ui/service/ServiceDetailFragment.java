package es.unex.giiis.koreku.ui.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.Service;
import es.unex.giiis.koreku.roomdb.DateConverter;

public class ServiceDetailFragment extends Fragment {

    private Service mSer;

    public ServiceDetailFragment() {

        // Required empty public constructor

    }

    public static ServiceDetailFragment newInstance(Service service) {

        ServiceDetailFragment fragment = new ServiceDetailFragment();
        Bundle args = new Bundle();

        args.putLong("id", service.getId());
        args.putString("subscription",service.getSubscription());
        args.putString("email",service.getEmail());
        args.putString("price",service.getPrice());
        args.putLong("startDate", DateConverter.toTimestamp(service.getStartDate()));
        args.putLong("dueDate", DateConverter.toTimestamp(service.getDueDate()));

        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();

        if (args != null) {

            mSer = new Service(args.getString("subscription"), args.getString("email"), args.getString("price"),
                    DateConverter.toDate(args.getLong("startDate")), DateConverter.toDate(args.getLong("dueDate")));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.service_detail, container, false);

        // Show item content

        TextView mSubscription = v.findViewById(R.id.subsServiceDetail);
        TextView mEmail = v.findViewById(R.id.emailServiceDetail);
        TextView mPrice = v.findViewById(R.id.priceServiceDetail);
        TextView mStartDate = v.findViewById(R.id.startDateServiceDetail);
        TextView mDueDate = v.findViewById(R.id.dueDateServiceDetail);

        mSubscription.setText(mSer.getSubscription());
        mEmail.setText(mSer.getEmail());
        mPrice.setText(mSer.getPrice());
        Instant StartDate = mSer.getStartDate().toInstant();
        Instant startCorrect = StartDate.plus(1, ChronoUnit.DAYS);
        mStartDate.setText(startCorrect.toString().subSequence(0,10));

        Instant DuetDate = mSer.getDueDate().toInstant();
        Instant dueCorrect = StartDate.plus(1, ChronoUnit.DAYS);
        mDueDate.setText(dueCorrect.toString().subSequence(0,10));

        return v;

    }

    @Override public void onResume() {

        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mSer.getSubscription());

    }

    @Override public void onStop() {

        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(((AppCompatActivity)getActivity()).getTitle());

    }

}
