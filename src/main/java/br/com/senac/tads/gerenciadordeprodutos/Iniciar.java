/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.tads.gerenciadordeprodutos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Iniciar {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here

        Gerenciador gerenciador = new Gerenciador();
        int opcao = 1;
        System.out.println("Gerenciador de Produtos Iniciado");
        while (opcao != 0) {
            System.out.println("\nDigite a opcao da operacao que deseja executar:");
            System.out.println("1 - Listar os produtos");
            System.out.println("2 - Incluir produto");
            System.out.println("3 - Excluir produto");
            System.out.println("4 - Atualizar os produtos");
            System.out.println("0 - Fechar o Programa\n");

            Scanner sc = new Scanner(System.in);
            try {
                opcao = (Integer.parseInt(sc.nextLine()));
            } catch (Exception e) {
                System.out.println("Ocorreu um erro, tente novamente");
            }
            List<Produto> listaprod = new ArrayList<Produto>();
            switch (opcao) {
                case 1:
                    listaprod = gerenciador.listar();
                    for (int i = 0; i < listaprod.size(); i++) {
                        Produto prod = listaprod.get(i);
                        if (prod != null) {
                            System.out.println("\nID: " + prod.getId());
                            System.out.println("Nome: " + prod.getNome());
                            System.out.println("Descricao: " + prod.getDescricao());
                            System.out.println("Preco de Compra: " + prod.getPrecoCompra());
                            System.out.println("Preco de Venda: " + prod.getPrecoVenda());
                            System.out.println("Quantidade de produtos: " + prod.getQuantidade());
                        }
                    }
                    break;
                case 2:
                    gerenciador.incluir();
                    break;
                case 3:
                    gerenciador.excluir();
                    break;
                case 4:
                    Produto ProdEditar = new Produto();
                    System.out.println("digite o id do produto:");
                    int id = (Integer.parseInt(sc.nextLine()));
                    System.out.println("digite o nome do produto:");
                    String nome = sc.nextLine();
                    ProdEditar.setNome(nome);
                    System.out.println("digite a descricao do produto:");
                    String descricao = sc.nextLine();
                    ProdEditar.setDescricao(descricao);
                    System.out.println("digite o preco de venda do produto:");
                    float precoVenda = (Float.parseFloat(sc.nextLine()));
                    ProdEditar.setPrecoVenda(precoVenda);
                    System.out.println("digite o preco de compra do produto:");
                    float precoCompra = (Float.parseFloat(sc.nextLine()));
                    ProdEditar.setPrecoCompra(precoCompra);
                    System.out.println("digite quantidade disponivel do produto:");
                    int quantidade = (Integer.parseInt(sc.nextLine()));
                    ProdEditar.setQuantidade(quantidade);
                    
                    gerenciador.editar(id, ProdEditar);
                    break;
                case 0:
                    System.out.println("\nPrograma Finalizado");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcao não é válida, Insira uma nova opcao");

            }
        }
    }
}
