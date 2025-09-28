package org.example;

import java.util.HashMap;
import java.util.Map;

//testar
public class Banco {
    private Map<String, Conta> contas = new HashMap<>();

    public Conta criarConta(String nome) {
        Conta conta = new Conta(nome);
        contas.put(nome, conta);
        return conta;
    }

    public Conta buscarConta(String nome) {
        return contas.get(nome);
    }
}