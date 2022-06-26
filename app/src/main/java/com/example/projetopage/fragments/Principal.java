package com.example.projetopage.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.projetopage.Data.Grupo;
import com.example.projetopage.R;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.fragments.adapters.RecyclerViewAdapter;

import java.util.ArrayList;

public class Principal extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Principal() {
        // Required empty public constructor
    }

    public static Principal newInstance(String param1, String param2) {
        Principal fragment = new Principal();
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

    String[] testeGrupos= new String[] {"POO em aplicativos", "POO 2022", "Prog. Linear", "Nome de grupo", "Tô sem ideia"};
    Context context;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        context= getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gpsrecentes);
        RecyclerView.LayoutManager recycleLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recycleLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(context, testeGrupos);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }












}