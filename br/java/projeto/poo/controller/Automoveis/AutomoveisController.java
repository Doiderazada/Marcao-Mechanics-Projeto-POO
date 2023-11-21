package br.java.projeto.poo.controller.Automoveis;

import java.util.ArrayList;


import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.controller.Clientes.ClienteShowController;
import br.java.projeto.poo.models.BO.ClienteBO;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.VeiculoVO;
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

public class AutomoveisController extends BaseController{
    
    @FXML private Label msgErroBusca;
    @FXML private Button cadastrar;
    @FXML private TableView<VeiculoVO> tbAutomoveis;
    @FXML private TableColumn<VeiculoVO, String> placa;
    @FXML private TableColumn<VeiculoVO, String> proprietario;
    @FXML private TableColumn<VeiculoVO, String> acoes;
    @FXML private TableColumn<VeiculoVO, String> modelo;
    @FXML private TableColumn<VeiculoVO, Long> id;
    @FXML private TextField buscar;


    private VeiculoBO veiculoB0 = new VeiculoBO();
    private ModalsController modalsController = new ModalsController();
    private ArrayList<VeiculoVO> listaAutomoveis;
    private ObservableList<VeiculoVO> automoveisDisponiveis;


    @FXML
    public void initialize () {
        try {
            super.initialize();
            acaoCompTela();
            listaAutomoveis = this.veiculoB0.listar();
            automoveisDisponiveis = FXCollections.observableArrayList(listaAutomoveis);
            inicializarTabela();
            msgErroBusca.setVisible(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    private void acaoCompTela() {
        cadastrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                abrirModalCadastro();
            }
            
        });
        buscar.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarVeiculo();
            }
            
        });
    }


    
    private void abrirModalCadastro() {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Automoveis/CadastrarAutomoveis.fxml"));
            Parent root = loader.load();

            
            Window wNV = cadastrar.getScene().getWindow();
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 255;
            centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 332;
            modalStage.setX(centralizarEixoX);
            modalStage.setY(centralizarEixoY);
            
            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);
            modalStage.showAndWait();

            listaAutomoveis = this.veiculoB0.listar();
            automoveisDisponiveis.setAll(listaAutomoveis);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void abrirModalEditar(VeiculoVO veiculo) {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Automoveis/EditarAutomoveis.fxml"));
            Parent root = loader.load();

            EditarAutomoveisController editarController = loader.getController();
            editarController.initialize(veiculo);

            
            Window wNV = cadastrar.getScene().getWindow();
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 260;
            centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 230;
            modalStage.setX(centralizarEixoX);
            modalStage.setY(centralizarEixoY);

            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);
            modalStage.showAndWait();

            listaAutomoveis = this.veiculoB0.listar();
            automoveisDisponiveis.setAll(listaAutomoveis);
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }


    private void abrirExclusao(VeiculoVO veiculo, int index) {
        if (modalsController.abrirModalExcluir("Tem certeza que deseja excluir esse veículo?", index)) {
            realizarExclusao(veiculo, index);
        }
    }







    
    private void buscarVeiculo() {
        try {
            ArrayList<VeiculoVO> veiculos;
            if(this.buscar.getText().length() > 2) {
                    if (this.buscar.getText().matches("^\\d{3}.*")) {
                        veiculos = veiculoB0.buscarPorDono(buscar.getText());
                        if (veiculos.isEmpty()) {
                            msgErroBusca.setVisible(true);
                        }else msgErroBusca.setVisible(false);
                    } else {
                        veiculos = veiculoB0.buscarPorPlaca(buscar.getText());
                        if (veiculos.isEmpty()) {
                            msgErroBusca.setVisible(true);
                        }else msgErroBusca.setVisible(false);
                    }
                    automoveisDisponiveis.setAll(veiculos);
            } else {
                    automoveisDisponiveis.setAll(listaAutomoveis);
                    msgErroBusca.setVisible(false);
            }

            veiculos = veiculoB0.buscarPorDono(buscar.getText());
            veiculos = veiculoB0.buscarPorPlaca(buscar.getText());
        } catch (Exception e) {
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }






    // inicializa a tabela
    private void inicializarTabela() {
        placa.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("placa"));
        proprietario.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("cpfDono"));
        modelo.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("modelo"));
        tbAutomoveis.setItems(automoveisDisponiveis);
        linhaSelecionada();
        inicializarBotoesDeAcao();
    }

    // inicializa os botões de ação
    private void inicializarBotoesDeAcao () {
        acoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnEdit.setPrefSize(25, 25);
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setPrefSize(25, 25);
                btnEdit.setOnAction(event -> {
                    
                    VeiculoVO veiculo = getTableView().getItems().get(getIndex());
                    abrirModalEditar(veiculo);
                    
                });

                btnDelete.setOnAction(event -> {
                    
                    VeiculoVO veiculo = getTableView().getItems().get(getIndex());
                    abrirExclusao(veiculo, getIndex());
                        
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











    private void linhaSelecionada() {
        
        tbAutomoveis.setRowFactory(event -> {
            TableRow<VeiculoVO> myRow = new TableRow<>();
            myRow.setOnMouseClicked( new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    VeiculoVO veiculoSelecionado = myRow.getItem();
                    if (!(myRow.isEmpty())) {
                        exibirCliente(veiculoSelecionado);
                    } 
                }
                
            });
            return myRow;
        });
        
        
    }


    
    private void exibirCliente(VeiculoVO veiculo) {
        try {
            ClienteBO clienteBO = new ClienteBO();
            ArrayList<ClienteVO> listaCliente;
            listaCliente = clienteBO.buscarPorCPF(veiculo.getCpfDono());
            ClienteVO cliente = listaCliente.get(0);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Clientes/ExibirCliente.fxml"));
            Parent root = loader.load();

            ClienteShowController controller = loader.getController();
            controller.initialize(cliente);
            
            Stage palco = new Stage();
            Scene cena = new Scene(root);
            palco  = (Stage)cadastrar.getScene().getWindow();
            palco.setScene(cena);
            palco.show();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }








    private void realizarExclusao(VeiculoVO veiculo, int index) {
        try {
            VeiculoBO veiculoExcluido = new VeiculoBO();
            if(!veiculoExcluido.deletar(veiculo.getPlaca())){
                listaAutomoveis.remove(index);
                automoveisDisponiveis.setAll(listaAutomoveis);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}