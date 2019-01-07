package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

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
    private JFXButton answerSheet,checkMcq;
    @FXML
    Button openQues;
    @FXML
    public VBox mcqBox,bak;
    @FXML
    private Label courseCode,courseName,totalQues,Date,startTime,endTime,clock,examStatus,checked,notChecked;

    Calendar calendar;
    int H=0,M=0,Ss=0,Ms=0;
    private String ampm;
    static Boolean Flag=false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->{
            if(Main.mStage.getWidth() > 1100){
                HBox.setMargin(bak,new Insets(0,226,0,0));
            }
            if(Main.mStage.getWidth() < 1100){
                HBox.setMargin(bak,new Insets(0,70,0,0));
            }
        };
        Main.mStage.widthProperty().addListener(stageSizeListener);
        Main.mStage.heightProperty().addListener(stageSizeListener);

        courseCode.setText(": "+Helper.courseCode);
        courseName.setText(": "+Helper.examTitle);
        totalQues.setText(": "+Integer.toString(Helper.totalQues));

        String ddate = new Date().toString();

        String[] ardate = ddate.split(" ");

        ddate = ardate[0]+" "+ardate[1]+" "+ardate[2]+" "+ardate[5];

        Date.setText(": "+ddate);

        calendar=Calendar.getInstance();

        Helper.setTime();
        startTime.setText(Helper.StartTime);
        endTime.setText(Helper.EndTime);


        Helper.wait = true;
        Helper.run = false;
        Clock();
        ansSender();

