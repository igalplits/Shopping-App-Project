package com.example.myapplicationLecture3.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationLecture3.Classes.RecyclerModel;
import com.example.myapplicationLecture3.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<RecyclerModel> recycleModels;
    private List<RecyclerModel> selectedItems = new ArrayList<>();

    public RecyclerAdapter(Context context, List<RecyclerModel> recycleModels) {
        this.context = context;
        this.recycleModels = recycleModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerModel currentItem = recycleModels.get(position);

        holder.txtDate.setImageResource(currentItem.getDate());
        holder.txtTitle.setText(currentItem.getTitle());
        holder.txtDescription.setText(currentItem.getDescription());
        holder.txtAmount.setText(String.valueOf(currentItem.getAmount()));


        if (selectedItems.contains(currentItem)) {
            holder.itemView.setBackgroundColor(Color.GREEN); // Set selected color
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFF5F5")); // Set default color
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (selectedItems.contains(currentItem)) {
                // Remove item from the list and reset background color
                selectedItems.remove(currentItem);
                holder.itemView.setBackgroundColor(Color.parseColor("#FFF5F5"));
            } else {
                // Add item to the list and change background color
                selectedItems.add(currentItem);
                holder.itemView.setBackgroundColor(Color.GREEN);
            }
        });

        // Handle amount update with + and - buttons
        holder.btnMinus.setOnClickListener(v -> {
            if (currentItem.getAmount() > 0) {
                currentItem.setAmount(currentItem.getAmount() - 1); // Decrease amount
                holder.txtAmount.setText(String.valueOf(currentItem.getAmount())); // Update UI
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            if (currentItem.getAmount() < 10) {
                currentItem.setAmount(currentItem.getAmount() + 1); // Increase amount
                holder.txtAmount.setText(String.valueOf(currentItem.getAmount())); // Update UI
            }
        });
    }

    @Override
    public int getItemCount() {
        return recycleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDescription, txtAmount;
        private ImageView txtDate;
        private Button btnMinus, btnPlus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txt_date);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDescription = itemView.findViewById(R.id.txt_description);
            txtAmount = itemView.findViewById(R.id.txt_amount); // Amount TextView

            btnMinus = itemView.findViewById(R.id.btn_minus); // Minus Button
            btnPlus = itemView.findViewById(R.id.btn_plus);   // Plus Button

            itemView.setOnClickListener(v -> {
                String message = "Clicked item: " + txtTitle.getText().toString();
                Drawable imageDrawable = txtDate.getDrawable();
                showCustomToast(v, message, imageDrawable);
            });
        }
    }

    public void setFilteredList(List<RecyclerModel> filteredList) {
        this.recycleModels = filteredList;
        notifyDataSetChanged();
    }

    private void showCustomToast(View view, String message, Drawable imageResId) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customToastView = inflater.inflate(R.layout.items_recycler, null);

        ImageView toastImage = customToastView.findViewById(R.id.txt_date);
        toastImage.setImageDrawable(imageResId);

        TextView toastText = customToastView.findViewById(R.id.txt_title);
        toastText.setText(message);

        TextView toastDescription = customToastView.findViewById(R.id.txt_description);
        toastDescription.setText("");

        customToastView.setBackgroundColor(Color.parseColor("#FF5722"));

        Toast toast = new Toast(view.getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToastView);
        toast.show();
    }

    public List<RecyclerModel> getSelecteditems()
    {
        return selectedItems;
    }
}
