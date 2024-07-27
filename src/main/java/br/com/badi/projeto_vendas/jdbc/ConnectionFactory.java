package br.com.badi.projeto_vendas.jdbc;

import java.sql.Connection;

import java.sql.DriverManager;

public class ConnectionFactory {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bdvendas","teste","123");
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }
}
