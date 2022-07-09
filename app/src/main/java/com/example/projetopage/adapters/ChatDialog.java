package com.example.projetopage.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.Mensagem;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.example.projetopage.util.UsuarioAutenticado;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ChatDialog extends Dialog {
    private DatabaseReference myRef;
    Encontro encontro;
    Context context;
    FragmentManager fragmentManager;
    private GroupAdapter adapter;

    public ChatDialog(Encontro encontro, Context context, FragmentManager fragmentManager, int themeResId, DatabaseReference myRef) {
        super(context, themeResId);
        this.encontro=encontro;
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.myRef=myRef;
    }
    TextView tema;
    RecyclerView chat;
    ImageView send, voltar;
    EditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        tema = (TextView) findViewById(R.id.Tema);
        chat = (RecyclerView) findViewById(R.id.chat);
        send = (ImageView) findViewById(R.id.send);
        texto = (EditText) findViewById(R.id.texto);
        tema.setText(encontro.getTema());
        voltar = (ImageView) findViewById(R.id.voltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        adapter = new GroupAdapter();
        chat.setLayoutManager(new LinearLayoutManager(context));
        chat.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!texto.getText().toString().equals("")){
                    Date date = new Date();
                    Mensagem mensagem = new Mensagem(MainActivity.genUUI(),
                            UsuarioAutenticado.UsuarioLogado().getDisplayName(),
                            UsuarioAutenticado.UsuarioLogado().getUid(),
                            texto.getText().toString(),
                            date);
                    myRef.child("Chat").child(mensagem.getIdMensagem()).setValue(mensagem);
                    texto.setText("");
                }
            }
        });
        getMessages();
    }

    private void getMessages() {
        Query query = myRef.child("Chat").orderByChild("data");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                ArrayList<Mensagem> lista = new ArrayList<Mensagem>();
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Mensagem mensagem = objsnapshot.getValue(Mensagem.class);
                    if(mensagem!=null){
                        lista.add(mensagem);
                    }
                }
                Collections.sort(lista);
                for (Mensagem mensagem:lista){
                    adapter.add(new MessageItem(mensagem));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private class MessageItem extends Item<ViewHolder> {

        private final Mensagem mensagem;

        private MessageItem(Mensagem message) {
            this.mensagem = message;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView nome = viewHolder.itemView.findViewById(R.id.nomeusuario);
            TextView txtMsg = viewHolder.itemView.findViewById(R.id.texto);

            nome.setText(mensagem.getNomeUsuario());
            txtMsg.setText(mensagem.getTexto());
        }

        @Override
        public int getLayout() {
            return mensagem.getIdUsuario().equals(UsuarioAutenticado.UsuarioLogado().getUid())
                    ? R.layout.mensagem_enviada
                    : R.layout.mensagem_recebida;
        }
    }
}

