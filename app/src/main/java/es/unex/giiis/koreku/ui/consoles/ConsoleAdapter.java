package es.unex.giiis.koreku.ui.consoles;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.roomdb.KorekuDatabase;
import es.unex.giiis.koreku.Consolas;
import es.unex.giiis.koreku.R;


public class ConsoleAdapter extends RecyclerView.Adapter<ConsoleAdapter.ViewHolder> {
    private List<Consolas> mCon = new ArrayList<Consolas>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Consolas consola);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConsoleAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConsoleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.console_item, parent, false);

        return new ViewHolder(mContext,v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mCon.get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCon.size();
    }

    public void add(Consolas c) {

        mCon.add(c);
        notifyDataSetChanged();

    }

    public void clear(){

        mCon.clear();
        notifyDataSetChanged();

    }

    public void load(List<Consolas> items){

        mCon.clear();
        mCon = items;
        notifyDataSetChanged();

    }

    public Object getConsole(int pos) { return mCon.get(pos); }

     static class ViewHolder extends RecyclerView.ViewHolder {

         private Context mContext;

        private TextView title;
        private TextView company;
        private TextView dateView;
        private ImageView imageView;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            mContext = context;

            // - Get the references to every widget of the Item View
            title =  itemView.findViewById(R.id.titleView);
            company =  itemView.findViewById(R.id.companyView);
            dateView =  itemView.findViewById(R.id.dateView);
            //imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(final Consolas c, final OnItemClickListener listener) {

            // - Display Title in TextView
            title.setText(c.getTitle());

            // - Display Company in a TextView
            company.setText(c.getCompany());

            //  - Display Date.
            dateView.setText(Consolas.FORMAT.format(c.getDate()).subSequence(0,10));

            //imageView.setImageURI(Uri.parse(c.getImage()));

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(c);
                }
            });
        }
    }

}
