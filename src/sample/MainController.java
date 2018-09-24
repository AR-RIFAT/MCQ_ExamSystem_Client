package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MainController implements Initializable {

    private Desktop desktop = Desktop.getDesktop();

    private static int wait_s,run_s;

    String ans;

    @FXML
    private JFXButton openQues,answerSheet,checkMcq;
    @FXML
    public VBox mcqBox;
    @FXML
    private Label waitClock,runClock,endClock,checked,notChecked;

  int H=0,M=0,Ss=0,Ms=0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



      //  remainingTime();

        Thread t=new Thread(){


            @Override
            public void run() {
                int shour = Integer.parseInt(Helper.startTime.substring(0,2));
                int smin = Integer.parseInt(Helper.startTime.substring(3,5));
                int ehour = Integer.parseInt(Helper.endTime.substring(0,2));
                int emin = Integer.parseInt(Helper.endTime.substring(3,5));


                while (Helper.wait || Helper.run)
                {
                    try {

                        sleep(800);
                        Date date=new Date();
                        H=date.getHours();
                        M=date.getMinutes();
                        Ss=date.getSeconds();

                        if(H>=12)
                            H=H-12;

                        if(Helper.wait &&((H==shour && smin==M)||((H>shour || (M>smin && H==shour)))))
                        {
                            Ss=run_s;
                            Helper.wait=false;
                            Helper.run=true;
                            Helper.sendFile = true;


                            System.out.println("Time "+Ss);
                            Ms=0;
                            Helper.isQuestionAvailable = true;

                        }
                        if(Helper.run &&((H==ehour && emin==M)||((H>ehour || (M>emin && H==ehour)))))
                        {
                            Helper.run=false;
                            //set ended exam;
                        }

                       /* if(Ms==660)
                        {
                            Ms=0;
                            Ss=Ss-1;
                        }

                        Ms=Ms+1;*/

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                 System.out.println(Helper.wait+" "+Helper.run);

                                String time=Integer.toString(H)+" : "+Integer.toString(M)+" : "+Integer.toString(Ss);

                                System.out.println(time);
                                if(Helper.wait && !Helper.run)
                                    waitClock.setText(time);
                                else if(!Helper.wait && Helper.run)
                                    runClock.setText(time);
                                else
                                    endClock.setText(time);


                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();

        List<ToggleGroup> tg = new ArrayList<>();

        int totalMCQ = Helper.totalQues;

        System.out.println("total "+totalMCQ);

        for(int i=1;i<=totalMCQ;i++){
            RadioButton a = new RadioButton("A");
            RadioButton b = new RadioButton("B");
            RadioButton c = new RadioButton("C");
            RadioButton d = new RadioButton("D");

            ToggleGroup ansGroup = new ToggleGroup();

            a.setToggleGroup(ansGroup);
            b.setToggleGroup(ansGroup);
            c.setToggleGroup(ansGroup);
            d.setToggleGroup(ansGroup);

            Insets insets = new Insets(0,60,0,20);
            a.setPadding(insets);
            b.setPadding(insets);
            c.setPadding(insets);
            d.setPadding(insets);

            HBox hb = new HBox(new Label("Ques No. " + Integer.toString(i)+" :  "),a,b,c,d);

            Insets insp = new Insets(12,12,12,26);

            Insets insm = new Insets(6,12,6,12);

            hb.setPadding(insp);

            String style1 = "-fx-background-color: rgba(174, 214, 241);";

            String style2 = "-fx-background-color: rgba(253, 254, 254);";

            if(i%2 == 0){
                hb.setStyle(style1);
            }else{
                hb.setStyle(style2);
            }

            mcqBox.getChildren().add(hb);

            tg.add(ansGroup);

        }

        checkMcq.setOnAction(e->{
            String ck="";
            int countck = 0;
            String notck="";
            int countNotck = 0;
            for(int i=1;i<=Helper.totalQues;i++){
                ToggleGroup tgp = tg.get(i-1);
                try{
                    RadioButton rb = (RadioButton) tgp.getSelectedToggle();
                    rb.getText();
                    ck+=Integer.toString(i)+", ";
                    countck++;
                }catch(NullPointerException en){
                    notck+=Integer.toString(i)+", ";
                    countNotck++;
                }
            }

            checked.setText("Checked ("+Integer.toString(countck)+") -> "+ck);
            notChecked.setText("Remaining ("+Integer.toString(countNotck)+") -> "+notck);

        });

        ans = Helper.regNo;

        answerSheet.setOnAction(e->{
            for(int i=0;i<totalMCQ;i++){
                ToggleGroup tgp = tg.get(i);
                try{
                    RadioButton rb = (RadioButton) tgp.getSelectedToggle();
                    ans+=rb.getText();
                    System.out.println(i+1 + " :  "+ rb.getText());
                }catch(NullPointerException en){
                    ans+="X";
                    System.out.println(i+1 + " :  X" );
                }
            }
            Helper.ans = ans;
        });

        openQues.setOnAction(e->{
            if(Helper.isQuestionAvailable){
                File file = new File(System.getProperty("user.home")+"/Desktop/"+Helper.courseCode+".pdf");
                try {
                    desktop.open(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Question nai. Pore ashen ", ButtonType.OK);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    alert.close();
                }
            }
        });

    }

    //// ------- Methods ------- /////

    private void stopClient() throws IOException {
        System.out.println("bondho kori ");
        Helper.acceptClient = false;
        Socket sock = new Socket("127.0.0.1",226);
    }

    private void remainingTime(){

        String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
        int h = Integer.parseInt(timeStamp.substring(0,2));
        int m = Integer.parseInt(timeStamp.substring(2,4));
        int s = Integer.parseInt(timeStamp.substring(4,6));
        int shour = Integer.parseInt(Helper.startTime.substring(0,2));
        int smin = Integer.parseInt(Helper.startTime.substring(3,5));
        int ehour = Integer.parseInt(Helper.endTime.substring(0,2));
        int emin = Integer.parseInt(Helper.endTime.substring(3,5));



        if(h>=12)
            h=h-12;
        if(shour>=12)
            shour=shour-12;
        if(ehour>=12)
            ehour=ehour-12;



        wait_s=((shour*3600+smin*60)-(h*3600+m*60+s));
        if(wait_s<0) {
            wait_s = 0;

        }
        Ss=wait_s;
        run_s=((ehour*3600+emin*60)-(shour*3600+smin*60));

    }

}
