package sample;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    public ServerThread() {

    }

    @Override
    public void run() {

        String stringMyPort = Helper.regNo+"#"+Integer.toString(Helper.myPort);

        Socket socket = null;
        PrintStream ps = null;
        try {
            socket = new Socket(Helper.hostIP,Integer.parseInt(Helper.serverPort));

            InputStream in = null;
            OutputStream out = null;

            ps = new PrintStream(socket.getOutputStream());

            System.out.println(stringMyPort);
            ps.println(stringMyPort);
            ps.println(Helper.studentName);

            Scanner sc = new Scanner(socket.getInputStream());
            System.out.println("Eki holo 3 - "+ Helper.studentName);

            Helper.courseCode = sc.nextLine();
            System.out.println("vuttt");
            Helper.examTitle = sc.nextLine();
            System.out.println("Ami gesi : "+Helper.examTitle);
            Helper.startTime = sc.nextLine();
            Helper.endTime = sc.nextLine();
            Helper.totalQues = sc.nextInt();

            String temp = Helper.endTime;
            System.out.println(Helper.endTime);
            Helper.startTime = temp.substring(0,5);
            Helper.endTime = temp.substring(5,temp.length());
            Helper.setTime();

            System.out.println("Ami start time ?? : "+Helper.startTime);
            System.out.println("Ami ?? : "+Helper.endTime);

            System.out.println(Helper.totalQues);

            System.out.println(Helper.courseCode);



            //Helper.acceptClient = true;

            try {
                in = new BufferedInputStream(socket.getInputStream());
                String x=System.getProperty("user.home");

                out = new FileOutputStream(x+"/Desktop/"+Helper.courseCode+".pdf");

                BufferedOutputStream bos = new BufferedOutputStream(out);

                System.out.println(x);

                byte[] bytes = new byte[16];

                int count;
                while ((count = in.read(bytes)) > 0) {
                    System.out.println("v  : "+count);
                    bos.write(bytes, 0, count);
                }
                //IOUtils.copyLarge(in,bos);

                System.out.println("File Done");

                in.close();
                bos.close();

            } catch (Exception ex) {
                System.out.println("File not found. ");
            }

            String defaultAns = "";
            for(int i=0;i<Helper.totalQues;i++){
                defaultAns+="x";
            }
            Helper.ans = defaultAns;

/*            while (true){
                if(sc.hasNextLine()){
                    String data = sc.nextLine();
                    System.out.println(data);
                    String ck = data.split("#")[0];
                    if(ck.equals("startTime")){
                        Helper.startTime = data.split("#")[1];
                    }else if(ck.equals("duration")){
                        Helper.endTime = data.split("#")[1]; // Update Required !!
                    }else{
                        Helper.startTime = data.split("#")[1];
                        Helper.endTime = data.split("#")[2];
                    }

                    System.out.println("Time Updated");
                    Helper.timeUpdate = true;
                    if(data.equals("close")){
                        break;
                    }
                }
            }*/


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("hellllo");
        }


    }
}
