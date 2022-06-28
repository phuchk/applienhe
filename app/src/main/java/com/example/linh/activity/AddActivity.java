package com.example.linh.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.linh.R;
import com.example.linh.data.SQLHelper;
import com.example.linh.model.Contact;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private EditText edPhone, edName;
    private ImageView imgContact, imgBack;
    private Button btn, btnselect;
    private int SELECT_PICTURE = 200;
    private Uri selectedImageUri = Uri.parse("android.resource://com.example.linh/drawable/ic_contact_user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = new Intent(AddActivity.this, MainActivity.class);

        edPhone = findViewById(R.id.new_phone);
        edName = findViewById(R.id.new_name);
        btn = findViewById(R.id.submit);
        imgContact = findViewById(R.id.new_img);
        imgBack = findViewById(R.id.ic_close_add);
        btnselect = findViewById(R.id.select_image);

        SQLHelper db = new SQLHelper(this);
        btn.setOnClickListener(view -> {
            String phone = edPhone.getText().toString().trim();
            String name = edName.getText().toString().trim();
            String name1="";
            name1+=name.substring(0,1).toUpperCase();
            name1+=name.substring(1,name.length());

            Bitmap bitmap = null;
            if (phone.length() == 0 || name.length() == 0) {
                Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
            }
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_contact_user);
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//            Toast.makeText(this,currentDate+" "+currentTime,Toast.LENGTH_SHORT).show();
            Contact c = new Contact(phone, name1, bitmap,  currentTime, currentDate, "0");
            db.addItem(c);
            Toast.makeText(this, "Thêm liên hệ thành công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
        btnselect.setOnClickListener(view -> {
            imageChooser();
        });
        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imgContact.setImageURI(selectedImageUri);
                }
            }
        }
    }
}