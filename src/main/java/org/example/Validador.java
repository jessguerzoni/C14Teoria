package org.example;

public class Validador {

    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        // Regex que permite letras (com acento) e espaços
        return nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");
    }
}