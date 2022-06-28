package com.example.linh.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linh.activity.DetailActivity;
import com.example.linh.minterfcae.Interface;
import com.example.linh.R;
import com.example.linh.adapter.LastAdapter;
import com.example.linh.data.SQLHelper;
import com.example.linh.model.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentLast extends Fragment {
    private RecyclerView recyclerView;
    private LastAdapter adapter;
    private SQLHelper db;
    private static final int MY_REQUEST_CODE = 555;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.love_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new SQLHelper(view.getContext());
        List<Contact> mlist = new ArrayList<>();
        mlist = db.getAllByTime();
        recyclerView = view.findViewById(R.id.love_recyclerview);
        adapter = new LastAdapter(new Interface() {
            @Override
            public void onClick(Contact c) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", c.getId());
                startActivity(intent);
            }
        }, new Interface() {
            @Override
            public void onClick(Contact c) {
                askPermissionAndCall(c);
            }
        });
        adapter.setList(mlist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<Contact> mlist = db.getAllByTime();
        adapter.setList(mlist);
    }

    private void askPermissionAndCall(Contact c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE);
            if (result != 0) {
                requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_REQUEST_CODE
                );
                return;
            }
        }
        callNow(c);
    }

    private void callNow(Contact c) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + c.getPhone()));
        startActivity(callIntent);
        String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        c.setTimelastuse(time);
        db.updateLast(c);
    }
}
