package com.example.sahin.navigasyon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Go extends AppCompatActivity {
    ImageButton btnucakb,btnotob,btnarack,btntrenb,btnaraba,btnyuru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);
        btnucakb=(ImageButton)findViewById(R.id.btnGOUckbltal);
        btnotob=(ImageButton)findViewById(R.id.btnGOgotbsbal);
        btnarack=(ImageButton)findViewById(R.id.btnGOarckrala);
        btntrenb=(ImageButton)findViewById(R.id.btnGOtrnblal);
        btnaraba=(ImageButton)findViewById(R.id.btnGOkendiaracin);
        btnyuru=(ImageButton)findViewById(R.id.btnGOyuruyerek);


        if(MapsActivity.baslangicnoktasi!=null && MapsActivity.bitisnoktasi!=null)
        {
        btnyuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(Go.this,Turlar.class);
                startActivity(in);

            }
        });
        btnaraba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(Go.this,OtelBul.class);
                startActivity(in);
            }
        });
        btntrenb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(Go.this,TrainBiletal.class);
                startActivity(in);
            }
        });
        btnarack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Go.this,AracKirala.class);
                startActivity(in);
            }
        });
        btnotob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Go.this,Otobusbiletial.class);
                startActivity(in);
            }
        });

        btnucakb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(Go.this,Ucakbileti.class);
                startActivity(in);

            }
        });
    }
    else
        {
            Toast.makeText(Go.this,"Rota seçimi yapılmadığı için bu menü kullanılamaz",Toast.LENGTH_LONG).show();
        }
    }
}
