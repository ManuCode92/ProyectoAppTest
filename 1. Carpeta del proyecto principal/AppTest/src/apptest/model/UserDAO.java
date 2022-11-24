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
import javafx.scene.control.Alert;

/**
 *
 * @author JMBJ
 */
public class UserDAO {

    /**
     * Registrar un usuario
     *
     * @param user el usuario a registrar
     * @param password del usuario a registrar.
     * @return si devuelve 1 es que lo a encontrado.Si
     * devuelve -1 esque no se a podido conectar con la base de datos
     * si devuelve 2 esque el usuario no existe en la base de datos.
     */
    public int registro(String user, String password) {
        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;

        int state = -1;

        try {
            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {

                String sql = "SELECT COUNT(username) FROM users WHERE  username=?";
                pst = connection.prepareStatement(sql);
                pst.setString(1, user);

                rs = pst.executeQuery();
                rs.next();
                if (rs.getInt(1) == 1) {
                    state = 1;
                    System.out.println("ERROR " + state);

                } else {
                    state = 2;

                }

            } else {
                mostrarMensajeError("Error BD", " al conectarse con la BD");
            }

        } catch (HeadlessException | SQLException ex) {
            mostrarMensajeError("Hubo un error de ejecución, posibles errores:\n", ex.getMessage());
        }

        return state;

    }

    /**
     * 
     * @param user
     * @param password
     * @return state es 0 es que se a registrado el usuario
     * state es -1 problemas al conectar con la base de datos.
     * 
     */
    public int crearUsuario(String user, String password) {
        int state = 0;
        Connection connection = null;
        PreparedStatement pst;
        try {

            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {
                String sql = "insert into users values('" + user + "','" + password + "','false')";

                pst = connection.prepareStatement(sql);
                pst.executeUpdate();

            } else {
                state = -1;
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

        return state;
    }

    /**
     * Iniciar session 
     * @param user el usuario que debe de estar en la BD
     * @param password 
     * @return si state es -1 problemas con conectar con la base de datos.
     * si state 1 esque el usuario existe y puede acceder al test
     * si state 0 esque no hay ese usuario en la DB
     */
    public int login(String user, String password) {

        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;
        //HUBO UN ERROR.
        int state = -1; 

        try {

            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {

                String sql = "SELECT * FROM users WHERE  username=? AND password=?";

                pst = connection.prepareStatement(sql);
                pst.setString(1, user);
                pst.setString(2, password);

                rs = pst.executeQuery();

                if (rs.next()) {
                    state = 1;
                } else {
                    state = 0;
                }

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

        return state;

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
     * para que no le salga otra vez a realizar el test Y ASI SOLO CARGAR EL
     * RESULTADO QUE TIENE.
     *
     * @param user
     * @param pass
     * @return
     */

    /**
     * Comprueba si a realizado el test.
     * @param user se le pasa el usuario 
     * @param pass y su contraseña 
     * @return boolean si devuelve true esque el usuario a realizado el test 
     * si devuelve false 
     */
    public boolean comprobarNoRealizoTest(String user, String pass) {
        Connection connection = null;
        PreparedStatement pst;
        ResultSet rs;
        //HUBO UN ERROR.
        boolean attempt = false;

        try {

            connection = ConnectionPoolMySQL.getInstance().getConnection();

            if (connection != null) {

                String sql = "SELECT attempt FROM `users` WHERE username='" + user + "' AND password= '" + pass + "'";

                pst = connection.prepareStatement(sql);

                rs = pst.executeQuery();

                if (rs.next()) {
                    if (rs.getString("attempt").equals("false")) {
                        attempt = false;
                    } else {
                        attempt = true;
                    }
                }
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

        return attempt;
    }

}
