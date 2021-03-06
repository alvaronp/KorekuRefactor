package es.unex.giiis.koreku.ui.service;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.Instant;

import es.unex.giiis.koreku.AppContainer;
import es.unex.giiis.koreku.MyApplication;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.DateConverter;


public class ServiceDetailFragment extends Fragment {

    private Service mSer;

    Button delete;

    TextView mTitle;
    TextView mSubscription;
    TextView mEmail;
    TextView mPrice;
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
        args.putLong("dueDate", DateConverter.toTimestamp(service.getDueDate()));

        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();

        if (args != null) {

            mSer = new Service(args.getLong("id") , args.getString("title"), args.getString("subscription"),
                                args.getString("email"), args.getString("price"),
                                DateConverter.toDate(args.getLong("dueDate")));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.service_detail, container, false);
        TransitionInflater tInf = TransitionInflater.from(this.getActivity());
        setEnterTransition(tInf.inflateTransition(R.transition.slide_right));
        // Show item content

        mTitle = v.findViewById(R.id.titleServiceDetail);
        mSubscription = v.findViewById(R.id.subsServiceDetail);
        mEmail = v.findViewById(R.id.emailServiceDetail);
        mPrice = v.findViewById(R.id.priceServiceDetail);
        mDueDate = v.findViewById(R.id.dueDateServiceDetail);

        mTitle.setText(mSer.getTitle());
        mSubscription.setText(mSer.getSubscription());
        mEmail.setText(mSer.getEmail());
        mPrice.setText(mSer.getPrice());

        Instant DueDate = mSer.getDueDate().toInstant();
        mDueDate.setText(DueDate.toString().subSequence(0,10));

        // Delete Service

        delete = (Button) v.findViewById(R.id.deleteButtonService);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // - Attach Listener to FloatingActionButton. Implement onClick()

                AppContainer appContainer = ((MyApplication) ServiceDetailFragment.this.getActivity().getApplication()).appContainer;
                ServiceViewModel mViewModel = new ViewModelProvider(ServiceDetailFragment.this, appContainer.sfactory).get(ServiceViewModel.class);
                mViewModel.delete(mSer.getTitle());
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
                intent.putExtra("price", mSer.getPrice());
                intent.putExtra("dueDate", mSer.getDueDate().toInstant().toString().subSequence(0, 10));

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
        if (requestCode == 0) {

            if (resultCode == getActivity().RESULT_OK) {

                Service s = new Service(data);
                mSer.setTitle(s.getTitle());
                mSer.setSubscription(s.getSubscription());
                mSer.setEmail(s.getEmail());
                mSer.setPrice(s.getPrice());
                mSer.setDueDate(s.getDueDate());

                AppContainer appContainer = ((MyApplication) ServiceDetailFragment.this.getActivity().getApplication()).appContainer;
                ServiceViewModel mViewModel = new ViewModelProvider(ServiceDetailFragment.this, appContainer.sfactory).get(ServiceViewModel.class);
                mViewModel.update(mSer);

                mTitle.setText(mSer.getTitle());
                mSubscription.setText(mSer.getSubscription());
                mEmail.setText(mSer.getEmail());
                mPrice.setText(mSer.getPrice());
                mDueDate.setText(mSer.getDueDate().toInstant().toString().subSequence(0,10));

            }
        }
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
