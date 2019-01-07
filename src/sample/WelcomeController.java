package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    JFXButton newExam;

    @FXML
    JFXTextField hostAdd,port,regNo,studentName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new TimeUpdateCheckerThread().start();

        newExam.setOnAction(e->{

            if(hostAdd.getText().length() > 8){
                Helper.hostIP = hostAdd.getText();
                Helper.serverPort = port.getText();
                Helper.regNo = regNo.getText();
                Helper.studentName = studentName.getText();
                ServerThread serverThread = new ServerThread();
                serverThread.start();
                System.out.println(Helper.regNo+" ## "+Helper.studentName);
                try {
                    AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/examDashboard.fxml")); //newExamForm
                    Main.mStage.setScene(new Scene(root,1024,600));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Give A Valid Host Ip Address ", ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
                hostAdd.requestFocus();
            }
        });

    }
}
