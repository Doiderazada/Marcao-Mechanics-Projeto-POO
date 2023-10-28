package br.java.projeto.poo.controller.Orcamentos;

import java.sql.SQLException;
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
import br.java.projeto.poo.models.VO.VeiculoVO;
import br.java.projeto.poo.src.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;

public class CriarOrcamentosController extends BaseController{
    PecaBO pecaBo = new PecaBO();
    VeiculoBO veiculoBO = new VeiculoBO();
    ServicoBO servicoBO = new ServicoBO();
    OrcamentoBO orcamentoBO = new OrcamentoBO();
    double valor = 0; String cpfCliente;
    ModalsController modalsController = new ModalsController();
    
    @FXML private TableView<PecaVo> tbPecas;
    @FXML private TableColumn<PecaVo, String> acaoPeca;
    @FXML private TableColumn<PecaVo, String> nomePeca;
    @FXML private TableColumn<PecaVo, Double> valorPeca;
    @FXML private TableColumn<PecaVo, Integer> quantidade;
    @FXML private Button voltaTelaInicial;
    @FXML private Button salvarNovoOrcamento;
    @FXML private TextField buscarVeiculo;
    @FXML private TextField campoBuscaPeca;
    @FXML private TextField campoBuscaServ;
    @FXML private ListView<PecaVo> pecasBuscadas;
    @FXML private Label dadosCliente;
    @FXML private TableView<ServicoVO> tbServicos;
    @FXML private TableColumn<ServicoVO, String> acaoServico;
    @FXML private TableColumn<ServicoVO, String> servicoNome;
    @FXML private TableColumn<ServicoVO, Double> servicoValor;
    @FXML private ListView<ServicoVO> servicosBuscados;
    @FXML private Label msgErroPecas;
    @FXML private Label msgErroServicos;
    @FXML private Label valorOrcamento;

    ObservableList<PecaVo> itensPecas = FXCollections.observableArrayList();
    ObservableList<PecaVo> pecasEscolhidas = FXCollections.observableArrayList();
    ObservableList<ServicoVO> itensServicos = FXCollections.observableArrayList();
    ObservableList<ServicoVO> servicosEscolhidos = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        super.initialize();
        tbServicos.setItems(servicosEscolhidos);
        tbPecas.setItems(pecasEscolhidas);
        pecasBuscadas.setVisible(false);
        servicosBuscados.setVisible(false);
        salvarNovoOrcamento.setDisable(true);
        servicosBuscados.setItems(itensServicos);
        servicosBuscados.setOnMouseClicked(event -> {
            this.atualizarValoresServicos();
        });
        pecasBuscadas.setItems(itensPecas);
        pecasBuscadas.setOnMouseClicked(event -> {
            this.atualizarValoresPecas();
        });

        inicializarTabelas();
    }

    @FXML
    void salvarOrcamento(ActionEvent event) {
        try {
            if (App.usuarioLogado == null) {
                throw new Exception("Você não tem permissão para fazer essa ação");
            }
            OrcamentoVO orcamentoVo = new OrcamentoVO();
            orcamentoVo.setId(0);
            orcamentoVo.setCpfCliente(this.cpfCliente);
            orcamentoVo.setCpfFuncionario(App.usuarioLogado.getCpf());
            orcamentoVo.setPlacaVeiculo(buscarVeiculo.getText());
            ArrayList<PecaVo> pecas = new ArrayList<>(pecasEscolhidas);
            ArrayList<ServicoVO> servicos = new ArrayList<>(servicosEscolhidos);
            orcamentoVo.setPecas(pecas);
            orcamentoVo.setServicos(servicos);
            orcamentoVo.setValor(valor);
            orcamentoBO.inserir(orcamentoVo);
            App.navegarEntreTelas("novoOrcamento");
            modalsController.abrirModalSucesso("Orcamento cadastrado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
            App.navegarEntreTelas("login");
        }
    }

    @FXML
    void buscarDadosDoVeiculo(KeyEvent event) {
        try {
            if(buscarVeiculo.getText().length() > 7) {
                ArrayList<VeiculoVO> veiculos = veiculoBO.buscarPorPlaca(buscarVeiculo.getText());
                if(veiculos.get(0).getId() > 0) {
                    this.cpfCliente = veiculos.get(0).getCpfDono();
                    dadosCliente.setText("Veiculo existe");
                    dadosCliente.setStyle("-fx-text-fill: green;");
                    salvarNovoOrcamento.setDisable(false);
                }
            } else {
                salvarNovoOrcamento.setDisable(true);
                dadosCliente.setText("");
                dadosCliente.setStyle(null);
            }
        } catch (Exception e) {
            dadosCliente.setStyle("-fx-text-fill: red;");
            dadosCliente.setText("Veiculo não encontrado");
        } 
    }

    // funçoes para configurar a tabela de peças
    @FXML
    void buscarPecasPorNome(KeyEvent event) {
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

    void atualizarValoresPecas() {
        try {
            PecaVo peca = pecasBuscadas.getSelectionModel().getSelectedItem();
            int indice = pecasEscolhidas.indexOf(peca);
            
            if(peca.getQuantidade() == 0) {
                throw new InvalidQuantidadeException("Sem estoque para essa peça");
            }

            if (!pecasEscolhidas.contains(peca)) {
                pecasEscolhidas.add(new PecaVo(peca.getId(), peca.getNome(), peca.getFabricante(), peca.getValor(), 1));
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

    private void inicializarBotoesDeAcaoPeca (ObservableList<PecaVo> funcs) {
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

    // funçoes para configurar a tabela de Servicos
    @FXML
    void buscarServicosPorNome(KeyEvent event) {
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

    void atualizarValoresServicos() {
        try {
            ServicoVO servico = servicosBuscados.getSelectionModel().getSelectedItem();
        
            if (!servicosEscolhidos.contains(servico)) {
                servicosEscolhidos.add(servico);
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

    private void inicializarBotoesDeAcaoServico (ObservableList<ServicoVO> funcs) {
        acaoServico.setCellFactory(param -> new TableCell<>() {
            private final Button btnDelete = new Button();
            private final HBox btnContainer = new HBox(btnDelete);

            {
                btnDelete.getStyleClass().add("btn-delete");
                btnDelete.setOnAction(event -> {
                    try {
                        ServicoVO servicoVo = getTableView().getItems().get(getIndex());
                        servicosEscolhidos.remove(getIndex());
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

    private void inicializarTabelas() throws SQLException {
        nomePeca.setCellValueFactory(new PropertyValueFactory<PecaVo, String>("nome"));
        valorPeca.setCellValueFactory(new PropertyValueFactory<PecaVo, Double>("valor"));
        quantidade.setCellValueFactory(new PropertyValueFactory<PecaVo, Integer>("quantidade"));
        servicoNome.setCellValueFactory(new PropertyValueFactory<ServicoVO, String>("nome"));
        servicoValor.setCellValueFactory(new PropertyValueFactory<ServicoVO, Double>("valor"));
        this.inicializarBotoesDeAcaoPeca(itensPecas);
        this.inicializarBotoesDeAcaoServico(itensServicos);
    }

    @FXML
    public void voltaTelaInicial() throws Exception {
        App.navegarEntreTelas("orcamentos");
    }

    public void setDadosCliente(String text){
        dadosCliente.setText(text);
        dadosCliente.setVisible(true);
        dadosCliente.setStyle("-fx-text-fill: black;-fx-font-weight: bold;");
    }

    public void setBuscarVeic(String text){
        buscarVeiculo.setText(text);
    }
}
