package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        joinExam.setOnAction(e->{
            Helper.regNo = regNo.getText();
            Helper.studentName = studentName.getText();

            System.out.println(Helper.subjectName);

            acceptClient();

            try {
                AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/initExam.fxml"));
                Main.mStage.setScene(new Scene(root,800,600));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

/*            Thread t2=new Thread(){
                @Override
                public void run() {

                    boolean x=true;

                    while(x)
                    {
                        try {
                            sleep(20);
                        } catch (InterruptedException e1) {

                            System.out.println("Eki holo putu mara");
                            e1.printStackTrace();
                        }
                        if(Helper.acceptClient)
                        {

                            System.out.println("Eki holo");
                            x=false;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/initExam.fxml"));
                                        Main.mStage.setScene(new Scene(root,800,600));
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            };
            t2.start();*/


        });

    }

    private void acceptClient(){
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    public void backtoHome() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("fxml/welcome.fxml"));
        Main.mStage.setScene(new Scene(root,800,600));
    }
}
