package br.java.projeto.poo.controller.Automoveis;

import java.io.IOException;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class EditarAutomoveisController {
    VeiculoBO veiculoBO = new VeiculoBO();
    int indice = 0;
    String cpfDono;
    String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";
    String[] tipoVeicArray = {"Carro", "Moto"};
    VeiculoVO veiculoEditar = new VeiculoVO();

    @FXML private TextField ano;
    @FXML private Button cadastrar;
    @FXML private Button cancelar;
    @FXML private TextField cor;
    @FXML private TextField km;
    @FXML private TextField modelo;
    @FXML private Label msgErro;
    @FXML private TextField placa;
    @FXML private ChoiceBox<String> tipo;

    @FXML
    public void initialize(VeiculoVO veiculo) {
        this.msgErro.setVisible(false);
        tipo.getItems().addAll(tipoVeicArray);
        tipo.setValue(tipoVeicArray[0]);
        veiculoEditar = veiculo;
        preencherCampos(veiculoEditar);
    }

    




    
    void abrirModalFail(String mensagem) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalFalha.fxml"));
        Parent root = loader.load();

        ModalsController controller = loader.getController();
        controller.ExibirMensagemFalha(mensagem);

        Scene popup = new Scene(root);
        Stage palco = new Stage();
        palco.setScene(popup);
        palco.setResizable(false);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wCV = cadastrar.getScene().getWindow();
        double centralizarEixoX = (wCV.getX() + wCV.getWidth()/2) - 200;
        double centralizarEixoY = (wCV.getY() + wCV.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();

    }

    
    void abrirModalSucess(String mensagem) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalSucesso.fxml"));
        Parent root = loader.load();
        
        ModalsController controller = loader.getController();
        controller.ExibirMensagemSucesso(mensagem);
        
        Scene popup = new Scene(root);
        Stage palco = new Stage();
        palco.setScene(popup);
        palco.setResizable(false);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wCV = cadastrar.getScene().getWindow();
        double centralizarEixoX = (wCV.getX() + wCV.getWidth()/2) - 200;
        double centralizarEixoY = (wCV.getY() + wCV.getHeight()/2) - 100;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();
        
    }




    @FXML
    void editarVeiculo(ActionEvent event) {
        try {
            if(validarCampos()){
                veiculoEditar.setAno(ano.getText());
                veiculoEditar.setCor(cor.getText());
                veiculoEditar.setKm(Double.parseDouble(km.getText()));
                veiculoEditar.setModelo(modelo.getText());
                veiculoEditar.setPlaca(placa.getText());
                veiculoEditar.setTipo(tipo.getValue());
                
                veiculoBO.atualizar(veiculoEditar);
                
                this.fecharModal();
                abrirModalSucess("Veículo editado com sucesso.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.msgErro.setText(e.getMessage());
            this.msgErro.setVisible(true);
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        this.fecharModal();
    }

    private void fecharModal() {
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
            cpfDono = vo.getCpfDono();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }






    boolean validarCampos(){
        
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