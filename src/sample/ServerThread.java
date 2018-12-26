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
            socket = new Socket(Helper.hostIP,Integer.parseInt(Helper.port));

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
            System.out.println("Ami gesi : "+Helper.subjectName);
            Helper.startTime = sc.nextLine();
            Helper.endTime = sc.nextLine();
            Helper.totalQues = sc.nextInt();

            String temp = Helper.endTime;
            System.out.println(Helper.endTime);
            Helper.startTime = temp.substring(0,5);
            Helper.endTime = temp.substring(5,temp.length());

            System.out.println("Ami start time ?? : "+Helper.startTime);
            System.out.println("Ami ?? : "+Helper.endTime);

            System.out.println(Helper.totalQues);

            System.out.println(Helper.courseCode);



            //Helper.acceptClient = true;

            try {
                in = new BufferedInputStream(socket.getInputStream());
                String x=System.getProperty("user.home");

                out = new FileOutputStream(x+"/Desktop/"+Helper.courseCode+".pdf");
                System.out.println(x);

                byte[] bytes = new byte[1];

                int count;
                while ((count = in.read(bytes)) > 0) {
                    System.out.println("v  : "+count);
                    out.write(bytes, 0, count);
                }

                System.out.println("File Done");

                out.flush();
                in.close();
                out.close();

            } catch (Exception ex) {
                System.out.println("File not found. ");
            }

/*            while (true)
            {
                if(Helper.sendFile)
                {
                    try {
                        in = new BufferedInputStream(socket.getInputStream());
                        String x=System.getProperty("user.home");

                        out = new FileOutputStream(x+"/Desktop/"+Helper.courseCode+".pdf");
                        System.out.println(x);

                        byte[] bytes = new byte[1];

                        int count;
                        while ((count = in.read(bytes)) > 0) {
                            System.out.println("v  : "+count);
                            out.write(bytes, 0, count);
                        }

                        System.out.println("File Done");

                        out.flush();
                        in.close();
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
            }*/

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("hellllo");
        }


    }
}
