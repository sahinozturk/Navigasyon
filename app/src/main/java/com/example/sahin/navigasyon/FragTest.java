package com.example.sahin.navigasyon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Sahin on 25.11.2017.
 */

public class FragTest extends Fragment
{
    View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_test, container, false);

        Log.e("x","FragTest Initialized");
        // okk
        Button btn = (Button) root.findViewById(R.id.btnTest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("x","DENEME BTN CLICKED!");
                Toast.makeText(getActivity(), "111111111111111", Toast.LENGTH_SHORT)
                        .show();



            }
        });

        return root;
    }
}
