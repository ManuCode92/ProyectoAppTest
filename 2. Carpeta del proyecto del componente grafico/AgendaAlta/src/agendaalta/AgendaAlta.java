/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaalta;

import agendaalta.view.MostrarAltaController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author JMBJ
 */
public class AgendaAlta extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       //Inicializamos el customControl
       MostrarAltaController customControl = new MostrarAltaController();
       
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
