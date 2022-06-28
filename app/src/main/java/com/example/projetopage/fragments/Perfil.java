package com.example.projetopage.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetopage.R;
import com.example.projetopage.fragments.adapters.RecyclerViewAdapter;
import com.example.projetopage.fragments.adapters.RecyclerViewAdapterExtended;


public class Perfil extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Perfil() {

    }

    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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
    String[] testeGpsUsuario= new String[] {"POO em aplicativos", "POO 2022", "Prog. Linear", "Nome de grupo", "Tô sem ideia"};
    Context context;
    RecyclerViewAdapterExtended recyclerViewAdapterExtended;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        context= getContext();
        //Lista de grupos
        RecyclerView usuariogpsView = (RecyclerView) view.findViewById(R.id.gpsusuario);
        RecyclerView.LayoutManager recycleLayoutUsuario = new LinearLayoutManager(context) ;
        usuariogpsView.setLayoutManager(recycleLayoutUsuario);
        recyclerViewAdapterExtended = new RecyclerViewAdapterExtended(context, testeGpsUsuario);
        usuariogpsView.setAdapter(recyclerViewAdapterExtended);
        usuariogpsView.setNestedScrollingEnabled( false );

        return view;
    }
}