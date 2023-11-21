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

public class PecasEditController {
    
    private PecaVo pecaEditar;
    private PecaBO pecaBO = new PecaBO();
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";

    @FXML private Button salvarEdicao;
    @FXML private Button cancelarEdicao;
    @FXML private Label mensagemErroEdit;
    @FXML private TextField campoEditFabricante;
    @FXML private TextField campoEditNome;
    @FXML private TextField campoEditValor;
    @FXML private TextField campoEditQuantidade;
    



    
    public void initialize(PecaVo peca) throws Exception {
        pecaEditar = new PecaVo();
        pecaEditar = peca;
        this.preencherCampos(peca);
        mensagemErroEdit.setVisible(false);
        acaoDosBotoes();
    }










    
    private void abrirModalFail(String mensagem, Button b) throws Exception{
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

    
    private void abrirModalSucess(String mensagem, Button b) throws IOException{
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
        salvarEdicao.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                try {editarPeca();} 
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    ModalsController modalsController = new ModalsController();
                    modalsController.abrirModalFalha(e.getMessage());
                }
            }
            
        });
        cancelarEdicao.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                try {cancelarEdicao();} 
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    ModalsController modalsController = new ModalsController();
                    modalsController.abrirModalFalha(e.getMessage());
                }
            }
            
        });

        campoEditNome.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditNome.setStyle(null);
            }

        });
        campoEditFabricante.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditFabricante.setStyle(null);
            }

        });
        campoEditQuantidade.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditQuantidade.setStyle(null);;
            }

        });
        campoEditValor.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditValor.setStyle(null);;
            }

        });
        
    }








    
    private void editarPeca() throws Exception{
        String nome = null, fabricante = null;
        int quantidade = 0; 
        double valor = 0;
        
        
        try {
            if(validarCampos()){
                nome = campoEditNome.getText();
                fabricante = campoEditFabricante.getText();
                quantidade = Integer.parseInt(campoEditQuantidade.getText());
                valor = Double.parseDouble(campoEditValor.getText());


                pecaEditar.setNome(nome);
                pecaEditar.setFabricante(fabricante);
                pecaEditar.setQuantidade(quantidade);
                pecaEditar.setValor(valor);
                String labelSucesso = "Peça editada com sucesso.";
                pecaBO.atualizar(pecaEditar);
                cancelarEdicao();
                abrirModalSucess(labelSucesso, salvarEdicao);
            }
        } catch (Exception ex) {
            String labelFalha = ex.getMessage();
            cancelarEdicao();
            abrirModalFail(labelFalha, cancelarEdicao);
        }
    }

    
    private void cancelarEdicao(){
        Stage palco = (Stage)this.cancelarEdicao.getScene().getWindow();
        palco.close();
    }








    private void preencherCampos(PecaVo peca){
        
        try{
            campoEditNome.setText(peca.getNome());
            campoEditFabricante.setText(peca.getFabricante());
            campoEditQuantidade.setText(String.valueOf(peca.getQuantidade()));
            campoEditValor.setText(String.valueOf(peca.getValor()));
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }







    
    private void setInvisibleEdit(){
        this.mensagemErroEdit.setVisible(false);
    }








    private boolean validarCampos(){
        if (campoEditNome.getText().isEmpty()) {
            mensagemErroEdit.setText("O nome não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditNome.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditNome).play();
            return false;
        }else{
            if (campoEditNome.getText().matches("[a-zA-Z^0-9,.\\sãâáéêóõôí/]{2,50}")) {
                System.out.println("Campo nome válido");
            } else {
                mensagemErroEdit.setText("Formato de nome inválido");
                mensagemErroEdit.setVisible(true);
                campoEditNome.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditNome).play();
                return false;
            }
        }

        if (campoEditFabricante.getText().isEmpty()) {
            mensagemErroEdit.setText("O fabricante não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditFabricante.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditFabricante).play();
            return false; 
        } else {
            if (campoEditFabricante.getText().matches("[a-zA-Z^0-9,.\\sãâáéêóõôí]{2,40}")) {
                System.out.println("Campo fabricante válido");
            } else {
                mensagemErroEdit.setText("Formato de fabricante inválido");
                mensagemErroEdit.setVisible(true);
                campoEditFabricante.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditFabricante).play();
                return false;
            }
        }
        
        if (campoEditQuantidade.getText().isEmpty()) {
            mensagemErroEdit.setText("A quantidade não pode ser vazia");
            mensagemErroEdit.setVisible(true);
            campoEditQuantidade.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditQuantidade).play();
            return false;
        } else {
            if (campoEditQuantidade.getText().matches("[1-9]{1}[0-9]{0,8}")) {
                System.out.println("Campo quantidade válido");
            } else {
                mensagemErroEdit.setText("Formato de quantidade inválido");
                mensagemErroEdit.setVisible(true);
                campoEditQuantidade.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditQuantidade).play();
                return false;
            }
        }
        
        if (campoEditValor.getText().isEmpty()) {
            mensagemErroEdit.setText("O valor não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditValor.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditValor).play();
            return false;
        } else {
            if (campoEditValor.getText().matches("[1-9]{1}[0-9]*[.]?[0-9]*")) {
                System.out.println("Campo valor válido");
            } else {
                mensagemErroEdit.setText("Formato de valor inválido");
                mensagemErroEdit.setVisible(true);
                campoEditValor.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditValor).play();
                return false;
            }
        }
        return true;
    }
}
