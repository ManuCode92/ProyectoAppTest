/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest;

import apptest.view.AddPreguntaAdminUIController;
import apptest.view.QuizViewController;
import apptest.view.TestAdminUIController;
import apptest.view.VentanaAdminLoginController;
import apptest.view.VentanaPrincipalController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author JMBJ
 */
public class Main extends Application {

    private Stage stagePrincipal;
    private Stage stageAdminPrincipal = new Stage();

    @Override
    public void start(Stage stagePrincipal) throws Exception {
        
        
        this.stagePrincipal = stagePrincipal;
        mostrarPantallaPrincipal();

    }

    /**
     * CARGA LA PANTALLA DE INICIO DONDE ESTA EL LOGIN DEL USUARIO PARA REALIZAR
     * EL TEST SI NO TIENE UN USUARIO TIENE LA OPCION DE REGISTRARSE Y ACESSO AL
     * ADMINISTRADOR DEL TEST
     */
    public void mostrarPantallaPrincipal() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/PrincipalInicioFXML.fxml"));
            /**
             * Border pane
             */
            BorderPane page = (BorderPane) loader.load();
            stagePrincipal.setTitle("Menu principal");
            VentanaPrincipalController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCloseMainApp(stagePrincipal);
            Scene scene = new Scene(page);
            stagePrincipal.setScene(scene);
            stagePrincipal.setResizable(false);
            stagePrincipal.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * MOSTRAR LAS PREGUNTAS DEL TEST
     *
     */
    public void mostrarTestPrincipal() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/QuizView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);

            Stage stageTestPrincipal = new Stage();

            QuizViewController controller = loader.getController();
            //le paso la stage actual
            controller.setMainApp(this);
            controller.setCloseApp(stageTestPrincipal);
            stageTestPrincipal.setTitle("Menu Test Principal");
            
            //Añadido para que se solo se use la ventana que se abra
            stageTestPrincipal.initModality(Modality.WINDOW_MODAL);
            stageTestPrincipal.initOwner(stagePrincipal);
            stageTestPrincipal.setScene(scene);
            stageTestPrincipal.show();
            stageTestPrincipal.setResizable(false);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * VALIDA QUE ERES ADMINISTRADOR TEST
     *
     */
    public void mostrarLoginAdmin() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/AdminFXML.fxml"));
            //Creo un HBox porque es el que eh utilizado en scene builder
            HBox page = (HBox) loader.load();
            Scene scene = new Scene(page);
            Stage stageLoginAdmin = new Stage();
            //
            VentanaAdminLoginController controller = loader.getController();
            //le paso la stage actual
            controller.setMainStageApp(stageLoginAdmin);
            //le paso la scenna actual
            controller.setMainApp(this);

            stageLoginAdmin.setTitle("Menu Login Admin");
            
            //Añadido para que se solo se use la ventana que se abra
            stageLoginAdmin.initModality(Modality.WINDOW_MODAL);
            stageLoginAdmin.initOwner(stagePrincipal);
            stageLoginAdmin.setScene(scene);
            stageLoginAdmin.show();
            stageLoginAdmin.setResizable(false);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * MUESTRA EL ADMINISTRADOR DEL TEST DONDE SOLO PUEDE ACCEDER UN USUARIO
     * ADMINISTRADOR OPCIONES QUE PUEDE HACER 1 AÑADIR UNA NUEVA PREGUNTA 2
     * ELIMINAR PREGUNTA 3 ACTUALIZAR LA BASE DE DATOS DE LAS PREGUNTAS DEL TEST
     *
     */
    public void mostrarTestAdminPrincipal() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/TestUIAdmin.fxml"));
            BorderPane page = (BorderPane) loader.load();

            Scene scene = new Scene(page);
            //Muy importante volver a inicializarlo aqui 
            /**
             * Ya que cuando vuelves a introducir los datos
             * de administrador no se carga aunque arriba ya lo este.
             */
            stageAdminPrincipal = new Stage();
            TestAdminUIController controller = loader.getController();
            //le paso la stage actual
            controller.setMainApp(this);
            controller.setStage(stageAdminPrincipal);
            stageAdminPrincipal.setTitle("Menu administrador de test ");
            
            //Añadido para que se solo se use la ventana que se abra

            stageAdminPrincipal.initModality(Modality.WINDOW_MODAL);
            stageAdminPrincipal.initOwner(stagePrincipal);
            stageAdminPrincipal.setScene(scene);
            stageAdminPrincipal.show();
            stageAdminPrincipal.setResizable(false);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * MUESTRA UNA VENTANA PARA AÑADIR PREGUNTAS AL TEST
     *
     */
    public void mostrarAddPregunta() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/AddPreguntaAdminUI.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Scene scene = new Scene(page);
            Stage stageAddPreguntaAdminPrincipal = new Stage();

            AddPreguntaAdminUIController controller = loader.getController();
            //le paso la stage actual
            controller.setMainStageApp(stageAddPreguntaAdminPrincipal);

            stageAddPreguntaAdminPrincipal.setTitle("Añadir Pregunta");
            //Añadido para que se solo se use la ventana que se abra
            stageAddPreguntaAdminPrincipal.initModality(Modality.WINDOW_MODAL);
           stageAddPreguntaAdminPrincipal.initOwner(stageAdminPrincipal);
            stageAddPreguntaAdminPrincipal.setScene(scene);
            stageAddPreguntaAdminPrincipal.show();
            stageAddPreguntaAdminPrincipal.setResizable(false);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * CARGARA ESTA PARTE SI EL USUARIO A REALIZADO EL TEST O A TERMINADO DE
     * HACER EL TEST
     */
    public void mostrarResultadoTest() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ResultadoFXML.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);

            Stage stageResultTest = new Stage();
            ResultadoFXMLController controller = loader.getController();
            //le paso la stage actual
            controller.setCloseStageApp(stageResultTest);
            stageResultTest.setTitle("Resultado TEST");
            //Añadido para que se solo se use la ventana que se abra
            stageResultTest.initModality(Modality.WINDOW_MODAL);
            stageResultTest.initOwner(stagePrincipal);
            stageResultTest.setScene(scene);
            stageResultTest.show();
            stageResultTest.setResizable(false);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    

}
