package br.com.badi.projeto_vendas.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import br.com.badi.projeto_vendas.jdbc.ConnectionFactory;
import br.com.badi.projeto_vendas.model.ItemVenda;
import br.com.badi.projeto_vendas.model.Produtos;


/**
 *
 * @author Erivelton
 */
public class ItemVendaDAO {

    private Connection con;

    public ItemVendaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //metodo que cadastra itens
    public void cadastrarItem(ItemVenda obj) {
        try {
            String sql = "INSERT INTO tb_itensvendas (venda_id, produto_id, qtd, subtotal) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getVenda().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubTotal());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    //metodo lista itens de uma venda por id    
    public List<ItemVenda> listarItensPorVenda(int venda_id) {
        List<ItemVenda> lista = new ArrayList<>();
        String sql = "SELECT i.id, p.descricao, i.qtd, p.preco, i.subtotal FROM tb_itensvendas AS i "
                + "INNER JOIN tb_produtos AS p ON (i.produto_id = p.id) WHERE i.venda_id = ?";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, venda_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemVenda item = new ItemVenda();
                Produtos prod = new Produtos();

                item.setId(rs.getInt("i.id"));
                prod.setDescricao(rs.getString("p.descricao"));
                item.setQtd(rs.getInt("i.qtd"));
                prod.setPreco(rs.getDouble("p.preco"));
                item.setSubTotal(rs.getDouble("i.subtotal"));

                item.setProduto(prod);

                lista.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Você pode adicionar tratamento adicional de erro aqui se necessário.
        }
        return lista;
    }
}
