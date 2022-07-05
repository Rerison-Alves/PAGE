package com.example.projetopage.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetopage.AbaPrincipal;
import com.example.projetopage.Data.Aluno;
import com.example.projetopage.R;
import com.example.projetopage.util.UsuarioAutenticado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class BottomSheetLoginAluno extends BottomSheetDialogFragment {

    EditText email, senha;
    FrameLayout btn_concluir;
    private FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_aluno, container, false);
        auth=FirebaseAuth.getInstance();
        email = (EditText) view.findViewById(R.id.email);
        senha = (EditText) view.findViewById(R.id.senha);
        btn_concluir = (FrameLayout) view.findViewById(R.id.btn_concluir);
        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificadados()){
                    Aluno aluno = new Aluno();
                    aluno.setEmail(email.getText().toString());
                    aluno.setSenha(senha.getText().toString());
                    login(aluno);
                }
            }
        });
        return view;
    }

    private void login(Aluno aluno) {
        auth.signInWithEmailAndPassword(aluno.getEmail(), aluno.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Bem-vindo ao PAGE", Toast.LENGTH_LONG).show();
                    Intent ittela2 = new Intent(getActivity(), AbaPrincipal.class);
                    startActivity(ittela2);
                    dismiss();
                }else {
                    String excessão;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excessão="Usuário não cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excessão="Email ou senha incorreto(os)";
                    }catch (Exception e){
                        excessão="Erro ao logar o usuario "+e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), excessão, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificadados() {
        boolean result = true;
        if(!(email.getText().toString().equals("")&&senha.getText().toString().equals(""))) {
            try {
                InternetAddress emailAddr = new InternetAddress(email.getText().toString());
                emailAddr.validate();
            } catch (AddressException ex) {
                result = false;
            }
        }else {
            Toast.makeText(getContext(), "Preencha os campos!", Toast.LENGTH_LONG).show();
            result = false;
        }
        return result;
    }
}
