package br.com.badi.projeto_vendas.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Erivelton
 */



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.badi.projeto_vendas.jdbc.ConnectionFactory;
import br.com.badi.projeto_vendas.model.Fornecedores;
import br.com.badi.projeto_vendas.model.Produtos;



/**
 *
 * @author Erivelton
 */
public class ProdutosDao {

    private Connection con;

    public ProdutosDao() {
        this.con = new ConnectionFactory().getConnection();
    }

    public void cadastrarProdutos(Produtos obj) {
        try {

            String sql = "insert into tb_produtos (descricao,preco,qtde_estoque,for_id) values (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtdEstoque());
            stmt.setInt(4, obj.getFornecedor().getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto Cadastrado Com Sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro" + e);
        }
    }

    public Produtos consultaPorNome(String nome) {
        try {
            String sql = "select p.id, p.descricao, p.qtd_estoque, f.nome "
                    + "from tb_produtos as p inner join tb_fornecedores as "
                    + "f on (p.for_id = f.id) where p.descricao = ?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();

            if (rs.next()) {
                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtdEstoque(rs.getInt("qtd_estoque"));
                f.setNome(rs.getString("f.nome"));
                
                obj.setFornecedor(f);
                return obj;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado! " + e);
        }

        return null;
    }
    
    //busca produto por codigo
    
    public Produtos buscaPorCodigo(int id) {
        try {
            String sql = "select * from tb_produtos where id = ?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            Produtos obj = new Produtos();
            

            if (rs.next()) {
                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtdEstoque(rs.getInt("qtd_estoque"));
               
                
                
                return obj;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado! " + e);
        }

        return null;
    }
    
    
    public List<Produtos> listarProdutos() {
        List<Produtos> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.descricao, p.preco, p.qtd_estoque, f.nome "
                + "FROM tb_produtos AS p INNER JOIN tb_fornecedores AS f ON "
                + "p.for_id = f.id";

        try ( PreparedStatement stmt = con.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtdEstoque(rs.getInt("qtd_estoque"));
                f.setNome(rs.getString("nome"));

                obj.setFornecedor(f);

                lista.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Você pode adicionar tratamento adicional de erro aqui se necessário.
        }
        return lista;
    }
    
    //metodo listar por nome
   public List<Produtos> listarProdutosPorNome(String nome) {
    List<Produtos> lista = new ArrayList<>();
    String sql = "SELECT p.id, p.descricao, p.preco, p.qtd_estoque, f.nome "
               + "FROM tb_produtos AS p INNER JOIN tb_fornecedores AS f ON "
               + "(p.for_id = f.id) WHERE p.descricao LIKE ?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setString(1, "%" + nome + "%"); // Adicionado % para o LIKE funcionar corretamente

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("id"));
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtdEstoque(rs.getInt("qtd_estoque"));
                f.setNome(rs.getString("nome"));

                obj.setFornecedor(f);

                lista.add(obj);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Você pode adicionar tratamento adicional de erro aqui se necessário.
    }
    return lista;
}

    
    public void cadastrar(Produtos obj){
        try {
          
            String sql = "insert into tb_produtos (descricao,)";
            
        } catch (Exception e) {
        
        }
    }

    //metodo alterar produtos
    public void alterarProdutos(Produtos obj) {
        try {

            String sql = "update tb_produtos set descricao = ?, preco = ?, qtd_estoque = ?, for_id = ? where id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtdEstoque());
            stmt.setInt(4, obj.getFornecedor().getId());

            stmt.setInt(5, obj.getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto Alterado Com Sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro" + e);
        }
    }

    //metodo excluir produtos
    public void excluirProdutos(Produtos obj) {
        try {

            String sql = "delete from tb_produtos where id=?";

            //conectar com bd e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto Excluido Com Sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "NÃO Excluido! " + erro);
        }
    }

    //metodo alterar todos os produtos
    public void alterarTodosProdutos(Produtos obj) {
        try {

            String sql = "update tb_produtos set descricao = ?, preco = ?, qtd_estoque = ?, for_id = ? where id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtdEstoque());
            stmt.setInt(4, obj.getFornecedor().getId());

            stmt.setInt(5, obj.getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produtos Alterados Com Sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro" + e);
        }
    }
     //metodo da baixa no estoqur
    public void baixarEstoque(int id, int qtd_nova){
        try {
            
            String sql="uptade tb_produtos set qtd_estoque =? where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            
            stmt.execute();
            stmt.close();
            
            
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Erro" + erro);
        }
    }
    //retorna estoque atual do produto
    public int retornaEsoqueAtual(int id){
        try {
            int qtd_estoque = 0;
            
            String sql = "select qtd_estoque from tb_produtos where id =?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                Produtos p = new Produtos();
                
                qtd_estoque = (rs.getInt("qtd_estoque"));
            }
            return qtd_estoque;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //metodo que da baixa no estoque
    public void adicionarEstoque(int id, int qtd_nova){
        try {
            
            String sql = "uptade tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1,qtd_nova);
            stmt.setInt(1,id);
            
            stmt.execute();
            stmt.close();
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro" + e);
        }
    }
}


