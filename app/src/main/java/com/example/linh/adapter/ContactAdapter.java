package com.example.linh.adapter;

import android.graphics.Color;
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


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    List<Contact> mlist = new ArrayList<>();
    Interface mInterface, mInterface1;


    public ContactAdapter(Interface mInterface, Interface mInterface1) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mlist.get(position).getName());
        holder.img.setImageBitmap(mlist.get(position).getImg());
        holder.imgstar.setImageResource(R.drawable.ic_star);
        if (mlist.get(position).getIsLove().equals("1"))
            holder.imgstar.setColorFilter(Color.argb(255, 52, 107, 235));
        else
            holder.imgstar.setColorFilter(Color.argb(140, 0, 0, 0));
        holder.imgstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String change = mlist.get(position).getIsLove();
                if (change.equals("1")) {
                    mlist.get(position).setIsLove("0");
                    holder.imgstar.setColorFilter(Color.argb(140, 0, 0, 0));
                } else {
                    mlist.get(position).setIsLove("1");
                    holder.imgstar.setColorFilter(Color.argb(255, 52, 107, 235));
                }
                mInterface1.onClick(mlist.get(position));
            }
        });
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

        TextView name;
        ImageView img, imgstar;
        private LinearLayout cardView;

        public MyViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.contact_name);
            img = v.findViewById(R.id.contact_img);
            imgstar = v.findViewById(R.id.star_contact);
            cardView = v.findViewById(R.id.contact_cardView);
        }
    }

}
