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

import com.squareup.picasso.Picasso;

import org.o7planning.gambittest.R;
import org.o7planning.gambittest.model.Constructor;

import java.util.ArrayList;

public class ConstructorListAdapter extends RecyclerView.Adapter<ConstructorListAdapter.MyViewHolder> {
    private ArrayList<Constructor> constructorList;
    private Context context;

    SharedPreferences sPref;

    public ConstructorListAdapter(ArrayList<Constructor> constructorList, Context context) {
        this.constructorList = constructorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        if (sPref == null) {
            sPref = parent.getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        }
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(constructorList.get(position));
    }

    @Override
    public int getItemCount() {
        return constructorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Constructor currentModel;

        TextView title;
        ImageView image, like, btnMinus, btnPlus;
        TextView price, countxt;
        Button btnBasket;
        int count;
        boolean flag = true;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
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

            btnBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = 1;
                    btnBasket.setVisibility(View.INVISIBLE);
                    if (count > 0) {
                        btnPlus.setVisibility(View.VISIBLE);
                        countxt.setVisibility(View.VISIBLE);
                        countxt.setText(Integer.toString(count));
                        btnMinus.setVisibility(View.VISIBLE);
                    }
                }
            });

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count += 1;
                    countxt.setText(Integer.toString(count));
                }
            });

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count -= 1;
                    btnPlus.setVisibility(View.VISIBLE);
                    countxt.setVisibility(View.VISIBLE);
                    countxt.setText(Integer.toString(count));
                    btnMinus.setVisibility(View.VISIBLE);
                    if (count <= 0) {
                        btnBasket.setVisibility(View.VISIBLE);
                        btnPlus.setVisibility(View.INVISIBLE);
                        countxt.setVisibility(View.INVISIBLE);
                        countxt.setText(Integer.toString(count));
                        btnMinus.setVisibility(View.INVISIBLE);
                    }
                }
            });


            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sPref.getBoolean(currentModel.getSectionId(), false)) {
                        like.setImageResource(R.drawable.liked);
                        saveData(currentModel.getSectionId(), false);
                    } else {
                        like.setImageResource(R.drawable.like);
                        saveData(currentModel.getSectionId(), true);
                    }
                    notifyItemRangeChanged(0, constructorList.size(), false);
                }
            });
        }

        public void bind(Constructor model) {
            currentModel = model;

            title.setText(model.getSectionTitle());
            price.setText(model.getPrice());
            Picasso.get()
                    .load(model.getSectionImage())
                    .into(image);


            if (sPref.getBoolean(currentModel.getSectionId(), false)) {
                like.setImageResource(R.drawable.liked);
            } else {
                like.setImageResource(R.drawable.like);
            }
        }

        public void saveData(String id, boolean flag) {
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean(id, flag);
            ed.apply();
        }
    }
}
