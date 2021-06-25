/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecthr;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.transitions.JFXFillTransition;
import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Year;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * FXML Controller class
 *
 * @author itxpa
 */
public class HomePageController implements Initializable {

    int systolic, diastolic = 0, heartrate = 0, chron = 0, nonchron = 0;
    Double bmi = 0.0;
    int i = 0;
    String loginName;
    public int rating = 100;

    public void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.millis(duration), c);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDuration(Duration.millis(duration));
        rt.setRate(3);
        rt.setCycleCount(10);
        rt.play();
    }
    int chronic;
    int nonchronic;

    public void NoOfDiseases(int x, int y) {
        chronic = x;
        nonchronic = y;
    }
    @FXML
    private JFXButton logoutbtn;
    @FXML
    private JFXButton stat;
    @FXML
    private Button bp;
    @FXML
    private Label lblnam;
    @FXML
    private Button hrate;

    @FXML
    private Button tips;
    @FXML
    private Button bmibtn;
    @FXML
    private Circle rcircle;
    @FXML
    private Button ratebtn;
    @FXML
    private Button ddbtn;
    @FXML
    Rectangle rectangle;
    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Health Rater");
        stage.show();

    }

    @FXML
    void bmi(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BMI.fxml"));
        Parent root = loader.load();
        BMIController bmiController = loader.getController();
        bmiController.shownam(loginName);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("BMI Calculator");
        stage.show();

    }

    public void showname(String s) {
        loginName = s;
        lblnam.setText(s);
    }
    @FXML
    Label suggestivetext;
