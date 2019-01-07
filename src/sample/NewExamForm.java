package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NewExamForm implements Initializable {

    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();
    @FXML
    JFXButton joinExam;
    @FXML
    JFXTextField regNo,studentName;

    AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        acceptClient();

        try {
            root = FXMLLoader.load(getClass().getResource("fxml/examDashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        joinExam.setOnAction(e->{
            Helper.regNo = regNo.getText();
            Helper.studentName = studentName.getText();

            System.out.println("my name : "+Helper.studentName);

           acceptClient();

            try {
                AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/examDashboard.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root,1024,600));
                stage.show();

                Node source = (Node)  e.getSource();
                Stage stageRoot  = (Stage) source.getScene().getWindow();
                //stage.getOnCloseRequest().handle(null);
                stageRoot.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        });*/

    }

    private void acceptClient(){
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    public void joinButton() throws IOException{
        try {
/*            acceptClient();

            Helper.regNo = regNo.getText();
            Helper.studentName = studentName.getText();

            System.out.println("my name : "+Helper.studentName);

            AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/examDashboard.fxml"));*/
            System.out.println("MMMMMMMMMMM");
            Main.mStage.setScene(new Scene(root,1024,600));

            System.out.println("PPPPPPPPPPPPPPPPPPP");
        }catch (Exception e){
            e.getCause();
        }

    }

    public void backtoHome() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/welcome.fxml"));
        Main.mStage.setScene(new Scene(root,800,600));
    }
}
