package com.aiep.simologia_app.network;

import com.aiep.simologia_app.model.Sismos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiSismos {


    @GET("general/public/sismos")
    Call<List<Sismos>> getSismos();

}
