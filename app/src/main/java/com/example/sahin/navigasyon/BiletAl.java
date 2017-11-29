package com.example.sahin.navigasyon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class BiletAl extends AppCompatActivity {
ImageButton btnucakb,btnotob,btnarack,btntrenb,btnotelb,btnturla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilet_al);
        btnucakb=(ImageButton)findViewById(R.id.btnimgUckbltal);
        btnotob=(ImageButton)findViewById(R.id.btnimgotbsbal);
        btnarack=(ImageButton)findViewById(R.id.btnimgarckrala);
        btntrenb=(ImageButton)findViewById(R.id.btnimgtrnblal);
        btnotelb=(ImageButton)findViewById(R.id.imgbtnhotelrzvs);
        btnturla=(ImageButton)findViewById(R.id.btnimgturlar);
        btnturla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(BiletAl.this,Turlar.class);
                startActivity(in);

            }
        });
        btnotelb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(BiletAl.this,OtelBul.class);
                startActivity(in);
            }
        });
        btntrenb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(BiletAl.this,TrainBiletal.class);
                startActivity(in);
            }
        });
        btnarack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(BiletAl.this,AracKirala.class);
                startActivity(in);
            }
        });
        btnotob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(BiletAl.this,Otobusbiletial.class);
                startActivity(in);
            }
        });

        btnucakb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(BiletAl.this,Ucakbileti.class);
                startActivity(in);

            }
        });



    }
}
