package org.o7planning.gambittest.contract;

import java.util.ArrayList;
import java.util.List;

public interface ProductListContract {

    interface Model {

        interface onFinishedCall {

            void onResponse(ArrayList<org.o7planning.gambittest.model.Model> catalogArrayList);

            void onFailure(Throwable t);
        }

        void getConstructorList(onFinishedCall onFinishedCall, int pageNo);

    }

    interface View {

        void setDataToRecycleView(List<org.o7planning.gambittest.model.Model> catalogArrayList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {

        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();
    }
}