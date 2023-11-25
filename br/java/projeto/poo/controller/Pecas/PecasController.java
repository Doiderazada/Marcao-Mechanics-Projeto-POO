package br.java.projeto.poo.controller.Pecas;

import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.PecaBO;
import br.java.projeto.poo.models.VO.PecaVo;
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

public class PecasController extends BaseController {
    
    
    @FXML private Button novaPeca;
    @FXML private Label msgErroBusca;
    @FXML private TextField buscarPeca;
    @FXML private TableColumn<PecaVo, String> columnBut;
    @FXML private TableColumn<PecaVo, String> columnFab;
    @FXML private TableColumn<PecaVo, String> columnNome;
    @FXML private TableColumn<PecaVo, Integer> columnQuant;
    @FXML private TableColumn<PecaVo, Double> columnVal;
    @FXML private TableView<PecaVo> tabelaPecas;


    private PecaBO pecaBO = new PecaBO();
    private ModalsController modalsController = new ModalsController();
    private ArrayList<PecaVo> listaPecas;
    private ObservableList<PecaVo> pecasDisponiveis;



    @Override
    public void initialize() throws Exception {
        super.initialize();
        acaoDosBotoes();
        msgErroBusca.setVisible(false);
        listaPecas = this.pecaBO.listar();
        pecasDisponiveis = FXCollections.observableArrayList(listaPecas);
        inicializarTabela();
        
    }






    
    private void abrirCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Pecas/CadastrarPeca.fxml"));
            Parent root = loader.load();
            Scene janelaCad = new Scene(root);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.setScene(janelaCad);
            Window wNP = novaPeca.getScene().getWindow();
            double centralizarEixoX = (wNP.getX() + wNP.getWidth()/2) - 200;
            double centralizarEixoY = (wNP.getY() + wNP.getHeight()/2) - 200;
            palco.setX(centralizarEixoX);
            palco.setY(centralizarEixoY);
            palco.showAndWait();


            listaPecas = pecaBO.listar();
            pecasDisponiveis.setAll(listaPecas);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }

    
    private void abrirEdicao(PecaVo peca) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Pecas/EditarPeca.fxml"));
            Parent root = loader.load();

            PecasEditController controller = loader.getController();
            controller.initialize(peca);

            Scene janelaCad = new Scene(root);
            Stage palco = new Stage(StageStyle.UNDECORATED);
            palco.initModality(Modality.APPLICATION_MODAL);
            palco.setScene(janelaCad);
            Window wNP = novaPeca.getScene().getWindow();
            double centralizarEixoX = (wNP.getX() + wNP.getWidth()/2) - 200;
            double centralizarEixoY = (wNP.getY() + wNP.getHeight()/2) - 200;
            palco.setX(centralizarEixoX);
            palco.setY(centralizarEixoY);
            palco.showAndWait();

            tabelaPecas.refresh();

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
    }



    private void abrirExclusao(PecaVo peca, int index) {
        if(modalsController.abrirModalExcluir("Tem certeza que deseja excluir essa peça?", index)){
            realizarExclusao(peca, index);
        }
    }








    private void acaoDosBotoes(){
        novaPeca.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                abrirCadastro(); 
            }
            
        });
        buscarPeca.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarPeca(arg0);
            }
            
        });
    }






    

    
    private void buscarPeca(KeyEvent event) {
        try {
            ArrayList<PecaVo> pecasVO;
            if (this.buscarPeca.getText().length() > 2) {
                PecaVo peca = new PecaVo(1, buscarPeca.getText(), buscarPeca.getText(), 0, 0);
                pecasVO = pecaBO.buscarPorNome(peca);
                msgErroBusca.setVisible(false);
                if(pecasVO.isEmpty()){
                    pecasVO = pecaBO.buscarPorFabricante(peca);
                    pecasDisponiveis.setAll(pecasVO);
                    if (pecasVO.isEmpty()) {
                        msgErroBusca.setVisible(true);
                    }
                }
                pecasDisponiveis.setAll(pecasVO);
                
            } else {
               pecasDisponiveis.setAll(listaPecas);
               msgErroBusca.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }









    private void inicializarTabela() {
        columnNome.setCellValueFactory(new PropertyValueFactory<PecaVo, String>("nome"));
        columnFab.setCellValueFactory(new PropertyValueFactory<PecaVo, String>("fabricante"));
        columnQuant.setCellValueFactory(new PropertyValueFactory<PecaVo, Integer>("quantidade"));
        columnVal.setCellValueFactory(new PropertyValueFactory<PecaVo, Double>("valor"));
        tabelaPecas.setItems(pecasDisponiveis);
        inicializarBotoesDeAcao();
    }

    private void inicializarBotoesDeAcao() {
        columnBut.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnEdit, btnDelete);

            {
                btnEdit.getStyleClass().add("btn-edit");
                btnDelete.getStyleClass().add("btn-delete");
                btnEdit.setOnAction(event -> {
                    try {
                        PecaVo peca = getTableView().getItems().get(getIndex());
                        abrirEdicao(peca);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                btnDelete.setOnAction(event -> {
                    try{
                        PecaVo peca = getTableView().getItems().get(getIndex());
                        abrirExclusao(peca, getIndex());
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
                    setGraphic(btnContainer);
                    btnContainer.setAlignment(Pos.CENTER);
                }
            }
        });
    }




    
    private void realizarExclusao(PecaVo peca, int index) {
        PecaBO pecaExcluida = new PecaBO();
            if(!pecaExcluida.deletar(peca)){
                pecasDisponiveis.remove(index);
            }
    }
  
}
