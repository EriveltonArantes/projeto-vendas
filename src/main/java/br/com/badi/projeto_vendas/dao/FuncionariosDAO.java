package br.com.badi.projeto_vendas.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.badi.projeto_vendas.jdbc.ConnectionFactory;
import br.com.badi.projeto_vendas.model.Funcionarios;
import br.com.badi.projeto_vendas.view.Login;
import br.com.badi.projeto_vendas.view.Menu;




public class FuncionariosDAO {
    private Connection con;

    public FuncionariosDAO() {
        this.con = new ConnectionFactory().getConnection();
    }


    //metodo cadastrar

    public void cadastrarFuncionarios(Funcionarios obj) {

        try {

            String sql = "insert into tb_funcionarios(nome,rg,cpf,email,senha,"+
            "cargo,nivel_acesso,telefone,celular,cep,endereco,numero,complemento,"
            +"bairro,cidade,estado)"+
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivelAcesso());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getUf());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null,"Cadastrado com Sucesso! ");

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(null,"Cadastro NÃO REALIZADO! " + erro);

        }
    }

    
    //metodo listar todos funcionarios
    public List<Funcionarios> listarFuncionarios() {

        try {

            List<Funcionarios> lista = new ArrayList<>();
            String sql = "select * from tb_funcionarios";

            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionarios obj = new Funcionarios();

                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivelAcesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));
                lista.add(obj);
            }
            return lista;

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(null,"Funcionario NÃO Encontrado! " + erro);

            return null;

        }
        
    }
    //metodo efetuar login
    public void efetuarLogin(String email, String senha) {
    try {
        String sql = "SELECT * FROM tb_funcionarios WHERE email = ? AND senha = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, senha);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String nivelAcesso = rs.getString("nivel_acesso");
            String nomeFuncionario = rs.getString("nome");

            if (nivelAcesso.equals("Administrador")) {
                JOptionPane.showMessageDialog(null, "Bem vindo, " + nomeFuncionario);
                Menu tela = new Menu();
                tela.usuarioLogado = nomeFuncionario;
                tela.setVisible(true);
            } else if (nivelAcesso.equals("Usuario")) {
                JOptionPane.showMessageDialog(null, "Bem vindo, " + nomeFuncionario);
                Menu tela = new Menu();
                tela.usuarioLogado = nomeFuncionario;

                // Desabilitar os menus
                tela.menuposicaodia.setEnabled(false);
                tela.menucontrolevendas.setEnabled(false);
                tela.menufornecedores.setEnabled(false);
                tela.menufuncionarios.setEnabled(false);

                tela.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Email ou Senha Inválidos");
            new Login().setVisible(true);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao efetuar o login: " + ex.getMessage());
    }
}

    
    // metodo excluir funcionario

    public void excluirFuncionarios(Funcionarios obj) {

        try {

            String sql = "delete from tb_funcionarios where id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getId());    

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null,"Excluido com Sucesso! ");

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(null,"NÃO Excluido! " + erro);

        }

    }
    // metodo alterar funcionario

    public void alterarFuncionarios(Funcionarios obj) {

        try {

            String sql = "update tb_funcionarios set nome=?,rg=?,cpf=?,email=?,senha=?,cargo=?,"+
            "nivel_acesso=?,telefone=?,celular=?,cep=?,endereco=?,numero=?,complemento=?,"+
            "bairro=?,cidade=?,estado=? where id=?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivelAcesso());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getUf());
            stmt.setInt(17, obj.getId());

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null,"Alterado com Sucesso! ");

        } catch (Exception erro) {

            JOptionPane.showMessageDialog(null,"NÃO Alterado! " + erro);

        }

    }
    //consulta funcionario pelo nome
    public Funcionarios consultaPorNome(String nome) {
        try {
            String sql = "SELECT * FROM tb_funcionarios WHERE nome = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,  nome );
            ResultSet rs = stmt.executeQuery();

            Funcionarios obj = new Funcionarios();

            if (rs.next()) {
                obj = new Funcionarios();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivelAcesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));
            }

            return obj;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Funcionário não encontrado! " + e);
        }

        return null;
    }

    //metodo buscar funcionario por nome
    public List <Funcionarios> buscarFuncionarioPorNome(String nome) {
        try {
            List <Funcionarios> lista = new ArrayList<>();
            String sql = "select * from tb_funcionarios where nome like ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionarios obj = new Funcionarios();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivelAcesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));

                lista.add(obj);

            }

            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao Consultar! " + e);
        }

        return null;
    }
}












