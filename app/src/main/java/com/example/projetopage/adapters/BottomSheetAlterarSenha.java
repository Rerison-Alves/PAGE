package com.example.projetopage.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetopage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class BottomSheetAlterarSenha extends BottomSheetDialogFragment {

    EditText email;
    FrameLayout btn_concluir;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheetalterarsenha, container, false);
        email = (EditText) view.findViewById(R.id.email);
        btn_concluir = (FrameLayout) view.findViewById(R.id.btn_concluir);
        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidEmailAddress(email.getText().toString())){
                    Toast.makeText(getContext(), "Email inválido!", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Solicitação de alteração de senha enviada!" +
                                                "\n Verifique seu email", Toast.LENGTH_LONG).show();
                                        dismiss();
                                    }else {
                                        Toast.makeText(getContext(), "Erro ao enviar solicitação.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}

