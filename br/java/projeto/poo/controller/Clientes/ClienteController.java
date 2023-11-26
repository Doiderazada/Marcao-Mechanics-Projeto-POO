package br.java.projeto.poo.controller.Clientes;

import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ClienteBO;
import br.java.projeto.poo.models.VO.ClienteVO;
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


public class ClienteController extends BaseController{


    @FXML protected Button novoCliente;
    @FXML private Label mensagemErroBusca;
    @FXML private TextField campoBusca;
    @FXML protected TableView<ClienteVO> tabelaClientes;
    @FXML private TableColumn<ClienteVO, String>  columnBut;
    @FXML private TableColumn<ClienteVO, String>  columnCPF;
    @FXML private TableColumn<ClienteVO, String>  columnEnd;
    @FXML private TableColumn<ClienteVO, Integer> columnIdCliente;
    @FXML private TableColumn<ClienteVO, String>  columnNome;
    @FXML private TableColumn<ClienteVO, String>  columnTel;
    
    
    private ClienteBO clienteBO = new ClienteBO();
    private ModalsController modalsController = new ModalsController();
    private ArrayList<ClienteVO> listaClientes = new ArrayList<ClienteVO>();
    private ObservableList<ClienteVO> clientesDisponiveis;



    @Override
    public void initialize() {
        try {
            super.initialize();;
            acaoCompTela();
            listaClientes = this.clienteBO.listar();
            clientesDisponiveis = FXCollections.observableArrayList(listaClientes);
            mensagemErroBusca.setVisible(false);
            inicializarTabela();
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
        novoCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                abrirCadastro();
            }
            
        });
        campoBusca.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarCliente();
            }
            
        });
    }

    

    /**
     * <p> Opens up a popup screen for creating a new {@code cliente}.
     * 
     * <p> This method has no parameters.
     */
    private void abrirCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Clientes/CadastrarCliente.fxml"));
            Parent root = loader.load();
            Scene janelaCad = new Scene(root);
            Stage palco = new Stage();
            palco.setResizable(false);
            palco.setScene(janelaCad);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.initStyle(StageStyle.UNDECORATED);
            Window wNC = novoCliente.getScene().getWindow();
            palco.setX((wNC.getX() + wNC.getWidth()/2) - 250);
            palco.setY((wNC.getY() + wNC.getHeight()/2) - 325);
            palco.showAndWait();

            listaClientes = this.clienteBO.listar();
            clientesDisponiveis.setAll(listaClientes);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }



    /**
     * <p> Opens up a popup screen to edit the current {@code cliente}.
     * 
     * @param cliente to be edited.
     */
    private void abrirEdicao(ClienteVO cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Clientes/EditarCliente.fxml"));
            Parent root = loader.load();

            ClienteEditController controller = loader.getController();
            controller.initialize(cliente);

            Scene janelaEdit = new Scene(root);
            Stage palco = new Stage();
            palco.setResizable(false);
            palco.setScene(janelaEdit);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.initStyle(StageStyle.UNDECORATED);
            Window wNC = novoCliente.getScene().getWindow();
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wNC.getX() + wNC.getWidth()/2) - 250;
            centralizarEixoY = (wNC.getY() + wNC.getHeight()/2) - 225;
            palco.setX(centralizarEixoX);
            palco.setY(centralizarEixoY);
            palco.showAndWait();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }



    /**
     * <p> Opens up an exclusion warning popup screen.
     * @param cliente to be excluded.
     * @param index of the {@code cliente} on its table.
     */
    private void abrirExclusao(ClienteVO cliente, int index) {
        if(modalsController.abrirModalExcluir("Tem certeza que deseja excluir esse cliente?")){
            realizarExclusao(cliente, index);
        }
    }



    /**
     * <p> Searches an {@code cliente} on the database by the given name in the TextField {@link br.java.projeto.poo.controller.Clientes.ClienteController#campoBusca campoBusca}.
     * 
     * <p> This method has no parameters.
     */
    private void buscarCliente(){
        try {
            ArrayList<ClienteVO> clienteVOs;
            if (this.campoBusca.getText().length() > 2) {
                mensagemErroBusca.setVisible(false);
                if (this.campoBusca.getText().matches("^\\d{3}.*")) {
                    clienteVOs = clienteBO.buscarPorCPF(this.campoBusca.getText());
                    clientesDisponiveis.setAll(clienteVOs);
                    if (clienteVOs.isEmpty()) {
                        mensagemErroBusca.setVisible(true);
                    }
                } else {
                    clienteVOs = clienteBO.buscarPorNome(this.campoBusca.getText());
                    clientesDisponiveis.setAll(clienteVOs);
                    if (clienteVOs.isEmpty()) {
                        mensagemErroBusca.setVisible(true);
                    }
                }
            } else {
               clientesDisponiveis.setAll(listaClientes);
               mensagemErroBusca.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }


    
    /**
     * <p> Sets the action for a clicked row on the main table.
     * 
     * <p> This method has no parameters.
     */
    private void linhaSelecionada() {
        
        tabelaClientes.setRowFactory(event -> {
            TableRow<ClienteVO> myRow = new TableRow<>();
            myRow.setOnMouseClicked( new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    ClienteVO clienteSelecionado = myRow.getItem();
                    if (!(myRow.isEmpty())) {
                        exibirCliente(clienteSelecionado);
                    } 
                }
                
            });
            return myRow;
        });
    }



    /**
     * <p> Opens up the user information screen.
     * 
     * @param cliente wich data will be shown.
     */
    private void exibirCliente(ClienteVO cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Clientes/ExibirCliente.fxml"));
            Parent root = loader.load();

            ClienteShowController controller = loader.getController();
            controller.initialize(cliente);
            
            Stage palco = new Stage();
            Scene cena = new Scene(root);
            palco  = (Stage)novoCliente.getScene().getWindow();
            palco.setScene(cena);
            palco.show();

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    } 



    /**
     * <p> Sets the main table and then calls the method {@link br.java.projeto.poo.controller.Clientes.ClienteController#inicializarBotoesDeAcao() inicializarBotoesDeAcao} 
     * that sets the buttons on it.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarTabela(){
        columnNome.setCellValueFactory(new PropertyValueFactory<ClienteVO, String>("nome"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<ClienteVO, String>("cpf"));
        columnEnd.setCellValueFactory(new PropertyValueFactory<ClienteVO, String>("endereco"));
        columnTel.setCellValueFactory(new PropertyValueFactory<ClienteVO, String>("telefone"));
        tabelaClientes.setItems(clientesDisponiveis);
        linhaSelecionada();
        inicializarBotoesDeAcao();
    }



    /**
     * <p> Sets the buttons on its corresponding table.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarBotoesDeAcao() {
        columnBut.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnEdit.setPrefSize(25, 25);;
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setPrefSize(25, 25);
                
                btnEdit.setOnAction(event -> {
                    
                    ClienteVO cliente = getTableView().getItems().get(getIndex());
                    abrirEdicao(cliente);
                    tabelaClientes.refresh();
                    
                });

                btnDelete.setOnAction(event -> {
                    
                    ClienteVO cliente = getTableView().getItems().get(getIndex());
                    abrirExclusao(cliente, getIndex());
                    
                });
            }

            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnContainer.setSpacing(10);
                    btnContainer.setAlignment(Pos.CENTER);
                    setGraphic(btnContainer);
                }
            }
        });
    }



    /**
     * <p> Excludes the {@code cliente} from the database.
     * @param cliente to be excluded.
     * @param index of the {@code cliente} on tha table.
     */
    private void realizarExclusao(ClienteVO cliente, int index) {
        ClienteBO clienteExcluido = new ClienteBO();
        if(!clienteExcluido.deletar(cliente)){
            clientesDisponiveis.remove(index);
        }
    }
}