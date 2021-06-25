/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class PressureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label label;

    @FXML
    private  JFXButton confirm;

    @FXML
    private Slider sysSlider;

    @FXML
    private Slider diaslider;

    @FXML
    private Label label1;

    @FXML
    private Label label11;
    private int pressure;
    private int pressure1;
    String loginName;
    public void shownam (String s)
    {   loginName = s;
       
    }
    @FXML
    void confirm2update(ActionEvent event) throws SQLException {
        pressure = (int) sysSlider.getValue();
       pressure1 = (int) diaslider.getValue();
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
      String query = "update central set systolic = ? , diastolic = ? where name = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt   (1, pressure);
      preparedStmt.setInt   (2, pressure1);
      preparedStmt.setString(3,loginName);
      preparedStmt.executeUpdate();
      preparedStmt.close();
         conn.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       pressure = (int) sysSlider.getValue();
       pressure1 = (int) diaslider.getValue();
		label.setText(Integer.toString(pressure));
                label1.setText(Integer.toString(pressure1));
		
		sysSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {
				
				pressure = (int) sysSlider.getValue();
				label.setText(Integer.toString(pressure));
				
			}			
		});
                label.setText(Integer.toString(pressure));
		
		diaslider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {
				
				pressure1 = (int) diaslider.getValue();
				label1.setText(Integer.toString(pressure1));
				
			}			
		});	
	}
}
