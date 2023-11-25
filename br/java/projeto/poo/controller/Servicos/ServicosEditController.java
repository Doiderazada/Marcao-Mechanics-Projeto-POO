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

public class ServicosEditController {
    

    @FXML private TextField campoEditNome;
    @FXML private TextField campoEditValor;
    @FXML private Button cancelarEdicao;
    @FXML private Label mensagemErroEdit;
    @FXML private Button salvarEdicao;

    
    private ServicoVO servicoEditar = new ServicoVO();
    private ServicoBO servicoBO = new ServicoBO();
    private ModalsController modalsController = new ModalsController();
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";



    public void initialize(ServicoVO servico, int index) throws Exception {
        
        acaoCompTela();
        servicoEditar = servico;
        preencherCampos(servico, index);
        setInvisibleEdit();
        
    }
    
    

    
    
    





    private void acaoCompTela(){
        salvarEdicao.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                editarServico();
            }
            
        });
        cancelarEdicao.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cancelarEdicao();
            }
            
        });
        campoEditNome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                setInvisibleEdit();
                campoEditNome.setStyle(null);
            }
        });

        campoEditValor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                setInvisibleEdit();
                campoEditValor.setStyle(null);
            }
        });
    }






    
    
    private void editarServico() {
        try {
            
            if(validarCampos()){
                String nome = null;
                double valor = 0;

                servicoEditar.setNome(nome);
                servicoEditar.setValor(valor);

                servicoBO.atualizar(servicoEditar);
                cancelarEdicao();
                modalsController.abrirModalSucesso("Peça editada com sucesso.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            cancelarEdicao();
            modalsController.abrirModalFalha(ex.getMessage());
        }
    }

    
    private void cancelarEdicao() {
        Stage palco = (Stage)this.cancelarEdicao.getScene().getWindow();
        palco.close();
    }


    private void preencherCampos(ServicoVO servico, int index){
        
        campoEditNome.setText(servico.getNome());
        campoEditValor.setText(String.valueOf(servico.getValor()));
        
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
        } else System.out.println("Campo nome válido");

        if (campoEditValor.getText().isEmpty()) {
            mensagemErroEdit.setText("O valor não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditValor.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditValor).play();
            return false;
        } else {
            
            if (campoEditValor.getText().matches("[1-9^,.]{1,9}")) {
                    System.out.println("Campo valor válido");
            }else{
                mensagemErroEdit.setText("Campo valor inválido");
                mensagemErroEdit.setVisible(true);
                campoEditValor.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditValor).play();
                return false;
            }
            
            
            
        }
        return true;
    }
}
