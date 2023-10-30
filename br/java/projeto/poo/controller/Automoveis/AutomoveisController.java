package br.java.projeto.poo.controller.Automoveis;

import java.sql.SQLException;
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
import javafx.event.ActionEvent;
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
    private VeiculoBO veiculoB0 = new VeiculoBO();
    public static ArrayList<VeiculoVO> listaAutomoveis;
    static ObservableList<VeiculoVO> automoveisDisponiveis;
    
    @FXML private Label msgErroBusca;
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
            linhaSelecionada();
            msgErroBusca.setVisible(false);
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

    void abrirModalEditar(VeiculoVO vo, int indice) throws Exception {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Automoveis/EditarAutomoveis.fxml"));
            Parent root = loader.load();

            EditarAutomoveisController editarController = loader.getController();
            editarController.initialize(vo);

            
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
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }


    private void abrirExclusao(VeiculoVO veiculo, int index) throws Exception {
        Stage palco = new Stage();
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalExcluir.fxml"));
        Parent root = loader.load();
        ModalsController modalExc = loader.getController();

        String mensagem = "Tem certeza que deseja excluir esse veículo?";

        modalExc.ExibirMensagemExcluir(mensagem);
        

        Window wNV = cadastrar.getScene().getWindow();
        double centralizarEixoX, centralizarEixoY;
        centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 225;
        centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 150;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);

        Scene janelaEdit = new Scene(root);
        palco.setResizable(false);
        palco.setScene(janelaEdit);
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











    void linhaSelecionada() throws Exception{
        
        tbAutomoveis.setRowFactory(event -> {
            TableRow<VeiculoVO> myRow = new TableRow<>();
            myRow.setOnMouseClicked( new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    VeiculoVO veiculoSelecionado = myRow.getItem();
                    if (!(myRow.isEmpty())) {
                        try{
                            exibirCliente(veiculoSelecionado);
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


    
    private void exibirCliente(VeiculoVO veiculo) throws Exception {

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
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }
}