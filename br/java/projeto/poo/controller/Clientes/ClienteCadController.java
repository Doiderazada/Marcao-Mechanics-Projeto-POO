package br.java.projeto.poo.controller.Clientes;

import java.util.ArrayList;


import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ClienteBO;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.TelefoneVO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClienteCadController {


    @FXML private Button cadastrarCliente;
    @FXML private Button cancelarCadastro;
    @FXML private ChoiceBox<String> tipoV;
    @FXML private Label mensagemErroCad;
    @FXML private Pane cadastroCliente;
    @FXML private TextField campoAnoVeic;
    @FXML private TextField campoCPFCliente;
    @FXML private TextField campoCorVeic;
    @FXML private TextField campoEndCliente;
    @FXML private TextField campoKMVeic;
    @FXML private TextField campoModVeic;
    @FXML private TextField campoNomeCliente;
    @FXML private TextField campoPlacVeic;
    @FXML private TextField campoTelefone;
    

    
    private EnderecoVO nEnderecoVO = new EnderecoVO();
    private ClienteBO clienteBO = new ClienteBO();
    private VeiculoBO nVeiculoBO = new VeiculoBO();
    private ModalsController modalsController = new ModalsController();
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";
    private String[] tipoVeic = {"Carro","Moto"};
    private ObservableList<String> listaTipoVeic = FXCollections.observableArrayList(tipoVeic);



    
    public void initialize(){
        acaoCompTela();
        tipoV.setItems(listaTipoVeic);
        tipoV.setValue(listaTipoVeic.get(0));

    }







    private void acaoCompTela(){
        campoNomeCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoNomeCliente.setStyle(null);
            }
            
        });
        campoCPFCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoCPFCliente.setStyle(null);
            }
            
        });
        campoCPFCliente.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        campoTelefone.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoTelefone.setStyle(null);
            }
            
        });
        campoTelefone.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        campoEndCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoEndCliente.setStyle(null);
            }
            
        });
        campoModVeic.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoModVeic.setStyle(null);
            }
            
        });
        campoAnoVeic.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoAnoVeic.setStyle(null);
            }
            
        });
        campoKMVeic.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoKMVeic.setStyle(null);
            }
            
        });
        campoPlacVeic.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoPlacVeic.setStyle(null);
            }
            
        });
        campoCorVeic.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoCorVeic.setStyle(null);
            }
            
        });


        cadastrarCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cadastrarCliente();
            }
            
        });
        cancelarCadastro.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cancelarCadastro();
            }
            
        });
    }






    
    private void autoComplete(){
        // auto-complete cpf
        if(campoCPFCliente.getText().length() == 3){
            campoCPFCliente.setText(campoCPFCliente.getText() + ".");
            campoCPFCliente.end();
        };

        if(campoCPFCliente.getText().length() == 7){
            campoCPFCliente.setText(campoCPFCliente.getText() + ".");
            campoCPFCliente.end();
        };
        
        if(campoCPFCliente.getText().length() == 11){
            campoCPFCliente.setText(campoCPFCliente.getText() + "-");
            campoCPFCliente.end();
        };
        
        
        
        // auto-complete telefone
        if(campoTelefone.getText().length() == 2){
            campoTelefone.setText(campoTelefone.getText() + " ");
            campoTelefone.end();
        };
        
        if(campoTelefone.getText().length() == 8){
            campoTelefone.setText(campoTelefone.getText() + "-");
            campoTelefone.end();
        };

    }






    
    private void cadastrarCliente() {
        try{
            if (validarCampos()) {
            
                nEnderecoVO.pegarValoresComoString(campoEndCliente.getText());

                ArrayList<VeiculoVO> listaveiculos = new ArrayList<VeiculoVO>();

                VeiculoVO veiculo = new VeiculoVO(0, 
                    campoPlacVeic.getText(), 
                    campoCorVeic.getText(), 
                    campoModVeic.getText(), 
                    campoCPFCliente.getText(), 
                    tipoV.getValue(), 
                    campoAnoVeic.getText(), 
                    Double.parseDouble(campoKMVeic.getText()));
                listaveiculos.add(veiculo);

                TelefoneVO telefoneVO = new TelefoneVO(0, campoCPFCliente.getText(), null, campoTelefone.getText());
                ClienteVO nClienteVO = new ClienteVO(0, campoNomeCliente.getText(), campoCPFCliente.getText(), nEnderecoVO, listaveiculos, telefoneVO);

                
                if(clienteBO.inserir(nClienteVO)){
                    
                    if(nVeiculoBO.inserir(veiculo)){
                        cancelarCadastro();
                        modalsController.abrirModalSucesso("Cliente cadastrado com sucesso.");
                    } else modalsController.abrirModalFalha("Não foi possível cadastrar o veículo."); 

                } else modalsController.abrirModalFalha("Não foi possível cadastrar o cliente.");
                
            }
        }
        catch (Exception ex){
            cancelarCadastro();
            System.out.println(ex.getMessage());
            modalsController.abrirModalFalha(ex.getMessage());

        }
    }
    
    
    private void cancelarCadastro() {
        Stage palco = (Stage) this.cancelarCadastro.getScene().getWindow();
        palco.close();
    }
    






    private boolean validarCampos(){
        
        if(campoNomeCliente.getText().isEmpty()){
            mensagemErroCad.setText("O nome não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoNomeCliente.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoNomeCliente).play();
            return false;
        }else{
            if( campoNomeCliente.getText().matches("^[a-zA-Z\\s^áéíóúãâêõ]{1,60}$") )
                System.out.println("campo nome válido");
            else{
                mensagemErroCad.setText("O nome não pode conter números ou símbolos");
                mensagemErroCad.setVisible(true);
                campoNomeCliente.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoNomeCliente).play();
                return false;
            }
        }



        if(campoCPFCliente.getText().isEmpty()){
            mensagemErroCad.setText("O cpf não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoCPFCliente.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCPFCliente).play();
            return false;
        }else{
            if (campoCPFCliente.getText().matches("^[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}$"))
                System.out.println("campo cpf válido");
            else{
                mensagemErroCad.setText("Formato inválido de CPF");
                mensagemErroCad.setVisible(true);
                campoCPFCliente.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoCPFCliente).play();
                return false;
            }
        }


        if(campoTelefone.getText().isEmpty()){
            mensagemErroCad.setText("O telefone não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoTelefone.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoTelefone).play();
            return false;
        }else{
            if (campoTelefone.getText().matches("[\\d]{2}[\\s][\\d]{5}[\\-][\\d]{4}")) 
                System.out.println("campo telefone válido");
            else{
                mensagemErroCad.setText("Formato inválido de telefone");
                mensagemErroCad.setVisible(true);
                campoTelefone.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoTelefone).play();
                return false;
            }
        }
        

        if(campoEndCliente.getText().isEmpty()){
            mensagemErroCad.setText("O endereço não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoEndCliente.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEndCliente).play();
            return false;
        }else{
            if (campoEndCliente.getText().matches("^[a-zA-Z\\s^áãàéêèíóõú.,]{1,50}[\\d^,]{1,5}[a-zA-Z\\s^áãàéêèíóõú,]{1,50}"))
                System.out.println("campo endereço válido");
            else{
                mensagemErroCad.setText("O endereço não pode conter símbolos");
                mensagemErroCad.setVisible(true);
                campoEndCliente.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEndCliente).play();
                return false;
            }

        }


        if(campoModVeic.getText().isEmpty()){
            mensagemErroCad.setText("O modelo do veículo não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoModVeic.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoModVeic).play();
            return false;
        }else{
            if (campoModVeic.getText().matches("^[a-zA-Z\\s^áéíóúãêõô]{1,45}[\\d^.]{0,3}$")) 
                System.out.println("campo modelo válido");
            else{
                mensagemErroCad.setText("Formato inválido de modelo");
                mensagemErroCad.setVisible(true);
                campoModVeic.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoModVeic).play();
                return false;
            }
        }
        
        
        if(campoAnoVeic.getText().isEmpty()){
            mensagemErroCad.setText("O ano do veículo não pode ser vazio");
            mensagemErroCad.setVisible(true);
            campoAnoVeic.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoAnoVeic).play();
            return false;
        }else{
            if (campoAnoVeic.getText().matches("[\\d]{1,4}")) 
                System.out.println("campo ano válido");
            else{
                mensagemErroCad.setText("Formato inválido de de ano");
                mensagemErroCad.setVisible(true);
                campoAnoVeic.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoAnoVeic).play();
                return false;
            }
        }
        
        
        if(campoKMVeic.getText().isEmpty()){
            mensagemErroCad.setText("A quilometragem não pode ser vazia");
            mensagemErroCad.setVisible(true);
            campoKMVeic.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoKMVeic).play();
            return false;
        }else{
            if (campoKMVeic.getText().matches("[\\d^.]{1,9}")) 
                System.out.println("campo quilometragem válido");
            else{
                mensagemErroCad.setText("Formato inválido de quilometragem");
                mensagemErroCad.setVisible(true);
                campoKMVeic.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoKMVeic).play();
                return false;
            }
        }
        
        
        if(campoPlacVeic.getText().isEmpty()){
            mensagemErroCad.setText("A placa não pode ser vazia");
            mensagemErroCad.setVisible(true);
            campoPlacVeic.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoPlacVeic).play();
            return false;
        }else{
            if (campoPlacVeic.getText().matches("^[a-zA-Z]{3}[-|0-9]{1}[0-9|a-zA-Z]{1}[0-9]{2,3}$")) 
                System.out.println("campo placa válido");
            else{
                mensagemErroCad.setText("Formato inválido de placa");
                mensagemErroCad.setVisible(true);
                campoPlacVeic.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoPlacVeic).play();
                return false;
            }
        }
        
        
        if(campoCorVeic.getText().isEmpty()){
            mensagemErroCad.setText("A cor não pode ser vazia");
            mensagemErroCad.setVisible(true);
            campoCorVeic.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoCorVeic).play();
            return false;
        }else{
            if (campoCorVeic.getText().matches("^[a-zA-Z\\s^-]{1,20}$")) 
                System.out.println("campo cor válido");
            else{
                mensagemErroCad.setText("Formato inválido de cor");
                mensagemErroCad.setVisible(true);
                campoCorVeic.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoCorVeic).play();
                return false;
            }
        }


        return true;
    }



    
    private void setInvisibleCad(){
        this.mensagemErroCad.setVisible(false);
    }

}