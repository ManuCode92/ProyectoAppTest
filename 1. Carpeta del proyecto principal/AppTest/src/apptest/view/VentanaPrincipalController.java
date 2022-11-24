/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.view;

import agendaalta.view.MostrarAltaController;
import apptest.Main;
import apptest.ResultadoFXMLController;
import apptest.model.DataBaseHandler;
import apptest.model.UserDAO;
import java.awt.Desktop;
import java.io.File;

import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class VentanaPrincipalController implements Initializable {

    private Main main;

    private final UserDAO model = new UserDAO();
    private Stage dialogStage;

    /**
     * EL USUARIO LOGIN VENTANA CUSTOMIZADA *
     */
    @FXML
    private MostrarAltaController iniciarSesion;
    /**
     * EL REGISTRARSE LOGIN VENTANA CUSTOMIZADA
     */
    @FXML
    private MostrarAltaController registrarse;

    /**
     * LOS BOTONES
     *
     */
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegistro;
    @FXML
    private Button buttonAdmin;
    @FXML
    private Button buttonHelp;

    public void setMainApp(Main mainTest) {
        this.main = mainTest;
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        /**
         * VALIDAR LOGIN INICIO SESION QUE NO LO DEJE EN BLANCO
         */
        if (evt.equals(iniciarSesion.getUsuario())) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(iniciarSesion.getPassword())) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }

        /**
         * VALIDAR REGISTRO LOGIN QUE NO LO DEJE EN BLANCO
         */
        if (evt.equals(registrarse.getUsuario())) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(registrarse.getPassword())) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
    }

    @FXML
    public void registroEventAction(ActionEvent event) {
        Object evt = event.getSource();

        /**
         * Al pulsar el boton de registro comprueba que el usuario y la
         * contraseña introducida no esten vacia
         */
        if (evt.equals(buttonRegistro)) {
            if (!registrarse.getUsuario().isEmpty() && !registrarse.getPassword().isEmpty()) {
                /**
                 * en 2 string recogo los valores que se han introducido para
                 * registrar al usuario y la contraseña
                 */
                String user = registrarse.getUsuario();
                String pass = registrarse.getPassword();

                int state = model.registro(user, pass);

                if (state != -1) {
                    System.out.println("ANTES DE ENTRAR EN SWITCH " + state);

                    switch (state) {
                        case 1:
                            System.out.println(state);
                            mostrarMensajeError("Error usuario duplicado", " Ya existe ese usuario en la BD");
                            //Elimina lo que haz introducido
                            limpiarUserPass();

                            break;
                        case 2:
                            /**
                             * muestra el mensaje de que se a dado de alta al
                             * usuario con su nombre
                             */

                            model.crearUsuario(user, pass);
                            mostrarMensajeInfo("Registro correctamente ", " Se a dado de alta al usuario" + registrarse.getUsuario() + " en nuestro sistema.");
                            //Elimina lo que haz introducido
                            limpiarUserPass();
                            break;
                    }

                }
            } else {
                mostrarMensajeError("Error al registrars. ", "ADVERTENCIA: Error datos vacio Introduce un usuario y contraseña ");

            }

        }

    }

    public void setCloseApp(Stage stageTestPrincipal) {
        dialogStage = stageTestPrincipal;

    }

    private void limpiarUserPass() {
        registrarse.setPassword("");
        registrarse.setUsuario("");

        iniciarSesion.setUsuario("");
        iniciarSesion.setPassword("");

    }

    @FXML
    private void eventAction(ActionEvent event) {

        Object evt = event.getSource();
        boolean attempt ;
        int r = 0;
        if (evt.equals(buttonLogin)) {

            if (!iniciarSesion.getUsuario().isEmpty() && !iniciarSesion.getPassword().isEmpty()) {

                String user = iniciarSesion.getUsuario();
                String pass = iniciarSesion.getPassword();

                int state = model.login(user, pass);

                if (state != -1) {

                    if (state == 1) {
                        mostrarMensajeInfo("Conectado ", "Datos correctos puede ingresar al sistema");

                        attempt = model.comprobarNoRealizoTest(iniciarSesion.getUsuario(), iniciarSesion.getPassword());

                        if (attempt) {
                            ResultadoFXMLController.username = iniciarSesion.getUsuario();
                            System.out.println("USUARIO CON TEST REALIZADO A true");
                            //SI QUIERES QUE AL MOSTRAR LOS RESULTADO DEVUELVA CIERRE LAS VENTANAS
                            //dialogStage.close();
                            //CARGAR LOS RESULTADO DEL TEST
                            main.mostrarResultadoTest();

                        } else {
                            System.out.println("USUARIO CON TEST REALIZADO A FALSE");
                            System.out.println("nombre usuario que se esta pasando " + iniciarSesion.getUsuario());

                            QuizViewController.username = iniciarSesion.getUsuario();
                            //Contador cuanto test hay para luego mostrarlo en el test
                            ResultSet mr = DataBaseHandler.executeQuery("select count(*)as c from test");

                            try {
                                mr.next();
                                r = mr.getInt("c");
                            } catch (SQLException ex) {
                                mostrarMensajeInfo("Error..!", "Sin resultado Select count from test.");

                            }
                            //Ya no hay mas test por hacer
                            if (r == 0) {
                                mostrarMensajeInfo("Lo siento !", "No hay test para hacer.");
                            } else {
                                System.out.println("numero de test a realizar " + r);
                                QuizViewController.total = r;

                                main.mostrarTestPrincipal();

                            }
                        }

                    } else {
                        mostrarMensajeInfo("Oops !", "Revisa tu usuario/contraseña no son validas.");
                        //Borra lo que se a introducido 
                        limpiarUserPass();
                    }

                }

            } else {
                mostrarMensajeError("Error al conectar con la BD ", "Error al iniciar sesión datos de acceso incorrectos ADVERTENCIA");

            }

        }
    }

    /**
     * Al pulsar el buttonAdmin este muestra una ventana de login.
     * @param event 
     */
    @FXML
    public void ventanaLoginAdmin(ActionEvent event
    ) {

        Object evt = event.getSource();

        if (evt.equals(buttonAdmin)) {

            main.mostrarLoginAdmin();
        }

    }

    /**
     * Al pulsar el button de ayuda este carga el manual de usuario.
     * @throws Exception 
     */
    @FXML
    public void manualDeAyuda() throws Exception {

        File file = new File("manual.pdf");

        /**
         * Combrueba si la ruta del pdf es valida 
         */
        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    //abre el pdf
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    mostrarMensajeError("Error PDF VENTANA PRINCIPAL CONTROLLER", e.getMessage());
                }
            }).start();
        }
        }else{
            mostrarMensajeError("Error. Al buscar el pdf","comprobar la ruta del fichero "+file.getAbsolutePath());
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setCloseMainApp(Stage stagePrincipal) {
        dialogStage = stagePrincipal;
    }

}
