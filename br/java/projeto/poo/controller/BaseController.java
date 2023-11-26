package br.java.projeto.poo.controller;

import br.java.projeto.poo.src.App;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * <p> Loads the main actions of all screens.
 * 
 * <p> <b> Every main screen controller should inherit this Class.
 */
public class BaseController {
    
    @FXML private Label nAuto;
    @FXML private Label nClie;
    @FXML private Label nFunc;
    @FXML private Label nOrcamentos;
    @FXML private Label nPecas;
    @FXML private Label nSair;
    @FXML private Label nServ;
    @FXML private HBox menu;


    @FXML
    public void initialize() {
        this.verificarPermissoes();
    }

    @FXML
    final void carregarAutomoveis(MouseEvent event) {
        App.navegarEntreTelas("automoveis");
    }

    @FXML
    final void carregarClientes(MouseEvent event) {
        App.navegarEntreTelas("clientes");
    }   

    @FXML
    final void carregarFuncionarios(MouseEvent event) {
        App.navegarEntreTelas("funcionarios");
    }

    @FXML
    final void carregarOrcamentos(MouseEvent event) {
        App.navegarEntreTelas("orcamentos");
    }

    @FXML
    final void carregarPecas(MouseEvent event) {
        App.navegarEntreTelas("pecas");
    }

    @FXML
    final void carregarServicos(MouseEvent event) {
        App.navegarEntreTelas("servicos");
    }

    @FXML
    final void logout(MouseEvent event) {
        App.usuarioLogado = null;
        App.navegarEntreTelas("login");
    }


    
    /**
     * <p> Verifies the logged user to display the avaliable screens for its level.
     * 
     * <p> This method has no parameters.
     */
    protected void verificarPermissoes() {
        
        ObservableList<Node> itensMenu = menu.getChildren();
        if (App.usuarioLogado.getNivel() == 1) {
            itensMenu.remove(nFunc);
            itensMenu.remove(nServ);
            itensMenu.remove(nPecas);
        } else if (App.usuarioLogado.getNivel() == 2) {
            itensMenu.remove(nFunc);
        } 
         
    }
}
