package br.java.projeto.poo.controller.Clientes;

import java.io.IOException;
import java.util.ArrayList;


import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ClienteBO;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.TelefoneVO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ClienteCadController {
    
    EnderecoVO nEnderecoVO = new EnderecoVO();
    ClienteBO clienteBO = new ClienteBO();
    VeiculoBO nVeiculoBO = new VeiculoBO();
    String ano = null, cor = null, cpf = null, endereco = null, modelo = null, nome = null, placa = null, tipoV = null, telefone = null;
    double quilometragem = 0;
    long id = 0;
    String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";

    @FXML private Button cadastrarCliente;
    @FXML private Button cancelarCadastro;
    @FXML private ChoiceBox<String> tipoVeic;
    @FXML private Label mensagemErroCad;
    @FXML private Pane cadastroCliente;
    private String[] tipoVeic_Array = {"Carro","Moto"};
    @FXML private TextField campoAnoVeic;
    @FXML private TextField campoCPFCliente;
    @FXML private TextField campoCorVeic;
    @FXML private TextField campoEndCliente;
    @FXML private TextField campoKMVeic;
    @FXML private TextField campoModVeic;
    @FXML private TextField campoNomeCliente;
    @FXML private TextField campoPlacVeic;
    @FXML private TextField campoTelefone;
    


    @FXML
    void initialize(){

        tipoVeic.getItems().addAll(tipoVeic_Array);
        tipoVeic.setValue(tipoVeic_Array[0]);

        this.acaoTextField();
    
    }






    @FXML
    void abrirModalFail(Label mensagem, Button b) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalFalha.fxml"));
        Parent root = loader.load();

        ModalsController controller = loader.getController();
        controller.ExibirMensagemFalha(mensagem.getText());

        Scene popup = new Scene(root);
        Stage palco = new Stage();
        palco.setScene(popup);
        palco.setResizable(false);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wCC = b.getScene().getWindow();
        double centralizarEixoX = (wCC.getX() + wCC.getWidth()/2) - 200;
        double centralizarEixoY = (wCC.getY() + wCC.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();

    }





    @FXML
    void abrirModalSucess(Label mensagem, Button b, ClienteVO cliente) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalSucesso.fxml"));
        Parent root = loader.load();
        
        ModalsController controller = loader.getController();
        controller.ExibirMensagemSucesso(mensagem.getText());
        
        Scene popup = new Scene(root);
        Stage palco = new Stage();
        palco.setScene(popup);
        palco.setResizable(false);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wCC = b.getScene().getWindow();
        double centralizarEixoX = (wCC.getX() + wCC.getWidth()/2) - 200;
        double centralizarEixoY = (wCC.getY() + wCC.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();
        
    }





    void acaoTextField(){
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
        campoTelefone.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                campoTelefone.setStyle(null);
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
        
    }






    @FXML
    void autoComplete(){
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






    @FXML
    void cadastrarCliente() throws Exception{
        
        try{

            if (validarCampos()) {
                ano = campoAnoVeic.getText();
                cor = campoCorVeic.getText();
                cpf = campoCPFCliente.getText();
                endereco = campoEndCliente.getText();
                modelo = campoModVeic.getText();
                nome = campoNomeCliente.getText();
                placa = campoPlacVeic.getText();
                quilometragem = Double.parseDouble(campoKMVeic.getText());
                telefone = this.campoTelefone.getText();
                tipoV = this.tipoVeic.getValue();
            
                nEnderecoVO.pegarValoresComoString(endereco);

                ArrayList<VeiculoVO> listaveiculos = new ArrayList<VeiculoVO>();

                VeiculoVO veiculo = new VeiculoVO(id, placa, cor, modelo, cpf, tipoV, ano, quilometragem);
                listaveiculos.add(veiculo);
                String cpfNull = null;

                TelefoneVO telefoneVO = new TelefoneVO(id, cpf, cpfNull, telefone);
                ClienteVO nClienteVO = new ClienteVO(id, nome, cpf, nEnderecoVO, listaveiculos, telefoneVO);

                
                if(clienteBO.inserir(nClienteVO)){
                    
                    nVeiculoBO.inserir(veiculo);
                    Label labelSucesso = new Label("Cliente cadastrado com sucesso.");
                    cancelarCadastro();
                    abrirModalSucess(labelSucesso, cadastrarCliente, nClienteVO);
                }
                
            }
        }
        catch (Exception ex){
            Label labelFalha = new Label();
            labelFalha.setText(ex.getMessage());
            cancelarCadastro();
            abrirModalFail(labelFalha, cadastrarCliente);

        }
    }
    
    @FXML
    void cancelarCadastro() throws Exception{
        Stage palco = (Stage) this.cancelarCadastro.getScene().getWindow();
        palco.close();
    }
    






    boolean validarCampos(){
        
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



    
    void setInvisibleCad(){
        this.mensagemErroCad.setVisible(false);
    }

}