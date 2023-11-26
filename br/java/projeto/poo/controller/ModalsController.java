package br.java.projeto.poo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalsController {
    
    @FXML private Pane ModalSucess;
    @FXML private Pane ModalFail;
    @FXML private Pane ModalExcluir;
    @FXML private Label mensagemSucesso;
    @FXML private Label mensagemFalha;
    @FXML private Label mensagemExclusao;
    @FXML private Button cancelarExclusao;
    @FXML private Button confirmarExclusao;

    
    private boolean exclusaoValid = false;


    @FXML
    private void confirmarExclusao(){
        setExclusaoValid();
        CloseModalE();
    }

    /**
     * <p> Sets the message to display in the popup. 
     * @param mensagem to be displayed.
     */
    private void ExibirMensagemExcluir(String mensagem){
        mensagemExclusao.setText(mensagem);
    }



    /**
     * <p> Sets the message to display in the popup. 
     * @param mensagem to be displayed.
     */
    private void ExibirMensagemFalha(String mensagem){
        mensagemFalha.setText(mensagem);
    }
    


    /**
     * <p> Sets the message to display in the popup. 
     * @param mensagem to be displayed.
     */
    private void ExibirMensagemSucesso(String mensagem){
        mensagemSucesso.setText(mensagem);
    }



    /**
     * <p> Closes the current popup.
     * 
     * <p> This method has no parameters.
     */
    @FXML
    private void CloseModalE(){
        Stage palco = (Stage)this.ModalExcluir.getScene().getWindow();
        palco.close();
    }



    /**
     * <p> Closes the current popup.
     * 
     * <p> This method has no parameters.
     */
    @FXML
    private void CloseModalS(){
        Stage palco = (Stage)this.ModalSucess.getScene().getWindow();
        palco.close();
    }



    /**
     * <p> Closes the current popup.
     * 
     * <p> This method has no parameters.
     */
    @FXML
    private void CloseModalF(){
        Stage palco = (Stage)this.ModalFail.getScene().getWindow();
        palco.close();
    }



    private void setExclusaoValid(){
        this.exclusaoValid = true;
    }


    
    public boolean getExclusaoValid(){
        return this.exclusaoValid;
    }



    /**
     * <p> Opens up a confirmation popup screen.
     * @param mensagem the message to be displayed.
     */
    public void abrirModalSucesso(String mensagem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Modals/ModalSucesso.fxml"));
            Parent root = loader.load();
            
            ModalsController controller = loader.getController();
            controller.ExibirMensagemSucesso(mensagem);
            
            Scene popup = new Scene(root);
            Stage palco = new Stage();
            palco.setScene(popup);
            palco.setResizable(false);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.initStyle(StageStyle.UNDECORATED);
            palco.showAndWait();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * <p> Opens up an error popup screen.
     * @param mensagem the message to be displayed.
     */
    public void abrirModalFalha(String mensagem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Modals/ModalFalha.fxml"));
            Parent root = loader.load();
            
            ModalsController controller = loader.getController();
            controller.ExibirMensagemFalha(mensagem);
            
            Scene popup = new Scene(root);
            Stage palco = new Stage();
            palco.setScene(popup);
            palco.setResizable(false);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.initStyle(StageStyle.UNDECORATED);
            palco.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * <p> Opens up an exclusion popup screen.
     * @param mensagem the message to be displayed.
     */
    public boolean abrirModalExcluir(String mensagem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Modals/ModalExcluir.fxml"));
            Parent root = loader.load();
            ModalsController modalExc = loader.getController();

            modalExc.ExibirMensagemExcluir(mensagem);

            Scene janelaEdit = new Scene(root);
            Stage palco = new Stage();
            palco.setResizable(false);
            palco.setScene(janelaEdit);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.initStyle(StageStyle.UNDECORATED);
            palco.showAndWait();

            return modalExc.getExclusaoValid();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
