package com.example.shoppinglist;

import static com.example.shoppinglist.R.drawable.*;

import static java.lang.Math.random;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;
    private final Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.product_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // use a temporary product to save the Product instance thus position won't get to the next value
        Product product = productList.get(position);
        holder.nameView.setText(product.getName());
        holder.numView.setText(String.valueOf(product.getNum()));

        // Give each product a random picture
        Random random = new Random();
        int flag = random.nextInt(7);
        switch (flag) {
            case 1:
                holder.productImage.setImageResource(avatar2);
                break;
            case 2:
                holder.productImage.setImageResource(avatar3);
                break;
            case 3:
                holder.productImage.setImageResource(avatar4);
                break;
            case 4:
                holder.productImage.setImageResource(avatar5);
                break;
            case 5:
                holder.productImage.setImageResource(avatar6);
                break;
            default:
                holder.productImage.setImageResource(avatar1);
                break;
        }

        holder.decreaseNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getNum() == 1) {
                    return;
                }
                product.setNum(product.getNum()-1);
                holder.numView.setText(String.valueOf(product.getNum()));
            }
        });

        holder.increaseNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setNum(product.getNum()+1);
                holder.numView.setText(String.valueOf(product.getNum()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView numView;
        private final ImageView productImage;
        private final MaterialButton decreaseNumBtn;
        private final MaterialButton increaseNumBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_textview);
            numView = itemView.findViewById(R.id.num_textview);
            productImage = itemView.findViewById(R.id.product_image);
            decreaseNumBtn = itemView.findViewById(R.id.decrease_num_btn);
            increaseNumBtn = itemView.findViewById(R.id.increase_num_btn);
        }
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        this.onRecyclerItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener{
        void onRecyclerItemClick(int position);
    }



}
