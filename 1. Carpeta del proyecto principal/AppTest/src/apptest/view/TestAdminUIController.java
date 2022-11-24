/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.view;

import apptest.AbrirReporte;
import apptest.Main;
import apptest.model.Preguntas;
import apptest.model.TestAdminDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author JMBJ
 */
public class TestAdminUIController implements Initializable {

    private Main main;

    private Stage adminUIStage;
    private final TestAdminDAO model = new TestAdminDAO();

    ObservableList<Preguntas> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Preguntas> table;
    @FXML
    private TableColumn<Preguntas, Integer> idCol;
    @FXML
    private TableColumn<Preguntas, String> preguntaCol;
    @FXML
    private TableColumn<Preguntas, String> op1Col;
    @FXML
    private TableColumn<Preguntas, String> op2Col;
    @FXML
    private TableColumn<Preguntas, String> op3Col;
    @FXML
    private TableColumn<Preguntas, String> op4Col;
    @FXML
    private TableColumn<Preguntas, String> rspCol;

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonRefresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarCols();
        cargartabledatos();
    }

    /**
     * Inicializar la Table columns
     */
    private void iniciarCols() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        preguntaCol.setCellValueFactory(new PropertyValueFactory<>("pregunta"));
        op1Col.setCellValueFactory(new PropertyValueFactory<>("op1"));
        op2Col.setCellValueFactory(new PropertyValueFactory<>("op2"));
        op3Col.setCellValueFactory(new PropertyValueFactory<>("op3"));
        op4Col.setCellValueFactory(new PropertyValueFactory<>("op4"));
        rspCol.setCellValueFactory(new PropertyValueFactory<>("respuesta"));
    }

    /**
     * Cargar datos.
     */
    private void cargartabledatos() {
        list = model.cargarDatos();
        /**
         * Controlar que la lista que se cargue no este vacia.
         */

        if (list.isEmpty()) {
            mostrarMensajeInfo("AVISO ", " Aun no hay ningun test para mostrar");

        } else {
            table.getItems().setAll(list);
        }

    }

    @FXML
    private void AddT(ActionEvent event) {
            main.mostrarAddPregunta();
        
    }

    /**
     * Quitar de la tabla la pregunta test
     * @param event 
     */
    @FXML
    private void removeT(ActionEvent event) {
        try {
            Preguntas respuesta = table.getSelectionModel().getSelectedItem();
            String sql = "delete from test where qid=" + respuesta.getId();
            Boolean ok = model.removerTest(sql);
            if (ok) {
                mostrarMensajeInfo("Operacion realizada con exito", "Pregunta Eliminada");
                refresh();
            } else {
                mostrarMensajeInfo("Error", "La pregunta selecionada no se puede eliminar.");
            }
        } catch (Exception ex) {
            mostrarMensajeError("Error", "No has selecionado una pregunta");
        }
    }

    /**
     * Actualizar la tabla columns con los datos nuevos agregados.
     */
    @FXML
    private void refresh() {
        table.getItems().clear();
        list.removeAll(list);
        cargartabledatos();
        mostrarMensajeInfo("La Base de datos  ", "A sido !!ACTUALIZADA!!");

    }
    /**
     * Abre el reporte con jasper Report
     */
    @FXML
    public void reportesAbrir(){
        AbrirReporte abrir = new AbrirReporte();
        
        try {
            abrir.abrir();
        } catch (ClassNotFoundException ex) {
        mostrarMensajeError("Error. No se encontraron los archivos ", ex.getMessage());
            Logger.getLogger(TestAdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
        mostrarMensajeError("Error.Al conectar con la base de datos. ", ex.getMessage());

            Logger.getLogger(TestAdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
        mostrarMensajeError("Error. JasperReport. ", ex.getMessage());

            Logger.getLogger(TestAdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        mostrarMensajeError("Error. IOException. ", ex.getMessage());

            Logger.getLogger(TestAdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    public void salirAplicacion(){
        adminUIStage.close();
    }

    private void mostrarMensajeInfo(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(null);
        alerta.showAndWait();
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(null);
        alerta.showAndWait();
    }

    public void setMainApp(Main aThis) {
        this.main= aThis;
        
    }
    
    public void setStage(Stage dialogStage) {
        this.adminUIStage = dialogStage;
    }
  
}
