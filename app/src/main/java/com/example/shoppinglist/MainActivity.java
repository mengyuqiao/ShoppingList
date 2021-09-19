package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    private CheckBox all_cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        all_cb = (CheckBox) findViewById(R.id.select_all_cb);

        // generate initial products
        for (int i = 0; i < 200; i++) {
            productList.add(new Product("Test Product Number" + i, i));
        }

        // send data to adapter
        RecyclerView recyclerView = findViewById(R.id.rcView);

        // use linear layout in recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // send product list to the adapter of recyclerview
        ProductAdapter productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);

        // add listener
        productAdapter.setOnRecyclerItemClickListener(new ProductAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                System.out.println("position" + position);
            }
        });

//        // add listener to all check box
//        all_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                for (int i = 0; i < productList.size(); i++) {
//                    productList.get(i).setSelected(b);
//                }
//            }
//        });
    }
}