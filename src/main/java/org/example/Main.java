package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco();

        // Pede o nome do titular
        System.out.print("Nome do titular: ");
        String nome = sc.nextLine();

        // Validação do nome (não aceita números nem caracteres especiais)
        while (!Validador.validarNome(nome)) {
            System.out.println("Nome inválido! Digite novamente (somente letras e espaços):");
            nome = sc.nextLine();
        }

        Conta conta = banco.criarConta(nome);

        // Menu do sistema bancário
        while (true) {
            System.out.println("\n1 - Ver saldo");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Sair");

            String op = sc.nextLine();
            switch (op) {
                case "1":
                    System.out.println("Saldo de " + conta.getTitular() + ": R$ " + conta.getSaldo());
                    break;

                case "2":
                    System.out.print("Valor: ");
                    try {
                        double deposito = Double.parseDouble(sc.nextLine());
                        conta.depositar(deposito);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido! Digite apenas números.");
                    }
                    break;

                case "3":
                    System.out.print("Valor: ");
                    try {
                        double saque = Double.parseDouble(sc.nextLine());
                        if (!conta.sacar(saque)) {
                            System.out.println("Saldo insuficiente.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido! Digite apenas números.");
                    }
                    break;

                case "4":
                    System.out.println("Obrigado! Volte sempre.");
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
