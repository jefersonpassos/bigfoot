/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.tads.gerenciadordeprodutos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author p0636192
 */
public class Gerenciador {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    private Connection obterConexao() throws ClassNotFoundException, SQLException {
        // 1A) Declarar o driver JDBC de acordo com o Banco de dados usado
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/produtobd", "root", "");
        return conn;
    }

    public List<Produto> listar() throws ClassNotFoundException, SQLException {
        List<Produto> listaProdutos = new ArrayList<Produto>();

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, nome, descricao, preco_compra, preco_venda, quantidade, dt_cadastro FROM PRODUTO");
                ResultSet resultados = stmt.executeQuery()) {

            while (resultados.next()) {
                long id = resultados.getLong("id");
                String nome = resultados.getString("nome");
                String descricao = resultados.getString("descricao");
                float precoVenda = resultados.getFloat("preco_venda");
                float precoCompra = resultados.getFloat("preco_compra");
                int quantidade = resultados.getInt("quantidade");
                Date dtCadastro = resultados.getDate("dt_cadastro");
                Produto produto = new Produto();
                produto.setId(id);
                produto.setNome(nome);
                produto.setDescricao(descricao);
                produto.setPrecoVenda(precoVenda);
                produto.setPrecoCompra(precoCompra);
                produto.setQuantidade(quantidade);
                produto.setDtCadastro(dtCadastro);

                listaProdutos.add(produto);
            }
            return listaProdutos;
        }
    }

    public void incluir() throws ClassNotFoundException, SQLException {

        Scanner in = new Scanner(System.in);
        System.out.println("Digite o nome do produto:");
        String nome = in.nextLine();
        System.out.println("Digite descricao:");
        String descricao = in.nextLine();
        System.out.println("Digite o preco de compra:");
        float preco_compra = (Float.parseFloat(in.nextLine()));
        System.out.println("Digite o preco de venda:");
        float preco_venda = (Float.parseFloat(in.nextLine()));
        System.out.println("Digite a quantidade:");
        int quantidade = (Integer.parseInt(in.nextLine()));

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO PRODUTO (nome, descricao, preco_compra, preco_venda, quantidade, dt_cadastro ) VALUES (?,?,?,?,?,?)")) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setFloat(3, preco_compra);
            stmt.setFloat(4, preco_venda);
            stmt.setInt(5, quantidade);
            stmt.setTimestamp(6, new java.sql.Timestamp(java.util.Calendar.getInstance().getTimeInMillis()));

            int status = stmt.executeUpdate();
            System.out.println("Status: " + status);
        }
    }
    
    public void excluir() throws ClassNotFoundException, SQLException {

        Scanner in = new Scanner(System.in);
        System.out.println("Digite o id do produto:");
        int id = (Integer.parseInt(in.nextLine()));
        
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM PRODUTO WHERE ID = (?)")) {
            stmt.setInt(1, id);

            int status = stmt.executeUpdate();
        }
    }
    
    public void editar(int id, Produto prod) throws ClassNotFoundException, SQLException { 
        
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "update produto set nome=(?),descricao=(?), preco_compra=(?), preco_venda=(?), quantidade=(?) where ID = (?)")) {
            stmt.setInt(6, id);
            stmt.setString(1, prod.getNome());
            stmt.setString(2, prod.getDescricao());
            stmt.setFloat(3, prod.getPrecoCompra());
            stmt.setFloat(4, prod.getPrecoVenda());
            stmt.setInt(5, prod.getQuantidade());

            int status = stmt.executeUpdate();
            System.out.println("Status: " + status);
        }
    }
    
}
