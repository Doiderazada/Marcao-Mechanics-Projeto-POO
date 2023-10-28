package br.java.projeto.poo.src;

import java.util.HashMap;
import java.util.Map;

import br.java.projeto.poo.exceptions.TelaNaoEncotradaException;

public class TelasDisponiveis {
    
    private Map<String, String> telasDisponiveis;
    /**
     * @implNote serve para carregar o nome e endereco relativo de todas as telas
     */

    public TelasDisponiveis() {
        this.telasDisponiveis = new HashMap<String, String>();
        this.telasDisponiveis.put("orcamentos", "Orcamentos/Orcamentos.fxml");
        this.telasDisponiveis.put("novoOrcamento", "Orcamentos/NovoOrcamento.fxml");
        this.telasDisponiveis.put("editarOrcamento", "Orcamentos/EditarOrcamento.fxml");
        this.telasDisponiveis.put("clientes", "Clientes/Clientes.fxml");
        this.telasDisponiveis.put("automoveis", "Automoveis/Automoveis.fxml");
        this.telasDisponiveis.put("login", "Login/Login.fxml");
        this.telasDisponiveis.put("pecas", "Pecas/Pecas.fxml");
        this.telasDisponiveis.put("servicos", "Servicos/Servicos.fxml");
        this.telasDisponiveis.put("funcionarios", "Funcionarios/Funcionarios.fxml");
    }

    /**
     * 
     * @return retorna um Map contendo o nome da tela e o seu endereço
     */

    public Map<String, String> getTelasDisponiveis() {
        return this.telasDisponiveis;
    }

    public String pegarTelaPorNome(String nome) throws TelaNaoEncotradaException{
        if (!this.telasDisponiveis.containsKey(nome)) {
            throw new TelaNaoEncotradaException("tela " + nome + " nao encontrada");
        }

        return this.telasDisponiveis.get(nome);
    }
}
