package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    public ServerThread() {

    }

    @Override
    public void run() {
        Socket socket = null;
        PrintStream ps = null;
        try {
            socket = new Socket(Helper.hostIP,226);

            InputStream in = null;
            OutputStream out = null;

            ps = new PrintStream(socket.getOutputStream());

            System.out.println(Helper.regNo);
            ps.println(Helper.regNo);
            ps.println(Helper.studentName);


            Scanner sc = new Scanner(socket.getInputStream());
            System.out.println("Eki holo 3");

            Helper.courseCode = sc.nextLine();
            System.out.println("vuttt");
            Helper.subjectName = sc.nextLine();
            Helper.startTime = sc.nextLine();
            Helper.endTime = sc.nextLine();
            Helper.totalQues = sc.nextInt();

            System.out.println(Helper.totalQues);

            System.out.println(Helper.courseCode);



            Helper.acceptClient = true;

            while (true)
            {
                if(Helper.sendFile)
                {
                    try {
                        in = socket.getInputStream();
                        String x=System.getProperty("user.home");

                        out = new FileOutputStream(x+"/Desktop/"+Helper.courseCode+".pdf");
                        System.out.println(x);

                        byte[] bytes = new byte[16*1024];

                        int count;
                        while (true) {
                            count = in.read(bytes);
                            System.out.println("v  : "+count);
                            if(count < 16380){
                                out.write(bytes, 0, count);
                                break;
                            }else{
                                out.write(bytes, 0, count);
                            }

                        }

                        System.out.println("File Done");
                        //in.close();
                        out.close();
                        break;

                    } catch (Exception ex) {
                        System.out.println("File not found. ");
                    }

                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("hellllo");
        }

        System.out.println("Tumi amar");

        while(true){

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!Helper.wait && !Helper.run){
                try {
                    System.out.println(Helper.ans);
                    ps.println(Helper.ans);
                    System.out.println("Answer disi ..");
                    //ps.close();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
