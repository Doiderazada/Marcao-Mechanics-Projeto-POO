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
    
    @FXML
    private Label id;

    @FXML
    private TextField ano;

    @FXML
    private Button cadastrar;

    @FXML
    private Button cancelar;

    @FXML
    private TextField cor;

    @FXML
    private TextField km;

    @FXML
    private TextField modelo;

    @FXML
    private Label msgErro;

    @FXML
    private TextField placa;

    @FXML 
    private ChoiceBox<String> tipo;

    @FXML
    void initialize() {
        this.id.setVisible(false);
        this.msgErro.setVisible(false);
        tipo.getItems().addAll("Carro", "Moto");
        tipo.setValue("Carro");
    }

    @FXML
    void cancelar(ActionEvent event) {
        this.fecharModal();
    }

    private void fecharModal() {
        Stage stage = (Stage) this.cancelar.getScene().getWindow();
        stage.close();
    }




    @FXML
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

    @FXML
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
            VeiculoVO veiculo = new VeiculoVO(Integer.valueOf(id.getText()),
            placa.getText().toUpperCase(),
            cor.getText(),
            modelo.getText(),
            cpfDono, tipo.getValue(), ano.getText(), Double.valueOf(km.getText()));

            veiculoBO.atualizar(veiculo);
            
            this.fecharModal();
            abrirModalSucess("Veículo editado com sucesso.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.msgErro.setText(e.getMessage());
            this.msgErro.setVisible(true);
        }
    }

    public void setDados(VeiculoVO vo, int indice) {
        try {
            if (Long.valueOf(vo.getId()) <= 0) { // verifica se o funcionario possui um id válido
                this.id.setText(String.valueOf(veiculoBO.buscarPorPlaca(vo.getPlaca()).get(0).getId()));
            } else {
                this.id.setText(String.valueOf(vo.getId()));   
            } 

            ano.setText(vo.getAno());
            placa.setText(vo.getPlaca());
            modelo.setText(vo.getModelo());
            tipo.setValue(vo.getTipo());
            cor.setText(vo.getCor());
            km.setText(String.valueOf(vo.getKm()));
            cpfDono = vo.getCpfDono();
            this.indice = indice;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
