package br.java.projeto.poo.controller.Pecas;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.PecaBO;
import br.java.projeto.poo.models.VO.PecaVo;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PecasCadController {
    
    

    @FXML private TextField campoCadFabricante;
    @FXML private TextField campoCadNome;
    @FXML private TextField campoCadValor;
    @FXML private TextField campoCadQuantidade;
    @FXML private Label mensagemErroCad;
    @FXML private Button cadastrarPeca;
    @FXML private Button cancelarCadastro;
    

    private ModalsController modalsController = new ModalsController();
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";



    public void initialize(){
        acaoCompTela();
        setInvisibleCad();
    }


    





    private void acaoCompTela(){
        cadastrarPeca.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cadastrarPeca(); 
            }
            
        });
        cancelarCadastro.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cancelarCadastro();
            }
            
        });

        campoCadNome.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoCadNome.setStyle(null);;
            }

        });
        campoCadFabricante.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoCadFabricante.setStyle(null);;
            }

        });
        campoCadQuantidade.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoCadQuantidade.setStyle(null);;
            }

        });
        campoCadValor.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoCadValor.setStyle(null);;
            }

        });
        
    }





    
    
    private void cadastrarPeca() {
        
        try {
            if(validarCampos()){
                String nome = null, fabricante = null;
                int quantidade = 0; 
                double valor = 0;

                nome = campoCadNome.getText();
                fabricante = campoCadFabricante.getText();
                quantidade = Integer.parseInt(campoCadQuantidade.getText());
                valor = Double.parseDouble(campoCadValor.getText());

                PecaVo novaPeca = new PecaVo(0, nome, fabricante, valor, quantidade);
                PecaBO pecaBO = new PecaBO();
                if(pecaBO.inserir(novaPeca)){
                    cancelarCadastro();
                    modalsController.abrirModalSucesso("Peça cadastrada com sucesso.");
                }
            }
        } catch (Exception ex) {
            cancelarCadastro();
            modalsController.abrirModalFalha(ex.getMessage());
        }
    }

    private void cancelarCadastro(){
        Stage palco = (Stage)this.cancelarCadastro.getScene().getWindow();
        palco.close();
    }


    





    private void setInvisibleCad(){
        this.mensagemErroCad.setVisible(false);
    }






    private boolean validarCampos(){
        if (campoCadNome.getText().isEmpty()) {
            mensagemErroCad.setText("O nome não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoCadNome.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCadNome).play();
            return false;
        }else{
            if (campoCadNome.getText().matches("[a-zA-Z^0-9,.\\sãâáéêóõôí/]{2,50}")) {
                System.out.println("Campo nome válido");
            } else {
                mensagemErroCad.setText("Formato de nome inválido");
                mensagemErroCad.setVisible(true);
                campoCadNome.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoCadNome).play();
                return false;
            }
        }

        if (campoCadFabricante.getText().isEmpty()) {
            mensagemErroCad.setText("O fabricante não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoCadFabricante.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCadFabricante).play();
            return false; 
        } else {
            if (campoCadFabricante.getText().matches("[a-zA-Z^0-9,.\\sãâáéêóõôí]{2,40}")) {
                System.out.println("Campo fabricante válido");
            } else {
                mensagemErroCad.setText("Formato de fabricante inválido");
                mensagemErroCad.setVisible(true);
                campoCadFabricante.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoCadFabricante).play();
                return false;
            }
        }
        
        if (campoCadQuantidade.getText().isEmpty()) {
            mensagemErroCad.setText("A quantidade não pode ser vazia");
            mensagemErroCad.setVisible(true);
            campoCadQuantidade.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCadQuantidade).play();
            return false;
        } else {
            if (campoCadQuantidade.getText().matches("[1-9]{1}[0-9]{0,8}")) {
                System.out.println("Campo valor válido");
            } else {
                mensagemErroCad.setText("Formato de quantidade inválido");
                mensagemErroCad.setVisible(true);
                campoCadQuantidade.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoCadQuantidade).play();
                return false;
            }
        }
        
        if (campoCadValor.getText().isEmpty()) {
            mensagemErroCad.setText("O valor não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoCadValor.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCadValor).play();
            return false;
        } else {
            if (campoCadValor.getText().matches("[1-9]{1}[0-9]*[.]?[0-9]*")) {
                System.out.println("Campo valor válido");
            } else {
                mensagemErroCad.setText("Formato de valor inválido");
                mensagemErroCad.setVisible(true);
                campoCadValor.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoCadValor).play();
                return false;
            }
        }
        return true;
    }
}
