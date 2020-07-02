package com.oguzhancet.cryptojava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oguzhancet.cryptojava.R;
import com.oguzhancet.cryptojava.model.Crypto;

import java.util.List;

public class RcAdapter  extends RecyclerView.Adapter<RcAdapter.CustomViewHolder> {
    private List<Crypto> cryptos;
    private Context context;

    public RcAdapter(List<Crypto> cryptos, Context context) {
        this.cryptos = cryptos;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View  view = layoutInflater.inflate(R.layout.card_view,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Crypto crypto = cryptos.get(position);
        holder.textViewPrice.setText(crypto.getPrice());
        holder.textViewCurrency.setText(crypto.getCurrency());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(cryptos != null){
            return cryptos.size();
        }else {
            return 0;
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewCurrency,textViewPrice;
        private CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCurrency = itemView.findViewById(R.id.textView_customView_currency);
            textViewPrice = itemView.findViewById(R.id.textView_customView_price);
            cardView = itemView.findViewById(R.id.cardView);

        }

    }

    public void setCryptos(List<Crypto> cryptos) {
        this.cryptos = cryptos;
        notifyDataSetChanged();
    }
}
