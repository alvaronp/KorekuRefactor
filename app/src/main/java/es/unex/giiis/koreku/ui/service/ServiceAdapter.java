package es.unex.giiis.koreku.ui.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.R;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{

        private List<Service> mServices = new ArrayList<Service>();
        Context mContext;

    public interface OnItemClickListener {

        void onItemClick(Service item);     //Type of the element to be returned

    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor

    public ServiceAdapter(Context context, OnItemClickListener listener) {

        mContext = context;
        this.listener = listener;

    }

    // Create new views (invoked by the layout manager)

    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // - Inflate the View for every element

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);

        return new ViewHolder(mContext,v);

    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(mServices.get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)

    @Override
    public int getItemCount() {

        return mServices.size();

    }

    public void add(Service service) {

        mServices.add(service);
        notifyDataSetChanged();

    }

    public void clear(){

        mServices.clear();
        notifyDataSetChanged();

    }

    public void load(List<Service> items){

        mServices.clear();
        mServices = items;
        notifyDataSetChanged();

    }

    public Object getService(int pos) {

        return mServices.get(pos);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        private TextView title;
        private TextView price;
        private TextView dueDateView;

        public ViewHolder(Context context, View itemView) {

            super(itemView);

            mContext = context;

            // - Get the references to every widget of the Item View

            title =  itemView.findViewById(R.id.service_item_titleView);
            dueDateView =  itemView.findViewById(R.id.service_item_DueDateView);
            price =  itemView.findViewById(R.id.service_item_priceView);

        }

        public void bind(final Service service, final OnItemClickListener listener) {

            // - Displays in TextView

            title.setText(service.getTitle());
            dueDateView.setText(service.FORMAT.format(service.getDueDate()));
            price.setText(service.getPrice());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(service);

                }
            });
        }
    }

}
