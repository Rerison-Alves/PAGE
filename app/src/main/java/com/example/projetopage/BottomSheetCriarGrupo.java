package com.example.projetopage;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetCriarGrupo extends BottomSheetDialogFragment {
    public BottomSheetCriarGrupo() {
    }
    FrameLayout btn_convidaalunos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.criargrupo, container, false);
        btn_convidaalunos= (FrameLayout) view.findViewById(R.id.btn_convidaalunos);
        btn_convidaalunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fragmentdialog(getActivity().getSupportFragmentManager());
            }
        });
        return view;
    }
}
