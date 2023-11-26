package br.java.projeto.poo.controller.Automoveis;

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
import javafx.stage.Stage;

public class CadastrarAutomoveisController {
    

    @FXML private TextField ano;
    @FXML private Button cadastrar;
    @FXML private Button cancelar;
    @FXML private TextField cor;
    @FXML private TextField cpf;
    @FXML private TextField endereco;
    @FXML private TextField km;
    @FXML private TextField modelo;
    @FXML private Label msgErro;
    @FXML private TextField nome;
    @FXML private TextField placa;
    @FXML private TextField telefone;
    @FXML private ChoiceBox<String> tipo;


    private VeiculoBO veiculoBO = new VeiculoBO();
    private ClienteBO clienteBO = new ClienteBO();
    private ModalsController modalsController = new ModalsController();
    private boolean clienteExisteFlag = false;
    private String[] tipoVeic = {"Carro","Moto"};
    private ObservableList<String> listaTipoVeic = FXCollections.observableArrayList(tipoVeic);
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";


    
    @FXML
    public void initialize() {
        acaoCompTela();
        this.msgErro.setVisible(false);
        tipo.setItems(listaTipoVeic);
        tipo.setValue(listaTipoVeic.get(0));
        
    }



    /**
     * <p> Sets the action from all elements on its corresponding screen.
     * 
     * <p> This method has no parameters.
     */
    private void acaoCompTela(){
        cadastrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cadastrarVeiculo();
            }
            
        });
        cancelar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cancelar();
            }
            
        });
        nome.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                nome.setStyle(null);
            }
            
        });
        cpf.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                cpf.setStyle(null);
            }
            
        });
        cpf.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
                buscarCliente();
            }
            
        });
        telefone.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                telefone.setStyle(null);
            }
            
        });
        telefone.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        endereco.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                endereco.setStyle(null);
            }
            
        });
        modelo.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                modelo.setStyle(null);
            }
            
        });
        ano.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                ano.setStyle(null);
            }
            
        });
        km.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                km.setStyle(null);
            }
            
        });
        placa.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                placa.setStyle(null);
            }
            
        });
        cor.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleCad();
                cor.setStyle(null);
            }
            
        });
        
    }



    /**
     * <p> Auto-completes the filled content in the {@code TextField}s.
     * 
     * <p> This method has no parameters.
     */
    private void autoComplete(){
        // auto-complete cpf
        if(cpf.getText().length() == 3){
            cpf.setText(cpf.getText() + ".");
            cpf.end();
        };

        if(cpf.getText().length() == 7){
            cpf.setText(cpf.getText() + ".");
            cpf.end();
        };
        
        if(cpf.getText().length() == 11){
            cpf.setText(cpf.getText() + "-");
            cpf.end();
        };
        
        
        
        // auto-complete telefone
        if(telefone.getText().length() == 2){
            telefone.setText(telefone.getText() + " ");
            telefone.end();
        };
        
        if(telefone.getText().length() == 8){
            telefone.setText(telefone.getText() + "-");
            telefone.end();
        };

    }



    /**
     * <p> Searches for the current {@code cliente} in the database.
     * 
     * <p> This method has no parameters.
     */
    private void buscarCliente() {
        try {
            if(cpf.getText().length() == 14) {
                ArrayList<ClienteVO> clientes = clienteBO.buscarPorCPF(cpf.getText());
                if(!clientes.isEmpty()) {
                    ClienteVO cliente = clientes.get(0);
                    nome.setText(cliente.getNome());
                    endereco.setText(cliente.getEndereco().toString());
                    telefone.setText(cliente.getTelefone().toString());
                    clienteExisteFlag = true;
                } else {
                    clienteExisteFlag = false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * <p> Saves the current {@code veiculo} to the database.
     * 
     * <p> This method has no parameters.
     */
    private void cadastrarVeiculo() {
        try {
            
            if (validarCampos()) {

                EnderecoVO enderecoCliente = new EnderecoVO().pegarValoresComoString(endereco.getText());
                
                VeiculoVO veiculoVO = new VeiculoVO(0, 
                        placa.getText().toUpperCase(), 
                        cor.getText(), 
                        modelo.getText(), 
                        cpf.getText(), 
                        tipo.getValue(), 
                        ano.getText(), 
                        Double.valueOf(km.getText()));

                ArrayList<VeiculoVO> listaVeiculos = new ArrayList<VeiculoVO>();
                listaVeiculos.add(veiculoVO);

                TelefoneVO telefoneVO = new TelefoneVO(0, cpf.getText(), null, telefone.getText());
                
                ClienteVO ClienteVO = new ClienteVO(0, nome.getText(), cpf.getText(), enderecoCliente, listaVeiculos, telefoneVO);

                if (!clienteExisteFlag) {
                    clienteBO.inserir(ClienteVO);
                }

                if(veiculoBO.inserir(veiculoVO)){

                    cancelar();
                    modalsController.abrirModalSucesso("Veículo cadastrado com sucesso.");
                } else modalsController.abrirModalFalha("Não foi possível cadastrar o veículo.");
            }

        } catch (Exception e) {
            this.msgErro.setText(e.getMessage());
            this.msgErro.setVisible(true);
        }
    }

    

    /**
     * <p> Closes the current screen.
     * 
     * <p> This method has no parameters.
     */
    private void cancelar() {
        Stage stage = (Stage) this.cancelar.getScene().getWindow();
        stage.close();
    }



    /**
     * <p> Validates the contents from the {@code TextField}s on screen.
     * @return <b>true</b> if the content of all {@code TextField} are valid. 
     */
    private boolean validarCampos(){
        
        if(nome.getText().isEmpty()){
            msgErro.setText("O nome não pode ser vazio");
            msgErro.setVisible(true);
            nome.setStyle(textFieldStyle);
            new animatefx.animation.Shake(nome).play();
            return false;
        }else{
            if( nome.getText().matches("^[a-zA-Z\\s^áéíóúãâêõ]{1,60}$") )
                System.out.println("campo nome válido");
            else{
                msgErro.setText("O nome não pode conter números ou símbolos");
                msgErro.setVisible(true);
                nome.setStyle(textFieldStyle);
                new animatefx.animation.Shake(nome).play();
                return false;
            }
        }


        if(cpf.getText().isEmpty()){
            msgErro.setText("O cpf não pode ser vazio");
            msgErro.setVisible(true);
            cpf.setStyle(textFieldStyle);
            new animatefx.animation.Shake(cpf).play();
            return false;
        }else{
            if (cpf.getText().matches("^[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}$"))
                System.out.println("campo cpf válido");
            else{
                msgErro.setText("Formato inválido de CPF");
                msgErro.setVisible(true);
                cpf.setStyle(textFieldStyle);
                new animatefx.animation.Shake(cpf).play();
                return false;
            }
        }


        if(telefone.getText().isEmpty()){
            msgErro.setText("O telefone não pode ser vazio");
            msgErro.setVisible(true);
            telefone.setStyle(textFieldStyle);
            new animatefx.animation.Shake(telefone).play();
            return false;
        }else{
            if (telefone.getText().matches("[\\d]{2}[\\s][\\d]{5}[\\-][\\d]{4}")) 
                System.out.println("campo telefone válido");
            else{
                msgErro.setText("Formato inválido de telefone");
                msgErro.setVisible(true);
                telefone.setStyle(textFieldStyle);
                new animatefx.animation.Shake(telefone).play();
                return false;
            }
        }
        

        if(endereco.getText().isEmpty()){
            msgErro.setText("O endereço não pode ser vazio");
            msgErro.setVisible(true);
            endereco.setStyle(textFieldStyle);
            new animatefx.animation.Shake(endereco).play();
            return false;
        }else{
            if (endereco.getText().matches("^[a-zA-Z\\s^áãàéêèíóõú.,]{1,50}[\\d^,]{1,5}[a-zA-Z\\s^áãàéêèíóõú,]{1,50}"))
                System.out.println("campo endereço válido");
            else{
                msgErro.setText("O endereço não pode conter símbolos");
                msgErro.setVisible(true);
                endereco.setStyle(textFieldStyle);
                new animatefx.animation.Shake(endereco).play();
                return false;
            }

        }


        if(modelo.getText().isEmpty()){
            msgErro.setText("O modelo do veículo não pode ser vazio");
            msgErro.setVisible(true);
            modelo.setStyle(textFieldStyle);
            new animatefx.animation.Shake(modelo).play();
            return false;
        }else{
            if (modelo.getText().matches("^[a-zA-Z\\s^áéíóúãêõô]{1,45}[\\d^.]{0,3}$")) 
                System.out.println("campo modelo válido");
            else{
                msgErro.setText("Formato inválido de modelo");
                msgErro.setVisible(true);
                modelo.setStyle(textFieldStyle);
                new animatefx.animation.Shake(modelo).play();
                return false;
            }
        }
        
        
        if(ano.getText().isEmpty()){
            msgErro.setText("O ano do veículo não pode ser vazio");
            msgErro.setVisible(true);
            ano.setStyle(textFieldStyle);
            new animatefx.animation.Shake(ano).play();
            return false;
        }else{
            if (ano.getText().matches("[\\d]{1,4}")) 
                System.out.println("campo ano válido");
            else{
                msgErro.setText("Formato inválido de de ano");
                msgErro.setVisible(true);
                ano.setStyle(textFieldStyle);
                new animatefx.animation.Shake(ano).play();
                return false;
            }
        }
        
        
        if(km.getText().isEmpty()){
            msgErro.setText("A quilometragem não pode ser vazia");
            msgErro.setVisible(true);
            km.setStyle(textFieldStyle);
            new animatefx.animation.Shake(km).play();
            return false;
        }else{
            if (km.getText().matches("[\\d^.]{1,9}")) 
                System.out.println("campo quilometragem válido");
            else{
                msgErro.setText("Formato inválido de quilometragem");
                msgErro.setVisible(true);
                km.setStyle(textFieldStyle);
                new animatefx.animation.Shake(km).play();
                return false;
            }
        }
        
        
        if(placa.getText().isEmpty()){
            msgErro.setText("A placa não pode ser vazia");
            msgErro.setVisible(true);
            placa.setStyle(textFieldStyle);
            new animatefx.animation.Shake(placa).play();
            return false;
        }else{
            if (placa.getText().matches("^[a-zA-Z]{3}[-|0-9]{1}[0-9|a-zA-Z]{1}[0-9]{2,3}$")) 
                System.out.println("campo placa válido");
            else{
                msgErro.setText("Formato inválido de placa");
                msgErro.setVisible(true);
                placa.setStyle(textFieldStyle);
                new animatefx.animation.Shake(placa).play();
                return false;
            }
        }
        
        
        if(cor.getText().isEmpty()){
            msgErro.setText("A cor não pode ser vazia");
            msgErro.setVisible(true);
            cor.setStyle(textFieldStyle);
            new animatefx.animation.Shake(cor).play();
            return false;
        }else{
            if (cor.getText().matches("^[a-zA-Z\\s^-]{1,20}$")) 
                System.out.println("campo cor válido");
            else{
                msgErro.setText("Formato inválido de cor");
                msgErro.setVisible(true);
                cor.setStyle(textFieldStyle);
                new animatefx.animation.Shake(cor).play();
                return false;
            }
        }
        return true;
    }




    private void setInvisibleCad(){
        this.msgErro.setVisible(false);
    }
}