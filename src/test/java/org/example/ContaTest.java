package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conjunto de Testes para a Lógica Principal da Conta Bancária")
class ContaTest {

    private Conta conta;
    private final String TITULAR_PADRAO = "Maria Santos";
    private final double SALDO_INICIAL = 100.00;

    @BeforeEach
    void setup() {
        // Inicializa a conta com saldo zero para alguns testes, ou deposita saldo inicial para outros
        conta = new Conta(TITULAR_PADRAO);
    }


    @Test
    @DisplayName("T1: Deve iniciar com saldo zero (Construtor)")
    void t1_deveIniciarComSaldoZero() {
        assertEquals(0.0, conta.getSaldo(), 0.001, "O saldo inicial deve ser zero.");
    }

    @Test
    @DisplayName("T2: Deve armazenar e retornar o nome do titular")
    void t2_deveRetornarTitularCorreto() {
        assertEquals(TITULAR_PADRAO, conta.getTitular(), "O nome do titular deve ser retornado corretamente.");
    }

    // 2. Testes de Depósito
    // -------------------------------------------------------------

    @Test
    @DisplayName("T3: Deve aumentar o saldo com depósito simples")
    void t3_deveAumentarSaldoComDepositoSimples() {
        double valorDeposito = 50.00;
        conta.depositar(valorDeposito);

        // Saldo esperado: 0.0 + 50.00 = 50.00
        assertEquals(50.00, conta.getSaldo(), 0.001, "O saldo deve ser igual ao valor depositado.");
    }

    @Test
    @DisplayName("T4: Deve somar múltiplos depósitos ao saldo")
    void t4_deveSomarMultiplosDepositos() {
        conta.depositar(10.00);
        conta.depositar(20.50);

        // Saldo esperado: 10.00 + 20.50 = 30.50
        assertEquals(30.50, conta.getSaldo(), 0.001, "O saldo deve ser a soma de todos os depósitos.");
    }

    // 3. Testes de Saque (Saque Válido)
    // -------------------------------------------------------------

    @Test
    @DisplayName("T5: Deve subtrair valor e retornar true em saque válido")
    void t5_deveSubtrairSaldoERetornarTrueEmSaqueValido() {
        conta.depositar(SALDO_INICIAL);
        double valorSaque = 40.00;

        // Ação:
        boolean resultado = conta.sacar(valorSaque);

        // Verificação 1: Saldo esperado: 100.00 - 40.00 = 60.00
        assertEquals(60.00, conta.getSaldo(), 0.001, "O saldo deve ser reduzido após o saque.");

        // Verificação 2:
        assertTrue(resultado, "O saque deve ser bem-sucedido e retornar true.");
    }

    @Test
    @DisplayName("T6: Deve sacar o valor total, deixando o saldo zerado")
    void t6_deveZerarSaldoAoSacarTotal() {
        conta.depositar(75.50);

        // Ação: Sacar o valor exato do saldo
        boolean resultado = conta.sacar(75.50);

        // Verificação:
        assertEquals(0.0, conta.getSaldo(), 0.001, "O saldo deve ser zero após sacar o valor total.");
        assertTrue(resultado, "O saque do valor total deve ser bem-sucedido.");
    }

    // 4. Testes de Saque (Saldo Insuficiente/Inválido)
    // -------------------------------------------------------------

    @Test
    @DisplayName("T7: Não deve sacar e deve retornar false por saldo insuficiente")
    void t7_naoDeveSacarPorSaldoInsuficiente() {
        conta.depositar(10.00); // Saldo: 10.00

        // Ação: Tenta sacar um valor maior
        boolean resultado = conta.sacar(50.00);

        // Verificação 1:
        assertFalse(resultado, "O saque deve falhar e retornar false.");

        // Verificação 2: O saldo não deve ter mudado
        assertEquals(10.00, conta.getSaldo(), 0.001, "O saldo deve permanecer inalterado após saque falho.");
    }


    @Test
    @DisplayName("T9: Depósito de valor zero (comportamento atual: não muda o saldo)")
    void t9_depositoDeValorZero() {
        double saldoAntes = conta.getSaldo(); // 0.0
        conta.depositar(0.0);

        // O método depositar permite a soma (0.0 + 0.0), então o saldo deve permanecer o mesmo
        assertEquals(saldoAntes, conta.getSaldo(), "O saldo não deve ser afetado por depósito de zero.");
    }

    @Test
    @DisplayName("T10: Saque com saldo negativo (Garante que a classe original suporta, se for necessário)")
    void t10_naoDevePermitirSaldoNegativo() {
        // O saque com saldo insuficiente falha (T7), garantindo que o saldo NUNCA é negativo.
        // Se a lógica do 'sacar' fosse apenas 'saldo -= valor', este teste falharia.
        conta.depositar(50.00);
        conta.sacar(100.00); // Falha, saldo permanece 50.00

        assertFalse(conta.sacar(100.00), "Tentar sacar mais que o saldo deve ser impedido.");
        assertTrue(conta.getSaldo() >= 0, "O saldo nunca deve ser negativo após qualquer operação de saque.");
    }
}