//disease diary
        @FXML
    private ImageView warn;
    void hidekabel()
    {
        suggestivetext.setVisible(false);
      rectangle.setVisible(false);
      warn.setVisible(false);
    }
     
    void setlbl() throws InterruptedException {
        if (rating >= 85) {
            suggestivetext.setText("Your in pretty good shape.Make sure to keep up the good work ");
            suggestivetext.setVisible(true);
            rectangle.setVisible(true);
            warn.setVisible(true);
        }
        if (rating >= 60 && rating < 85) {
            suggestivetext.setText("You are a patient .See your doctors regularly and take your medicines.Wake up early and take a walk ");
            suggestivetext.setVisible(true);
            rectangle.setVisible(true);
            warn.setVisible(true);
        }
        if (rating < 60) {
            suggestivetext.setText("You are not in  good shape .Your health is at risk.Visit a doctor as soon as possible ");
            suggestivetext.setVisible(true);
            rectangle.setVisible(true);
            warn.setVisible(true);
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> hidekabel());
        pause.play();
    }

    @FXML
    void dd(ActionEvent event) throws IOException, SQLException {
//checking for collumn existing in data base named same as username
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        Statement statement = conn.createStatement();
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, "Chronic" + loginName, null);
        if (rs.next()) {
            //Column in table exist
            System.out.println("Table in database exist");
        } else {
            System.out.println("Table in database doesnt exist, adding it");
            String sql = "CREATE TABLE Chronic" + loginName + ""
                    + "(No INT PRIMARY KEY AUTO_INCREMENT, "
                    + " Chronic VARCHAR(100))";
            statement.executeUpdate(sql);
            String sql1 = "CREATE TABLE nonChronic" + loginName + ""
                    + "(No INT PRIMARY KEY AUTO_INCREMENT, "
                    + " nonChronic VARCHAR(100)) ";
            statement.executeUpdate(sql1);
        }

        //Parent root = FXMLLoader.load(getClass().getResource("DD.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DD.fxml"));
        Parent root = loader.load();
        DDController ddController = loader.getController();
        ddController.shownam(loginName);
        ddController.showdtabledisease();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //scene.getStylesheets().add(getClass().getResource("liststyles.css").toExternalForm());
        stage.setTitle("Disease Diary");
        stage.show();
    }

    @FXML
    void showRating(ActionEvent event) throws SQLException, InterruptedException {
        getStats();
        //algo for rating will be here
        rating = 100 - chron * 15;
        rating = rating - rating * nonchron / 200;
        if (bmi > 25 || bmi < 18) {
            if (bmi > 30 || bmi < 15) {
                rating = rating - rating * 10 / 100;
            } else {
                rating = rating - rating * 5 / 100;
            }
        }
        if (systolic != 80 || diastolic != 120) {
            if (systolic < 60 || diastolic > 140) {
                rating = rating - rating * 10 / 100;
            } else {
                rating = rating - rating * 5 / 100;
            }
        }
        if (heartrate < 60 || heartrate > 100) {
            rating = rating - rating * 5 / 100;
        }
        if (rating < 0) {
            rating = 0;
        }
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        String query = "update central set rating = ? where name = ? ";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, rating);
        preparedStmt.setString(2, loginName);
        preparedStmt.executeUpdate();
        preparedStmt.close();
        conn.close();
        setRotate(rcircle, false, 360, 600);
        JFXFillTransition transition = new JFXFillTransition();
        transition.setDuration(Duration.millis(2000));
        transition.setRegion(ratebtn);
        transition.setFromValue(Color.WHITESMOKE);
        if (rating > 60) {
            transition.setToValue(Color.rgb(0, 230, 118));
        } else {
            
            transition.setToValue(Color.rgb(255, 82, 82));
            
        }
        transition.play();
        transition.setOnFinished(e -> {
            try {
                setlbl();
            } catch (InterruptedException ex) {
                Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ratebtn.setText(String.valueOf(i));
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), (ActionEvent event1) -> {
            i++;
            
            ratebtn.setText(String.valueOf(i) + "%");
        }));
        timeline.setCycleCount(rating);
        timeline.play();
        i = 0;
        //delayForTwoSecond();
        //TimeUnit.SECONDS.sleep(2);
        
        
        //suggestivetext.setVisible(false);
    }

    @FXML

    int random() {
        Random rand = new Random();
        int r = rand.nextInt(100);
        return r;
    }
    @FXML
    private AnchorPane rootanchorpane;

    @FXML
    void suggest(ActionEvent event) throws SQLException, IOException {
        /*Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tip", "root", "");
        Statement statement = conn.createStatement();
        int tip = random();
        //System.out.println(tip);
        //String s=String.valueOf(tip);
        String query = "SELECT * FROM tip where no = '" + tip + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String st = (rs.getString("suggestion"));
            JLabel label = new JLabel(st);
            label.setFont(new Font("comic sans", Font.ITALIC, 18));
            ImageIcon icon = new ImageIcon("src/tp.png");

            JOptionPane.showMessageDialog(null, label, "Health tips", JOptionPane.INFORMATION_MESSAGE, icon);

        } */

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Tipsonline.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        TipsonlineController tps = fxmlLoader.getController();
        tps.showtips();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL); //prevents previous window from working when pop up is on
        stage.setTitle("Tips");
        stage.showAndWait();

    }

    @FXML //creating a pop up window for heart rate scale
    void hpopup(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HRATE.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        HRATEController hr = fxmlLoader.getController();
        hr.shownam(loginName);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.setTitle("Heart Rate");
        stage.initModality(Modality.APPLICATION_MODAL); //prevents previous window from working when pop up is on
        stage.showAndWait();

    }

    @FXML
    void gotobp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Pressure.fxml"));
        Parent root2 = (Parent) fxmlLoader.load();
        PressureController hr = fxmlLoader.getController();
        hr.shownam(loginName);
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.setResizable(false);
        stage.setTitle("Pressure");
        stage.initModality(Modality.APPLICATION_MODAL); //prevents prvius window from working when pop up is on
        stage.showAndWait();
    }

    @FXML
    void showStat(ActionEvent event) throws IOException, SQLException {
        String go = "Male";
        int g = 0;
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        //Statement statement = conn.createStatement();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM logininfo where name = ? AND Gender = ?");
        //ResultSet rs = statement.executeQuery( "SELECT * FROM logininfo where name = '"+loginName+"' AND Gender ='" + go + "'");
        stm.setString(1, loginName);
        stm.setString(2, "Male");
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            g = 1;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Stat.fxml"));
        Parent root = loader.load();
        StatController statController = loader.getController();
        statController.shownameandgender(loginName, g);
        statController.getInfo();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Statistics");
        stage.show();
    }
    Double height;
    int weight;
    int ye;
    int age;

    void getStats() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chronic", "root", "");
        Statement statement = conn.createStatement();
        ResultSet resultset = statement.executeQuery("select * from central where name = '" + loginName + "' ");
        while (resultset.next()) {
            bmi = resultset.getDouble("bmi");
            systolic = resultset.getInt("systolic");
            diastolic = resultset.getInt("diastolic");
            heartrate = resultset.getInt("heartrate");
            chron = resultset.getInt("chronicno");
            nonchron = resultset.getInt("nonchronicno");
            rating = resultset.getInt("rating");
            height = resultset.getDouble("height");
            weight = resultset.getInt("weight");
            ye = resultset.getInt("year");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void delayForTwoSecond() throws InterruptedException {

       // Thread.sleep(2000);

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
