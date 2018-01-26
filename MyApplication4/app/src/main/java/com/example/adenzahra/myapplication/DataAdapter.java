package com.example.adenzahra.myapplication;

/**
 * Created by Aden Zahra on 1/26/2018.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.adenzahra.myapplication.Models.Library;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    ArrayList<Library> libraryList;
    Context context;

    public DataAdapter(Context context, ArrayList<Library> libraryList) {
        this.libraryList =libraryList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.library_list,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Library l1 = libraryList.get(position);
        Toast.makeText(context, ""+l1.getIsbn(), Toast.LENGTH_SHORT).show();
        holder.id.setText(l1.getId()+"");
        holder.name.setText(l1.getName()+"");
        holder.isbn.setText(l1.getIsbn()+"");
        holder.author.setText(l1.getAuthor()+"");
        holder.cost.setText(l1.getCost()+"");
        holder.layout.setBackgroundColor(position%2==1?Color.RED:Color.GREEN);
    }


    @Override
    public int getItemCount() {
        return libraryList.size();
    }


    /**
     * DataAdapter.ViewHolder Class is below It will be used for designing
     * and setting the data entries in the adapter for recyclerview
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView name;
        public TextView isbn;
        public TextView author;
        public TextView cost;
        public LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.txt_id);
            name = (TextView) view.findViewById(R.id.txt_name);
            isbn = (TextView) view.findViewById(R.id.txt_email);
            author = (TextView) view.findViewById(R.id.txt_address);
            cost = (TextView) view.findViewById(R.id.txt_cost);
            layout = (LinearLayout) view.findViewById(R.id.layoutId);
        }
    }


}
