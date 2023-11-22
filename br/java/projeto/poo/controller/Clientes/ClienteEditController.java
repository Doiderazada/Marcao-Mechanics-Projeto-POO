package br.java.projeto.poo.controller.Clientes;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ClienteBO;
import br.java.projeto.poo.models.BO.EnderecoBO;
import br.java.projeto.poo.models.BO.TelefoneBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.EnderecoVO;
import br.java.projeto.poo.models.VO.TelefoneVO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClienteEditController {
    

    @FXML private Button cancelarEdicao;
    @FXML private Button salvarEdicao;
    @FXML private Label mensagemErroEdit;
    @FXML private TextField campoEditCPF;
    @FXML private TextField campoEditEnd;
    @FXML private TextField campoEditNome;
    @FXML private TextField campoEditTel;
    
    
    private EnderecoBO enderecoBO = new EnderecoBO();
    private ClienteBO clienteBO = new ClienteBO();
    private TelefoneBO telefoneBO = new TelefoneBO();
    private ClienteVO clienteEditar = new ClienteVO();
    private ModalsController modalsController = new ModalsController();
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";



    public void initialize(ClienteVO cliente){
        
        clienteEditar = new ClienteVO();
        clienteEditar = cliente;
        this.preencherCampos(clienteEditar);
        this.acaoCompTela();
            
    }
    






    private void acaoCompTela(){
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
        campoEditCPF.setOnKeyReleased(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        campoEditTel.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                campoEditTel.setStyle(null);
            }
            
        });
        campoEditTel.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        
        cancelarEdicao.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cancelarEdicao();

            }
            
        });
        salvarEdicao.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                editarCliente();
            }
            
        });
    }




    
    private void autoComplete(){
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
    
    
    
    
    private void editarCliente() {
        try{
            if (validarCampos()) {
                String nome = null, cpf = null, endereco = null, telefone = null;
                
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
                modalsController.abrirModalSucesso("Cliente editado com sucesso!");
            }

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            cancelarEdicao();
            modalsController.ExibirMensagemFalha(ex.getMessage());
        }
    }

    
    private void cancelarEdicao() {
        Stage palco = (Stage) this.cancelarEdicao.getScene().getWindow();
        palco.close();
    }




    private boolean validarCampos(){
        
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





    private void preencherCampos(ClienteVO cliente){
        
        try{

            campoEditNome.setText(cliente.getNome());
            campoEditCPF.setText(cliente.getCpf());
            campoEditEnd.setText(cliente.getEndereco().toString());
            campoEditTel.setText(cliente.getTelefone().toString());

        } catch(Exception ex){
            System.out.println(ex.getMessage());
            modalsController.abrirModalFalha(ex.getMessage());
        }
    }


    public ClienteVO pegarClienteEditado(){
        return this.clienteEditar;
    }

    
    
    private void setInvisibleEdit(){
        this.mensagemErroEdit.setVisible(false);
    }

}