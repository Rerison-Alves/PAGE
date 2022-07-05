package com.example.projetopage.util;

import com.example.projetopage.Data.Aluno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioAutenticado {
    public static FirebaseUser UsuarioLogado(){
        FirebaseAuth usuario = ConfiguraBd.Firebaseautenticacao();
        return usuario.getCurrentUser();
    }

}
