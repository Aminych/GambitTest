package org.o7planning.gambittest.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;

import org.o7planning.gambittest.R;
import org.o7planning.gambittest.model.Model;

import java.util.ArrayList;

public class ModelListAdapter extends RecyclerView.Adapter<ModelListAdapter.MyViewHolder> {

    private final ArrayList<Model> modelList;
    private final Context context;

    SharedPreferences sPref;
    SharedPreferences sPreff;

    public ModelListAdapter(ArrayList<Model> constructorList, Context context) {
        this.modelList = constructorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        if (sPref == null) {
            sPref = parent.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        }
        if (sPreff == null) {
            sPreff = parent.getContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        }
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final SwipeLayout swd;

        Model currentModel;

        TextView title;
        ImageView image, like, btnMinus, btnPlus;
        TextView price, countxt;
        Button btnBasket;
        int count;
        boolean flag = true;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            swd = itemView.findViewById(R.id.swd);

            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.txtPrice);
            image = itemView.findViewById(R.id.imageCatalog);

            countxt = itemView.findViewById(R.id.txtCount);

            like = itemView.findViewById(R.id.like);
            btnBasket = itemView.findViewById(R.id.btnBasket);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);

            if (count <= 0) {
                btnPlus.setVisibility(View.INVISIBLE);
                countxt.setVisibility(View.INVISIBLE);
                btnMinus.setVisibility(View.INVISIBLE);
            }

            btnBasket.setOnClickListener(v -> {
                count = 1;
                btnBasket.setVisibility(View.GONE);
                if (count > 0) {
                    btnPlus.setVisibility(View.VISIBLE);
                    countxt.setVisibility(View.VISIBLE);
                    countxt.setText(Integer.toString(count));
                    btnMinus.setVisibility(View.VISIBLE);
                }
            });

            btnPlus.setOnClickListener(v -> {
                count += 1;
                countxt.setText(Integer.toString(count));
                saveDataq(currentModel.getSectionId(), count);
            });

            btnMinus.setOnClickListener(v -> {
                count -= 1;
                btnPlus.setVisibility(View.VISIBLE);
                countxt.setVisibility(View.VISIBLE);
                countxt.setText(Integer.toString(count));
                btnMinus.setVisibility(View.VISIBLE);
                if (count <= 0) {
                    btnBasket.setVisibility(View.VISIBLE);
                    btnPlus.setVisibility(View.GONE);
                    countxt.setVisibility(View.GONE);
                    countxt.setText(Integer.toString(count));
                    btnMinus.setVisibility(View.GONE);
                }
            });
        }

        public void bind(Model model) {
            currentModel = model;

            title.setText(model.getSectionTitle());
            price.setText(model.getPrice());
            Picasso.get()
                    .load(model.getSectionImage())
                    .into(image);


//            if (sPref.getBoolean(currentModel.getSectionId(), false)) {
//                like.setImageResource(R.drawable.liked);
//            } else {
//                like.setImageResource(R.drawable.like);
//            }

            String a = sPreff.getString(currentModel.getSectionId(), " ");

            if (model.getSectionId() == sPreff.getString(currentModel.getSectionId(), " ")) {
                btnBasket.setVisibility(View.GONE);
                btnMinus.setVisibility(View.VISIBLE);
                btnPlus.setVisibility(View.VISIBLE);
                countxt.setVisibility(View.VISIBLE);

            } else {
                btnBasket.setVisibility(View.VISIBLE);
                btnMinus.setVisibility(View.GONE);
                btnPlus.setVisibility(View.GONE);
                countxt.setVisibility(View.GONE);
            }
        }

        public void saveData(String id, boolean flag) {
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean(id, flag);
            ed.apply();
        }

        public void saveDataq(String id, int count) {
            SharedPreferences.Editor ed = sPreff.edit();
            ed.putInt(id, count);
            ed.apply();
        }
    }
}