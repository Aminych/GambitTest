package org.o7planning.gambittest.network;

import org.o7planning.gambittest.model.Constructor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("3")
    Call<List<Constructor>> getCatalog();
}
