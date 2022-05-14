package org.o7planning.gambittest.service;

import android.util.Log;

import org.o7planning.gambittest.contract.ProductListContract;
import org.o7planning.gambittest.model.Model;
import org.o7planning.gambittest.network.ApiClient;
import org.o7planning.gambittest.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListModel implements ProductListContract.Model {

    private final String TAG = "ProductListModel";
    private final int pageNo = 1;

    @Override
    public void getConstructorList(onFinishedCall onFinishedCall, int pageNo) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Model>> call = apiService.getCatalog();

        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                ArrayList<Model> constructors = (ArrayList<Model>) response.body();
                Log.e(TAG, "Numbers of product received" + constructors.size());
                onFinishedCall.onResponse(constructors);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedCall.onFailure(t);
            }
        });
    }
}