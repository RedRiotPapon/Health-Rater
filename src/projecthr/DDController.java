/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class DDController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ObservableList<String> list1 = FXCollections.observableArrayList();
    ObservableList<String> list2 = FXCollections.observableArrayList();
    

    String loginName; //username passed to this scene
    String insertString1;
    String insertString2;
    @FXML
    private JFXListView<String> listview1;

    @FXML
    private JFXListView<String> listview2;
    @FXML
    private JFXButton back;

    @FXML
    private JFXComboBox combox1;

    @FXML
    private Label lblncr;

    @FXML
    private JFXButton btn;
    @FXML
    private JFXComboBox combox2;
    @FXML
    private Label lc;

    @FXML
    private Label lnc;
    @FXML
    private JFXButton del1;
     @FXML
    private AnchorPane pane;
     @FXML
    void delfromDatabase(ActionEvent event) throws SQLException {
         System.out.println("clicked");
         System.out.println(insertString1);
       Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
      String query = "delete from Chronic"+loginName+" where chronic = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString(1, insertString1);
      preparedStmt.execute();
      preparedStmt.close();
         conn.close();
         
         list1.remove(insertString1);
         showdtabledisease();
         update2central();
      
         
    }

    @FXML
    void delfromDatabase1(ActionEvent event) throws SQLException {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
      String query = "delete from nonChronic"+loginName+" where nonchronic = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString(1, insertString2);
      preparedStmt.execute();
      preparedStmt.close();
         conn.close();
         
         list2.remove(insertString2);
         showdtabledisease();
         update2central();
    }


    @FXML
    private JFXButton del11;

    int i = 1;
    int max = 0;
    int rownumberofChronic;
    int rownumberofNonChronic;
    int max1 =1;
    int k = 1;
     void update2central() throws SQLException {
         System.out.println("Updated");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
      String query = "update central set chronicNo = ? , nonChronicno = ? where name = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt   (1, rownumberofChronic);
      preparedStmt.setInt   (2, rownumberofNonChronic);
      preparedStmt.setString   (3,loginName);
      preparedStmt.executeUpdate();
      preparedStmt.close();
      conn.close();
    }
  public  void showdtabledisease() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        Statement statement = conn.createStatement();
        String query = "SELECT MAX(no) FROM Chronic"+loginName+"";
        String q = "select count(*) from Chronic"+loginName+""; // counting the number of rows for chronic
        
        ResultSet resultSets = statement.executeQuery(query);
        //Extact result from ResultSet rs
        while (resultSets.next()) {
            max = resultSets.getInt("MAX(No)"); //max number of primary key
        }
        ResultSet rs = statement.executeQuery(q);
        while (rs.next()) {
            rownumberofChronic = rs.getInt("count(*)"); //Number of rows from the primary collumn 
        }

        while (i <= max) {
            String que = "SELECT * FROM Chronic"+loginName+" where no = '" + i + "'";
            rs = statement.executeQuery(que);
            if (rs.next()) {
                //System.out.println(rs.getString(loginName));
                list1.add(rs.getString("Chronic"));
            }
            i++;
        }
        showdisease2();
        update2central();

    }
     
  
    void showdisease2() throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        Statement statement = conn.createStatement();
        String q1 = "select count(*) from nonChronic"+loginName+"";
        String query = "SELECT MAX(no) FROM nonChronic"+loginName+"";
        ResultSet resultSets = statement.executeQuery(query);
        while (resultSets.next()) {
            max1 = resultSets.getInt("MAX(No)"); //max number of primary key
        }
         ResultSet resultSet1= statement.executeQuery(q1);
         while (resultSet1.next()) {
        rownumberofNonChronic= resultSet1.getInt(1); //maximum Number from the primary collumn table non chronic
 } 
         while (k<=max1){
        String queryx = "SELECT * FROM nonChronic"+loginName+" where no = '" + k + "'";
        ResultSet rs2 = statement.executeQuery(queryx);
        if (rs2.next())
        {
            //System.out.println(rs2.getString(loginName));
            list2.add(rs2.getString("nonChronic"));
        }
        k++;
        }
    } 
    
    
    
   
    @FXML
    void showdisease(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
      /*  Statement statement = conn.createStatement();
        String query = "SELECT MAX(no) FROM test2";
        String q = "select count(*) from test2"; // counting the number of rows for chronic
        
        ResultSet resultSets = statement.executeQuery(query);
        //Extact result from ResultSet rs
        while (resultSets.next()) {
            max = resultSets.getInt("MAX(No)"); //max number of primary key
        }
        ResultSet rs = statement.executeQuery(q);
        while (rs.next()) {
            rownumberofChronic = rs.getInt("count(*)"); //Number of rows from the primary collumn 
        }

        while (i <= max) {
            String que = "SELECT * FROM test2 where no = '" + i + "'";
            rs = statement.executeQuery(que);
            if (rs.next()) {
                System.out.println(rs.getString("Des"));
                list1.add(rs.getString("Des"));
            }
            i++;
        }
        showdisease2();
        update2central(rownumberofChronic,rownumberofNonChronic);
        conn.close();
 */     
       list1.clear();
       list2.clear();
       String query = "delete from Chronic"+loginName+" where no= ?";
       String query2 = "delete from nonChronic"+loginName+" where no = ?";
      for (i=1;i<=max;i++){
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, i);
      preparedStmt.execute();
      preparedStmt.close();
       }
       for (i=1;i<=max1;i++){
      PreparedStatement preparedStmt = conn.prepareStatement(query2);
      preparedStmt.setInt(1 , i);
      preparedStmt.execute();
      preparedStmt.close();
       }
       
      conn.close();
       showdtabledisease();
       update2central();
    }

    @FXML //for showing username
    private Label lblnam;

    public void shownam(String s) {
        loginName = s;
        //lblnam.setText(s);
    }

    @FXML
    void b2h(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();
        
        HomePageController homepageController = loader.getController();
        homepageController.hidekabel();
        homepageController.showname(loginName); //sending username back to home
        homepageController. NoOfDiseases(rownumberofChronic,rownumberofNonChronic); //sending no of diseases back to home
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Health Rater");
        showdtabledisease();
        stage.show();
        System.out.println(max);
    }

    @FXML //gets the value of chronic disease and puts in string and shows it on label
    void comb1(ActionEvent event) {
        String s = combox1.getSelectionModel().getSelectedItem().toString();
        lc.setText(s);
        insertString1 = s;

    }

    @FXML
    void comb2(ActionEvent event) {
        String s = combox2.getSelectionModel().getSelectedItem().toString();
        lnc.setText(s);
        insertString2 = s;
    }

    //insertion time!
    @FXML
    void addChronic(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Chronic"+loginName+" where chronic = '" + insertString1 + "'"); //checking if value alrady exists in the column or not
        if (!rs.next()) {  // if value is unique then insert
            String st = "insert  into Chronic"+loginName+" " + " values(NULL,'" + insertString1 + "')";
            statement.executeUpdate(st);
            list1.add(insertString1);
             update2central();
        }
        
    }

    @FXML
    void addnonChronic(ActionEvent event) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM nonChronic"+loginName+" where nonChronic = '" + insertString2 + "'");
        if (!rs.next()) {
            String st = "insert  into nonChronic"+loginName+" " + " values(NULL,'" + insertString2 + "')";
            statement.executeUpdate(st);
            list2.add(insertString2);
            update2central();
        }
         
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list1.addListener((Observable observable) -> {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        listview1.setItems(list1);
        list2.addListener((Observable observable) -> {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        listview2.setItems(list2);
        ObservableList<String> listforChronic = FXCollections.observableArrayList("Cancer", "Diabetes", "Arthritis", "Asthma","HIV","Epilepsy","Heart disease","Migraine");
        combox1.setItems(listforChronic);
        ObservableList<String> listforNonChronic = FXCollections.observableArrayList("Allergies", "Fever", "Diarrhea", "Pneumonia","Typhoid","Stomach Aches","Nausea","Pink Eye","Chickenpox");
        combox2.setItems(listforNonChronic);
        
         listview1.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            System.out.println("clicked on " + listview1.getSelectionModel().getSelectedItem());
            combox1.setValue(listview1.getSelectionModel().getSelectedItem());
        }
    });
         listview2.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            System.out.println("clicked on " + listview2.getSelectionModel().getSelectedItem());
            combox2.setValue(listview2.getSelectionModel().getSelectedItem());
        }
    });
    }
    //

}
