package br.java.projeto.poo.controller.Funcionarios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.ArrayList;



import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.FuncionarioBO;
import br.java.projeto.poo.models.VO.FuncionarioVO;


public class FuncionariosController extends BaseController{
    

    @FXML private Button cadastrarFuncionario;
    @FXML private Label msgErroBusca;
    @FXML private TableColumn<FuncionarioVO, Integer> FuncNivel;
    @FXML private TableColumn<FuncionarioVO, String> funcAcoes;
    @FXML private TableColumn<FuncionarioVO, String> funcAdmi;
    @FXML private TableColumn<FuncionarioVO, String> funcCPF;
    @FXML private TableColumn<FuncionarioVO, String> funcNome;
    @FXML private TableColumn<FuncionarioVO, Double> funcSalario;
    @FXML private TableView<FuncionarioVO> tabelaFuncionarios;
    @FXML private TextField buscar;


    private FuncionarioBO funcionarioBO = new FuncionarioBO();
    private ModalsController modalsController = new ModalsController();
    private ObservableList<FuncionarioVO> funcionariosDisponiveis;
    private ArrayList<FuncionarioVO> listaFuncionarios;


    
    public void initialize() throws Exception {
        super.initialize();
        acaoCompTela();
        listaFuncionarios = this.funcionarioBO.listar();
        funcionariosDisponiveis = FXCollections.observableArrayList(listaFuncionarios);
        inicializarTabela();
        msgErroBusca.setVisible(false);
        linhaSelecionada();
    }




    
    private void buscarFuncionario() {
        try {
            ArrayList<FuncionarioVO> funcionarioVOs;
            if (this.buscar.getText().length() > 2) {
                msgErroBusca.setVisible(false);
                if (this.buscar.getText().matches("^\\d{3}.*")) {
                    funcionarioVOs = funcionarioBO.buscarPorCPF(this.buscar.getText());
                    if (funcionarioVOs.isEmpty()) {
                        msgErroBusca.setVisible(true);
                    }
                    funcionariosDisponiveis.setAll(funcionarioVOs);
                } else {
                    funcionarioVOs = funcionarioBO.buscarPorNome(this.buscar.getText());
                    if (funcionarioVOs.isEmpty()) {
                        msgErroBusca.setVisible(true);
                    }
                    funcionariosDisponiveis.setAll(funcionarioVOs);
                }
            } else {
                funcionariosDisponiveis.setAll(listaFuncionarios);
                msgErroBusca.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }




    
    private void abirModalCadastro() {
        try{
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setResizable(false);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Funcionarios/CadastrarFuncionario.fxml"));
            Parent root = loader.load();

            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);

            Window wNF = cadastrarFuncionario.getScene().getWindow();
            modalStage.setX((wNF.getX() + wNF.getWidth()/2) - 251);
            modalStage.setY((wNF.getY() + wNF.getHeight()/2) - 325);

            modalStage.showAndWait();

            listaFuncionarios = this.funcionarioBO.listar();
            funcionariosDisponiveis.setAll(listaFuncionarios);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }

    private void abrirModalEditar(FuncionarioVO funcionario) {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Funcionarios/EditarFuncionario.fxml"));
            Parent root = loader.load();

            EditarFuncionariosController editarController = loader.getController();
            editarController.initialize(funcionario);

            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);

            Window wNF = cadastrarFuncionario.getScene().getWindow();
            modalStage.setX((wNF.getX() + wNF.getWidth()/2) - 250);
            modalStage.setY((wNF.getY() + wNF.getHeight()/2) - 300);
            
            modalStage.showAndWait();

            listaFuncionarios = this.funcionarioBO.listar();
            funcionariosDisponiveis.setAll(listaFuncionarios);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }

    private void abrirModalDeletar(FuncionarioVO funcionario, int indice) {
        if(modalsController.abrirModalExcluir("Tem certeza que deseja excluir esse funcionário", 0)){
            realizarExclusao(funcionario, indice);
        }
    }






    private void acaoCompTela(){
        cadastrarFuncionario.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                abirModalCadastro();
            }
            
        });
        buscar.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarFuncionario();
            }
            
        });
    }







    private void linhaSelecionada() {
        
        tabelaFuncionarios.setRowFactory(event -> {
            TableRow<FuncionarioVO> myRow = new TableRow<>();
            myRow.setOnMouseClicked( new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    FuncionarioVO funcionarioSelecionado = myRow.getItem();
                    if (!(myRow.isEmpty())) {
                        exibirFuncionario(funcionarioSelecionado);
                    } 
                }
                
            });
            return myRow;
        });
        
        
    }


    
    private void exibirFuncionario(FuncionarioVO funcionario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Funcionarios/ExibirFuncionario.fxml"));
            Parent root = loader.load();

            FuncionarioShowController controller = loader.getController();
            controller.initialize(funcionario);
            
            Stage palco = new Stage();
            Scene cena = new Scene(root);
            palco  = (Stage)cadastrarFuncionario.getScene().getWindow();
            palco.setScene(cena);
            palco.show();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }





    
    private void inicializarTabela() {
        funcNome.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, String>("nome"));
        FuncNivel.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, Integer>("nivel"));
        funcAdmi.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, String>("dataDeAdimissao"));
        funcCPF.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, String>("cpf"));
        funcSalario.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, Double>("salario"));
        tabelaFuncionarios.setItems(funcionariosDisponiveis);
        linhaSelecionada();
        inicializarBotoesDeAcao();
    }

    
    private void inicializarBotoesDeAcao() {
        funcAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);
            

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnDelete.getStyleClass().add("btn-delete");

                btnEdit.setOnAction(event -> {
                    FuncionarioVO funcionario = getTableView().getItems().get(getIndex());
                    abrirModalEditar(funcionario);
                });

                btnDelete.setOnAction(event -> {                    
                    FuncionarioVO funcionario = getTableView().getItems().get(getIndex());
                    abrirModalDeletar(funcionario, getIndex());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnContainer.setAlignment(Pos.CENTER);
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                }
            }
        });
    }  




    private void realizarExclusao(FuncionarioVO funcionario, int index) {
        if(!funcionarioBO.deletar(funcionario)){
            funcionariosDisponiveis.remove(index);
        }
    }
}
