package br.java.projeto.poo.controller.Orcamentos;

import java.util.ArrayList;

import br.java.projeto.poo.controller.BaseController;
import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.exceptions.InvalidQuantidadeException;
import br.java.projeto.poo.exceptions.UsuarioNaoEncontradoException;
import br.java.projeto.poo.models.BO.OrcamentoBO;
import br.java.projeto.poo.models.BO.PecaBO;
import br.java.projeto.poo.models.BO.ServicoBO;
import br.java.projeto.poo.models.BO.VeiculoBO;
import br.java.projeto.poo.models.VO.OrcamentoVO;
import br.java.projeto.poo.models.VO.PecaVo;
import br.java.projeto.poo.models.VO.ServicoVO;
import br.java.projeto.poo.models.VO.VeiculoVO;
import br.java.projeto.poo.src.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

public class CriarOrcamentosController extends BaseController{
    PecaBO pecaBo = new PecaBO();
    VeiculoBO veiculoBO = new VeiculoBO();
    ServicoBO servicoBO = new ServicoBO();
    OrcamentoBO orcamentoBO = new OrcamentoBO();
    boolean veicValid = false;
    double valor = 0; 
    String cpfCliente;
    ModalsController modalsController = new ModalsController();
    
    
    @FXML private Button voltaTelaInicial;
    @FXML private Button salvarNovoOrcamento;
    @FXML private Label dadosCliente;
    @FXML private Label msgErroPecas;
    @FXML private Label msgErroServicos;
    @FXML private Label valorOrcamento;
    @FXML private ListView<PecaVo> pecasBuscadas;
    @FXML private ListView<ServicoVO> servicosBuscados;
    @FXML private TextField buscarVeiculo;
    @FXML private TextField campoBuscaPeca;
    @FXML private TextField campoBuscaServ;
    @FXML private TableColumn<PecaVo, String> acaoPeca;
    @FXML private TableColumn<PecaVo, String> nomePeca;
    @FXML private TableColumn<PecaVo, Double> valorPeca;
    @FXML private TableColumn<PecaVo, Integer> quantidade;
    @FXML private TableColumn<ServicoVO, String> acaoServico;
    @FXML private TableColumn<ServicoVO, String> servicoNome;
    @FXML private TableColumn<ServicoVO, Double> servicoValor;
    @FXML private TableView<PecaVo> tbPecas;
    @FXML private TableView<ServicoVO> tbServicos;


    ObservableList<PecaVo> itensPecas = FXCollections.observableArrayList();
    ObservableList<PecaVo> pecasEscolhidas = FXCollections.observableArrayList();
    ObservableList<ServicoVO> itensServicos = FXCollections.observableArrayList();
    ObservableList<ServicoVO> servicosEscolhidos = FXCollections.observableArrayList();





    @FXML
    public void initialize() throws Exception {
        super.initialize();
        acaoDosBotoes();
        dadosCliente.setVisible(false);
        tbServicos.setItems(servicosEscolhidos);
        tbPecas.setItems(pecasEscolhidas);
        pecasBuscadas.setVisible(false);
        servicosBuscados.setVisible(false);
        salvarNovoOrcamento.setDisable(true);

        servicosBuscados.setItems(itensServicos);
        pecasBuscadas.setItems(itensPecas);

        inicializarTabelas();
    }



