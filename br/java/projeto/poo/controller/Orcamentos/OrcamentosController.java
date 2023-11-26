package br.java.projeto.poo.controller.Orcamentos;

import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.OrcamentoBO;
import br.java.projeto.poo.models.VO.OrcamentoVO;
import br.java.projeto.poo.src.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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

public class OrcamentosController extends BaseController {
    

    @FXML private Button novoOrcamento;
    @FXML private Button gerarRelatorio;
    @FXML private ChoiceBox<String> cbSituacao;
    @FXML private Label msgErroBusca;
    @FXML private TableView<OrcamentoVO> tbOrcamentos;
    @FXML private TableColumn<OrcamentoVO, Double> valor;
    @FXML private TableColumn<OrcamentoVO, String> proprietario;
    @FXML private TableColumn<OrcamentoVO, String> situacao;
    @FXML private TableColumn<OrcamentoVO, String> placa;
    @FXML private TableColumn<OrcamentoVO, String> acoes;
    @FXML private TextField buscar;


    private OrcamentoBO orcamentoBO = new OrcamentoBO();
    private ModalsController modalsController = new ModalsController();
    private ArrayList<OrcamentoVO> listaOrcamentos;
    private ObservableList<OrcamentoVO> orcamentosDisponiveis;

    private String[] situacoes = {"Em aberto", "Todos"};
    private ObservableList<String> listaSituacao = FXCollections.observableArrayList(situacoes);



