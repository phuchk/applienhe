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


public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.MyViewHolder> {
    List<Contact> mlist = new ArrayList<>();
    Interface mInterface, mInterface1;

    public LoveAdapter(Interface mInterface, Interface mInterface1) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.love_item, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mlist.get(position).getName());
        holder.img.setImageBitmap(mlist.get(position).getImg());
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

        TextView name;
        ImageView img, imgCall;
        LinearLayout rView;

        public MyViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.contact_name1);
            img = v.findViewById(R.id.contact_img1);
            imgCall = v.findViewById(R.id.star_contact1);
            rView = v.findViewById(R.id.contact_cardView1);
        }
    }
}
