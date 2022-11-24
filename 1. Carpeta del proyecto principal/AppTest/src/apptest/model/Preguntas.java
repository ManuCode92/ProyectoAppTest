/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author JMBJ
 */
public class Preguntas {
    
        private  SimpleIntegerProperty id;
        private  SimpleStringProperty pregunta;
        private  SimpleStringProperty op1;
        private  SimpleStringProperty op2;
        private  SimpleStringProperty op3;
        private  SimpleStringProperty op4;
        private  SimpleStringProperty respuesta;

        public Preguntas(int id, String pregunta, String op1, String op2, String op3, String op4, String respuesta) {
            this.id = new SimpleIntegerProperty(id);
            this.pregunta = new SimpleStringProperty(pregunta);
            this.op1 = new SimpleStringProperty(op1);
            this.op2 = new SimpleStringProperty(op2);
            this.op3 = new SimpleStringProperty(op3);
            this.op4 = new SimpleStringProperty(op4);
            this.respuesta = new SimpleStringProperty(respuesta);
        }

        public int getId() {
            return id.get();
        }

        public String getPregunta() {
            return pregunta.get();
        }

        public String getOp1() {
            return op1.get();
        }

        public String getOp2() {
            return op2.get();
        }

        public String getOp3() {
            return op3.get();
        }

        public String getOp4() {
            return op4.get();
        }

        public String getRespuesta() {
            return respuesta.get();
        }
    
}
