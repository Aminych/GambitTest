package org.o7planning.gambittest.presenter;

import org.o7planning.gambittest.contract.ProductListContract;
import org.o7planning.gambittest.model.Model;
import org.o7planning.gambittest.service.ProductListModel;

import java.util.ArrayList;

public class ProductPresenter implements ProductListContract.Presenter, ProductListContract.Model.onFinishedCall {

    private ProductListContract.View productListView;
    private final ProductListContract.Model productListModel;

    public ProductPresenter(ProductListContract.View catalogListView) {
        this.productListView = catalogListView;
        productListModel = new ProductListModel();
    }

    @Override
    public void onDestroy() {
        this.productListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {
        productListModel.getConstructorList(this, pageNo);
    }

    @Override
    public void requestDataFromServer() {
        productListModel.getConstructorList(this, 1);
    }

    @Override
    public void onResponse(ArrayList<Model> catalogArrayList) {
        productListView.setDataToRecycleView(catalogArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        productListView.onResponseFailure(t);
    }
}