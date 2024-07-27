package br.com.badi.projeto_vendas.jdbc;

import java.sql.Connection;

import javax.swing.JOptionPane;

public class TestaConexao {

    public static void main(String[] args) {

        try {
            new ConnectionFactory().getConnection();

            JOptionPane.showMessageDialog(null,"Conexão realizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Conexão não realizada!" + e);
        }

    }
}
