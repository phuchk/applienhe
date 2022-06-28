package com.example.linh.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linh.activity.DetailActivity;
import com.example.linh.minterfcae.Interface;
import com.example.linh.R;
import com.example.linh.adapter.ContactAdapter;
import com.example.linh.adapter.SearchContactAdapter;
import com.example.linh.data.SQLHelper;
import com.example.linh.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class FragmentContact extends Fragment {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private SearchContactAdapter sAdapter;
    private SearchView search;
    private SQLHelper db;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.contact_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new SQLHelper(view.getContext());

        toolbar = view.findViewById(R.id.mn11);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.more) {
                    checkContactPermissions();
                }
                return true;
            }
        });
        recyclerView = view.findViewById(R.id.contact_recyclerview);
        search = view.findViewById(R.id.contact_search);
        adapter = new ContactAdapter(new Interface() {
            @Override
            public void onClick(Contact c) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("id", c.getId());
                startActivity(intent);
            }
        }, new Interface() {
            @Override
            public void onClick(Contact c) {
                db.updateLove(c);
            }
        });
        sAdapter = new SearchContactAdapter(new Interface() {
            @Override
            public void onClick(Contact c) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("id", c.getId());
                startActivity(intent);
            }
        });

        List<Contact> mlist = db.getAll();
        adapter.setList(mlist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }

            private void filter(String s) {
                List<Contact> filterlist = new ArrayList<>();
                filterlist = db.search(s);
                String b = "aaa";
                if (s.equals(null) || s.length() == 0) {
                    List<Contact> mlist1 = new ArrayList<>();
                    sAdapter.setList(mlist1);
                    recyclerView.setAdapter(sAdapter);

                    List<Contact> mlist = new ArrayList<>();
                    mlist = db.search(s);
                    adapter.setList(mlist);
                    recyclerView.setAdapter(adapter);
                }
                if (filterlist.isEmpty()) {
                } else if (!s.equals(null) && s.length() > 0)  {
                    sAdapter.setList(filterlist);
                    recyclerView.setAdapter(sAdapter);
                }
            }
        });
    }

    private void checkContactPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            readContacts();
        }
    }

    private void readContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, sort);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));

                @SuppressLint("Range") String name = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";

                Cursor phoneCusor = getActivity().getContentResolver().query(uriPhone, null, selection,
                        new String[]{id}, null);

                if (phoneCusor.moveToNext()) {
                    @SuppressLint("Range") String number = phoneCusor.getString(
                            phoneCusor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_contact_user);
                    Contact c = new Contact(number, name, bitmap, "", "", "0");
                    db.addItem(c);
                    phoneCusor.close();
                }
            }
            List<Contact> mlist = db.getAll();
            adapter.setList(mlist);
            Toast.makeText(getActivity(),"Đồng bộ thành công",Toast.LENGTH_SHORT).show();
            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readContacts();
        } else
            checkContactPermissions();
    }


    @Override
    public void onResume() {
        super.onResume();
        List<Contact> mlist = db.getAll();
        adapter.setList(mlist);
    }
}
