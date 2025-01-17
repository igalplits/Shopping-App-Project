package com.example.myapplicationLecture3.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplicationLecture3.Activities.MainActivity;
import com.example.myapplicationLecture3.Classes.userManager;
import com.example.myapplicationLecture3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentTwo extends Fragment {

    private Button loginButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentTwo newInstance(String param1, String param2) {
        fragmentTwo fragment = new fragmentTwo();
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
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        loginButton = view.findViewById(R.id.buttonLogin);
        Button registerButton = view.findViewById(R.id.buttonRegister2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity =(MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.login();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity =(MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.navigateToRegister();
            }
        });

return view;
    }
}