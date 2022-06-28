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
import android.widget.TextView;
import android.widget.Toast;

import com.example.linh.R;
import com.example.linh.data.SQLHelper;
import com.example.linh.model.Contact;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    private SQLHelper db;
    private EditText edPhone, edName;
    private TextView textToolbar;
    private ImageView imgContact, imgBack;
    private Button btnSubmit, btnSelect;
    private int SELECT_PICTURE = 200;
    private Uri selectedImageUri = Uri.parse("android.resource://com.example.linh/drawable/ic_contact_user");
    private Contact c;
    boolean isSelectImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db = new SQLHelper(this);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        c = db.getById(id);
        Intent intent = new Intent(EditActivity.this, MainActivity.class);

        edPhone = findViewById(R.id.edit_phone);
        edName = findViewById(R.id.edit_name);
        textToolbar = findViewById(R.id.text_toolbar);
        textToolbar.setText("Chỉnh sửa liên hệ");
        btnSubmit = findViewById(R.id.submit);
        imgContact = findViewById(R.id.edit_img);
        imgBack = findViewById(R.id.ic_close_add);
        btnSelect = findViewById(R.id.select_image_edit);

        edName.setText(c.getName());
        edPhone.setText(c.getPhone());
        imgContact.setImageBitmap(c.getImg());

        btnSubmit.setOnClickListener(view -> {
            String phone = edPhone.getText().toString().trim();
            String name = edName.getText().toString().trim();
            Bitmap bitmap = null;
            if (phone.length() == 0 || name.length() == 0) {
                Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
            }
            if (!isSelectImage)  bitmap = c.getImg();
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            c.setName(name);
            c.setPhone(phone);
            c.setImg(bitmap);
            db.updateContact(c);
            Toast.makeText(this, "Cập nhật liên hệ thành công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
        btnSelect.setOnClickListener(view -> {
            imageChooser();
        });
        imgBack.setOnClickListener(view -> {
            Intent intent1 = new Intent(EditActivity.this, MainActivity.class);
            startActivity(intent1);
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
                    isSelectImage = true;
                }
            }
        }
    }
}