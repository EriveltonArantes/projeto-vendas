package br.com.badi.projeto_vendas.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Erivelton
 */
public class Funcionarios extends Clientes{

    private String senha;
    private String cargo;
    private String nivelAcesso;

    public Funcionarios(){}
    
    public Funcionarios(String senha, String cargo, String nivelAcesso) {
        this.senha = senha;
        this.cargo = cargo;
        this.nivelAcesso = nivelAcesso;
    }
    
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getNivelAcesso() {
        return nivelAcesso;
    }
    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    
}

