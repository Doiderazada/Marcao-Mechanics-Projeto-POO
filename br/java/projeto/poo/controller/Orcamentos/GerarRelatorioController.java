package br.java.projeto.poo.controller.Orcamentos;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.OrcamentoBO;
import br.java.projeto.poo.models.VO.OrcamentoVO;
import br.java.projeto.poo.models.VO.PecaVo;
import br.java.projeto.poo.models.VO.ServicoVO;
import br.java.projeto.poo.src.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GerarRelatorioController {
    

    @FXML private Button gerarRelatorio;
    @FXML private Button cancelar;
    @FXML private TextField dataCriacao;
    @FXML private TextField dataEncerramento;
    @FXML private Label msgErro;
    @FXML private ChoiceBox<String> status;

    private java.sql.Date dataInicio;
    private java.sql.Date dataFinal;

    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";
    private String[] itens = {"Todos", "Em aberto", "Finalizado"};
    private ObservableList<String> listaItens = FXCollections.observableArrayList(itens);    
    private OrcamentoBO orcamentoBO = new OrcamentoBO();
    private ModalsController modalsController = new ModalsController();



    @FXML
    public void initialize() {
        acaoCompTela();
        this.msgErro.setVisible(false);
        status.setItems(listaItens);
        status.setValue(listaItens.get(0));
    }




    /**
     * <p> Sets the action of all elements present on screen.
     * <p> This method should be invoked by {@code initialize} method.
     * <p> This method has no parameters.
     */
    private void acaoCompTela() {
        gerarRelatorio.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                gerarRelatorio();
            }
            
        });

        cancelar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                fecharModal();
            }
            
        });

        dataCriacao.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        dataCriacao.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                dataCriacao.setStyle(null);
                msgErro.setVisible(false);
            }
            
        });
        
        dataEncerramento.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        dataEncerramento.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                dataEncerramento.setStyle(null);
                msgErro.setVisible(false);
            }
            
        });
    }

    


    /**
     * <p> Auto-complete the contents in the {@code TextField}.
     * 
     * <p> This method has no parameters.
     */
    private void autoComplete() {
        if (dataCriacao.getText().length() == 2) {
            dataCriacao.setText(dataCriacao.getText() + "/");
            dataCriacao.end();
        }

        if (dataCriacao.getText().length() == 5) {
            dataCriacao.setText(dataCriacao.getText() + "/");
            dataCriacao.end();
        }
        
        if (dataEncerramento.getText().length() == 2) {
            dataEncerramento.setText(dataEncerramento.getText() + "/");
            dataEncerramento.end();
        }

        if (dataEncerramento.getText().length() == 5) {
            dataEncerramento.setText(dataEncerramento.getText() + "/");
            dataEncerramento.end();
        }

    }

    


    /**
     * <p> Creates the PDF report file.
     * 
     * <p> This method has no parameters.  
     * 
     */
    private void gerarRelatorio() {
        try {
            
            validarCampos();
            
            Date dataAtual = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
            String dataFormatada = dateFormat.format(dataAtual);

            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String inicioFormatado = dateFormat.format(dataInicio);
            String finalFormatado = dateFormat.format(dataFinal);

            int numeroStatus = this.retornarStatus(status.getValue());
            OrcamentoVO orcamentoVO = new OrcamentoVO();
            orcamentoVO.setDataDeCriação(dataInicio);
            orcamentoVO.setDataDeEncerramento(dataFinal);
            orcamentoVO.setStatus(numeroStatus);
            ArrayList<OrcamentoVO> orcamentos = orcamentoBO.buscarPorStatusData(orcamentoVO);





            // Criação do documento PDF

            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("br/java/projeto/poo/relatorios/Relatorio-" + dataFormatada + ".pdf"));
            documento.open();
            documento.setPageSize(PageSize.A4);
            documento.addCreationDate();
            
            Image logo = Image.getInstance("br/java/projeto/poo/public/LogoPrimary.png");
            
            

            Paragraph titulo = new Paragraph("Relatorio de ganhos", new Font(FontFamily.TIMES_ROMAN, 32));
            titulo.setSpacingBefore(15);
            titulo.setSpacingAfter(35);
            
            
    
            Phrase resumo = new Phrase();
            Phrase ws = new Phrase("\n\n");

            if (numeroStatus == 0 ) {
                resumo.add("Lista de orçamentos com situação: Em aberto, a partir de " + inicioFormatado + ".");
            }

            if (numeroStatus == 1) {
                resumo.add("Lista de orçamentos com situação: Finalizado, do perídodo de " + inicioFormatado + " a " + finalFormatado + ".");
            }

            if (numeroStatus == 2){
                resumo.add("Lista de todos os orçamentos constantes do período de " + inicioFormatado + " a " + finalFormatado + ".");
            }
            


            PdfPTable table = new PdfPTable(7);
            table.setTotalWidth(3000f);
            table.setWidthPercentage(100f);
            
            float[] columnWidths = {600f, 280f, 280f, 380f, 280f, 290f, 250f};
            table.setWidths(columnWidths);
            
            Phrase textoItens = new Phrase("Itens", new Font(FontFamily.HELVETICA, 10));
            Phrase textoPreco = new Phrase("Preço do item", new Font(FontFamily.HELVETICA, 10));
            Phrase textoAutomovel = new Phrase("Automóvel", new Font(FontFamily.HELVETICA, 10));
            Phrase textoCliente = new Phrase("Cliente", new Font(FontFamily.HELVETICA, 10));
            Phrase textoInicio = new Phrase("Data de abertura", new Font(FontFamily.HELVETICA, 10));
            Phrase textoFinal = new Phrase("Data de fechamento", new Font(FontFamily.HELVETICA, 10));
            Phrase textoValor = new Phrase("Valor total", new Font(FontFamily.HELVETICA, 10));
            
            PdfPCell itensCell = new PdfPCell(textoItens);
            PdfPCell precoCell = new PdfPCell(textoPreco);
            PdfPCell veiculoCell = new PdfPCell(textoAutomovel);
            PdfPCell clienteCell = new PdfPCell(textoCliente);
            PdfPCell inicioCell = new PdfPCell(textoInicio);
            PdfPCell finalCell = new PdfPCell(textoFinal);
            PdfPCell valorCell = new PdfPCell(textoValor);

            
            table.addCell(itensCell);
            table.addCell(precoCell);
            table.addCell(veiculoCell);
            table.addCell(clienteCell);
            table.addCell(inicioCell);
            table.addCell(finalCell);
            table.addCell(valorCell);
            


            for (OrcamentoVO orcamentoAtual : orcamentos) {
                List list = new List();
                List listQ = new List();
                listQ.setListSymbol("");

                for (PecaVo peca :orcamentoAtual.getPecas()){
                    Phrase item = new Phrase(peca.getNome(), new Font(FontFamily.HELVETICA, 10));
                    Phrase qntd = new Phrase(String.valueOf(peca.getValor()), new Font(FontFamily.HELVETICA, 10));
                    list.add(item.getContent());
                    listQ.add(qntd.getContent());
                }
                for (ServicoVO servico : orcamentoAtual.getServicos()) {
                    Phrase item = new Phrase(servico.getNome(), new Font(FontFamily.HELVETICA, 10));
                    Phrase qntd = new Phrase(String.valueOf(servico.getValor()), new Font(FontFamily.HELVETICA, 10));
                    list.add(item.getContent());
                    listQ.add(qntd.getContent());
                }
                PdfPCell cell = new PdfPCell();
                PdfPCell cell2 = new PdfPCell();
                cell.addElement(list);
                
                cell2.addElement(listQ);
                
                Phrase placa = new Phrase(orcamentoAtual.getPlacaVeiculo(), new Font(FontFamily.HELVETICA, 10));
                Phrase cliente = new Phrase(orcamentoAtual.getCpfCliente(), new Font(FontFamily.HELVETICA, 10));
                Phrase inicio = new Phrase(String.valueOf(orcamentoAtual.getDataDeCriação()), new Font(FontFamily.HELVETICA, 10));
                Phrase valor = new Phrase(String.valueOf(orcamentoAtual.getValor()), new Font(FontFamily.HELVETICA, 10));
                Phrase finall;
                if (orcamentoAtual.getStatus() == 0) {
                    finall = new Phrase("Em aberto", new Font(FontFamily.HELVETICA, 10));
                } else finall = new Phrase(String.valueOf(orcamentoAtual.getDataDeEncerramento()), new Font(FontFamily.HELVETICA, 10));


                table.addCell(cell);
                table.addCell(cell2);
                table.addCell(placa);
                table.addCell(cliente);
                table.addCell(inicio);
                table.addCell(finall);
                table.addCell(valor);
            }
            



            documento.add(logo);
            documento.add(titulo);
            documento.add(resumo);
            documento.add(ws);
            documento.add(table);
            documento.close();
            fecharModal();

            modalsController.abrirModalSucesso("Relatorio criado com sucesso!");
            App.navegarEntreTelas("orcamentos");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            msgErro.setText(e.getMessage());
            msgErro.setVisible(true);
        }
    }



    /**
     * <p> Closes the actual screen.
     * 
     * <p> This method has no parameters.
     */
    private void fecharModal() {
        Stage stage = (Stage) this.cancelar.getScene().getWindow();
        stage.close();
    }





    /**
     * <p> Validate the contents in the {@Code TextField} presented on screen.
     * 
     * <p> This method has no parameters.
     * @throws Exception if any validation fails.
     */
    private void validarCampos() throws Exception {

        if (status.getValue().isEmpty()) {
            throw new Exception("O status não pode ser vazio");
        }

        if (dataCriacao.getText().isEmpty()) {
            dataCriacao.setStyle(textFieldStyle);
            new animatefx.animation.Shake(dataCriacao).play();
            throw new Exception("A data de criação não pode ser vazia");
        }

        if (!dataCriacao.getText().matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            dataCriacao.setStyle(textFieldStyle);
            new animatefx.animation.Shake(dataCriacao).play();
            throw new Exception("Data de criação inválida");
        }

        if (status.getSelectionModel().getSelectedIndex() != 1) {
            if (dataEncerramento.getText().isEmpty()) {
                dataEncerramento.setStyle(textFieldStyle);
                new animatefx.animation.Shake(dataEncerramento).play();
                throw new Exception("A data de encerramento não pode ser vazia");
            }

            if (!dataEncerramento.getText().matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                dataEncerramento.setStyle(textFieldStyle);
                new animatefx.animation.Shake(dataEncerramento).play();
                throw new Exception("Data de encerramento inválida");
            }

            this.dataInicio = gerarData(dataCriacao.getText());
            this.dataFinal = gerarData(dataEncerramento.getText());
            if (dataFinal.before(dataInicio)) {
                dataCriacao.setStyle(textFieldStyle);
                dataEncerramento.setStyle(textFieldStyle);
                new animatefx.animation.Shake(dataCriacao).play();
                new animatefx.animation.Shake(dataEncerramento).play();
                throw new Exception("A data de encerramento não pode ser anterior a de inicio");
            }
        }

        
    }




    /**
     * <p> Converts the given {@code String} to a {@code Date}.
     * 
     * @param data the date to be converted.
     * @return a {@code Date}.
     * @see java.sql.Date#Date Date;
     */
    private java.sql.Date gerarData(String data) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
            String dia = data.substring(0, 2);
            String mes = data.substring(3, 5);
            String ano = data.substring(6);
            data = ano + "-" + mes + "-" + dia;

            Date dataGerada = dateFormat.parse(data);
            java.sql.Date sqlDate = new java.sql.Date(dataGerada.getTime());

            return sqlDate;

        } catch(ParseException e) {
            System.out.println( e.getMessage());
            e.printStackTrace();
            modalsController.abrirModalFalha(e.getMessage());
        }
        
        return null;
    }




    /**
     * <p> Converts the {@code status} from {@code String} format to {@code int} format.
     * @param status to be converted.
     * @return the {@code int} value representing the {@code status}.
     */
    private int retornarStatus (String status) {
        if (status.equals("Finalizado")) {
            return 1;
        }

        if (status.equals("Em aberto")) {
            return 0;
        }

        return 2;
    }
}