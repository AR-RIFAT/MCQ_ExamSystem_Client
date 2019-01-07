package sample;

import javafx.event.ActionEvent;
import javafx.scene.AmbientLight;
import javafx.scene.control.RadioButton;

import java.util.ArrayList;

import static com.sun.javafx.event.EventUtil.fireEvent;

public class Helper {

    public static String serverPort = "";

    public static int myPort = 0;

    public static String hostIP;

    public static boolean acceptClient = false ;

    public static boolean sendFile = false;

    public static boolean isQuestionAvailable = false;

    public static String courseCode, examTitle,quesPath,startTime="",endTime="",regNo,studentName;

    public static int totalQues=0,shour,ehour,emin,smin;

    public static ArrayList<String> students=new ArrayList<>();
    public static boolean wait=true,run=false,timeUpdate=false, breakTimeUpdate = false, senderUpdate = false;

    public static String ans,StartTime,EndTime;

    public static void setTime()
    {

        shour = Integer.parseInt(Helper.startTime.toString().substring(0,2));
        smin = Integer.parseInt(Helper.startTime.toString().substring(3,5));
        ehour = Integer.parseInt(Helper.endTime.toString().substring(0,2))+shour;

        String AMPM="AM";
        if(ehour>=12) {
            ehour -= 12;
            AMPM="PM";
        }
        emin = Integer.parseInt(Helper.endTime.toString().substring(3,5))+smin;
        if(emin>=60)
        {
            ehour+=1;
            emin-=60;
            if(ehour>=12)
                ehour-=12;

        }

        if(shour>=12) {
            shour -= 12;
            AMPM="PM";
        }

        int NewSH=shour,NewEH=ehour;
        if(shour==0)
            NewSH=12;
        if(ehour==0)
            NewEH=12;

        StartTime=String.format("%02d",NewSH)+" : "+String.format("%02d",smin)+" "+AMPM;
        EndTime=String.format("%02d",NewEH)+" : "+String.format("%02d",emin)+" "+AMPM;
    }

    public static String customDuration(int a){
        String custom = "";
        if(a>60){
            int res = a/60;
            int mod = a%60;
            if(mod>9) custom = "0"+res+":"+mod;
            else custom = "0"+res+":"+"0"+mod;
        }else{
            if(a>9) custom = "00"+":"+a;
            else custom = "00"+":"+"0"+a;
        }
        return custom;
    }

    public static class MyRadioButton extends RadioButton {
        public MyRadioButton() {
        }

        public MyRadioButton(String text) {
            super(text);
        }
        @Override
        public void fire() {
            if (!isDisabled()) {
                setSelected(!isSelected());
                fireEvent(new ActionEvent());
            }
        }
    }

}
