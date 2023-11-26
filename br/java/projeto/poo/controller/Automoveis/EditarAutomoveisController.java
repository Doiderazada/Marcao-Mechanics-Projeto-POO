package br.java.projeto.poo.controller.Automoveis;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.VeiculoBO;
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

public class EditarAutomoveisController {
    

    @FXML private TextField ano;
    @FXML private Button cadastrar;
    @FXML private Button cancelar;
    @FXML private TextField cor;
    @FXML private TextField km;
    @FXML private TextField modelo;
    @FXML private Label msgErro;
    @FXML private TextField placa;
    @FXML private ChoiceBox<String> tipo;


    private VeiculoBO veiculoBO = new VeiculoBO();
    private VeiculoVO veiculoEditar = new VeiculoVO();
    private ModalsController modalsController = new ModalsController();
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";
    private String[] tipoVeic = {"Carro", "Moto"};
    private ObservableList<String> listaTipoVeic = FXCollections.observableArrayList(tipoVeic);




    @FXML
    public void initialize(VeiculoVO veiculo) {
        acaoCompTela();
        setInvisibleEdit();
        tipo.setItems(listaTipoVeic);
        tipo.setValue(listaTipoVeic.get(0));
        veiculoEditar = veiculo;
        preencherCampos(veiculoEditar);
    }

    

    /**
     * <p> Sets the action from all elements on its corresponding screen.
     * 
     * <p> This method has no parameters.
     */
    private void acaoCompTela() {
        cadastrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                editarVeiculo();
            }
            
        });
        cancelar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                cancelar();
            }
            
        });
        ano.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                ano.setStyle(null);
            }
            
        });
        cor.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                cor.setStyle(null);
            }
            
        });
        km.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                km.setStyle(null);
            }
            
        });
        modelo.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                modelo.setStyle(null);
            }
            
        });
        placa.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                placa.setStyle(null);
            }
            
        });
    }



    /**
     * <p> Edits the current {@code veiculo} in the database.
     * 
     * <p> This method has no parameters.
     */
    private void editarVeiculo() {
        try {
            if(validarCampos()){
                veiculoEditar.setAno(ano.getText());
                veiculoEditar.setCor(cor.getText());
                veiculoEditar.setKm(Double.parseDouble(km.getText()));
                veiculoEditar.setModelo(modelo.getText());
                veiculoEditar.setPlaca(placa.getText());
                veiculoEditar.setTipo(tipo.getValue());
                
                veiculoBO.atualizar(veiculoEditar);
                
                cancelar();
                modalsController.abrirModalSucesso("Veículo editado com sucesso.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
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
     * <p> Fills up the {@code TextField}s with the data from the {@code veiculo} to be edited.
     * @param veiculo which data will fill the {@code TextField}s.
     */
    public void preencherCampos(VeiculoVO veiculo) {
        try { 

            ano.setText(veiculo.getAno());
            placa.setText(veiculo.getPlaca());
            modelo.setText(veiculo.getModelo());
            tipo.setValue(veiculo.getTipo());
            cor.setText(veiculo.getCor());
            km.setText(String.valueOf(veiculo.getKm()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * <p> Validates the contents from the {@code TextField}s on screen.
     * @return <b>true</b> if the content of all {@code TextField} are valid. 
     */
    private boolean validarCampos(){
        
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

    
    private void setInvisibleEdit() {
        this.msgErro.setVisible(false);
    }
}