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
        this.msgErro.setVisible(false);
        tipo.setItems(listaTipoVeic);
        tipo.setValue(listaTipoVeic.get(0));
        veiculoEditar = veiculo;
        preencherCampos(veiculoEditar);
    }

    


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
    }





    
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

    private void cancelar() {
        Stage stage = (Stage) this.cancelar.getScene().getWindow();
        stage.close();
    }






    

    public void preencherCampos(VeiculoVO vo) {
        try { 

            ano.setText(vo.getAno());
            placa.setText(vo.getPlaca());
            modelo.setText(vo.getModelo());
            tipo.setValue(vo.getTipo());
            cor.setText(vo.getCor());
            km.setText(String.valueOf(vo.getKm()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }






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
}