/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest;

import apptest.model.ConnectionPoolMySQL;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JMBJ
 */
public class ResultadoFXMLController implements Initializable {

    public static String username;
    private Stage dialogStage;

    @FXML
    private Label totalPreguntas;
    @FXML
    private Label totalTestCorrecta;
    @FXML
    private Label porcentaje;
    @FXML
    private Button okbut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //TODAS LAS PREGUNTAS
        int tot = 0;
        //CORRECTA
        int cor = 0;
        //PORCENTAJE
        double accu;
        String sql = "select username,respuesta,response from test,respuestatest where username='" + username + "'"
                + " and test.qid=respuestatest.qno";

        String sqlTestRespondido = "update users set attempt='true' where username='" + username + "'";

        Connection connection = null;
        PreparedStatement pstActualizar;
        PreparedStatement pst;
        try {
            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {
                pstActualizar = connection.prepareStatement(sqlTestRespondido);
                pstActualizar.executeUpdate();
                pst = connection.prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                while (rs.next()) {
                    ++tot;
                    if (rs.getString("respuesta").equalsIgnoreCase(rs.getString("response"))) {
                        ++cor;
                    }
                }
                accu = (double) cor / (double) tot * 100;
                totalPreguntas.setText(String.valueOf(tot));
                totalTestCorrecta.setText(String.valueOf(cor));
                porcentaje.setText(String.valueOf(accu) + " %");

            } else {
                mostrarMensajeError("Error BD", " al conectarse con la BD");
            }

        } catch (HeadlessException | SQLException ex) {
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n", ex.getMessage());
        }

    }

    @FXML
    private void gotologin(ActionEvent event) {

        dialogStage.close();

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

    void setCloseStageApp(Stage stageResultTest) {
        this.dialogStage = stageResultTest;
    }

    
}
