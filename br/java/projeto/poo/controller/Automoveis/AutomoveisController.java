package br.java.projeto.poo.controller.Automoveis;

import java.sql.SQLException;
import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class AutomoveisController extends BaseController{
    private VeiculoBO veiculoB0 = new VeiculoBO();
    public static ArrayList<VeiculoVO> listaAutomoveis;
    static ObservableList<VeiculoVO> automoveisDisponiveis;

    @FXML private Button cadastrar;
    @FXML private TableView<VeiculoVO> tbAutomoveis;
    @FXML private TableColumn<VeiculoVO, String> placa;
    @FXML private TableColumn<VeiculoVO, String> proprietario;
    @FXML private TableColumn<VeiculoVO, String> acoes;
    @FXML private TableColumn<VeiculoVO, String> modelo;
    @FXML private TableColumn<VeiculoVO, Long> id;
    @FXML private TextField buscar;

    @FXML
    public void initialize () {
        try {
            super.initialize();
            listaAutomoveis = this.veiculoB0.listar();
            automoveisDisponiveis = FXCollections.observableArrayList(listaAutomoveis);
            inicializarTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirModalCadastro(ActionEvent event) {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Automoveis/CadastrarAutomoveis.fxml"));
            Parent root = loader.load();
            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);
            Window wNV = cadastrar.getScene().getWindow();
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 255;
            centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 332;
            modalStage.setX(centralizarEixoX);
            modalStage.setY(centralizarEixoY);
            modalStage.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void abrirModalEditar(VeiculoVO vo, int indice) throws Exception {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Automoveis/EditarAutomoveis.fxml"));
            Parent root = loader.load();
            EditarAutomoveisController editarController = loader.getController();
            editarController.setDados(vo, indice);
            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);
            Window wNV = cadastrar.getScene().getWindow();
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 260;
            centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 230;
            modalStage.setX(centralizarEixoX);
            modalStage.setY(centralizarEixoY);
            modalStage.showAndWait();

            listaAutomoveis = this.veiculoB0.listar();
            automoveisDisponiveis.setAll(listaAutomoveis);
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void abrirExclusao(VeiculoVO veiculo, int index) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalExcluir.fxml"));
        Parent root = loader.load();
        ModalsController modalExc = loader.getController();

        String mensagem = "Tem certeza que deseja excluir esse veículo?";

        modalExc.ExibirMensagemExcluir(mensagem);

        Scene janelaEdit = new Scene(root);
        Stage palco = new Stage();
        palco.setResizable(false);
        palco.setScene(janelaEdit);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wNV = cadastrar.getScene().getWindow();
        double centralizarEixoX, centralizarEixoY;
        centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 225;
        centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 150;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();

        if(modalExc.getExclusaoValid()){
            realizarExclusao(veiculo, index);
        }
    }

    // inicializa a tabela
    private void inicializarTabela() throws SQLException {
        placa.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("placa"));
        proprietario.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("cpfDono"));
        modelo.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("modelo"));
        id.setCellValueFactory(new PropertyValueFactory<VeiculoVO, Long>("id"));
        tbAutomoveis.setItems(automoveisDisponiveis);
        this.inicializarBotoesDeAcao(automoveisDisponiveis);
    }

    // inicializa os botões de ação
    private void inicializarBotoesDeAcao (ObservableList<VeiculoVO> funcs) {
        acoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnDelete.getStyleClass().add("btn-delete");
                btnEdit.setOnAction(event -> {
                    try {
                        VeiculoVO veiculo = getTableView().getItems().get(getIndex());
                        abrirModalEditar(veiculo, getIndex());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                btnDelete.setOnAction(event -> {
                    try {
                        VeiculoVO veiculo = getTableView().getItems().get(getIndex());
                        abrirExclusao(veiculo, getIndex());
                        
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
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                    btnContainer.setAlignment(Pos.CENTER);
                }
            }
        });
    }  

    private void realizarExclusao(VeiculoVO veiculo, int index) throws Exception {
        VeiculoBO veiculoExcluido = new VeiculoBO();
        if(!veiculoExcluido.deletar(veiculo.getPlaca())){
            listaAutomoveis.remove(index);
            automoveisDisponiveis.setAll(listaAutomoveis);
        }
    }

    @FXML
    void buscarVeiculo(KeyEvent event) {
        try {
            ArrayList<VeiculoVO> veiculos;
            if(this.buscar.getText().length() > 2) {
                    if (this.buscar.getText().matches("^\\d{3}.*")) {
                        veiculos = veiculoB0.buscarPorDono(buscar.getText());
                    } else {
                        veiculos = veiculoB0.buscarPorPlaca(buscar.getText());
                    }
                    automoveisDisponiveis.setAll(veiculos);
            } else {
                    automoveisDisponiveis.setAll(listaAutomoveis);
            }

            veiculos = veiculoB0.buscarPorDono(buscar.getText());
            veiculos = veiculoB0.buscarPorPlaca(buscar.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
