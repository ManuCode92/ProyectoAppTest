/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.model;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author JMBJ
 */
public class TestAdminDAO {

   ObservableList<Preguntas> list = FXCollections.observableArrayList();

    public ObservableList<Preguntas> cargarDatos() {

        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;
       
        try {
            //Conexion con la base de datos.
            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {

                //Sentencia sql
                String sql = "SELECT * FROM test";

                //consultar todos los datos del test
                pst = connection.prepareStatement(sql);

                //traerme cada datoy filtrarlo guardando luego en una ObservableList 
                rs = pst.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("qid");
                    String pregunta = rs.getString("pregunta");
                    String op1 = rs.getString("option1");
                    String op2 = rs.getString("option2");
                    String op3 = rs.getString("option3");
                    String op4 = rs.getString("option4");
                    String rsp = rs.getString("respuesta");
                    list.add(new Preguntas(id, pregunta, op1, op2, op3, op4, rsp));
                } 
                
                rs.close();

            } else {
                mostrarMensajeError("Error BD", " al conectarse con la BD");
            }

        } catch (HeadlessException | SQLException ex) {
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n", ex.getMessage());

        } finally {

            try {
                if (connection != null) {
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

        }

        return list;

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
     * borrar test.
     * @param sql se le pasa una sentencia sql de tipo String
  
     * @return 
     */
    public Boolean removerTest(String sql) {

        Boolean ok=false;
        
        
        Connection connection = null;
        PreparedStatement pst;
        //HUBO UN ERROR.
       

        try {

            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {

                pst = connection.prepareStatement(sql);

                int state = pst.executeUpdate();
                if (state == 1) {
                    ok=true;
                }else{
                    ok=false;
                }
                pst.close();
            } else {
                mostrarMensajeError("Error BD", " al conectarse con la BD");
            }

        } catch (HeadlessException | SQLException ex) {
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n", ex.getMessage());

        } finally {

            try {
                if (connection != null) {
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

        }

        return ok;
    }

    /**
     * Insertar datos.
     * @param sql se le pasa una sentencia sql de tipo String
     * @return 
     */
    public boolean insertDatos(String sql) {
 Boolean ok=false;
        
        
        Connection connection = null;
        PreparedStatement pst;
        //HUBO UN ERROR.
       

        try {

            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {

                pst = connection.prepareStatement(sql);

                int state = pst.executeUpdate();
                if (state == 1) {
                    ok=true;
                }else{
                    ok=false;
                }
               
                pst.close();

            } else {
                mostrarMensajeError("Error BD", " al conectarse con la BD");
            }

        } catch (HeadlessException | SQLException ex) {
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n", ex.getMessage());

        } finally {

            try {
                if (connection != null) {
                    ConnectionPoolMySQL.getInstance().closeConnection(connection);
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

        }

        return ok;

    }
}
