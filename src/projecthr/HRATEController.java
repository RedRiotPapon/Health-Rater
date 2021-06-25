/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

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
public class HRATEController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int myHeartrate;
     @FXML
    private Slider slider;

    @FXML
    private Label label;

    @FXML
    private Button confirm;
    String loginName;
    public void shownam (String s)
    {   loginName = s;
       
    }

    @FXML
    void updateHr(ActionEvent event) throws SQLException {
        myHeartrate = (int) slider.getValue();
 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
      String query = "update central set HeartRate = ? where name = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt   (1, myHeartrate);
      preparedStmt.setString(2,loginName);
      preparedStmt.executeUpdate();
      preparedStmt.close();
         conn.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       myHeartrate = (int) slider.getValue();
		label.setText(Integer.toString(myHeartrate));
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {
				
				myHeartrate = (int) slider.getValue();
				label.setText(Integer.toString(myHeartrate));
				
			}			
		});		
	}
}
