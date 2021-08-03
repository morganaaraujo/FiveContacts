package com.example.fivecontacts.main.dados;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fivecontacts.main.model.Contato;
import com.example.fivecontacts.main.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//Classe que contem os métodos para persistencia dos contatos;
public class Contatos {

    public static void atualizarListaDeContatos(Context context, User user) {
        SharedPreferences recuperarContatos = context.getSharedPreferences("contatos" + user.getLogin(), Activity.MODE_PRIVATE);

        int num = recuperarContatos.getInt("numContatos", 0);

        Contato contato;
        ArrayList<Contato> contatos = new ArrayList<Contato>();

        for (int i = 1; i <= num; i++) {
            String objSel = recuperarContatos.getString("contato" + i, "");
            if (objSel.compareTo("") != 0) {
                try {
                    ByteArrayInputStream bis =
                            new ByteArrayInputStream(objSel.getBytes(StandardCharsets.ISO_8859_1.name()));
                    ObjectInputStream oos = new ObjectInputStream(bis);
                    contato = (Contato) oos.readObject();

                    if (contato != null) {
                        contatos.add(contato);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Log.v("PDM3", "contatos:" + contatos.size());
        user.setContatos(contatos);
    }

    //Deleta os contatos
    public static void deletarContato(Context context, int index, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("contatos" + user.getLogin(), Activity.MODE_PRIVATE);
        int totalElementos = sharedPreferences.getInt("numContatos", 0);
        if (totalElementos == 0)
            return;
        //uso do editor para manipular os contatos
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("numContatos", totalElementos - 1);

        for (int i = index; i < totalElementos - 1; i++) {
            String x = sharedPreferences.getString("contato" + (i + 2), "");
            editor.putString("contato" + (i + 1), x);
        }

        editor.commit();

        user.getContatos().remove(index);
    }

    public static void salvarContato(Context context, Contato w, User user) {
        SharedPreferences salvaContatos = context.getSharedPreferences("contatos" + user.getLogin(), Activity.MODE_PRIVATE);

        int num = salvaContatos.getInt("numContatos", 0); //checando quantos contatos já tem
        if (num >= 5) {
            throw new RuntimeException("muito contatos");
        }

        SharedPreferences.Editor editor = salvaContatos.edit();
        try {
            ByteArrayOutputStream dt = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(dt);
            dt = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(dt);
            oos.writeObject(w);
            String contatoSerializado = dt.toString(StandardCharsets.ISO_8859_1.name());
            editor.putString("contato" + (num + 1), contatoSerializado);
            editor.putInt("numContatos", num + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
        user.getContatos().add(w);
    }


}
