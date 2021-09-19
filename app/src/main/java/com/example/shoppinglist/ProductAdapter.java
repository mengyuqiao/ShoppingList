package com.example.shoppinglist;

import static com.example.shoppinglist.R.drawable.*;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;
    private final Context context;
    private int flag = 1;

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
        String price = "$" + String.valueOf(product.getPrice());
        holder.priceView.setText(price);
        holder.numView.setText(String.valueOf(product.getNum()));

        switch (flag) {
            case 0:
                holder.productImage.setImageResource(avatar1);
            case 1:
                holder.productImage.setImageResource(avatar2);
            case 2:
                holder.productImage.setImageResource(avatar3);
            case 3:
                holder.productImage.setImageResource(avatar4);
            case 4:
                holder.productImage.setImageResource(avatar5);
            case 5:
                holder.productImage.setImageResource(avatar6);
            default:
                holder.productImage.setImageResource(avatar1);
        }
        flag = (flag+1)%6;

        // in some cases, it will prevent unwanted situations
        holder.selectedCheckBox.setOnCheckedChangeListener(null);
        // set checkbox
        holder.selectedCheckBox.setChecked(product.isSelected());

        holder.selectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                product.setSelected(b);
                System.out.println(product.isSelected());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView priceView;
        private final TextView numView;
        private final CheckBox selectedCheckBox;
        private final ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_textview);
            priceView = itemView.findViewById(R.id.price_textview);
            numView = itemView.findViewById(R.id.num_textview);
            selectedCheckBox = itemView.findViewById(R.id.item_cb);
            productImage = itemView.findViewById(R.id.product_image);

            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerItemClickListener != null){
                        onRecyclerItemClickListener.onRecyclerItemClick(getAdapterPosition());
                    }
                }
            });
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
