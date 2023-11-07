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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;



import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.FuncionarioBO;
import br.java.projeto.poo.models.VO.FuncionarioVO;


public class FuncionariosController extends BaseController{
    private FuncionarioBO funcionarioBO = new FuncionarioBO(); 
    static ObservableList<FuncionarioVO> funcionariosDisponiveis;
    static ArrayList<FuncionarioVO> listaFuncionarios;

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


    
    public void initialize() throws Exception {
        super.initialize();
        listaFuncionarios = this.funcionarioBO.listar();
        funcionariosDisponiveis = FXCollections.observableArrayList(listaFuncionarios);
        this.inicializarTabela();
        msgErroBusca.setVisible(false);
        acaoDosBotoes();
        linhaSelecionada();
    }




    
    void buscarFuncionario() {
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




    
    void abirModalCadastro() throws IOException {
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
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }

    void abrirModalEditar(FuncionarioVO vo, int indice) throws Exception {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Funcionarios/EditarFuncionario.fxml"));
            Parent root = loader.load();

            EditarFuncionariosController editarController = loader.getController();
            editarController.initialize(vo);

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
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }

    void abrirModalDeletar(FuncionarioVO funcionario, int indice) throws Exception {
        try {
            ModalsController controller = new ModalsController();
            if(controller.abrirModalExcluir("Tem certeza que deseja excluir esse funcionário", 0)){
                realizarExclusao(funcionario, indice);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }






    private void acaoDosBotoes(){
        cadastrarFuncionario.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                try{abirModalCadastro();}
                catch(Exception e){
                    System.out.println(e.getMessage());
                    ModalsController modalsController = new ModalsController();
                    modalsController.abrirModalFalha(e.getMessage());
                }
            }
            
        });
        buscar.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarFuncionario();
            }
            
        });
    }







    private void linhaSelecionada() throws Exception{
        
        tabelaFuncionarios.setRowFactory(event -> {
            TableRow<FuncionarioVO> myRow = new TableRow<>();
            myRow.setOnMouseClicked( new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    FuncionarioVO funcionarioSelecionado = myRow.getItem();
                    if (!(myRow.isEmpty())) {
                        try{
                            exibirFuncionario(funcionarioSelecionado);
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                            ModalsController modalsController = new ModalsController();
                            modalsController.abrirModalFalha(e.getMessage());
                        }
                    } 
                }
                
            });
            return myRow;
        });
        
        
    }


    
    private void exibirFuncionario(FuncionarioVO funcionario) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Funcionarios/ExibirFuncionario.fxml"));
        Parent root = loader.load();

        FuncionarioShowController controller = loader.getController();
        controller.initialize(funcionario);
        
        Stage palco = new Stage();
        Scene cena = new Scene(root);
        palco  = (Stage)cadastrarFuncionario.getScene().getWindow();
        palco.setScene(cena);
        palco.show();
    }





    
    private void inicializarTabela() throws SQLException {
        funcNome.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, String>("nome"));
        FuncNivel.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, Integer>("nivel"));
        funcAdmi.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, String>("dataDeAdimissao"));
        funcCPF.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, String>("cpf"));
        funcSalario.setCellValueFactory(new PropertyValueFactory<FuncionarioVO, Double>("salario"));
        tabelaFuncionarios.setItems(funcionariosDisponiveis);
        this.inicializarBotoesDeAcao(funcionariosDisponiveis);
    }

    
    private void inicializarBotoesDeAcao (ObservableList<FuncionarioVO> funcs) {
        funcAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);
            

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnDelete.getStyleClass().add("btn-delete");
                btnEdit.setOnAction(event -> {
                    try {
                        FuncionarioVO funcionario = getTableView().getItems().get(getIndex());
                        abrirModalEditar(funcionario, getIndex());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                btnDelete.setOnAction(event -> {
                    try {
                        FuncionarioVO funcionario = getTableView().getItems().get(getIndex());
                        abrirModalDeletar(funcionario, getIndex());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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




    private void realizarExclusao(FuncionarioVO funcionario, int index) throws Exception {
        
            if(!funcionarioBO.deletar(funcionario)){
                funcionariosDisponiveis.remove(index);
            }
    }
}
