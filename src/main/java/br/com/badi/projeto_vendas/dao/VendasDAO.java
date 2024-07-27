package br.com.badi.projeto_vendas.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.badi.projeto_vendas.jdbc.ConnectionFactory;
import br.com.badi.projeto_vendas.model.Clientes;
import br.com.badi.projeto_vendas.model.Vendas;


public class VendasDAO {

    private Connection con;

    public VendasDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    // Cadastrar venda
    public void cadastrarVenda(Vendas obj) {
        try {
            String sql = "INSERT INTO tb_vendas (cliente_id, data_venda, total_venda, observacoes) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getCliente().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObs());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    // Método para retornar a última venda
    public int ultimaVenda() {
        int idvenda = 0;
        try {
            String sql = "SELECT MAX(id) AS id FROM tb_vendas";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Vendas p = new Vendas();
                p.setId(rs.getInt("id"));
                idvenda = p.getId();
            }
        } catch (SQLException e) {
            // Trate a exceção de acordo com a sua lógica de negócio
            e.printStackTrace();
        }
        return idvenda;
    }

    //filtra vendas por data
    public List<Vendas> listarVendasPorPeriodo(LocalDate data_inicio, LocalDate data_fim) {
        List<Vendas> lista = new ArrayList<>();
        String sql = "SELECT v.id, date_format(v.data_venda, '%d/%m/%Y') as data_formatada  , c.nome, v.totalvenda, v.observacoes "
                + "FROM tb_vendas as v inner join tb_clientes as c on (v.cliente_id = c.id) "
                + "where v.datavenda BETWEEN ? and ?";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vendas obj = new Vendas();
                    Clientes c = new Clientes();

                    obj.setId(rs.getInt("v.id"));
                    obj.setData_venda(rs.getString("data_formatada"));
                    c.setNome(rs.getString("c.nome"));
                    obj.setTotal_venda(rs.getDouble("v.totalvenda"));
                    obj.setObs(rs.getString("v.observacoes"));

                    obj.setCliente(c);
                    lista.add(obj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Você pode adicionar tratamento adicional de erro aqui se necessário.
        }
        return lista;
    }

    //metodo calcula total de venda por data
    public double retornaTotalVendaPorData(LocalDate data_venda) {
        try {
            double totalvenda = 0;

            String sql = "SELECT SUM(total_venda) AS total FROM Tb_vendas WHERE data_venda = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, data_venda.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalvenda = rs.getDouble("total");
            }

            return totalvenda;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

}
