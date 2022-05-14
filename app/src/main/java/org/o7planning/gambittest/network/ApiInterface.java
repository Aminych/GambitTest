package org.o7planning.gambittest.network;

import org.o7planning.gambittest.model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("3")
    Call<List<Model>> getCatalog();
}