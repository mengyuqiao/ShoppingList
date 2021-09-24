package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SendToEmailDialogFragment.NoticeDialogListener {
    private List<Product> productList = new ArrayList<>();
    private final String email = "ymeng15@binghamton.edu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind components
        EditText inputEditText = findViewById(R.id.input_tv);
        MaterialButton button = findViewById(R.id.add_btn);
        Button email_btn = findViewById(R.id.email_btn);

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

        // create ItemTouchHelper to add swiping to delete
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
                Product delete_product = productList.get(viewHolder.getAdapterPosition());
                int end = viewHolder.getAdapterPosition();
                productList.remove(end);
                productAdapter.notifyItemRemoved(end);
                Snackbar.make(recyclerView, delete_product.getName() + " Removed", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productList.add(end, delete_product);
                        productAdapter.notifyItemInserted(end);
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);

        // use add_btn and input_tv to add a new product
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product_name = String.valueOf(inputEditText.getText()).trim();
                if ("".equals(product_name)){
                    Toast.makeText(getApplicationContext(),"Null Product Name", Integer.parseInt("10")).show();
                }
                else {
                    productList.add(0, new Product(product_name));
                    productAdapter.notifyItemInserted(0);
                    Snackbar.make(recyclerView, product_name + " Added", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productList.remove(0);
                            productAdapter.notifyItemRemoved(0);
                        }
                    }).show();
                }
            }
        });

        // when user press enter in edittext, just add the product
        // this method will get different results on AVD and Real phone
        inputEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)){
                    button.callOnClick();
                    inputEditText.setText("");
                    return true;
                }
                return false;
            }
        });

        // send list to email by pressing email button
        email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToEmailDialogFragment sendToEmailDialogFragment = new SendToEmailDialogFragment();
                sendToEmailDialogFragment.show(getSupportFragmentManager(), "emailConfirm");
            }
        });

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, email);
        i.putExtra(Intent.EXTRA_SUBJECT, "ShoppingList");
        String list_str = "";
        for (int j = 0; j < productList.size(); j++) {
            list_str += productList.get(j).toString();
        }
        i.putExtra(Intent.EXTRA_TEXT, list_str);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "Confirm", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
    }
}