/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.view;

import agendaalta.view.MostrarAltaController;
import apptest.Main;
import apptest.model.AdminDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author JMBJ
 */
public class VentanaAdminLoginController implements Initializable {
//para volver atras y asi cerrar la ventana nueva creada.
    private Stage dialogStage;

    private Main main;
    private AdminDAO model = new AdminDAO();
    
    /**
     * EL USUARIO LOGIN ADMIN
     * VENTANA CUSTOMIZADA 

     */
    @FXML
    private MostrarAltaController adminTest;
    
    @FXML
    private Button buttonInicio;
    @FXML
    private Button buttonvolver;
    
     @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        /**
         * VALIDAR LOGIN INICIO SESION QUE NO LO DEJE EN BLANCO
         */
        if (evt.equals(adminTest.getUsuario())) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(adminTest.getPassword())) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }

        
    }

     @FXML
    private void eventAction(ActionEvent event) {

        Object evt = event.getSource();

        if (evt.equals(buttonInicio)) {

            if (!adminTest.getUsuario().isEmpty() && !adminTest.getPassword().isEmpty()) {

                String user = adminTest.getUsuario();
                String pass = adminTest.getPassword();

                int state = model.login(user, pass);

                if (state != -1) {

                    if (state == 1) {

                        mostrarMensajeInfo("Conectando...", "Datos correctos puede ingresar al sistema");
                        main.mostrarTestAdminPrincipal();
                   

                    } else {
                        mostrarMensajeError("Error al iniciar sesión Admin", "Error al iniciar sesión datos de acceso incorrectos ADVERTENCIA");

                    }

                }

            } else {
                mostrarMensajeError("Error al conectar con la BD ", "Error al iniciar sesión ADMIN datos de acceso incorrectos ADVERTENCIA");

            }

        }
         if (evt.equals(buttonvolver)) {
            dialogStage.close();

         }

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
    /**
     * Initializes the controller class.
     */
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setMainStageApp(Stage stageLoginAdmin) {
        this.dialogStage = stageLoginAdmin;
    }

    public void setMainApp(Main aThis) {
        this.main=aThis;
    }
    
   
    
}