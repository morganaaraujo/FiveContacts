package com.example.fivecontacts.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fivecontacts.R;
import com.example.fivecontacts.main.dados.Contatos;
import com.example.fivecontacts.main.model.Contato;
import com.example.fivecontacts.main.model.User;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChecagemLogin_Activity extends AppCompatActivity {

    boolean primeiraVezUser=true;
    boolean primeiraVezSenha=true;
    EditText edUser;
    EditText edPass;
    Button btLogar;
    TextView btNovo;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checagem_login);

        //Existe um usuário padrão logado?
        if(montarObjetoUserSemLogar()){
            User user = montarObjetoUser();

            Contatos.atualizarListaDeContatos(this, user);

            //Abrir a atividade de Lista de Contatos
            Intent intent = new Intent(ChecagemLogin_Activity.this, ListaDeContatos_Activity.class);
            intent.putExtra("usuario",user);
            startActivity(intent);
            finish();
        } else { //Checar Usuário e Senha ou clicar em criar novo
            btLogar = findViewById(R.id.btLogar);
            btNovo = findViewById(R.id.btNovo);
            edUser = findViewById(R.id.edT_Login);
            edPass = findViewById(R.id.edt_Pass);

            //Colocando Underline (Vamos usar esse campo mais na frente com o FireBase)
            mTextView = findViewById(R.id.tvEsqueceuSenha);
            mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            //Evento de limpar Componente
            edUser.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (primeiraVezUser) {
                        edUser.setText("");
                        primeiraVezUser = false;
                    }
                    return false;
                }
            });

            //Evento de limpar Componente
            edPass.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (primeiraVezSenha) {
                        primeiraVezSenha = false;
                        edPass.setText("");
                        edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                        );
                    }
                    return false;
                }
            });

            btLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Ao clicar deve-se:
                    //1- Checar se existe um SharedPreferences
                    //2- Comparar login e senha salvos
                    //3- Se tudo der certo, resgatar lista de contatos
                    //4- Abrir a Atividade lista de Contatos passando como parametro o objeto User e seus 5 Contatos

                    SharedPreferences temUser = getSharedPreferences("usuarioPadrao", Activity.MODE_PRIVATE);
                    String loginSalvo = temUser.getString("login", "");
                    String senhaSalva = temUser.getString("senha", "");

                    if ((loginSalvo != null) && (senhaSalva != null)) {
                        //Recuperando da tela
                        String senha = edPass.getText().toString();
                        String login = edUser.getText().toString();

                        //Comparando
                        if ((loginSalvo.compareTo(login) == 0)
                                && (senhaSalva.compareTo(senha) == 0)) {

                            User user = montarObjetoUser();
                            Contatos.atualizarListaDeContatos(ChecagemLogin_Activity.this, user);
                            //Abrindo a Lista de Contatos
                            Intent intent = new Intent(ChecagemLogin_Activity.this, ListaDeContatos_Activity.class);
                            intent.putExtra("usuario", user);
                            startActivity(intent);


                        } else {
                            Toast.makeText(ChecagemLogin_Activity.this, "Login e Senha Incorretos", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(ChecagemLogin_Activity.this, "Login e Senha nulos", Toast.LENGTH_LONG).show();

                    }

                }
            });

            //Novo Usuário
            btNovo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChecagemLogin_Activity.this, NovoUsuario_Activity.class);
                    startActivity(intent);
                }
            });

        }
   }

    private User montarObjetoUser() {
        User user = null;
        SharedPreferences temUser= getSharedPreferences("usuarioPadrao", Activity.MODE_PRIVATE);
        String loginSalvo = temUser.getString("login","");
        String senhaSalva = temUser.getString("senha","");
        String nomeSalvo = temUser.getString("nome","");
        String emailSalvo = temUser.getString("email","");
        boolean manterLogado=temUser.getBoolean("manterLogado",false);

        user=new User(nomeSalvo,loginSalvo,senhaSalva,emailSalvo,manterLogado);
        return user;
    }


    private boolean montarObjetoUserSemLogar() {
        SharedPreferences temUser= getSharedPreferences("usuarioPadrao", Activity.MODE_PRIVATE);
        boolean manterLogado = temUser.getBoolean("manterLogado",false);
        return manterLogado;
    }

}