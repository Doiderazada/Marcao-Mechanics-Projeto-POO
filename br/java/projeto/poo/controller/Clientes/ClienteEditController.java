package br.java.projeto.poo.controller.Clientes;

import java.io.IOException;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ClienteBO;
import br.java.projeto.poo.models.BO.EnderecoBO;
import br.java.projeto.poo.models.BO.TelefoneBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.TelefoneVO;
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

public class ClienteEditController {
    ModalsController modalsController = new ModalsController();
    private EnderecoBO enderecoBO = new EnderecoBO();
    private ClienteBO clienteBO = new ClienteBO();
    private TelefoneBO telefoneBO = new TelefoneBO();
    private ClienteVO clienteEditar = new ClienteVO();
    private String nome = null, cpf = null, endereco = null, telefone = null;
    String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";

    @FXML private TextField campoEditCPF;
    @FXML private TextField campoEditEnd;
    @FXML private TextField campoEditNome;
    @FXML private TextField campoEditTel;
    @FXML private Button cancelarEdicao;
    @FXML private Label mensagemErroEdit;
    @FXML private Button salvarEdicao;
    
    
    void initialize(ClienteVO cliente, int index){
        
        clienteEditar = new ClienteVO();
        clienteEditar = cliente;
        this.preencherCampos(cliente, index);
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
        campoEditNome.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditNome.setStyle(null);
            }
            
        });
        campoEditCPF.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditCPF.setStyle(null);
            }
            
        });
        campoEditTel.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditTel.setStyle(null);
            }
            
        });
    }




    @FXML
    void autoComplete(){
        // auto-complete cpf
        if(campoEditCPF.getText().length() == 3){
            campoEditCPF.setText(campoEditCPF.getText() + ".");
            campoEditCPF.end();
        };

        if(campoEditCPF.getText().length() == 7){
            campoEditCPF.setText(campoEditCPF.getText() + ".");
            campoEditCPF.end();
        };
        
        if(campoEditCPF.getText().length() == 11){
            campoEditCPF.setText(campoEditCPF.getText() + "-");
            campoEditCPF.end();
        };
        
        
        
        // auto-complete telefone
        if(campoEditTel.getText().length() == 2){
            campoEditTel.setText(campoEditTel.getText() + " ");
            campoEditTel.end();
        };
        
        if(campoEditTel.getText().length() == 8){
            campoEditTel.setText(campoEditTel.getText() + "-");
            campoEditTel.end();
        };

    }
    
    
    
    @FXML
    void editarCliente() throws Exception{
        
        try{
            
            if (validarCampos()) {
                
                nome = campoEditNome.getText();
                cpf = campoEditCPF.getText();
                endereco = campoEditEnd.getText();
                telefone = campoEditTel.getText();

                TelefoneVO telefoneVO = new TelefoneVO(0, clienteEditar.getCpf(), null, telefone);
                EnderecoVO enderecoVO = new EnderecoVO();
                
                enderecoVO = enderecoBO.buscarPorCliente(clienteEditar.getCpf());
                telefoneVO = telefoneBO.buscarPorCliente(clienteEditar.getCpf());
                telefoneVO.setNumero(telefone);
                
                enderecoVO = enderecoVO.pegarValoresComoString(endereco);

                clienteEditar.setNome(nome);
                clienteEditar.setCpf(cpf);
                clienteEditar.setEndereco(enderecoVO);
                clienteEditar.setTelefone(telefoneVO);

                clienteBO.atualizar(clienteEditar);
                
                cancelarEdicao();
                abrirModalSucess(new Label("Cliente editado com sucesso!"), salvarEdicao, clienteEditar);
            }

        }
        catch(Exception ex){
            Label labelFalha = new Label(ex.getMessage());
            System.out.println(ex.getMessage());
            cancelarEdicao();
            abrirModalFail(labelFalha, cancelarEdicao);
        }
    }

    @FXML
    void cancelarEdicao() throws Exception{
        Stage palco = (Stage) this.cancelarEdicao.getScene().getWindow();
        palco.close();
    }




    boolean validarCampos(){
        
        if(campoEditNome.getText().isEmpty()){
            mensagemErroEdit.setText("O nome não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditNome.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditNome).play();
            return false;
        }else{
            if( campoEditNome.getText().matches("^[a-zA-Z\\s^áéíóúãâêõ]{1,60}$") )
                System.out.println("campo nome válido");
            else{
                mensagemErroEdit.setText("O nome não pode conter números ou símbolos");
                mensagemErroEdit.setVisible(true);
                campoEditNome.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditNome).play();
                return false;
            }
        }


        if(campoEditEnd.getText().isEmpty()){
            mensagemErroEdit.setText("O endereço não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditEnd.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditEnd).play();
            return false;
        }else{
            if (campoEditEnd.getText().matches("^[a-zA-Z\\s^áãàéêèíóõú.,]{1,50}[\\d^,]{1,5}[a-zA-Z\\s^áãàéêèíóõú,]{1,50}"))
                System.out.println("campo endereço válido");
            else{
                mensagemErroEdit.setText("O endereço não pode conter símbolos");
                mensagemErroEdit.setVisible(true);
                campoEditEnd.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditEnd).play();
                return false;
            }

        }


        if(campoEditCPF.getText().isEmpty()){
            mensagemErroEdit.setText("O cpf não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditCPF.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditCPF).play();
            return false;
        }else{
            if (campoEditCPF.getText().matches("^[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}$"))
                System.out.println("campo cpf válido");
            else{
                mensagemErroEdit.setText("Formato inválido de CPF");
                mensagemErroEdit.setVisible(true);
                campoEditCPF.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditCPF).play();
                return false;
            }
        }


        if(campoEditTel.getText().isEmpty()){
            mensagemErroEdit.setText("O telefone não pode ser vazio");
            mensagemErroEdit.setVisible(true);
            campoEditTel.setStyle(textFieldStyle);
            new animatefx.animation.Shake(campoEditTel).play();
            return false;
        }else{
            if (campoEditTel.getText().matches("[\\d]{2}[\\s][\\d]{5}[\\-][\\d]{4}")) 
                System.out.println("campo telefone válido");
            else{
                mensagemErroEdit.setText("Formato inválido de telefone");
                mensagemErroEdit.setVisible(true);
                campoEditTel.setStyle(textFieldStyle);
                new animatefx.animation.Shake(campoEditTel).play();
                return false;
            }
        }
        
        return true;
    }





    void preencherCampos(ClienteVO cliente, int index){
        
        try{

            campoEditNome.setText(cliente.getNome());
            campoEditCPF.setText(cliente.getCpf());
            campoEditEnd.setText(cliente.getEndereco().toString());
            campoEditTel.setText(cliente.getTelefone().toString());
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }



    
    @FXML
    void setInvisibleEdit(){
        this.mensagemErroEdit.setVisible(false);
    }

}