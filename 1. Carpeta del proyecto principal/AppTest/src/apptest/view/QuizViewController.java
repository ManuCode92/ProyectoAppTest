/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.view;

import apptest.Main;
import apptest.ResultadoFXMLController;
import apptest.model.ConnectionPoolMySQL;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author JMBJ
 */
public class QuizViewController  {

    public static String username;
    public static int total;
    public String respu = "NA";
    public int i = 0, qcount = 0;
    public List<preguntaTest> allPreguntas = new ArrayList<>();
    private Main mainApp;		// Referencia a mi aplicacion
    private Stage dialogStage;
    @FXML
    private Label pregunta;	// La pregunta
    @FXML
    private RadioButton rbA;
    @FXML
    private RadioButton rbB;
    @FXML
    private RadioButton rbC;
    @FXML
    private RadioButton rbD;

    @FXML
    private ToggleGroup rbGroup;

    private RadioButton[] rB;
    @FXML
    private ProgressBar pB = new ProgressBar();
    @FXML
    private Button bNext;
    @FXML
    private Label qno;
    @FXML
    private Label contador;

//CARGAR Preguntas
    public void load_question() {
        preguntaTest a = allPreguntas.get(i);
        pregunta.setText(a.pregunta);
        rbA.setText(a.option1);
        rbA.setSelected(false);
        rbB.setText(a.option2);
        rbB.setSelected(false);
        rbC.setText(a.option3);
        rbC.setSelected(false);
        rbD.setText(a.option4);
        rbD.setSelected(false);
        respu = "NA";
        qno.setText(String.valueOf(i + 1));
        //MOSTRAR CUANTO TEST HAY
        
        contador.setText(String.valueOf(total -i -1));
        qcount = a.qid;
        i++;

    }

 
    @FXML
    private void initialize() {
        rbGroup = new ToggleGroup();
        
       
        rbA.setToggleGroup(rbGroup);
        rbB.setToggleGroup(rbGroup);
        rbC.setToggleGroup(rbGroup);
        rbD.setToggleGroup(rbGroup);

        String sql = "select * from test";

        Connection connection = null;
        PreparedStatement pst;
        try {
            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {
                pst = connection.prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                while (rs.next()) {
                    preguntaTest a = new preguntaTest();
                    a.pregunta = rs.getString("pregunta");
                    a.option1 = rs.getString("option1");
                    a.option2 = rs.getString("option2");
                    a.option3 = rs.getString("option3");
                    a.option4 = rs.getString("option4");
                    a.respuestaTest = rs.getString("respuesta");

                    a.qid = rs.getInt("qid");
                    allPreguntas.add(a);
                }
                rs.close();
                pst.close();
                load_question();

            } else {
                mostrarMensajeError("Error BD", " al conectarse con la BD");
            }

        } catch (HeadlessException | SQLException ex) {
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n", ex.getMessage());
        }

           
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void bNext() {
        proceed();
        setProgress(pB.getProgress() + 0.30);
        
    }

    @FXML
    private void option1(ActionEvent event) {
        respu = rbA.getText();
    }

    @FXML
    private void option2(ActionEvent event) {
        respu = rbB.getText();
    }

    @FXML
    private void option3(ActionEvent event) {
        respu = rbC.getText();
    }

    @FXML
    private void option4(ActionEvent event) {
        respu = rbD.getText();
    }

    public void proceed() {
        String sql = "insert into respuestatest values('" + username + "'," + qcount + ",'" + respu + "')";
        Connection connection = null;
        PreparedStatement pst;

        try {
            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {
                pst = connection.prepareStatement(sql);
                if (pst.executeUpdate(sql) != 0 && i < total) {

                    load_question();
                } else {
                    //LE PASO EL USER QUE A REALIZADO EL TEST
                    ResultadoFXMLController.username = username;
                    dialogStage.close();
                    //CARGA LOS RESULTADO DEL TEST REALIZADO
                    mainApp.mostrarResultadoTest();
                }

            } else {
                mostrarMensajeError("Error BD", " al conectarse con la BD");
            }

        } catch (HeadlessException | SQLException ex) {
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n", ex.getMessage());
        }

    }

    public void setProgress(double d) {
        pB.setProgress(d);
    }

    public void setCloseApp(Stage stageTestPrincipal) {
        dialogStage = stageTestPrincipal;

    }

   

    public class preguntaTest {

        public int qid;
        public String pregunta;
        public String option1;
        public String option2;
        public String option3;
        public String option4;
        public String respuestaTest;
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
}
