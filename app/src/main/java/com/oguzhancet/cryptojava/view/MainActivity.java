package com.oguzhancet.cryptojava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oguzhancet.cryptojava.R;
import com.oguzhancet.cryptojava.adapter.RcAdapter;
import com.oguzhancet.cryptojava.model.Crypto;
import com.oguzhancet.cryptojava.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Crypto> cryptos;
    private final String BASE_URL = "https://api.nomics.com/v1/";
    private Retrofit retrofit;
    private RcAdapter rcAdapter;

    private io.reactivex.rxjava3.disposables.CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //retrofit && JSON

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

          loadData();



        //Recycler
        RecyclerView  recyclerView  = findViewById(R.id.recyclerView);
        rcAdapter = new RcAdapter(cryptos,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rcAdapter);



    }

    private void loadData(){
        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handeleResponse));



        /*Call<List<Crypto>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<Crypto>>() {
            @Override
            public void onResponse(Call<List<Crypto>> call, Response<List<Crypto>> response) {
                if(response.isSuccessful()){
                    cryptos = new ArrayList<>(response.body());

                    for (Crypto c : cryptos){
                        Log.e("crypto",c.getCurrency()+" : "+c.getPrice());
                    }

                    rcAdapter.setCryptos(cryptos);
                }
            }

            @Override
            public void onFailure(Call<List<Crypto>> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    private void handeleResponse(List<Crypto> cryptosGelen){
        this.cryptos = new ArrayList<>(cryptosGelen);

        rcAdapter.setCryptos(cryptos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}