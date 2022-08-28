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

public class SeeAllProductAdapter extends RecyclerView.Adapter<SeeAllProductAdapter.MyViewHolder> {

    private Context context;
    private List<ProductResponse> productList;

    public SeeAllProductAdapter(Context context, List<ProductResponse> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_all_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductResponse productResponse = productList.get(position);
        Picasso.get()
                .load("https://civildeal.com/public/assets/uploads/product/"+productList.get(position).getProductImage())
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.profile)
                .into(holder.productImage);


        holder.productName.setText(productList.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Product.class);
                intent.putExtra("product_id",productList.get(position).getId().toString());
//                Toast.makeText(context, "product_id"+productList.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView productImage;
        TextView productName;
        public MyViewHolder( View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.serviceImage);
            productName = itemView.findViewById(R.id.servicename);
            cardView= itemView.findViewById(R.id.fCard);
        }
    }
}
