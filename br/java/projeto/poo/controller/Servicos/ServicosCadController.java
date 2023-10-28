package br.java.projeto.poo.controller.Servicos;

import java.io.IOException;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ServicoBO;
import br.java.projeto.poo.models.VO.ServicoVO;
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

public class ServicosCadController {
    
    String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";

    @FXML private Button cadastrarServico;
    @FXML private TextField campoCadNome;
    @FXML private TextField campoCadValor;
    @FXML private Button cancelarCadastro;
    @FXML private Label mensagemErroCad;

    @FXML
    void initialize(){
        mensagemErroCad.setVisible(false);
        acaoTextField();
    }
    
    
    void abrirModalFail(String mensagem) throws Exception{
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
        Window wCS = cadastrarServico.getScene().getWindow();
        double centralizarEixoX = (wCS.getX() + wCS.getWidth()/2) - 200;
        double centralizarEixoY = (wCS.getY() + wCS.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();

    }

    
    void abrirModalSucess(String mensagem) throws IOException{
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
        Window wCS = cadastrarServico.getScene().getWindow();
        double centralizarEixoX = (wCS.getX() + wCS.getWidth()/2) - 200;
        double centralizarEixoY = (wCS.getY() + wCS.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();
        
    }




    void acaoTextField(){
        campoCadNome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                mensagemErroCad.setVisible(false);
                campoCadNome.setStyle(null);
            }
        });

        campoCadValor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                mensagemErroCad.setVisible(false);
                campoCadValor.setStyle(null);
            }
        });
    }






    @FXML
    void cadastrarServico() throws Exception {
        

        try {
            String nome = null;
            double valor = 0;
            if (validarCamposVazios()) {
                
                nome = campoCadNome.getText();
                valor = Double.parseDouble(campoCadValor.getText());
            
                ServicoVO novoServico = new ServicoVO(1, nome, valor);
                ServicoBO servicoBO = new ServicoBO();

                if(servicoBO.inserir(novoServico)){
                    String labelSucesso = "Serviço cadastrado com sucesso.";
                    ServicosController.listaServicos = servicoBO.listar();
                    ServicosController.servicosDisponiveis.setAll(ServicosController.listaServicos);
                    cancelarCadastro();
                    abrirModalSucess(labelSucesso);
                    
                }
            }
        } catch (Exception ex) {
            String labelFalha = ex.getMessage();
            System.out.println(ex.getMessage());
            cancelarCadastro();
            abrirModalFail(labelFalha);
        }
    }

    @FXML
    void cancelarCadastro(){
        Stage palco = (Stage)this.cancelarCadastro.getScene().getWindow();
        palco.close();
    }

    

    @FXML
    void setInvisibleCad(){
        this.mensagemErroCad.setVisible(false);
    }



    boolean validarCamposVazios(){

        if (campoCadNome.getText().isEmpty()) {
            mensagemErroCad.setText("O nome não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoCadNome.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCadNome).play();
            return false;
        } else System.out.println("Campo nome válido");

        if (campoCadValor.getText().isEmpty()) {
            mensagemErroCad.setText("O valor não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoCadValor.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCadValor).play();
            return false;
        } else {
            
            if (campoCadValor.getText().matches("[1-9^,.]{1,9}")) {
                    System.out.println("Campo valor válido");
            }else{
                mensagemErroCad.setText("Campo valor inválido");
                mensagemErroCad.setVisible(true);
                campoCadValor.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoCadValor).play();
                return false;
            }
            
            
            
        }
        return true;
    }
}