    @FXML
    public void initialize() {
        try {
            super.initialize();
            acaoCompTela();
            cbSituacao.setItems(listaSituacao);
            cbSituacao.setValue(listaSituacao.get(0));
            msgErroBusca.setVisible(false);
            listaOrcamentos = this.orcamentoBO.listarEmAberto();
            orcamentosDisponiveis = FXCollections.observableArrayList(listaOrcamentos); 
            inicializarTabela(0);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }



    /**
     * <p> Sets the action from all elements on its corresponding screen.
     * 
     * <p> This method has no parameters.
     */
    private void acaoCompTela(){
    
        novoOrcamento.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                novoOrcamento();
            }
            
        });

        gerarRelatorio.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {                
                gerarRelatorio();
            }
            
        });

        buscar.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarOrcamentos();
            }
            
        });
        
        cbSituacao.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                int i = cbSituacao.getSelectionModel().getSelectedIndex();
                try{
                    if (i == 1) {
                        listaOrcamentos = orcamentoBO.listarTodos();
                        orcamentosDisponiveis.setAll(listaOrcamentos);
                    }else{
                        listaOrcamentos = orcamentoBO.listarEmAberto();
                        orcamentosDisponiveis.setAll(listaOrcamentos);
                    }

                    inicializarTabela(i);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
            
        });
        
    }



    /**
     * <p> Opens up a Popup for creating a report.
     * 
     * <p> This method has no parameters.
     */
    private void gerarRelatorio() {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setResizable(false);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Orcamentos/ModalGerarRelatorio.fxml"));
            Parent root = loader.load();
            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);
            Window wNO = novoOrcamento.getScene().getWindow();
            modalStage.setX((wNO.getX() + wNO.getWidth()/2) - 260);
            modalStage.setY((wNO.getY() + wNO.getHeight()/2) - 230);
            modalStage.showAndWait();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }


    
    /**
     * <p> Searches an {@code orcamento} on the database with the given name in the TextField {@link br.java.projeto.poo.controller.Orcamentos.OrcamentosController#buscar buscar}.
     * 
     * <p> This method has no parameters.
     */
    private void buscarOrcamentos() {
        try {
            if (buscar.getText().length() > 2) {
                msgErroBusca.setVisible(false);
                if (buscar.getText().matches("^\\d{3}.*") ){
                    ArrayList<OrcamentoVO> listaTemporaria = orcamentoBO.buscarPorCPFCliente(buscar.getText());
                    for (OrcamentoVO orcamento : listaTemporaria){
                        if (cbSituacao.getSelectionModel().getSelectedIndex() == 0 && orcamento.getStatus() == 1) {
                            listaTemporaria.remove(orcamento);
                        }
                    }
                    orcamentosDisponiveis.setAll(listaTemporaria);
                    if (orcamentosDisponiveis.isEmpty()) {
                        msgErroBusca.setVisible(true);
                    }
                } else {
                    ArrayList<OrcamentoVO> listaTemporaria = orcamentoBO.buscarPorVeiculo(buscar.getText());
                    for (OrcamentoVO orcamento : listaTemporaria){
                        if (cbSituacao.getSelectionModel().getSelectedIndex() == 0 && orcamento.getStatus() == 1) {
                            listaTemporaria.remove(orcamento);
                        }
                    }
                    orcamentosDisponiveis.setAll(listaTemporaria);
                    if (orcamentosDisponiveis.isEmpty()) {
                        msgErroBusca.setVisible(true);
                    }
                }
            } else {
                // listaOrcamentos = this.orcamentoBO.listarEmAberto();
                orcamentosDisponiveis.setAll(listaOrcamentos);
                msgErroBusca.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    
    /**
     * <p> Calls the Method {@link br.java.projeto.poo.src.App#navegarEntreTelas(String) navegarEntreTelas} to switch from the actual active screen 
     * to the screen {@code novoOrcamento}.
     * 
     * <p> This method has no parameters. 
     */
    private void novoOrcamento(){
        App.navegarEntreTelas("novoOrcamento");
    }



    /**
     * <p> Sets the main table and then calls the method {@link br.java.projeto.poo.controller.Orcamentos.OrcamentosController#inicializarBotoesDeAcao() inicializarBotoesDeAcao} 
     * that sets the buttons on it.
     * 
     * @param sit the situation related to the selected value on {@code cbSituacao}.
     */
    private void inicializarTabela(int sit){
        proprietario.setCellValueFactory(new PropertyValueFactory<OrcamentoVO, String>("cpfCliente"));
        valor.setCellValueFactory(new PropertyValueFactory<OrcamentoVO, Double>("valor"));
        placa.setCellValueFactory(new PropertyValueFactory<OrcamentoVO, String>("placaVeiculo"));
        situacao.setVisible(false);

        if (sit == 1) {
            situacao.setCellValueFactory(new PropertyValueFactory<OrcamentoVO, String>("statusString"));
            situacao.setVisible(true);
        }
        
        tbOrcamentos.setItems(orcamentosDisponiveis);
        inicializarBotoesDeAcao();
    }



    /**
     * <p> Sets the buttons on its corresponding table.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarBotoesDeAcao () {
        acoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnFinalizar = new Button();
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnFinalizar, btnEdit, btnDelete);

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnEdit.setPrefSize(25, 25);

                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setPrefSize(25, 25);

                btnFinalizar.getStyleClass().add("btn-encerrar");
                btnFinalizar.setPrefSize(25, 25);

                btnContainer.setOnMouseEntered(event -> {
                    OrcamentoVO orcamento = getTableView().getItems().get(getIndex());
                    if (orcamento.getStatus() == 1) {
                        btnFinalizar.setDisable(true);
                        btnEdit.setDisable(true);
                        btnDelete.setDisable(true);
                    }
                });
                

                btnFinalizar.setOnAction(event -> {
                    try {
                        OrcamentoVO orcamentoVO = getTableView().getItems().get(getIndex());
                        if(modalsController.abrirModalExcluir("Realmente deseja fechar esse orçamento?")) {
                            orcamentoBO.encerrarRelatorio(orcamentoVO);
                            orcamentosDisponiveis.remove(getIndex());
                        };
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                btnEdit.setOnAction(event -> {
                    try {
                        OrcamentoVO orcamentoVO = getTableView().getItems().get(getIndex());
                        abrirTelaEditar(orcamentoVO);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                btnDelete.setOnAction(event -> {
                    try {
                        OrcamentoVO orcamentoVO = getTableView().getItems().get(getIndex());
                        if(modalsController.abrirModalExcluir("Realmente deseja excluir esse orçamento?")) {
                            orcamentoBO.deletar(orcamentoVO.getId());
                            orcamentosDisponiveis.remove(getIndex());
                        };
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
                    // btnContainer.setStyle("-fx-padding: 0 20 0 5;");
                    btnContainer.setAlignment(Pos.CENTER);
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                }
            }
        });
    }



    /**
     * <p> change the actual screen to {@code EditarOrcamento}, passing an {@code orcamento} as parameter.
     * 
     * @param orcamento the budget to be shown and edited on the screen.
     */
    private void abrirTelaEditar(OrcamentoVO orcamento){
        try {
            Stage stage = (Stage) this.novoOrcamento.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Orcamentos/EditarOrcamento.fxml"));
            Parent root = loader.load();

            EditarOrcamentosController editarController = loader.getController();
            
            editarController.initialize(orcamento);

            Scene modalScene = new Scene(root);
            stage.setScene(modalScene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha("Erro ao abrir edição: " + e.getMessage());
        }
    }
}