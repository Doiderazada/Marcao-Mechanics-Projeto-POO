package br.java.projeto.poo.controller.Pecas;

import java.io.IOException;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.PecaBO;
import br.java.projeto.poo.models.VO.PecaVo;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class PecasCadController {
    
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";

    @FXML private TextField campoCadFabricante;
    @FXML private TextField campoCadNome;
    @FXML private TextField campoCadValor;
    @FXML private TextField campoCadQuantidade;
    @FXML private Label mensagemErroCad;
    @FXML private Button cadastrarPeca;
    @FXML private Button cancelarCadastro;
    

    public void initialize(){
        mensagemErroCad.setVisible(false);
        acaoDosBotoes();
    }



    
    void abrirModalFail(String mensagem, Button b) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalFalha.fxml"));
        Parent root = loader.load();

        ModalsController controller = loader.getController();
        controller.ExibirMensagemFalha(mensagem);

        Scene popup = new Scene(root);
        Stage palco = new Stage();
        palco.setScene(popup);
        palco.setResizable(false);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wCP = b.getScene().getWindow();
        double centralizarEixoX = (wCP.getX() + wCP.getWidth()/2) - 200;
        double centralizarEixoY = (wCP.getY() + wCP.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();

    }

    
    void abrirModalSucess(String mensagem, Button b) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalSucesso.fxml"));
        Parent root = loader.load();
        
        ModalsController controller = loader.getController();
        controller.ExibirMensagemSucesso(mensagem);
        
        Scene popup = new Scene(root);
        Stage palco = new Stage();
        palco.setScene(popup);
        palco.setResizable(false);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wCP = b.getScene().getWindow();
        double centralizarEixoX = (wCP.getX() + wCP.getWidth()/2) - 200;
        double centralizarEixoY = (wCP.getY() + wCP.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();
        
    }


    






    private void acaoDosBotoes(){
        cadastrarPeca.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                try {cadastrarPeca();} 
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    ModalsController modalsController = new ModalsController();
                    modalsController.abrirModalFalha(e.getMessage());
                }
            }
            
        });
        cancelarCadastro.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                try {cancelarCadastro();} 
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    ModalsController modalsController = new ModalsController();
                    modalsController.abrirModalFalha(e.getMessage());
                }
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





    
    
    void cadastrarPeca() throws Exception{
        
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
                    String labelSucesso = "Peça cadastrada com sucesso.";
                    cancelarCadastro();
                    abrirModalSucess(labelSucesso, cadastrarPeca);
                }
            }
        } catch (Exception ex) {
            String labelFalha = ex.getMessage();
            cancelarCadastro();
            abrirModalFail(labelFalha, cancelarCadastro);
        }
    }

    void cancelarCadastro(){
        Stage palco = (Stage)this.cancelarCadastro.getScene().getWindow();
        palco.close();
    }


    





    void setInvisibleCad(){
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
