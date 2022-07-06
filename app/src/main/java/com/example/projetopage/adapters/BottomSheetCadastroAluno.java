package com.example.projetopage.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.example.projetopage.Data.Aluno;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.example.projetopage.util.ConfiguraBd;
import com.example.projetopage.util.UsuarioAutenticado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class BottomSheetCadastroAluno extends BottomSheetDialogFragment {

    EditText nome, email, matricula, senha, repetesenha;
    MaskedEditText datanasc;
    FrameLayout btn_concluir;
    Spinner curso;
    FirebaseAuth autenticacao;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cadastro_aluno, container, false);
        nome = (EditText) view.findViewById(R.id.nomealuno);
        email = (EditText) view.findViewById(R.id.email);
        matricula = (EditText) view.findViewById(R.id.matricula);
        curso = (Spinner) view.findViewById(R.id.curso);
        datanasc = (MaskedEditText) view.findViewById(R.id.datanasc);
        senha = (EditText) view.findViewById(R.id.senha);
        repetesenha = (EditText) view.findViewById(R.id.repetesenha);
        btn_concluir = (FrameLayout) view.findViewById(R.id.btn_concluir);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.cursos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        curso.setAdapter(adapter);

        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificadados()){
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
                    Date data=null;
                    try {
                        data=formata.parse(datanasc.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Aluno aluno = new Aluno(
                            nome.getText().toString(),
                            email.getText().toString(),
                            data,
                            curso.getSelectedItem().toString(),
                            matricula.getText().toString());
                    cadastrarAluno(aluno);
                }
            }
        });
        return view;
    }

    private void cadastrarAluno(Aluno aluno){
        autenticacao = ConfiguraBd.Firebaseautenticacao();
        autenticacao.createUserWithEmailAndPassword(
                aluno.getEmail(), senha.getText().toString()
        ).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = UsuarioAutenticado.UsuarioLogado();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(aluno.getNome())
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    dismiss();
                                                    MainActivity.loginaluno(getParentFragmentManager());
                                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                                    aluno.setIdAluno(user.getUid());
                                                    mDatabase.child("Usuario").child(user.getUid()).setValue(aluno);
                                                    Toast.makeText(getContext(), "Sua conta foi cadastrada!\nVerifique seu email!", Toast.LENGTH_LONG).show();
                                                }else {
                                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }else {
                    String excessao="";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthUserCollisionException e){
                        excessao="Essa conta já existe!";
                    }catch (Exception e){
                        excessao="Erro ao cadastrar " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), excessao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean verificadados() {
        boolean verifica = true;
        if(nome.getText().toString().equals("")||email.getText().toString().equals("")||
                matricula.getText().toString().equals("")||curso.getSelectedItem().toString().equals("Indefinido")||
                datanasc.getText().toString().equals("")||senha.getText().toString().equals("")||
                repetesenha.getText().toString().equals("")){
            verifica = false;
            Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }else {
            if(!isValidEmailAddress(email.getText().toString())){
                verifica = false;
                Toast.makeText(getContext(), "Email inválido!", Toast.LENGTH_SHORT).show();
            }
            if(matricula.getText().toString().length()<14){
                verifica = false;
                Toast.makeText(getContext(), "Matrícula inválida!", Toast.LENGTH_SHORT).show();
            }
            Date dataatual=new Date();
            Date datateste= null, datamin=null;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            try {
                datateste=formata.parse(datanasc.getText().toString());
                datamin=formata.parse("01/01/1900");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(dataatual.before(datateste)||dataatual.equals(datateste)||datamin.after(datateste)) {
                verifica=false;
                Toast.makeText(getContext(), "Data é inválida!", Toast.LENGTH_SHORT).show();
            }
            if (!verificasenha()){
                verifica=false;
                Toast.makeText(getContext(), "Senha é insegura!\nDeve conter: números, letras maiúsculas\ne minusculas e ao menos 8 caracteres",
                        Toast.LENGTH_LONG).show();
            }
            String tsenha = senha.getText().toString();
            String trepsenha = repetesenha.getText().toString();
            if(!(tsenha.equals(trepsenha))){
                verifica=false;
                Toast.makeText(getContext(), "Senhas não coincidem!", Toast.LENGTH_SHORT).show();
            }
        }
        return verifica;
    }

    private boolean verificasenha() {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(senha.getText().toString());
        return m.matches();
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
