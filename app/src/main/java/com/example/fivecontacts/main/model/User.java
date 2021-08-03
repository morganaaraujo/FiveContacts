package com.example.fivecontacts.main.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User implements Serializable {

    String nome;
    String login;
    String senha;
    String email;
    boolean manterLogado = false;
    ArrayList<Contato> contatos;

    public User(String nome, String login, String password, String email, boolean manterLogado) {
        this.nome = nome;
        this.login = login;
        this.senha = password;
        this.email = email;
        this.manterLogado = manterLogado;
        this.contatos = new ArrayList<Contato>();
    }

    public User() {
        this.contatos = new ArrayList<Contato>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isManterLogado() {
        return manterLogado;
    }

    public void setManterLogado(boolean manterLogado) {
        this.manterLogado = manterLogado;
    }

    public ArrayList<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(ArrayList<Contato> contatos) {
        this.contatos = contatos;
    }

}
