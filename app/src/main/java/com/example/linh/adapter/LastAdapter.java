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


public class LastAdapter extends RecyclerView.Adapter<LastAdapter.MyViewHolder> {
    List<Contact> mlist = new ArrayList<>();
    Interface mInterface, mInterface1;

    public LastAdapter(Interface mInterface, Interface mInterface1) {
        this.mInterface = mInterface;
        this.mInterface1 = mInterface1;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mlist.get(position).getName());
        holder.img.setImageBitmap(mlist.get(position).getImg());
        String time[] = mlist.get(position).getTimelastuse().trim().split(" ");
        String date[] = time[0].split("-");
        holder.time.setText(time[1] + " " + date[2] + "-" + date[1] + "-" + date[0]);
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface1.onClick(mlist.get(position));
            }
        });
        holder.rView.setOnClickListener(new View.OnClickListener() {
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

        TextView name, time;
        ImageView img, imgCall;
        LinearLayout rView;

        public MyViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.contact_name2);
            img = v.findViewById(R.id.contact_img2);
            imgCall = v.findViewById(R.id.star_contact2);
            rView = v.findViewById(R.id.contact_cardView2);
            time = v.findViewById(R.id.time_use);
        }
    }
}
