package com.example.linh.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.linh.R;
import com.example.linh.data.SQLHelper;
import com.example.linh.model.Contact;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgContact, imgCall, imgMessage, imgVideoCall;
    private ImageView imgBack, imgEdit, imgStar, imgRemove;
    private TextView tvName, tvPhone;
    private Contact c;
    private SQLHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        db = new SQLHelper(this);
        c = db.getById(id);

        imgCall = findViewById(R.id.call);
        imgContact = findViewById(R.id.detail_img);
        imgVideoCall = findViewById(R.id.call_video);
        tvName = findViewById(R.id.detail_name);
        tvPhone = findViewById(R.id.detail_phone);
        imgMessage = findViewById(R.id.message);

        imgBack = findViewById(R.id.ic_back);
        imgEdit = findViewById(R.id.ic_edit);
        imgStar = findViewById(R.id.ic_star);
        imgRemove = findViewById(R.id.ic_trash);

        imgContact.setImageBitmap(c.getImg());
        tvName.setText(c.getName());
        tvPhone.setText(c.getPhone());

        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        imgRemove.setOnClickListener(view -> {
            removeContact();
        });
        imgEdit.setOnClickListener(view -> {
            Intent intent1 = new Intent(DetailActivity.this, EditActivity.class);
            intent1.putExtra("id", String.valueOf(id));
            startActivity(intent1);
        });

        imgCall.setOnClickListener(view -> {
            askPermissionAndCall();
        });

        imgMessage.setOnClickListener(view -> {
            Uri uri = Uri.parse("smsto:" + c.getPhone());
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra("sms_body", "");
            startActivity(it);
        });
        if (c.getIsLove().equals("1"))
            imgStar.setColorFilter(Color.argb(255, 52, 107, 235));
        else
            imgStar.setColorFilter(Color.argb(140, 0, 0, 0));
        imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String change = c.getIsLove();
                if (change.equals("1")) {
                    c.setIsLove("0");
                    imgStar.setColorFilter(Color.argb(140, 0, 0, 0));
                } else {
                    c.setIsLove("1");
                    imgStar.setColorFilter(Color.argb(255, 52, 107, 235));
                }
                db.updateLove(c);
            }

        });
    }


    private void askPermissionAndCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if (result != 0) {
                requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        1
                );
                return;
            }
        }
        callNow();
    }

    private void callNow() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + c.getPhone()));
        startActivity(callIntent);
        String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        c.setTimelastuse(time);
        db.updateLast(c);
    }


    private void removeContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(c);
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "???? x??a 1 li??n h???", Toast.LENGTH_SHORT).show();
                    }
                })
                .setTitle("B???n c?? ch???c ch???n mu???n x??a kh??ng?")
                .setMessage("N???u b???n ???n v??o t??y ch???n x??a,m???c li??n h??? n??y " +
                        "s??? b??? Contact x??a v??nh vi???n kh???i thi???t b??? c???a b???n," +
                        " b???n s??? kh??ng th??? kh??i ph???c l???i ???????c.");
        builder.show();
    }
}