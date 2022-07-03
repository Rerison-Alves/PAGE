package com.example.projetopage.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetCadastroAluno extends BottomSheetDialogFragment {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cadastro_aluno, container, false);

        return view;
    }

}
