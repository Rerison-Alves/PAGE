package com.example.projetopage.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetCadastro extends BottomSheetDialogFragment {

    @Nullable
    FrameLayout btn_cadastroaluno, btn_loginaluno;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheetinicial, container, false);
        btn_cadastroaluno = (FrameLayout) view.findViewById(R.id.btn_cadastroaluno);
        btn_cadastroaluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                MainActivity.cadastroaluno(getParentFragmentManager());
            }
        });
        btn_loginaluno = (FrameLayout) view.findViewById(R.id.btn_loginaluno);
        btn_loginaluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                MainActivity.loginaluno(getParentFragmentManager());
            }
        });
        return view;
    }

}
