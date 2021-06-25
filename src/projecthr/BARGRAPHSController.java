/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class BARGRAPHSController implements Initializable {

    /**
     * Initializes the controller class.
     */
    int G;
    String loginName;
    Double bmi;
    int systol,diastol,heartrat,rate,avgrating=0;
    void getAvgRating(int r){
        avgrating=r;
        System.out.println(avgrating);
    }
     void getdata () throws SQLException
     {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic","root","");
        Statement statement = conn.createStatement();
        ResultSet resultset= statement.executeQuery("select * from central where no = 1 ");
        while (resultset.next()) {
            bmi = resultset.getDouble("bmi");
            systol= resultset.getInt("systolic");
             diastol= resultset.getInt("diastolic");
             heartrat= resultset.getInt("heartrate");
             rate = resultset.getInt("rating");
     } 
         ResultSet rs = statement.executeQuery("select * from central");
        int k =0;
        while (rs.next()) {
            avgrating+=rs.getInt("rating");
            k++;
            System.out.println(k);
            
        }
        avgrating = avgrating/k; //avarage rating
        System.out.println("Rating"+ avgrating);
         XYChart.Series series1 = new XYChart.Series<>();
        series1.setName("Standard");       
        series1.getData().add(new XYChart.Data("BMI", 25));
        series1.getData().add(new XYChart.Data("Systolic Pressure", 120));
        series1.getData().add(new XYChart.Data("Diastolic Pressure", 80));
        series1.getData().add(new XYChart.Data("Heart Rate", 100));
        series1.getData().add(new XYChart.Data("Rating", avgrating));
        XYChart.Series series2= new XYChart.Series<String, Number>();
        series2.setName("User");       
        series2.getData().add(new XYChart.Data("BMI",  bmi));
        series2.getData().add(new XYChart.Data("Systolic Pressure", systol));
        series2.getData().add(new XYChart.Data("Diastolic Pressure",  diastol));
        series2.getData().add(new XYChart.Data("Heart Rate", heartrat));
        series2.getData().add(new XYChart.Data("Rating", rate));
        healthChart.getData().addAll(series1,series2);
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //standard data
       
    } 
      public void shownam(String s ,int g) {
        loginName = s;
        G=g;
        //lblnam.setText(s);
    }
   @FXML
    private JFXButton back;

    @FXML
    private BarChart<String, Number> healthChart;

       @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

     @FXML
    void b2h(ActionEvent event) throws IOException, SQLException {
FXMLLoader loader = new FXMLLoader(getClass().getResource("Stat.fxml"));	
		Parent root = loader.load();	
		StatController statController = loader.getController();
                statController.shownameandgender(loginName,G);
                statController.getInfo ();
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
                stage.setTitle("Health Rater");
		stage.show();
                System.out.println(systol);
}

          
    }
