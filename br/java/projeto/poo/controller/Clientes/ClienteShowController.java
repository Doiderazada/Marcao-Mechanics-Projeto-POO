package br.java.projeto.poo.controller.Clientes;


import java.sql.SQLException;
import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.controller.Automoveis.AdicionarAutomovelController;
import br.java.projeto.poo.controller.Automoveis.EditarAutomoveisController;
import br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.ClienteVO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import br.java.projeto.poo.src.App;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ClienteShowController extends BaseController{
    
    ClienteVO clienteExibido = new ClienteVO();
    VeiculoBO veiculoBO = new VeiculoBO();
    static ObservableList<VeiculoVO> veiculosDoCliente;
    static ArrayList<VeiculoVO> listaVeiculos;

    @FXML private Button editarCliente; 
    @FXML private Button novoVeic;
    @FXML private Button telaInicial;
    @FXML private Label exibirCPF;
    @FXML private Label exibirEndereco; 
    @FXML private Label exibirNome;
    @FXML private Label exibirTelefone;
    @FXML private Label nomeClienteMenu;
    @FXML private TableColumn<VeiculoVO, String> columnAnoV;
    @FXML private TableColumn<VeiculoVO, String> columnButA;
    @FXML private TableColumn<VeiculoVO, String> columnCorV;
    @FXML private TableColumn<VeiculoVO, Double> columnKMV;
    @FXML private TableColumn<VeiculoVO, String> columnModeloV;
    @FXML private TableColumn<VeiculoVO, String> columnPlacaV;
    @FXML private TableColumn<VeiculoVO, String> columnTipoV;
    @FXML private TableView<VeiculoVO> tabelaAuto;
    




    
    public void initialize(ClienteVO cliente){
        try{
            clienteExibido = new ClienteVO();
            clienteExibido = cliente;
            listaVeiculos = veiculoBO.buscarPorDono(clienteExibido.getCpf());
            veiculosDoCliente = FXCollections.observableArrayList(listaVeiculos);
            this.preencherCampos(clienteExibido);
            this.initTable();
            acaoDosBotoes();

        }catch(Exception e){
            System.out.println(e.getMessage());
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }


    
    
    private void abrirCadVeic() {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Automoveis/AdicionarAutomovel.fxml"));
            Parent root = loader.load();

            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);

            AdicionarAutomovelController controller = loader.getController();
            controller.initialize(clienteExibido);

            Window wNV = novoVeic.getScene().getWindow();
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 250;
            centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 225;
            modalStage.setX(centralizarEixoX);
            modalStage.setY(centralizarEixoY);
            
            modalStage.showAndWait();
            listaVeiculos = veiculoBO.buscarPorDono(clienteExibido.getCpf());
            veiculosDoCliente.setAll(listaVeiculos);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }



    
    private void abrirEditCli() {

        try {
            Stage palco = new Stage();
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.initStyle(StageStyle.UNDECORATED);
            Window wEC = editarCliente.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Clientes/EditarCliente.fxml"));
            Parent root = loader.load();

            ClienteEditController controller = loader.getController();
            controller.initialize(clienteExibido);

            Scene janelaEdit = new Scene(root);
            palco.setScene(janelaEdit);
            
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wEC.getX() + wEC.getWidth()/2) - 250;
            centralizarEixoY = (wEC.getY() + wEC.getHeight()/2) - 225;
            palco.setX(centralizarEixoX);
            palco.setY(centralizarEixoY);
            palco.showAndWait();

            clienteExibido = controller.pegarClienteEditado();
            preencherCampos(clienteExibido);


        }catch(Exception e){
            System.out.println(e.getMessage());
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }





    private void abrirEditVeic(VeiculoVO vo, int indice) throws Exception {
        try {
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Automoveis/EditarAutomoveis.fxml"));
            Parent root = loader.load();
            EditarAutomoveisController editarController = loader.getController();
            editarController.initialize(vo);
            
            Window wNV = novoVeic.getScene().getWindow();
            double centralizarEixoX, centralizarEixoY;
            centralizarEixoX = (wNV.getX() + wNV.getWidth()/2) - 260;
            centralizarEixoY = (wNV.getY() + wNV.getHeight()/2) - 230;
            modalStage.setX(centralizarEixoX);
            modalStage.setY(centralizarEixoY);
            
            Scene modalScene = new Scene(root);
            modalStage.setScene(modalScene);
            modalStage.showAndWait();

            listaVeiculos = veiculoBO.buscarPorDono(clienteExibido.getCpf());
            veiculosDoCliente.setAll(listaVeiculos);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
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
        Window wNV = novoVeic.getScene().getWindow();
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





    




    private void acaoDosBotoes() throws Exception {
        telaInicial.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                voltaTelaInicial();
            }

        });
        
        editarCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0){
                abrirEditCli();
            }
            
        });

        novoVeic.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                abrirCadVeic();
            }
            
        });
    }







    private void initTable() throws SQLException {
        columnModeloV.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("modelo"));
        columnPlacaV.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("placa"));
        columnTipoV.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("tipo"));
        columnCorV.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("cor"));
        columnKMV.setCellValueFactory(new PropertyValueFactory<VeiculoVO, Double>("km"));
        columnAnoV.setCellValueFactory(new PropertyValueFactory<VeiculoVO, String>("ano"));
        tabelaAuto.setItems(veiculosDoCliente);
        this.initActBut(veiculosDoCliente);
    }

    private void initActBut (ObservableList<VeiculoVO> funcs) {
        columnButA.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final Button btnOrc = new Button();
            private final HBox btnContainer = new HBox(btnOrc, btnEdit, btnDelete);

            {
                btnOrc.getStyleClass().add("btn-orc");
                btnOrc.setPrefSize(25,25);

                btnEdit.getStyleClass().add("btn-edit");
                btnEdit.setPrefSize(25,25);

                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setPrefSize(25, 25);

                
                btnOrc.setOnAction(event -> {
                    try {
                        VeiculoVO veiculo = getTableView().getItems().get(getIndex());
                        
                        novoOrcamento(veiculo.getPlaca());
                        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        
                    }
                });

                btnEdit.setOnAction(event -> {
                    try {
                        VeiculoVO veiculo = getTableView().getItems().get(getIndex());
                        abrirEditVeic(veiculo, getIndex());
                        tabelaAuto.refresh();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        tabelaAuto.refresh();
                    }
                });

                btnDelete.setOnAction(event -> {
                    try{
                        VeiculoVO veiculo = getTableView().getItems().get(getIndex());
                        abrirExclusao(veiculo, getIndex());
                        
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
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






    private void novoOrcamento(String placa){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Orcamentos/NovoOrcamento.fxml"));
            Parent root = loader.load();

            CriarOrcamentosController controller = loader.getController();
            controller.setBuscarVeic(placa);
            String text = "Pressione ENTER para confirmar o veículo";
            String style = "-fx-text-fill: black;-fx-font-weight: bold;";
            controller.setDadosCliente(text, style);
            
            Stage palco = new Stage();
            Scene cena = new Scene(root);
            palco  = (Stage)novoVeic.getScene().getWindow();
            palco.setScene(cena);
            palco.show();

        }catch(Exception e){
            System.out.println(e.getMessage());
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }





    private void preencherCampos(ClienteVO cliente){
        try{
            exibirNome.setText(cliente.getNome());
            exibirNome.setVisible(true);

            exibirCPF.setText(cliente.getCpf());
            exibirCPF.setVisible(true);

            exibirEndereco.setText(cliente.getEndereco().toString());
            exibirEndereco.setVisible(true);

            exibirTelefone.setText(cliente.getTelefone().toString());
            exibirTelefone.setVisible(true);

            int espaco = cliente.getNome().indexOf(" ");
            this.nomeClienteMenu.setText(cliente.getNome().substring(0, espaco));
        }catch(Exception e){
            System.out.println("Erro no preenchimento dos campos: \n" + e.getMessage() + "\n");
            ModalsController modalsController = new ModalsController();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }







    private void realizarExclusao(VeiculoVO veiculo, int index) throws Exception {
        VeiculoBO veiculoExcluido = new VeiculoBO();

        if(!veiculoExcluido.deletar(veiculo.getPlaca())){
            listaVeiculos.remove(index);
            veiculosDoCliente.setAll(listaVeiculos);
        }
    }







    
    private void voltaTelaInicial(){
        App.navegarEntreTelas("clientes");
    }


}