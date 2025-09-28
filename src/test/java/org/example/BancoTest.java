package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários para a Classe Banco (Gerenciamento de Contas)")
class BancoTest {

    private Banco banco;
    private Conta contaPadrao;
    private final String NOME_TITULAR = "Fulano de Tal";

    @BeforeEach
    void setup() {
        // Inicializa uma nova instância do Banco antes de cada teste
        banco = new Banco();
        // Cria uma conta de referência para testes
        contaPadrao = new Conta(NOME_TITULAR);
    }

    // --- Teste 1: Criação e Armazenamento da Conta ---
    @Test
    @DisplayName("T1: Deve criar uma nova conta e armazená-la para busca imediata")
    void t1_deveCriarNovaContaEstarDisponivelNaBusca() {
        // Ação: Cria a conta através do método do Banco
        Conta novaConta = banco.criarConta(NOME_TITULAR);

        // Ação: Busca a conta usando o mesmo nome
        Conta contaBuscada = banco.buscarConta(NOME_TITULAR);

        // Verificação: 1. A conta deve ser encontrada e não deve ser nula
        assertNotNull(contaBuscada, "A conta deve ser encontrada no mapa após a criação.");

        // Verificação: 2. A instância criada e a instância buscada devem ser a mesma
        assertSame(novaConta, contaBuscada, "O objeto retornado pela busca deve ser o mesmo objeto criado.");
    }

    // --- Teste 2: Buscar Conta Existente ---
    @Test
    @DisplayName("T2: Deve buscar e retornar uma conta existente pelo nome do titular")
    void t2_deveBuscarContaExistenteComSucesso() {
        String nomeTitular2 = "Cicrano Teste";
        banco.criarConta(NOME_TITULAR); // Cria a conta padrão
        banco.criarConta(nomeTitular2); // Cria uma segunda conta

        // Ação: Buscar a segunda conta
        Conta contaEncontrada = banco.buscarConta(nomeTitular2);

        // Verificação: A conta deve ser encontrada e ter o titular correto
        assertNotNull(contaEncontrada, "A conta deve ser encontrada quando existe.");
        assertEquals(nomeTitular2, contaEncontrada.getTitular(), "A conta encontrada deve ter o titular solicitado.");
    }

    // --- Teste 3: Buscar Conta Inexistente ---
    @Test
    @DisplayName("T3: Deve retornar null ao buscar uma conta que não foi criada")
    void t3_deveRetornarNuloAoBuscarContaInexistente() {
        String nomeInexistente = "Titular Inexistente";
        // O banco está vazio/sem a conta

        // Ação
        Conta contaEncontrada = banco.buscarConta(nomeInexistente);

        // Verificação: O resultado deve ser nulo
        assertNull(contaEncontrada, "A busca por uma conta inexistente deve retornar nulo.");
    }

    // --- Teste 4: Chaves Diferentes (Maiúsculas/Minúsculas) ---
    @Test
    @DisplayName("T4: A busca de conta é Case Sensitive (dependente do Map)")
    void t4_buscaDeveSerCaseSensitive() {
        String nomeOriginal = "Maria Silva";
        String nomeBuscaDiferente = "maria silva"; // Mesma grafia, diferente caixa

        banco.criarConta(nomeOriginal);

        // Ação: Tentar buscar com a caixa diferente
        Conta contaBuscada = banco.buscarConta(nomeBuscaDiferente);

        // Verificação: HashMap usa a String como chave. Como "Maria Silva" é diferente de "maria silva", deve retornar nulo
        assertNull(contaBuscada, "A busca deve retornar nulo se a caixa da letra for diferente (padrão HashMap).");
    }

    // --- Teste 5: Criação de Conta com Nome Duplicado (Sobrescrita) ---
    @Test
    @DisplayName("T5: Deve sobrescrever uma conta existente se um novo objeto for criado com o mesmo nome")
    void t5_deveSobrescreverContaComNomeDuplicado() {
        String nomeDuplicado = "João da Paz";

        // 1. Cria a primeira conta
        Conta primeiraConta = banco.criarConta(nomeDuplicado);

        // 2. Cria uma segunda conta com o MESMO NOME (o Map vai sobrescrever)
        Conta segundaConta = banco.criarConta(nomeDuplicado);

        // 3. Ação: Busca a conta
        Conta contaBuscada = banco.buscarConta(nomeDuplicado);

        // Verificação 1: A conta buscada deve ser a SEGUNDA instância
        assertSame(segundaConta, contaBuscada, "O objeto buscado deve ser a última conta criada com aquela chave.");

        // Verificação 2: A primeira instância não deve mais estar no Map
        assertNotSame(primeiraConta, contaBuscada, "A primeira instância deve ter sido substituída.");
    }
}