/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apptest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author JMBJ
 */
public class AbrirReporte {
     public void abrir() throws ClassNotFoundException, SQLException, JRException, IOException {

        //Establecer la conexión con la base de datos
        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuario", "admin", "12345");
      
        //utilizar el fichero .jasper
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(getClass().getResource("view/reportes/TodosUsers.jasper"));

        //utilizar el fichero binario del reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,conexion);
        

        //exporta el reporte a pdf y lo guarda en un fichero llamado matriculados.pdf
        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("UsuarioRegistrados.pdf"));
        SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
        exp.setConfiguration(conf);
        exp.exportReport();

        // se muestra en una ventana aparte para su descarga
    
        //Para evitar que se cierre la aplicacion ponemos a false.
        JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
        jasperViewer.setVisible(true);
    }
}
