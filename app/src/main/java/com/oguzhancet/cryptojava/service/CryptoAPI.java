package com.oguzhancet.cryptojava.service;

import androidx.lifecycle.LiveData;

import com.oguzhancet.cryptojava.model.Crypto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    @GET("prices?key=59fb6b884201325730eb41c7f84ee897")
    Observable<List<Crypto>> getData();

    // Call<List<Crypto>>  getData();
}
