package com.example.myapplicationLecture3.Activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationLecture3.Adapters.RecyclerAdapter;
import com.example.myapplicationLecture3.Classes.Order;
import com.example.myapplicationLecture3.Classes.OrderItem;
import com.example.myapplicationLecture3.Classes.RecyclerModel;
import com.example.myapplicationLecture3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize Firebase


        // Get Firebase Database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("orders");



        // Initialize Firebase Authentication (optional, depending on your use case)
        mAuth = FirebaseAuth.getInstance();

        // Set window insets for edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    public void login()
    {
        String email = ((EditText) findViewById(R.id.loginEmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.loginPassword)).getText().toString();
        if (email.isEmpty() || password.isEmpty() ) {
            Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            return;
        }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_LONG).show();
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                navigateToThirdPage();
                            } else {
                                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });



    }
    public void register() {
        String email = ((EditText) findViewById(R.id.registerEmail)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.registerPassword)).getText().toString().trim();
        String phone = ((EditText) findViewById(R.id.registerPhone)).getText().toString().trim();
        String username = ((EditText) findViewById(R.id.registerUsername)).getText().toString().trim();

        // Input validation
        if (email.isEmpty() || password.isEmpty() || phone.isEmpty() || username.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Get user ID
                            String userId = mAuth.getCurrentUser().getUid();

                            // Create user details HashMap
                            HashMap<String, Object> userDetails = new HashMap<>();
                            userDetails.put("username", username);
                            userDetails.put("email", email);
                            userDetails.put("phone", phone);

                            // Save data to Firebase Realtime Database
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId)
                                    .setValue(userDetails)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                                                navigateToLogin();
                                            } else {
                                                Log.e("DatabaseError", "Failed to save user data", task.getException());
                                                Toast.makeText(MainActivity.this, "Failed to save user data", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Log.e("AuthError", "User registration failed", task.getException());
                            Toast.makeText(MainActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }




    private void navigateToLogin() {
        // Navigate to FragmentTwo after successful registration
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView);
        navController.navigate(R.id.action_fragmentOne_to_fragmentTwo);
    }
    private void navigateToThirdPage() {
        // Navigate to FragmentThree after successful registration
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView);
        navController.navigate(R.id.action_fragmentTwo_to_fragmentThree);
    }
    public void navigateToRegister() {
        // Navigate to FragmentThree after successful registration
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView);
        navController.navigate(R.id.action_fragmentThree_to_fragmentOne);
    }




}