/*        Thread ansSenderThread = new Thread(){

            @Override
            public void run() {
                int min;

                System.out.println("ami shei thread");

                while (true){
                    String ck = Helper.endTime;
                    if(!ck.equals("")){

                        min = Helper.emin;
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (true){
                    String ctime = dateFormatGmt.format(Calendar.getInstance().getTime());

                    int cmin = Integer.parseInt(ctime.substring(3,5));

                    if(cmin == min){
                        answerSheet.fire();
                        System.out.println("he he ami");
                        AnswerSender answerSender = new AnswerSender();
                        answerSender.start();
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        ansSenderThread.start();*/

        Thread updateTime = new Thread(){

            @Override
            public void run() {

                while(true){

                    if(Helper.timeUpdate){

                        Helper.senderUpdate = false;

                        ansSender();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("update Time");

                                startTime.setText(Helper.StartTime);
                                endTime.setText(Helper.EndTime);
                            }
                        });

                        Helper.timeUpdate = false;
                    }

                    if(Helper.breakTimeUpdate){
                        break;
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTime.start();

        List<ToggleGroup> tgList = new ArrayList<>();
        List<StackPane> stpList = new ArrayList<>();
        List<HBox> hboxList = new ArrayList<>();

        Thread mcqLoader = new Thread(){

            @Override
            public void run() {

                while(true){

                    if(!(Helper.totalQues == 0)){
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        int totalMCQ = Helper.totalQues;

                        System.out.println("total "+totalMCQ);

                        for(int i=1;i<=totalMCQ;i++){
                            Helper.MyRadioButton a = new Helper.MyRadioButton("A");
                            Helper.MyRadioButton b = new Helper.MyRadioButton("B");
                            Helper.MyRadioButton c = new Helper.MyRadioButton("C");
                            Helper.MyRadioButton d = new Helper.MyRadioButton("D");

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

                            hb.setAlignment(Pos.CENTER);

                            Insets insp = new Insets(12,12,12,26);

                            Insets insm = new Insets(6,12,6,12);

                            hb.setPadding(insp);

                            String white = "-fx-background-color: rgba(255, 255, 255);";

                            String style1 = "-fx-background-color: rgba( 146, 202, 223 );";

                            String style2 = "-fx-background-color: rgba(52, 152, 219);";

                            hb.setStyle(white);

                            HBox hbCover = new HBox(new Label("Question "+Integer.toString(i)));

                            hbCover.setAlignment(Pos.CENTER);


                            if(i%2 == 0){
                                hbCover.setStyle(style1);
                            }else{
                                hbCover.setStyle(style2);
                            }

                            hboxList.add(hbCover);

                            StackPane stMain = new StackPane(hb,hbCover);
                            stMain.setPrefHeight(66);

                            stpList.add(stMain);

                            mcqBox.getChildren().add(stMain);

                            tgList.add(ansGroup);

                        }
                        for(int i=0;i<totalMCQ;i++){
                            StackPane stp = stpList.get(i);
                            HBox mhb = hboxList.get(i);

                            stp.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
                                if (show) {
                                    mhb.setVisible(false);
                                } else {
                                    mhb.setVisible(true);
                                }
                            });

                        }
                    }
                });
            }
        };

        mcqLoader.start();

        checkMcq.setOnAction(e->{
            String ck="";
            int countck = 0;
            String notck="";
            int countNotck = 0;
            for(int i=1;i<=Helper.totalQues;i++){
                ToggleGroup tgp = tgList.get(i-1);
                try{
                    Helper.MyRadioButton rb = (Helper.MyRadioButton) tgp.getSelectedToggle();
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

        answerSheet.setOnAction(e->{
            ans = "";
            for(int i=0;i<Helper.totalQues;i++){
                ToggleGroup tgp = tgList.get(i);
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

        Main.mStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.NONE, "Current Exam Will be lost. Confirm ?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    // you may need to close other windows or replace this with Platform.exit();
                    try {
                        Helper.breakTimeUpdate = true;
                        new Socket("127.0.0.1", Helper.myPort);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Close Clicked");

                    Main.mStage.close();
                }

            }
        });

    }

    //// ------- Methods ------- /////

    private void Clock() {

        Thread t=new Thread(){


            @Override
            public void run() {

                while(true){

                    if(!Helper.startTime.equals("")){
                        Helper.setTime();

                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("start time"+Helper.startTime);

                while (Helper.wait || Helper.run)
                {
                    /*if(Flag){
                        Flag = false;
                        System.out.println("Clock Restarting");
                        break;
                    }*/
                    try {

                        sleep(800);
                        Date date=new Date();
                        Calendar cal=Calendar.getInstance();
                        H=cal.get(Calendar.HOUR);
                        M=cal.get(Calendar.MINUTE);
                        Ss=cal.get(Calendar.SECOND);

                       /* if(H>=12)
                            H=H-12;*/

                        if(Helper.wait &&(H==Helper.shour && Helper.smin==M))
                        {
                            Ss=run_s;
                            Helper.wait=false;
                            Helper.run=true;
                            Helper.sendFile = true;


                            System.out.println("Time "+Ss);
                            Ms=0;
                            Helper.isQuestionAvailable = true;

                        }
                        if(Helper.run &&(H==Helper.ehour && Helper.emin==M))
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
                                // System.out.println(Helper.wait+" "+Helper.run);

                                int NewH=H;
                                if(H==0)
                                    NewH=12;

                                String time=String.format("%02d",NewH)+" : "+String.format("%02d",M)+" : "+String.format("%02d",Ss);

                                if(Helper.run){
                                    //waitClock.setText(time);
                                    examStatus.setText("Running");
                                }
                                else if(!Helper.run &&!Helper.wait){
                                    //runClock.setText(time);
                                    examStatus.setText("Ended");
                                }
                                else{
                                    //endClock.setText(time);
                                    examStatus.setText("Waiting");
                                }
                                clock.setText(time);
/*                            //    System.out.println(time);
                                if(Helper.wait && !Helper.run)
                                    waitClock.setText(time);
                                else if(!Helper.wait && Helper.run)
                                    runClock.setText(time);
                                else
                                    endClock.setText(time);*/

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();
    }

    private void ansSender(){

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH:mm");
        Thread ansSenderThread = new Thread(){

            @Override
            public void run() {
                int min;

                System.out.println("ami shei thread");

                while (true){
                    String ck = Helper.endTime;
                    if(!ck.equals("")){

                        min = Helper.emin;
                        System.out.println("updated endMin : "+min);
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (true){
                    String ctime = dateFormatGmt.format(Calendar.getInstance().getTime());

                    int cmin = Integer.parseInt(ctime.substring(3,5));

                    if(Helper.senderUpdate){
                        Helper.timeUpdate = true;
                        break;
                    }

                    if(cmin == min){
                        answerSheet.fire();
                        System.out.println("he he ami");
                        AnswerSender answerSender = new AnswerSender();
                        answerSender.start();
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        ansSenderThread.start();
    }

    private void stopClient() throws IOException {
        System.out.println("bondho kori ");
        Helper.acceptClient = false;
        Socket sock = new Socket("127.0.0.1",226);
    }


}
