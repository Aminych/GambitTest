package org.o7planning.gambittest.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;

import org.o7planning.gambittest.R;
import org.o7planning.gambittest.contract.CatalogListContract;
import org.o7planning.gambittest.model.Constructor;
import org.o7planning.gambittest.presenter.CatalogPresenter;
import org.o7planning.gambittest.view.ConstructorListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CatalogListContract.View {

    private SwipeLayout swd;

    private CatalogPresenter catalogPresenter;
    private RecyclerView rvCatalog;
    private ArrayList<Constructor> constructorList;
    private ConstructorListAdapter constructorListAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCatalog = findViewById(R.id.rvAdapter);
        swd = findViewById(R.id.swd);

        layoutManager = new LinearLayoutManager(this);
        rvCatalog.setLayoutManager(new LinearLayoutManager(this));
        rvCatalog.setHasFixedSize(true);

//        swd.setShowMode(SwipeLayout.ShowMode.PullOut);
//        swd.addDrag(SwipeLayout.DragEdge.Right, swd.findViewById(R.id.linlur));

        constructorList = new ArrayList<Constructor>();

        catalogPresenter = new CatalogPresenter(this);
        catalogPresenter.requestDataFromServer();
    }

    @Override
    public void setDataToRecycleView(List<Constructor> catalogArrayList) {
        constructorList.addAll(catalogArrayList);
        constructorListAdapter = new ConstructorListAdapter(constructorList, MainActivity.this);
        rvCatalog.setAdapter(constructorListAdapter);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}