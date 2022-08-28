package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.Product;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.ProductResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myViewHolder> {

    private Context context;
    private List<ProductResponse> productList;

    public ProductAdapter(Context context, List<ProductResponse> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        ProductResponse productResponse = productList.get(position);
        Picasso.get()
                .load("https://civildeal.com/public/assets/uploads/product/"+productList.get(position).getProductImage())
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_error)
                .into(holder.productImage);


        holder.productName.setText(productList.get(position).getName());

        holder.cardView.setTag(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Product.class);
                intent.putExtra("product_id",productList.get(position).getId().toString());
//                Toast.makeText(context, ""+productList.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView productImage;
        TextView productName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.serviceImage);
            productName = itemView.findViewById(R.id.servicename);
            cardView = itemView.findViewById(R.id.fCard);
        }
    }
}
