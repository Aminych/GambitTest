
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
    SharedPreferences prefCount;
    SharedPreferences prefLike;

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
        if (prefCount == null) {
            prefCount = parent.getContext().getSharedPreferences("PREF_COUNT", Context.MODE_PRIVATE);
        }
        if (prefLike == null) {
            prefLike = parent.getContext().getSharedPreferences("PREF_LIKE", Context.MODE_PRIVATE);
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

        private SwipeLayout swipeLayout;

        Model currentModel;
        TextView title;
        ImageView image, imageLike, btnMinus, btnPlus;
        TextView price, countxt;
        Button btnBasket;
        int count;
        boolean flag = true;
        boolean imageLikeBool;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swd);
            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.txtPrice);
            image = itemView.findViewById(R.id.imageCatalog);
            countxt = itemView.findViewById(R.id.txtCount);
            imageLike = itemView.findViewById(R.id.imageLike);
            btnBasket = itemView.findViewById(R.id.btnBasket);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);

            swipeLayoutForRecItem();
        }

        public void bind(Model model) {
            currentModel = model;

            count = getCount();
            imageLikeBool = prefLike.getBoolean(String.valueOf(currentModel.getSectionId()), true);

            title.setText(model.getSectionTitle());
            price.setText(model.getPrice());
            Picasso.get()
                    .load(model.getSectionImage())
                    .into(image);

            countxt.setText(String.valueOf(getCount()));

            if (prefLike.getBoolean(currentModel.getSectionId(), imageLikeBool)) {
                imageLike.setImageResource(R.drawable.like);
            } else {
                imageLike.setImageResource(R.drawable.liked);
            }

            if (getCount() >= 1) {
                btnBasket.setVisibility(View.GONE);
                btnMinus.setVisibility(View.VISIBLE);
                btnPlus.setVisibility(View

                        .VISIBLE);
                countxt.setVisibility(View.VISIBLE);
            } else {
                btnBasket.setVisibility(View.VISIBLE);
                btnMinus.setVisibility(View.GONE);
                btnPlus.setVisibility(View.GONE);
                countxt.setVisibility(View.GONE);
            }

            btnBasket.setOnClickListener(v -> {
                count = 1;
                saveDataCount(currentModel.getSectionId(), count);
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
                saveDataCount(currentModel.getSectionId(), count);
            });

            btnMinus.setOnClickListener(v -> {
                count -= 1;
                saveDataCount(currentModel.getSectionId(), count);
                if (count < 1) {
                    btnBasket.setVisibility(View.VISIBLE);
                    btnMinus.setVisibility(View.GONE);
                    btnPlus.setVisibility(View.GONE);
                    countxt.setVisibility(View.GONE);
                } else {
                    btnPlus.setVisibility(View.VISIBLE);
                    countxt.setVisibility(View.VISIBLE);
                    countxt.setText(Integer.toString(count));
                    btnMinus.setVisibility(View.VISIBLE);
                }
            });
        }

        public void saveDataCount(String id, int count) {
            SharedPreferences.Editor ed = prefCount.edit();
            ed.putInt(id, count);
            ed.apply();
        }

        public Integer getCount() {
            return prefCount.getInt(currentModel.getSectionId(), 0);
        }

        public void saveDataImageLike(String id, boolean dataToSave) {
            SharedPreferences.Editor editor = prefLike.edit();
            editor.putBoolean(id, dataToSave);
            editor.apply();
        }

        public void swipeLayoutForRecItem() {
            // Реализация SwipeLayout
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.swipedLayout));
            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {

                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    if (imageLikeBool) {
                        imageLike.setImageResource(R.drawable.liked);
                        imageLikeBool = false;

                    } else {
                        imageLike.setImageResource(R.drawable.like);
                        imageLikeBool = true;
                    }
                    saveDataImageLike(String.valueOf(currentModel.getSectionId()), imageLikeBool);
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    layout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layout.close();

                        }
                    }, 1000);
                }
            });
        }
    }
}