package com.example.myapplicationLecture3.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationLecture3.Activities.MainActivity;

import com.example.myapplicationLecture3.Adapters.RecyclerAdapter;

import com.example.myapplicationLecture3.Classes.RecyclerModel;
import com.example.myapplicationLecture3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentThree extends Fragment {
    private List<RecyclerModel> selectedItems = new ArrayList<>();




    private RecyclerView recyclerView;
    private List<RecyclerModel> recyclerModels = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private SearchView searchView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentThree newInstance(String param1, String param2) {
        fragmentThree fragment = new fragmentThree();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        recyclerFill(view);
        // Get references to UI components
        Button logOut = view.findViewById(R.id.logoutButton);
        Button cartButton = view.findViewById(R.id.cartButton);
        Button addToCartButton = view.findViewById(R.id.buyButton);
        addToCartButton.setOnClickListener(v -> {
            // Handle adding items to cart here
            addSelectedItemsToCart();
        });
        cartButton.setOnClickListener(v -> showSelectedItemsDialog());
        TextView usernameText = view.findViewById(R.id.usernameText);

        // Fetch the currently logged-in user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the database node where user details are stored
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(userId);

            // Fetch the username
            userRef.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.getValue(String.class);
                        usernameText.setText(username); // Set username on the TextView
                    } else {
                        usernameText.setText("Username not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                    usernameText.setText("Error fetching username");
                }
            });
        } else {
            usernameText.setText("No user logged in");
        }

        // Set up logout button functionality
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.navigateToRegister();
            }
        });

        return view;
    }

    private void recyclerFill(View view )
    {
        searchView = view.findViewById(R.id.searchView1);
        if (searchView != null) {
            searchView.clearFocus();
        } else {
            Log.e("MainActivity", "SearchView is null. Check your XML layout and ID.");
        }
//        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });



        recyclerView = view.findViewById(R.id.rvname);

        //create data on recyclemodel
        recyclerModels.add(new RecyclerModel(R.drawable.shoe1,"Elegant Brown","1800$",0));
        recyclerModels.add(new RecyclerModel(R.drawable.shoe2,"Elegant ","1500$",0));
        recyclerModels.add(new RecyclerModel(R.drawable.shoe3,"Running","300$",0));
        recyclerModels.add(new RecyclerModel(R.drawable.shoe4,"sports","350$",0));
        recyclerModels.add(new RecyclerModel(R.drawable.shoe5,"a","400$",0));
        recyclerModels.add(new RecyclerModel(R.drawable.shoe6,"b","500$",0));
        recyclerModels.add(new RecyclerModel(R.drawable.shoe7,"c","400$",0));
        recyclerModels.add(new RecyclerModel(R.drawable.shoe8,"d","300$",0));
//        recyclerModels.add(new RecyclerModel(R.drawable.sandy,"Sandy","Spongebob's female friend"));
//        recyclerModels.add(new RecyclerModel(R.drawable.plankton,"Plankton","The tiny bad guy"));

        //call adapter
        recyclerAdapter = new RecyclerAdapter(view.getContext(), recyclerModels);

        //set adapter recycler view
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void fileList(String text) {
        List<RecyclerModel> filteredList = new ArrayList<>();
        for (RecyclerModel item : recyclerModels) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }

            recyclerAdapter.setFilteredList(filteredList);

        }
    }

    private void showSelectedItemsDialog() {
            // If there are no items selected, show a message
            if (selectedItems.isEmpty()) {
                Toast.makeText(getContext(), "No items selected", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a custom adapter for the dialog
            ArrayAdapter<RecyclerModel> adapter = new ArrayAdapter<RecyclerModel>(getContext(), R.layout.dialog_selected_items, selectedItems) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_selected_items, parent, false);
                    }

                    // Bind the data to the views in the dialog layout
                    RecyclerModel item = getItem(position);

                    TextView itemNameTextView = convertView.findViewById(R.id.item_name);
                    TextView itemPriceTextView = convertView.findViewById(R.id.item_price);
                    ImageView trashIcon = convertView.findViewById(R.id.trash_icon);

                    // Set the item name
                    itemNameTextView.setText(item.getTitle());

                    // Set the item price and amount
                    float itemPrice = item.getPriceAsFloat();
                    int itemAmount = item.getAmount();
                    itemPriceTextView.setText("Price: $" + String.format("%.2f", itemPrice) + " x " + itemAmount);

                    // Set up the trash icon click listener
                    trashIcon.setOnClickListener(v -> {
                        // Remove the item from the list
                        selectedItems.remove(item);
                        notifyDataSetChanged();  // Notify the adapter that the data has changed

                        // Show a confirmation message
                        Toast.makeText(getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
                    });

                    return convertView;
                }
            };

            // Create and show the dialog with a ListView
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Selected Items");

            // Add the ListView to the dialog
            ListView listView = new ListView(getContext());
            listView.setAdapter(adapter);
            builder.setView(listView);

            // Calculate the total price
            double totalAmount = selectedItems.stream().mapToDouble(item -> item.getPriceAsFloat() * item.getAmount()).sum();

            // Add a total amount line
            builder.setMessage("Total: $" + String.format("%.2f", totalAmount));

            // Add the "Order" button to trigger the order action
            builder.setPositiveButton("Order", (dialog, which) -> {
                placeOrder(selectedItems, totalAmount);
                dialog.dismiss();
            });

            // Add a "Close" button to dismiss the dialog
            builder.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        }


        // Method to handle the order action (place the order)
        private void placeOrder(List<RecyclerModel> selectedItems, double totalAmount) {
            if (selectedItems == null || selectedItems.isEmpty()) {
                Log.e("Order", "No items in the selected list");
                Toast.makeText(getContext(), "No items to order", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the current user's UID (user ID) from Firebase Authentication
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Create a Map to hold the order data
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("totalAmount", totalAmount);
            orderData.put("items", selectedItems);

            // Create a reference to the Firebase database where you want to store the order
            DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders").child(userId);

            // Save the order data under the logged-in user's ID
            ordersRef.push().setValue(orderData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the selected items or update UI after the order
                    selectedItems.clear();  // Clear the cart
                } else {
                    Log.e("Order", "Failed to place order", task.getException());
                    Toast.makeText(getContext(), "Failed to place order. Try again.", Toast.LENGTH_SHORT).show();
                }
            });

        }

    private void addSelectedItemsToCart() {
        // Get selected items from the adapter
        List<RecyclerModel> items = recyclerAdapter.getSelecteditems();

        // Add the selected items to the fragment's cart list
        selectedItems.clear();  // Clear the previous cart items if necessary
        selectedItems.addAll(items);  // Add the new selected items to the cart

        // Show a confirmation message (optional)
        Toast.makeText(getContext(), "Items added to cart", Toast.LENGTH_SHORT).show();
    }
}





