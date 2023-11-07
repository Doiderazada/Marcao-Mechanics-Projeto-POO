package br.java.projeto.poo.controller.Funcionarios;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.VO.FuncionarioVO;
import br.java.projeto.poo.src.App;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class FuncionarioShowController extends BaseController{
    
    FuncionarioVO funcionarioExibido;

    @FXML private Button editarFuncionario;
    @FXML private Button telaInicial;
    @FXML private Button verSenha;
    @FXML private Label exibirCPF;
    @FXML private Label exibirDataAdm;
    @FXML private Label exibirEndereco;
    @FXML private Label exibirNivel;
    @FXML private Label exibirNome;
    @FXML private Label exibirSalario;
    @FXML private Label exibirTelefone;
    @FXML private Label nomeFuncionarioMenu;
    @FXML private PasswordField senhaTF;
    @FXML private TextField verSenhaTF;


    public void initialize(FuncionarioVO funcionario){
        try {
            funcionarioExibido = new FuncionarioVO();
            funcionarioExibido = funcionario;
            verSenhaTF.setVisible(false);
            acaoDosBotoes();
            preencherCampos(funcionarioExibido);
        } catch (Exception e) {
            System.out.println(e.getMessage());     
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }








    private void abrirEditFunc() {

        try {
            Stage palco = new Stage();
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.initStyle(StageStyle.UNDECORATED);
            Window wEC = editarFuncionario.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Funcionarios/EditarFuncionario.fxml"));
            Parent root = loader.load();

            EditarFuncionariosController controller = loader.getController();
            controller.initialize(funcionarioExibido);

            Scene janelaEdit = new Scene(root);
            palco.setScene(janelaEdit);
            
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wEC.getX() + wEC.getWidth()/2) - 250;
            centralizarEixoY = (wEC.getY() + wEC.getHeight()/2) - 300;
            palco.setX(centralizarEixoX);
            palco.setY(centralizarEixoY);
            palco.showAndWait();

            funcionarioExibido = controller.pegarFuncionarioEditado();
            preencherCampos(funcionarioExibido);


        }catch(Exception e){
            System.out.println(e.getMessage());
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }









    private void acaoDosBotoes() throws Exception {
        telaInicial.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                voltaTelaInicial();
            }

        });
        
        editarFuncionario.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0){
                abrirEditFunc();
            }
            
        });

        verSenha.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                if (senhaTF.isVisible()) {
                    senhaTF.setVisible(false);
                    verSenhaTF.setText(funcionarioExibido.getSenha());
                    verSenhaTF.setVisible(true);
                    verSenha.getStyleClass().setAll("btn-senha-shown");
                }else {
                    senhaTF.setVisible(true);
                    verSenhaTF.setText(null);
                    verSenhaTF.setVisible(false);
                    verSenha.getStyleClass().setAll("btn-senha-hiden");
                }
            }
            
        });
    }







    private void preencherCampos(FuncionarioVO funcionario){
        try{
            exibirNome.setText(funcionario.getNome());
            exibirCPF.setText(funcionario.getCpf());
            exibirNivel.setText(String.valueOf(funcionario.getNivel()));
            exibirSalario.setText(String.valueOf(funcionario.getSalario()));
            exibirEndereco.setText(funcionario.getEndereco().toString());
            exibirTelefone.setText(funcionario.getTelefone().toString());
            exibirDataAdm.setText(funcionario.getDataDeAdimissao());
            senhaTF.setText(funcionario.getSenha());

            int espaco = funcionario.getNome().indexOf(" ");
            this.nomeFuncionarioMenu.setText(funcionario.getNome().substring(0, espaco));
        }catch(Exception e){
            System.out.println("Erro no preenchimento dos campos: \n" + e.getMessage() + "\n");
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }





    private void voltaTelaInicial(){
        App.navegarEntreTelas("funcionarios");
    }
}