package br.java.projeto.poo.controller.Servicos;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ServicoBO;
import br.java.projeto.poo.models.VO.ServicoVO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ServicosCadController {


    @FXML private Button cadastrarServico;
    @FXML private TextField campoCadNome;
    @FXML private TextField campoCadValor;
    @FXML private Button cancelarCadastro;
    @FXML private Label mensagemErroCad;


    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";
    private ModalsController modalsController = new ModalsController();



    @FXML
    private void initialize(){
        acaoCompTela();
        setInvisibleCad();
    }
    
    

    /**
     * <p> Sets the action from all elements on its corresponding screen.
     * 
     * <p> This method has no parameters.
     */
    private void acaoCompTela(){
        cadastrarServico.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cadastrarServico();
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
            public void handle(MouseEvent event){
                setInvisibleCad();
                campoCadNome.setStyle(null);
            }
        });

        campoCadValor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                setInvisibleCad();
                campoCadValor.setStyle(null);
            }
        });
    }



    /**
     * <p> Saves the current {@code peca} to the database.
     * 
     * <p> This method has no parameters.
     */ 
    private void cadastrarServico() {
        try {
            if (validarCampos()) {
                String nome = null;
                double valor = 0;
                
                nome = campoCadNome.getText();
                valor = Double.parseDouble(campoCadValor.getText());
            
                ServicoVO novoServico = new ServicoVO(1, nome, valor);
                ServicoBO servicoBO = new ServicoBO();

                if(servicoBO.inserir(novoServico)){
                    cancelarCadastro();
                    modalsController.abrirModalSucesso("Serviço cadastrado com sucesso.");
                    
                } else modalsController.abrirModalFalha("Não foi possível cadastrar o serviço.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            cancelarCadastro();
            modalsController.abrirModalFalha(ex.getMessage());
        }
    }

    

    /**
     * <p> Closes the current screen.
     * 
     * <p> This method has no parameters.
     */
    private void cancelarCadastro(){
        Stage palco = (Stage)this.cancelarCadastro.getScene().getWindow();
        palco.close();
    }



    /**
     * <p> Validates the contents from the {@code TextField}s on screen.
     * @return <b>true</b> if the content of all {@code TextField} are valid. 
     */
    private boolean validarCampos(){

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



    
    private void setInvisibleCad(){
        this.mensagemErroCad.setVisible(false);
    }
}
