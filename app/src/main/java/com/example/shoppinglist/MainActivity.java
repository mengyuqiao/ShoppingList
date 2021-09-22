package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // generate initial products
        for (int i = 0; i < 10; i++) {
            productList.add(new Product("Test Product Number" + i));
        }

        // send data to adapter
        RecyclerView recyclerView = findViewById(R.id.rcView);

        // use linear layout in recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // send product list to the adapter of recyclerview
        ProductAdapter productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);

        //creating a method to create item touch helper method for adding swipe to delete functionality.
        //we are specifying drag direction and position to right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int start_position = viewHolder.getAdapterPosition();
                int end_position = target.getAdapterPosition();
                Collections.swap(productList, start_position, end_position);
                productAdapter.notifyItemMoved(start_position,end_position);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when swipe our item to right direction.
                // get the item at a particular position.
                Product delete_product = productList.get(viewHolder.getAdapterPosition());
                int end = viewHolder.getAdapterPosition();
                //remove item from our array list.
                productList.remove(end);
                //notify our item is removed from adapter
                productAdapter.notifyItemRemoved(end);
                Snackbar.make(recyclerView, delete_product.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add on click listener to our action of snack bar.
                        // add our item to array list with a position.
                        productList.add(end, delete_product);
                        //notify item is added to our adapter class.
                        productAdapter.notifyItemInserted(end);
                    }
                }).show();
            }
            //add this to our recycler view.
        }).attachToRecyclerView(recyclerView);
    }
}