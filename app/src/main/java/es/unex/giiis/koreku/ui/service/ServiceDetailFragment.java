package es.unex.giiis.koreku.ui.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.Service;
import es.unex.giiis.koreku.roomdb.DateConverter;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.ui.profile.UpdateProfile;

public class ServiceDetailFragment extends Fragment {

    private Service mSer;

    Button delete;

    TextView mTitle;
    TextView mSubscription;
    TextView mEmail;
    TextView mPrice;
    TextView mStartDate;
    TextView mDueDate;

    public ServiceDetailFragment() {

        // Required empty public constructor

    }

    public static ServiceDetailFragment newInstance(Service service) {

        ServiceDetailFragment fragment = new ServiceDetailFragment();
        Bundle args = new Bundle();

        args.putLong("id", service.getId());
        args.putString("title", service.getTitle());
        args.putString("subscription", service.getSubscription());
        args.putString("email", service.getEmail());
        args.putString("price", service.getPrice());
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

            mSer = new Service(args.getString("title"), args.getString("subscription"),
                                args.getString("email"), args.getString("price"),
                                DateConverter.toDate(args.getLong("startDate")),
                                DateConverter.toDate(args.getLong("dueDate")));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.service_detail, container, false);

        // Show item content

        mTitle = v.findViewById(R.id.titleServiceDetail);
        mSubscription = v.findViewById(R.id.subsServiceDetail);
        mEmail = v.findViewById(R.id.emailServiceDetail);
        mPrice = v.findViewById(R.id.priceServiceDetail);
        mStartDate = v.findViewById(R.id.startDateServiceDetail);
        mDueDate = v.findViewById(R.id.dueDateServiceDetail);

        mTitle.setText(mSer.getTitle());
        mSubscription.setText(mSer.getSubscription());
        mEmail.setText(mSer.getEmail());
        mPrice.setText(mSer.getPrice());

        Instant StartDate = mSer.getStartDate().toInstant();
        Instant startCorrect = StartDate.plus(1, ChronoUnit.DAYS);
        mStartDate.setText(startCorrect.toString().subSequence(0,10));

        Instant DueDate = mSer.getDueDate().toInstant();
        Instant dueCorrect = DueDate.plus(1, ChronoUnit.DAYS);
        mDueDate.setText(dueCorrect.toString().subSequence(0,10));

        // Delete Service

        delete = (Button) v.findViewById(R.id.deleteButtonService);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AppExecutors.getInstance().diskIO().execute(new Runnable() {

                    @Override
                    public void run() {

                        KorekuDatabase db = KorekuDatabase.getInstance(getActivity());
                        db.getDao4().deleteService(mSer.getTitle());

                    }
                });

                getActivity().onBackPressed();

            }
        });

        // Edit Service

        Button editButton = (Button) v.findViewById(R.id.editButton_service_detail);
        editButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent intent = new Intent(getActivity(), UpdateService.class);

                intent.putExtra("title", mSer.getTitle());
                intent.putExtra("subscription", mSer.getSubscription());
                intent.putExtra("email", mSer.getEmail());
                intent.putExtra("startDate", mSer.getStartDate());
                intent.putExtra("dueDate", mSer.getDueDate());

                startActivityForResult(intent, 0);

            }
        });

        return v;

    }

    @Override public void onResume() {

        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mSer.getTitle());

    }

    @Override public void onStop() {

        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(((AppCompatActivity)getActivity()).getTitle());

    }

}