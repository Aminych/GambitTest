package org.o7planning.gambittest.contract;

import org.o7planning.gambittest.model.Constructor;

import java.util.ArrayList;
import java.util.List;

public interface CatalogListContract {

    interface Model {
        interface onFinishedCall {
            void onResponse(ArrayList<Constructor> catalogArrayList);

            void onFailure(Throwable t);
        }

        void getConstructorList(onFinishedCall onFinishedCall, int pageNo);

    }

    interface View {
        void setDataToRecycleView(List<Constructor> catalogArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();
    }
}