    /**
     * <p> Sets the action from all elements on its corresponding screen.
     * 
     * <p> This method has no parameters.
     */
    private void acaoDosBotoes(){

        buscarVeiculo.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                buscarDadosDoVeiculo();
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

        servicosBuscados.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                adicionarServicos();
            }
            
        });

        pecasBuscadas.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                adicionarPecas();
            }
            
        });

        voltaTelaInicial.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                try {
                    voltaTelaInicial();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    modalsController.abrirModalFalha(e.getMessage());
                }
            }
            
        });

        salvarNovoOrcamento.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                if(veicValid){
                    salvarOrcamento();
                }else modalsController.abrirModalFalha("Veículo inválido");
            }
            
        });

    }



    /**
     * <p> Saves the current {@code orcamento} to the database.
     * 
     * <p> This method has no parameters.
     */
    private void salvarOrcamento() {
        try {
            if (App.usuarioLogado == null) {
                throw new UsuarioNaoEncontradoException("Você não tem permissão para fazer essa ação");
            }
            
            OrcamentoVO orcamentoVo = new OrcamentoVO();
            orcamentoVo.setId(0);
            orcamentoVo.setCpfCliente(this.cpfCliente);
            orcamentoVo.setPlacaVeiculo(buscarVeiculo.getText());
            ArrayList<PecaVo> pecas = new ArrayList<>(pecasEscolhidas);
            ArrayList<ServicoVO> servicos = new ArrayList<>(servicosEscolhidos);
            orcamentoVo.setPecas(pecas);
            orcamentoVo.setServicos(servicos);
            orcamentoVo.setValor(valor);
            orcamentoBO.inserir(orcamentoVo);
            App.navegarEntreTelas("novoOrcamento");
            modalsController.abrirModalSucesso("Orcamento cadastrado com sucesso.");

        } catch (UsuarioNaoEncontradoException e) {
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
            App.navegarEntreTelas("login");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }

    }



    /**
     * <p> Searches the database for a {@code veículo} with same {@code placa} as given in the 
     * {@code TextField} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#buscarVeiculo buscarVeiculo}.
     * 
     * <p> This method has no parameters.
     */
    private void buscarDadosDoVeiculo() {
        try {
            if((buscarVeiculo.getText().contains("-") && buscarVeiculo.getText().length() == 8) || 
              (!buscarVeiculo.getText().contains("-") && buscarVeiculo.getText().length() == 7)) {

                ArrayList<VeiculoVO> veiculos = veiculoBO.buscarPorPlaca(buscarVeiculo.getText());
                if(veiculos.get(0).getId() > 0) {
                    this.cpfCliente = veiculos.get(0).getCpfDono();
                    
                    String text = "O veículo existe";
                    String style = "-fx-text-fill: green;-fx-font-weight: bold;";
                    setDadosCliente(text, style);
                    
                    veicValid = true;

                    ObservableList<PecaVo> listaPecas = tbPecas.getItems();
                    ObservableList<ServicoVO> listaServ = tbServicos.getItems();
                    
                    if (listaPecas.isEmpty() && listaServ.isEmpty()) {
                        salvarNovoOrcamento.setDisable(true);
                    } else salvarNovoOrcamento.setDisable(false);

                }
            } else {
                setDadosCliente("", "-fx-text-fill: black;");
                salvarNovoOrcamento.setDisable(true);
            }    

        } catch (Exception e) {
            String style = "-fx-text-fill: red;-fx-font-weight: bold;";
            String text = "Veiculo não encontrado";
            setDadosCliente(text, style);
            veicValid = false;
            salvarNovoOrcamento.setDisable(true);
        } 
    }



    /**
     * <p> Searches in the database for the {@code peca} with the given name on the 
     * {@code TextField} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#campoBuscaPeca campoBuscaPeca} and insert the results in the 
     * {@code LisView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#pecasBuscadas pecasBuscadas}.
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
     * <p> Adds the selected element on the {@code ListView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#pecasBuscadas pecasBuscadas} 
     * to the {@code TableView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#tbPecas tbPecas}. 
     * 
     * <p> This method has no parameters.
     */
    private void adicionarPecas() {
        try {
            PecaVo peca = pecasBuscadas.getSelectionModel().getSelectedItem();
            int indice = pecasEscolhidas.indexOf(peca);
            
            if(peca.getQuantidade() == 0) {
                throw new InvalidQuantidadeException("Sem estoque para essa peça");
            }

            if (!pecasEscolhidas.contains(peca)) {
                pecasEscolhidas.add(new PecaVo(peca.getId(), peca.getNome(), peca.getFabricante(), peca.getValor(), 1));
                if (veicValid) {
                    salvarNovoOrcamento.setDisable(false);    
                }
            } else {
                PecaVo pecaAdicionada = pecasEscolhidas.get(indice);
                if (peca.getQuantidade() == pecaAdicionada.getQuantidade()) {
                    throw new InvalidQuantidadeException("Sem estoque para essa peça");
                }
                pecaAdicionada.setQuantidade(pecaAdicionada.getQuantidade() + 1);
                pecasEscolhidas.set(indice, pecaAdicionada);
                if(veicValid){
                    salvarNovoOrcamento.setDisable(false);
                }
            }
            this.valor += peca.getValor();
            valorOrcamento.setText(String.valueOf(valor));
        } catch (InvalidQuantidadeException e) {
            msgErroPecas.setText(e.getMessage());
            msgErroPecas.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }
    }

    

    /**
     * <p> Searches in the database for the {@code servico} with the given name on the 
     * {@code TextField} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#campoBuscaServ campoBuscaServ} and insert the results in the 
     * {@code LisView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#servicosBuscados servicosBuscados}.
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
     * <p> Adds the selected element on the {@code ListView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#servicosBuscados servicosBuscados} 
     * to the {@code TableView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#tbServicos tbServicos}. 
     * 
     * <p> This method has no parameters.
     */
    void adicionarServicos() {
        try {
            ServicoVO servico = servicosBuscados.getSelectionModel().getSelectedItem();
        
            if (!servicosEscolhidos.contains(servico)) {
                servicosEscolhidos.add(servico);
                if(veicValid){
                    salvarNovoOrcamento.setDisable(false);
                }
                this.valor += servico.getValor();
                valorOrcamento.setText(String.valueOf(valor));
            } else {
                throw new InvalidQuantidadeException("Esse serviço ja foi adicionado");
            }
        } catch (InvalidQuantidadeException e) {
            msgErroServicos.setText(e.getMessage());
            msgErroServicos.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
        }

    }

    

    /**
     * <p> Sets up the {@code TableView}s present on the screen: {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#tbPecas tbPecas}, 
     * {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#tbServicos tbServicos}. Then call the methods 
     * {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#inicializarBotoesDeAcaoPeca() inicializarBotoesDeAcaoPeca} and 
     * {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#inicializarBotoesDeAcaoServico() inicializarBotoesDeAcaoServico} 
     * that sets the buttons on each table.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarTabelas() {
        nomePeca.setCellValueFactory(new PropertyValueFactory<PecaVo, String>("nome"));
        valorPeca.setCellValueFactory(new PropertyValueFactory<PecaVo, Double>("valor"));
        quantidade.setCellValueFactory(new PropertyValueFactory<PecaVo, Integer>("quantidade"));
        servicoNome.setCellValueFactory(new PropertyValueFactory<ServicoVO, String>("nome"));
        servicoValor.setCellValueFactory(new PropertyValueFactory<ServicoVO, Double>("valor"));
        this.inicializarBotoesDeAcaoPeca();
        this.inicializarBotoesDeAcaoServico();
    }



    /**
     * <p> Sets up the buttons on the {@code TableView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#tbPecas tbPecas}.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarBotoesDeAcaoPeca () {
        acaoPeca.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnDelete);

            {
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setPrefSize(25, 25);

                btnDelete.setOnAction(event -> {
                    try {
                        PecaVo peca = getTableView().getItems().get(getIndex());
                        if (peca.getQuantidade() > 1) {
                            peca.setQuantidade(peca.getQuantidade() - 1);
                            pecasEscolhidas.set(getIndex(), peca);
                        } else {
                            pecasEscolhidas.remove(getIndex());
                            if (pecasEscolhidas.isEmpty()) {
                                salvarNovoOrcamento.setDisable(true);
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
                    btnContainer.setAlignment(Pos.CENTER);
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                }
            }
        });
    }



    /**
     * <p> Sets up the buttons on the {@code TableView} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#tbServicos tbServicos}.
     * 
     * <p> This method has no parameters.
     */
    private void inicializarBotoesDeAcaoServico () {
        acaoServico.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnDelete);

            {
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setPrefSize(25, 25);
                
                btnDelete.setOnAction(event -> {
                    try {
                        ServicoVO servicoVo = getTableView().getItems().get(getIndex());
                        servicosEscolhidos.remove(getIndex());
                        if (servicosEscolhidos.isEmpty()) {
                            salvarNovoOrcamento.setDisable(true);
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
                    btnContainer.setAlignment(Pos.CENTER);
                    btnContainer.setSpacing(10);
                    setGraphic(btnContainer);
                }
            }
        });
    }



    /**
     * <p> Calls the method {@link br.java.projeto.poo.src.App#navegarEntreTelas(String) navegarEntreTelas} to switch from the actual active screen 
     * to the screen {@code orcamento}.
     * 
     * <p> This method has no parameters. 
     */
    public void voltaTelaInicial(){
        App.navegarEntreTelas("orcamentos");
    }



    /**
     * <p> Sets up the text and style of the {@code Label} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#dadosCliente dadosCliente} on the screen.
     * 
     * @param text to be set on the Label.
     * @param style to be set on the Label.
     */
    public void setDadosCliente(String text, String style){
        dadosCliente.setText(text);
        dadosCliente.setStyle(style);
        dadosCliente.setVisible(true);
    }



    /**
     * <p> Fills up the {@code TextField} {@link br.java.projeto.poo.controller.Orcamentos.CriarOrcamentosController#buscarVeiculo buscarVeiculo} 
     * with the name passed to the parameter {@code text}.
     * 
     * @param text to fill in the TextField.
     */
    public void setBuscarVeic(String text){
        buscarVeiculo.setText(text);
    }
}