package br.java.projeto.poo.controller.Servicos;

import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.ServicoBO;
import br.java.projeto.poo.models.VO.ServicoVO;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ServicosController extends BaseController{

    
    @FXML private Button novoServico;
    @FXML private Label msgErroBusca;
    @FXML private TextField buscarServico;
    @FXML private TableView<ServicoVO> tabelaServicos;
    @FXML private TableColumn<ServicoVO, String> columnBut;
    @FXML private TableColumn<ServicoVO, String> columnServ;
    @FXML private TableColumn<ServicoVO, Double> columnVal;


    private ServicoBO servicoBO = new ServicoBO();
    private ModalsController modalsController = new ModalsController();
    private ArrayList<ServicoVO> listaServicos;
    private ObservableList<ServicoVO> servicosDisponiveis;



    
    public void initialize() {
        try {    
            super.initialize();
            acaoCompTela();
            listaServicos = this.servicoBO.listar();
            servicosDisponiveis = FXCollections.observableArrayList(listaServicos);
            inicializarTabela();
            msgErroBusca.setVisible(false);
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
    private void acaoCompTela() {
        novoServico.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                abrirCadastro();
            }
            
        });
        buscarServico.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarServico();
            }
            
        });
    }
    


    /**
     * <p> Opens up a popup screen for creating a new {@code servico}.
     * 
     * <p> This method has no parameters.
     */
    private void abrirCadastro() {
        try {    
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Servicos/CadastrarServico.fxml"));
            Parent root = loader.load();
            Scene janelaCad = new Scene(root);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.setScene(janelaCad);
            Window wNS = novoServico.getScene().getWindow();
            double centralizarEixoX = (wNS.getX() + wNS.getWidth()/2) - 200;
            double centralizarEixoY = (wNS.getY() + wNS.getHeight()/2) - 150;
            palco.setX(centralizarEixoX);
            palco.setY(centralizarEixoY);
            palco.showAndWait();

            listaServicos = servicoBO.listar();
            servicosDisponiveis.setAll(listaServicos);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }



    /**
     * <p> Opens up a popup screen to edit the current {@code servico}.
     * 
     * @param servico to be edited.
     */
    private void abrirEdicao(ServicoVO servico) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Servicos/EditarServico.fxml"));
            Parent root = loader.load();

            ServicosEditController controller = loader.getController();
            controller.initialize(servico);

            Scene janelaCad = new Scene(root);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.setScene(janelaCad);
            Window wNS = novoServico.getScene().getWindow();
            double centralizarEixoX = (wNS.getX() + wNS.getWidth()/2) - 200;
            double centralizarEixoY = (wNS.getY() + wNS.getHeight()/2) - 150;
            palco.setX(centralizarEixoX);
            palco.setY(centralizarEixoY);
            palco.showAndWait();

            listaServicos = servicoBO.listar();
            servicosDisponiveis.setAll(listaServicos);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }



    /**
     * <p> Opens up an exclusion warning popup screen.
     * @param servico to be excluded.
     * @param index of the {@code servico} on its table.
     */
    private void abrirExclusao(ServicoVO servico, int index) {
        if(modalsController.abrirModalExcluir("Tem certeza que deseja exluir esse serviço?")){
            realizarExclusao(servico, index);
        }
    }



    /**
     * <p> Searches an {@code servico} on the database by the given name in the TextField {@link br.java.projeto.poo.controller.Servicos.ServicosController#buscarServico buscaServico}.
     * 
     * <p> This method has no parameters.
     */
    private void buscarServico(){
        try {
            ArrayList<ServicoVO> servicos;
            if (this.buscarServico.getText().length() > 2) {
                ServicoVO vo = new ServicoVO(1, buscarServico.getText(), 0);
                servicos = servicoBO.buscarPorNome(vo);
                if (servicos.isEmpty()) {
                    msgErroBusca.setVisible(true);
                    servicosDisponiveis.setAll(servicos);
                }else {
                    servicosDisponiveis.setAll(servicos);
                    msgErroBusca.setVisible(false);
                }
                
            } else {
               servicosDisponiveis.setAll(listaServicos);
               msgErroBusca.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * <p> Sets the main table and then calls the method {@link br.java.projeto.poo.controller.Servicos.ServicosController#inicializarBotoesDeAcao() inicializarBotoesDeAcao} 
     * that sets the buttons on it.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarTabela() {
        columnServ.setCellValueFactory(new PropertyValueFactory<ServicoVO, String>("nome"));
        columnVal.setCellValueFactory(new PropertyValueFactory<ServicoVO, Double>("valor"));
        tabelaServicos.setItems(servicosDisponiveis);
        inicializarBotoesDeAcao();
    }



    /**
     * <p> Sets the buttons on its corresponding table.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarBotoesDeAcao () {
        columnBut.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnEdit.setPrefSize(25, 25);
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setPrefSize(25, 25);
                
                btnEdit.setOnAction(event -> {
                    try {
                        ServicoVO servico = getTableView().getItems().get(getIndex());
                        abrirEdicao(servico);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                btnDelete.setOnAction(event -> {
                    try{
                        ServicoVO servico = getTableView().getItems().get(getIndex());
                        abrirExclusao(servico, getIndex());
                        
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



    /**
     * <p> Excludes the {@code servico} from the database.
     * @param servico to be excluded.
     * @param index of the {@code servico} on tha table.
     */
    private void realizarExclusao(ServicoVO servico, int index) {
        ServicoBO servicoExcluido = new ServicoBO();
            if(!servicoExcluido.deletar(servico)){
                listaServicos.remove(index);
                servicosDisponiveis.remove(index);
            }
    }

}