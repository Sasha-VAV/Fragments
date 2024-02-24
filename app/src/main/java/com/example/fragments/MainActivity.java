package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Valute> valutes = new ArrayList<>();
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));



        RecyclerView recyclerView = findViewById(R.id.RV);
        ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
        recyclerView.setAdapter(valuteAdapter);
    }
}