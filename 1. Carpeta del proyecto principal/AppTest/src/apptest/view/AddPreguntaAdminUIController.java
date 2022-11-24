/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.view;

import apptest.model.TestAdminDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JMBJ
 */
public class AddPreguntaAdminUIController implements Initializable {

    private final TestAdminDAO model = new TestAdminDAO();

    //para volver atras y asi cerrar la ventana nueva creada.
    private Stage dialogStage;
    @FXML
    private TextField txtFPregunta;
    @FXML
    private TextField txtFRespuesta;
    @FXML
    private TextField txtFOp1;
    @FXML
    private TextField txtFOp2;
    @FXML
    private TextField txtFOp3;
    @FXML
    private TextField txtFOp4;

    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * COMPROBAR QUE NO ESTEN VACIO LOS TEXTFIELD
     *
     * @return
     */
    public boolean check() {
        if (txtFOp1.getText().isEmpty() || txtFOp2.getText().isEmpty() || txtFOp3.getText().isEmpty() || txtFOp4.getText().isEmpty() || txtFRespuesta.getText().isEmpty() || txtFPregunta.getText().isEmpty()) {
            return false;
        }
        if (!(txtFRespuesta.getText().equals(txtFOp1.getText()) || txtFRespuesta.getText().equals(txtFOp2.getText()) || txtFRespuesta.getText().equals(txtFOp3.getText()) || txtFRespuesta.getText().equals(txtFOp4.getText()))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param event
     */
    @FXML
    private void insertData(ActionEvent event) {

        String sql = "insert into test(pregunta,option1,option2,option3,option4,respuesta) values('" + txtFPregunta.getText() + "','"
                + txtFOp1.getText() + "','" + txtFOp2.getText() + "','"
                + txtFOp3.getText() + "','" + txtFOp4.getText() + "','"
                + txtFRespuesta.getText() + "')";

        if (check() && model.insertDatos(sql)) {
            mostrarMensajeInfo("Datos insertados con exito! ", " Se agregado una nueva pregunta al test");
            txtFPregunta.setText("");
            txtFOp1.setText("");
            txtFOp2.setText("");
            txtFOp3.setText("");
            txtFOp4.setText("");
            txtFRespuesta.setText("");
        } else {
            mostrarMensajeError("REVISAR!", "Hay un problema con los datos de entrada.");

        }

    }

    @FXML
    private void goBack(ActionEvent event) {

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

    public void setMainStageApp(Stage stageAddPreguntaAdminPrincipal) {
        this.dialogStage = stageAddPreguntaAdminPrincipal;
    }
}
