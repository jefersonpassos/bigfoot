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
import java.util.Date;

/**
 *
 * @author p0636192
 */
public class Gerenciador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        
        Gerenciador gerenciador = new Gerenciador();
        
        gerenciador.listar();
    }
    
    private Connection obterConexao() throws ClassNotFoundException, SQLException {
            // 1A) Declarar o driver JDBC de acordo com o Banco de dados usado
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/agendabd", "root", "");
            return conn;
    }
    
    public void listar() throws ClassNotFoundException, SQLException{
        
        try(Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, nome, descricao, preco_compra, preco_venda, quantidade, dt_cadastro FROM PRODUTO");
                ResultSet resultados = stmt.executeQuery()) {
            
            while (resultados.next()) {
                long id = resultados.getLong("id");
                String nome = resultados.getString("nome");
                String descricao = resultados.getString("descricao");
                float preco_venda = resultados.getFloat("preco_venda");
                float preco_compra = resultados.getFloat("preco_compra");
                int quantidade = resultados.getInt("quantidade");
                Date dt_cadastro = resultados.getDate("dt_cadastro");
                System.out.println("id           | " + id);
                System.out.println("nome         | " + nome);
                System.out.println("descricao    | " + descricao);
                System.out.println("preco_venda  | " + preco_venda);
                System.out.println("preco_compra | " + preco_compra);
                System.out.println("quantidade   | " + quantidade);
                System.out.println("data cadastro| " + dt_cadastro);
            
            }
        }
    }
    
}
