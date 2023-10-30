package br.java.projeto.poo.controller.Pecas;

import java.sql.SQLException;
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
    
    PecaBO pecaBO = new PecaBO();
    static ArrayList<PecaVo> listaPecas;
    static ObservableList<PecaVo> pecasDisponiveis;

    
    @FXML private Button novaPeca;
    @FXML private Label msgErroBusca;
    @FXML private TextField buscarPeca;
    @FXML private TableColumn<PecaVo, String> columnBut;
    @FXML private TableColumn<PecaVo, String> columnFab;
    @FXML private TableColumn<PecaVo, String> columnNome;
    @FXML private TableColumn<PecaVo, Integer> columnQuant;
    @FXML private TableColumn<PecaVo, Double> columnVal;
    @FXML private TableView<PecaVo> tabelaPecas;





    @Override
    public void initialize() throws Exception {
        super.initialize();
        msgErroBusca.setVisible(false);
        listaPecas = this.pecaBO.listar();
        pecasDisponiveis = FXCollections.observableArrayList(listaPecas);
        this.inicializarTabela();
        acaoDosBotoes();
    }






    
    void abrirCadastro() throws Exception {
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
    }

    
    void abrirEdicao(PecaVo peca, int index) throws Exception{
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
    }



    void abrirExclusao(PecaVo peca, int index) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/Modals/ModalExcluir.fxml"));
        Parent root = loader.load();
        ModalsController modalExc = loader.getController();

        String mensagem = "Tem certeza que deseja excluir essa peça?";

        modalExc.ExibirMensagemExcluir(mensagem);

        Scene janelaEdit = new Scene(root);
        Stage palco = new Stage();
        palco.setResizable(false);
        palco.setScene(janelaEdit);
        palco.initModality(Modality.APPLICATION_MODAL);
        palco.initStyle(StageStyle.UNDECORATED);
        Window wNC = novaPeca.getScene().getWindow();
        double centralizarEixoX, centralizarEixoY;
        centralizarEixoX = (wNC.getX() + wNC.getWidth()/2) - 225;
        centralizarEixoY = (wNC.getY() + wNC.getHeight()/2) - 150;
        palco.setX(centralizarEixoX);
        palco.setY(centralizarEixoY);
        palco.showAndWait();

        if(modalExc.getExclusaoValid()){
            realizarExclusao(peca, index);
        }
    }








    private void acaoDosBotoes(){
        novaPeca.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                try {abrirCadastro();} 
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    ModalsController modalsController = new ModalsController();
                    modalsController.abrirModalFalha(e.getMessage());
                }
            }
            
        });
        buscarPeca.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                try {
                    buscarPeca(arg0);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    ModalsController modalsController = new ModalsController();
                    modalsController.abrirModalFalha(e.getMessage());
                }
            }
            
        });
    }






    

    
    void buscarPeca(KeyEvent event) {
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
        }
    }









    private void inicializarTabela() throws SQLException {
        columnNome.setCellValueFactory(new PropertyValueFactory<PecaVo, String>("nome"));
        columnFab.setCellValueFactory(new PropertyValueFactory<PecaVo, String>("fabricante"));
        columnQuant.setCellValueFactory(new PropertyValueFactory<PecaVo, Integer>("quantidade"));
        columnVal.setCellValueFactory(new PropertyValueFactory<PecaVo, Double>("valor"));
        tabelaPecas.setItems(pecasDisponiveis);
        this.inicializarBotoesDeAcao(pecasDisponiveis);
    }

    private void inicializarBotoesDeAcao (ObservableList<PecaVo> funcs) {
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
                        abrirEdicao(peca, getIndex());
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




    
    private void realizarExclusao(PecaVo peca, int index) throws Exception {
        PecaBO pecaExcluida = new PecaBO();
            if(!pecaExcluida.deletar(peca)){
                pecasDisponiveis.remove(index);
            }
    }
  
}
