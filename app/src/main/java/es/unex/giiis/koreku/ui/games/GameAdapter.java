package es.unex.giiis.koreku.ui.games;

import android.content.Context;
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

import es.unex.giiis.koreku.AppExecutors;
import es.unex.giiis.koreku.ui.consoles.Consolas;
import es.unex.giiis.koreku.R;
import es.unex.giiis.koreku.roomdb.KorekuDatabase;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private List<Games> mGa = new ArrayList<Games>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Games game);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GameAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);

        return new ViewHolder(mContext,v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mGa.get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mGa.size();
    }

    public void add(Games g) {

        mGa.add(g);
        notifyDataSetChanged();

    }

    public void clear(){

        mGa.clear();
        notifyDataSetChanged();

    }

    public void load(List<Games> items){

        mGa.clear();
        mGa = items;
        notifyDataSetChanged();

    }

    public Object getGame(int pos) { return mGa.get(pos); }

     static class ViewHolder extends RecyclerView.ViewHolder {

         private Context mContext;

        private TextView title;
        private CheckBox statusView;
        private TextView dateView;
        private ImageView imageView;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            // - Get the references to every widget of the Item View
            title =  itemView.findViewById(R.id.titleView);
            statusView = itemView.findViewById(R.id.statusCheckBox);
            dateView =  itemView.findViewById(R.id.dateView);
        }

        public void bind(final Games g, final OnItemClickListener listener) {

            // - Display Title in TextView
            title.setText(g.getTitle());

            //  - Display Date.
            dateView.setText(Consolas.FORMAT.format(g.getBuydate()).subSequence(0,10));

            //  - Set up Status CheckBox
            statusView.setChecked(g.getStatus() == Games.Status.FINISHED);

            statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if(isChecked)
                        g.setStatus(Games.Status.FINISHED);
                    else
                        g.setStatus(Games.Status.NOTFINISHED);
                    AppExecutors.getInstance().diskIO().execute(() -> KorekuDatabase.getInstance(mContext).getDao1().update(g));
                }});

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(g);
                }
            });
        }
    }

}
