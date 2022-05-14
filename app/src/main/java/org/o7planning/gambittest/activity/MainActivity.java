package org.o7planning.gambittest.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.o7planning.gambittest.R;
import org.o7planning.gambittest.contract.ProductListContract;
import org.o7planning.gambittest.model.Model;
import org.o7planning.gambittest.presenter.ProductPresenter;
import org.o7planning.gambittest.view.ModelListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListContract.View {

//    private SwipeLayout swd;

    private ProductPresenter productPresenter;
    private RecyclerView rvCatalog;
    private ArrayList<Model> modelList;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCatalog = findViewById(R.id.rvAdapter);

//        swd = findViewById(R.id.swd);

        layoutManager = new LinearLayoutManager(this);
        rvCatalog.setLayoutManager(new LinearLayoutManager(this));
        rvCatalog.setHasFixedSize(true);

//        swd.setShowMode(SwipeLayout.ShowMode.PullOut);
//        swd.addDrag(SwipeLayout.DragEdge.Right, swd.findViewById(R.id.layoutSwipe));

        modelList = new ArrayList<Model>();

        productPresenter = new ProductPresenter(this);
        productPresenter.requestDataFromServer();
    }

    @Override
    public void setDataToRecycleView(List<Model> catalogArrayList) {
        modelList.addAll(catalogArrayList);
        ModelListAdapter modelListAdapter = new ModelListAdapter(modelList, MainActivity.this);
        rvCatalog.setAdapter(modelListAdapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}