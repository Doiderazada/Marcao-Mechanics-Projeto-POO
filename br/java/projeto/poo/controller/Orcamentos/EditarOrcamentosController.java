package br.java.projeto.poo.controller.Orcamentos;

import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.exceptions.InvalidQuantidadeException;
import br.java.projeto.poo.models.BO.OrcamentoBO;
import br.java.projeto.poo.models.BO.PecaBO;
import br.java.projeto.poo.models.BO.ServicoBO;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.OrcamentoVO;
import br.java.projeto.poo.models.VO.PecaVo;
import br.java.projeto.poo.models.VO.ServicoVO;
import br.java.projeto.poo.src.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class EditarOrcamentosController extends BaseController {
    PecaBO pecaBo = new PecaBO();
    VeiculoBO veiculoBO = new VeiculoBO();
    ServicoBO servicoBO = new ServicoBO();
    OrcamentoBO orcamentoBO = new OrcamentoBO();
    double valor = 0; String cpfCliente;
    ModalsController modalsController = new ModalsController();
    OrcamentoVO orcamentoEditado = new OrcamentoVO();
    

    @FXML private Button editarOrcamento;
    @FXML private Button voltaTelaInicial;
    @FXML private Label dadosCliente;
    @FXML private Label dadosVeiculo;
    @FXML private Label msgErroPecas;
    @FXML private Label msgErroServicos;
    @FXML private Label valorOrcamento;
    @FXML private ListView<PecaVo> pecasBuscadas;
    @FXML private ListView<ServicoVO> servicosBuscados;
    @FXML private TableColumn<PecaVo, String> acaoPeca;
    @FXML private TableColumn<PecaVo, String> nomePeca;
    @FXML private TableColumn<PecaVo, Double> valorPeca;
    @FXML private TableColumn<PecaVo, Integer> quantidade;
    @FXML private TableColumn<ServicoVO, String> acaoServico;
    @FXML private TableColumn<ServicoVO, String> servicoNome;
    @FXML private TableColumn<ServicoVO, Double> servicoValor;
    @FXML private TableView<PecaVo> tbPecas;
    @FXML private TableView<ServicoVO> tbServicos;
    @FXML private TextField campoBuscaPeca;
    @FXML private TextField campoBuscaServ;


    ObservableList<PecaVo> itensPecas = FXCollections.observableArrayList();
    ObservableList<PecaVo> pecasEscolhidas = FXCollections.observableArrayList();
    ObservableList<ServicoVO> itensServicos = FXCollections.observableArrayList();
    ObservableList<ServicoVO> servicosEscolhidos = FXCollections.observableArrayList();

    @FXML
    public void initialize(OrcamentoVO orcamento) throws Exception {
        super.initialize();
        acaoDosBotoes();
        orcamentoEditado = orcamento;
        this.setDados(orcamento);
        tbServicos.setItems(servicosEscolhidos);
        tbPecas.setItems(pecasEscolhidas);
        pecasBuscadas.setVisible(false);
        servicosBuscados.setVisible(false);
        servicosBuscados.setItems(itensServicos);
        pecasBuscadas.setItems(itensPecas);
        inicializarTabelas();
    }




    /**
     * <p> Sets the action of all elements on the corresponding screen.
     * 
     * <p> This method has no parameters.
     */
    private void acaoDosBotoes(){
        editarOrcamento.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                if (!tbPecas.getItems().isEmpty() || !tbServicos.getItems().isEmpty()){
                    editarOrcamento();
                } else editarOrcamento();
            }
            
        });
        
        voltaTelaInicial.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                voltaTelaInicial();
            }
            
        });

        campoBuscaPeca.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarPecasPorNome();
            }
            
        });

        campoBuscaServ.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarServicosPorNome();
            }
            
        });

        pecasBuscadas.setOnMouseClicked(event -> {
            this.atualizarValoresPecas();
        });

        servicosBuscados.setOnMouseClicked(event -> {
            this.atualizarValoresServicos();
        });
    }




    
    /**
     * <p> Updates the current {@code orcamento}.
     * 
     * <p> This method has no parameters.  
     */
    void editarOrcamento() {
        try {
            if (App.usuarioLogado == null) {
                throw new Exception("Você não tem permissão para fazer essa ação");
            }
            
            ArrayList<PecaVo> pecas = new ArrayList<>(pecasEscolhidas);
            ArrayList<ServicoVO> servicos = new ArrayList<>(servicosEscolhidos);
            orcamentoEditado.setPecas(pecas);
            orcamentoEditado.setServicos(servicos);
            orcamentoEditado.setValor(valor);
            orcamentoBO.atualizar(orcamentoEditado);
            modalsController.abrirModalSucesso("Orcamento editado com sucesso.");
            App.navegarEntreTelas("orcamentos");
        } catch (Exception e) {
            e.printStackTrace();
            modalsController.abrirModalFalha("Erro ao editar: " + e.getMessage());
            App.navegarEntreTelas("login");
        }
    }




    
    /**
     * <p> Searches the database for the {@code peca} with the given name on the TextField 
     * {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#campoBuscaPeca campoBuscaPeca}.
     * 
     * <p> This method has no parameters.
     */
    private void buscarPecasPorNome() {
        try {
            msgErroPecas.setVisible(false);
            if (campoBuscaPeca.getText().length() > 0) {
                PecaVo vo = new PecaVo();
                vo.setNome(campoBuscaPeca.getText());
                itensPecas.setAll(pecaBo.buscarPorNome(vo));
                pecasBuscadas.setVisible(true);
            } else {
                pecasBuscadas.setVisible(false);
                itensPecas.removeAll();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p> Updates the items on the TableView {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#tbPecas tbPecas}.
     * 
     * <p> The values in the table is taken from the selected item in the ListView 
     * {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#pecasBuscadas pecasBuscadas}.
     * 
     * <p> This method has no parameters.
     */
    void atualizarValoresPecas() {
        try {
            PecaVo peca = pecasBuscadas.getSelectionModel().getSelectedItem();
            int indice = pecasEscolhidas.indexOf(peca);
            
            if(peca.getQuantidade() == 0) {
                throw new InvalidQuantidadeException("Sem estoque para essa peça");
            }

            if (!pecasEscolhidas.contains(peca)) {
                pecasEscolhidas.add(new PecaVo(peca.getId(), peca.getNome(), peca.getFabricante(), peca.getValor(), 1));
                editarOrcamento.setDisable(false);
            } else {
                PecaVo pecaAdicionada = pecasEscolhidas.get(indice);
                if (peca.getQuantidade() == pecaAdicionada.getQuantidade()) {
                    throw new InvalidQuantidadeException("Sem estoque para essa peça");
                }
                pecaAdicionada.setQuantidade(pecaAdicionada.getQuantidade() + 1);
                pecasEscolhidas.set(indice, pecaAdicionada);
            }
            this.valor += peca.getValor();
            valorOrcamento.setText(String.valueOf(valor));
        } catch (Exception e) {
            msgErroPecas.setText(e.getMessage());
            msgErroPecas.setVisible(true);
        }
    }


    /**
     * <p> Sets the buttons on the table {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#tbPecas tbPecas}.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarBotoesDeAcaoPeca () {
        acaoPeca.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnDelete);

            {
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setOnAction(event -> {
                    try {
                        PecaVo peca = getTableView().getItems().get(getIndex());
                        if (peca.getQuantidade() > 1) {
                            peca.setQuantidade(peca.getQuantidade() - 1);
                            pecasEscolhidas.set(getIndex(), peca);
                        } else {
                            pecasEscolhidas.remove(getIndex());
                            if (pecasEscolhidas.isEmpty() && servicosEscolhidos.isEmpty()) {
                                editarOrcamento.setDisable(true);
                            }
                        }
                        valor -= peca.getValor();
                        valorOrcamento.setText(String.valueOf(valor));
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
                    btnContainer.setStyle("-fx-padding: 0 20 0 20;");
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                }
            }
        });
    }

    

    /**
     * <p> Searches the database for the {@code servico} with the given name on the TextField 
     * {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#campoBuscaServ campoBuscaServ}.
     * 
     * <p> This method has no parameters.
     */
    void buscarServicosPorNome() {
        try {
            msgErroServicos.setVisible(false);
            if (campoBuscaServ.getText().length() > 0) {
                ServicoVO vo = new ServicoVO();
                vo.setNome(campoBuscaServ.getText());
                itensServicos.setAll(servicoBO.buscarPorNome(vo));
                servicosBuscados.setVisible(true);
            } else {
                servicosBuscados.setVisible(false);
                itensServicos.removeAll();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * <p> Updates the items on the TableView {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#tbServicos tbServicos}.
     * 
     * <p> The values in the table is taken from the selected item in the ListView 
     * {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#servicosBuscados servicosBuscadas}.
     * 
     * <p> This method has no parameters.
     */
    void atualizarValoresServicos() {
        try {
            ServicoVO servico = servicosBuscados.getSelectionModel().getSelectedItem();
        
            if (!servicosEscolhidos.contains(servico)) {
                servicosEscolhidos.add(servico);
                editarOrcamento.setDisable(false);
                this.valor += servico.getValor();
                valorOrcamento.setText(String.valueOf(valor));
            } else {
                throw new InvalidQuantidadeException("Esse serviço ja foi adicionado");
            }
        } catch (Exception e) {
            msgErroServicos.setText(e.getMessage());
            msgErroServicos.setVisible(true);
        }
    }


    /**
     * <p> Sets the buttons on the table {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#tbServicos tbServicos}.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarBotoesDeAcaoServico() {
        acaoServico.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnDelete);

            {
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setOnAction(event -> {
                    try {
                        ServicoVO servicoVo = getTableView().getItems().get(getIndex());
                        servicosEscolhidos.remove(getIndex());
                        if (servicosEscolhidos.isEmpty() && pecasEscolhidas.isEmpty()) {
                            editarOrcamento.setDisable(true);
                        }
                        valor -= servicoVo.getValor();
                        valorOrcamento.setText(String.valueOf(valor));
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
                    btnContainer.setStyle("-fx-padding: 0 20 0 20;");
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                }
            }
        });
    }


    
    /**
     * <p> Initializes the tables {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#tbPecas tbPecas} and 
     * {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#tbServicos tbServicos}, then calls the methods 
     * {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#inicializarBotoesDeAcaoPeca() inicializarBotoesDeAcaoPeca} and
     * {@link br.java.projeto.poo.controller.Orcamentos.EditarOrcamentosController#inicializarBotoesDeAcaoServico() inicializarBotoesDeAcaoServ}
     * that set the buttons on its corresponding table.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarTabelas(){
        nomePeca.setCellValueFactory(new PropertyValueFactory<PecaVo, String>("nome"));
        valorPeca.setCellValueFactory(new PropertyValueFactory<PecaVo, Double>("valor"));
        quantidade.setCellValueFactory(new PropertyValueFactory<PecaVo, Integer>("quantidade"));
        servicoNome.setCellValueFactory(new PropertyValueFactory<ServicoVO, String>("nome"));
        servicoValor.setCellValueFactory(new PropertyValueFactory<ServicoVO, Double>("valor"));
        this.inicializarBotoesDeAcaoPeca();
        this.inicializarBotoesDeAcaoServico();
    }

    

    /**
     * <p> Calls the Method {@link br.java.projeto.poo.src.App#navegarEntreTelas(String) navegarEntreTelas} to switch from the actual active screen 
     * to the screen {@code orcamento}.
     * 
     * <p> This method has no parameters. 
     */
    public void voltaTelaInicial() {
        App.navegarEntreTelas("orcamentos");
    }



    /**
     * <p> Sets the data on the screen with the given {@code orcamento}. 
     * 
     * @param orcamento the data to be set on the screen.
     */
    private void setDados(OrcamentoVO orcamento) {
        try {
            orcamento = orcamentoBO.buscarPorId(orcamento);
            pecasEscolhidas.setAll(orcamento.getPecas());
            servicosEscolhidos.setAll(orcamento.getServicos());
            this.valor = orcamento.getValor();
            valorOrcamento.setText(String.valueOf(valor));
            dadosCliente.setText(orcamento.getCpfCliente());
            dadosVeiculo.setText(orcamento.getPlacaVeiculo());;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
