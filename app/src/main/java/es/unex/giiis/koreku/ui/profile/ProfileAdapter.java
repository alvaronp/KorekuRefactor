package es.unex.giiis.koreku.ui.profile;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.Perfil;
import es.unex.giiis.koreku.R;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private List<Perfil> mCon = new ArrayList<Perfil>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Perfil perfil);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProfileAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_item, parent, false);

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

    public void add(Perfil c) {

        mCon.add(c);
        notifyDataSetChanged();

    }

    public void clear(){

        mCon.clear();
        notifyDataSetChanged();

    }

    public void load(List<Perfil> items){

        mCon.clear();
        mCon = items;
        notifyDataSetChanged();

    }

    public Object getPerfil(int pos) { return mCon.get(pos); }

     static class ViewHolder extends RecyclerView.ViewHolder {

         private Context mContext;

        private TextView title;
        private ImageView imageView;

        public ViewHolder(Context context, View itemView) {
            super(itemView);

            mContext = context;

            // - Get the references to every widget of the Item View
            title =  itemView.findViewById(R.id.titleView);
            imageView = itemView.findViewById(R.id.imagenprofileitem);
        }

        public void bind(final Perfil c, final OnItemClickListener listener) {

            // - Display Title in TextView
            title.setText(c.getTitle());


            imageView.setImageURI(Uri.parse(c.getImage()));

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(c);
                }
            });
        }
    }

}
