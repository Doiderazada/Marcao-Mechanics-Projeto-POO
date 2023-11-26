package br.java.projeto.poo.controller.Automoveis;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdicionarAutomovelController {

    @FXML private Button addNovoV;
    @FXML private Button cancelarAddV;
    @FXML private TextField anoNovoV;
    @FXML private TextField corNovoV;
    @FXML private TextField kmNovoV;
    @FXML private TextField modeloNovoV;
    @FXML private TextField placaNovoV;
    @FXML private Label msgErroNovoV;
    @FXML private ChoiceBox<String> tipoNovoV;


    
    private ClienteVO donoVeic = new ClienteVO();
    private ModalsController modalsController = new ModalsController();
    private String[] tipoVeic = {"Carro","Moto"};
    private ObservableList<String> listaTipoVeic = FXCollections.observableArrayList(tipoVeic);
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";



    public void initialize(ClienteVO cliente){
        acaoCompTela();
        donoVeic = cliente;
        tipoNovoV.setItems(listaTipoVeic);
        tipoNovoV.setValue(listaTipoVeic.get(0));
        
    }



    /**
     * <p> Sets the action from all elements on its corresponding screen.
     * 
     * <p> This method has no parameters.
     */
    private void acaoCompTela(){
        addNovoV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                addNovoV();
            }
            
        });
        cancelarAddV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cancelarAddV();
            }
            
        });
        modeloNovoV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleAdd();
                modeloNovoV.setStyle(null);
            }
            
        });
        anoNovoV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleAdd();
                anoNovoV.setStyle(null);
            }
            
        });
        kmNovoV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleAdd();
                kmNovoV.setStyle(null);
            }
            
        });
        placaNovoV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleAdd();
                placaNovoV.setStyle(null);
            }
            
        });
        corNovoV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleAdd();
                corNovoV.setStyle(null);
            }
            
        });
        
        
    }



    /**
     * <p> Saves a new {@code veiculo} to te database.
     * 
     * <p> This method has no parameters.
     */
    private void addNovoV() {
        try{   
            if (validarCampos()) {
                VeiculoBO veiculoBO = new VeiculoBO();

                String modelo, ano, cor, placa, tipo;
                double km;

                modelo = modeloNovoV.getText();
                ano = anoNovoV.getText();
                cor = corNovoV.getText();
                placa = placaNovoV.getText().toUpperCase();
                km = Double.parseDouble(kmNovoV.getText());
                tipo = tipoNovoV.getValue();

                VeiculoVO novoVeiculo = new VeiculoVO(0, placa, cor, modelo, donoVeic.getCpf(), tipo, ano, km);
                if (veiculoBO.inserir(novoVeiculo)) {
                    cancelarAddV();
                    modalsController.abrirModalSucesso("Veículo adicionado com sucesso");
                }else {
                    msgErroNovoV.setText("Não foi possível adicionar o veículo");
                    msgErroNovoV.setVisible(true);
                }


            }
        }catch(Exception e){
            this.cancelarAddV();
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }

    
    /**
     * <p> Closes the current screen.
     * 
     * <p> This method has no parameters.
     */
    private void cancelarAddV() {
        Stage palco = (Stage)this.cancelarAddV.getScene().getWindow();
        palco.close();
    }






    /**
     * <p> Validates the contents from the {@code TextField}s on screen.
     * @return <b>true</b> if the content of all {@code TextField} are valid. 
     */
    private boolean validarCampos(){
        
        if(modeloNovoV.getText().isEmpty()){
            msgErroNovoV.setText("O modelo do veículo não pode ser vazio");
            msgErroNovoV.setVisible(true);
            modeloNovoV.setStyle(textFieldStyle);
            new animatefx.animation.Shake(modeloNovoV).play();
            return false;
        }else{
            if (modeloNovoV.getText().matches("^[a-zA-Z\\s^áéíóúãêõô]{1,45}[\\d^.]{0,3}$")) 
                System.out.println("campo modelo válido");
            else{
                msgErroNovoV.setText("Formato inválido de modelo");
                msgErroNovoV.setVisible(true);
                modeloNovoV.setStyle(textFieldStyle);
                new animatefx.animation.Shake(modeloNovoV).play();
                return false;
            }
        }
        
        
        if(anoNovoV.getText().isEmpty()){
            msgErroNovoV.setText("O ano do veículo não pode ser vazio");
            msgErroNovoV.setVisible(true);
            anoNovoV.setStyle(textFieldStyle);
            new animatefx.animation.Shake(anoNovoV).play();
            return false;
        }else{
            if (anoNovoV.getText().matches("[\\d]{1,4}")) 
                System.out.println("campo ano válido");
            else{
                msgErroNovoV.setText("Formato inválido de de ano");
                msgErroNovoV.setVisible(true);
                anoNovoV.setStyle(textFieldStyle);
                new animatefx.animation.Shake(anoNovoV).play();
                return false;
            }
        }
        
        
        if(kmNovoV.getText().isEmpty()){
            msgErroNovoV.setText("A quilometragem não pode ser vazia");
            msgErroNovoV.setVisible(true);
            kmNovoV.setStyle(textFieldStyle);
            new animatefx.animation.Shake(kmNovoV).play();
            return false;
        }else{
            if (kmNovoV.getText().matches("[\\d^.]{1,9}")) 
                System.out.println("campo quilometragem válido");
            else{
                msgErroNovoV.setText("Formato inválido de quilometragem");
                msgErroNovoV.setVisible(true);
                kmNovoV.setStyle(textFieldStyle);
                new animatefx.animation.Shake(kmNovoV).play();
                return false;
            }
        }
        
        
        if(placaNovoV.getText().isEmpty()){
            msgErroNovoV.setText("A placa não pode ser vazia");
            msgErroNovoV.setVisible(true);
            placaNovoV.setStyle(textFieldStyle);
            new animatefx.animation.Shake(placaNovoV).play();
            return false;
        }else{
            if (placaNovoV.getText().matches("^[a-zA-Z]{3}[-|0-9]{1}[0-9|a-zA-Z]{1}[0-9]{2,3}$")) 
                System.out.println("campo placa válido");
            else{
                msgErroNovoV.setText("Formato inválido de placa");
                msgErroNovoV.setVisible(true);
                placaNovoV.setStyle(textFieldStyle);
                new animatefx.animation.Shake(placaNovoV).play();
                return false;
            }
        }
        
        
        if(corNovoV.getText().isEmpty()){
            msgErroNovoV.setText("A cor não pode ser vazia");
            msgErroNovoV.setVisible(true);
            corNovoV.setStyle(textFieldStyle);
            new animatefx.animation.Shake(corNovoV).play();
            return false;
        }else{
            if (corNovoV.getText().matches("^[a-zA-Z\\s^-]{1,20}$")) 
                System.out.println("campo cor válido");
            else{
                msgErroNovoV.setText("Formato inválido de cor");
                msgErroNovoV.setVisible(true);
                corNovoV.setStyle(textFieldStyle);
                new animatefx.animation.Shake(corNovoV).play();
                return false;
            }
        }
        return true;
    }






    private void setInvisibleAdd(){
        this.msgErroNovoV.setVisible(false);
    }

}