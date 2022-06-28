package com.example.linh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linh.minterfcae.Interface;
import com.example.linh.R;
import com.example.linh.model.Contact;

import java.util.ArrayList;
import java.util.List;


public class SearchContactAdapter extends RecyclerView.Adapter<SearchContactAdapter.MyViewHolder> {
    List<Contact> mlist = new ArrayList<>();
    Interface mInterface;


    public SearchContactAdapter(Interface mInterface) {
        this.mInterface = mInterface;

    }

    public void add(Contact item) {
        mlist.add(item);
        notifyDataSetChanged();
    }

    public void setList(List<Contact> list) {
        mlist = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_contact, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mlist.get(position).getName());
        holder.phone.setText(mlist.get(position).getPhone());
        holder.img.setImageBitmap(mlist.get(position).getImg());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onClick(mlist.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,phone;
        ImageView img;
        LinearLayout cardView;

        public MyViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.search_contact_name);
            img = v.findViewById(R.id.search_contact_img);
            phone = v.findViewById(R.id.search_contact_phone);
            cardView = v.findViewById(R.id.search_cardView);
        }
    }

}
