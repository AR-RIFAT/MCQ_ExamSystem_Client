package sample;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TimeUpdateCheckerThread extends Thread {

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println("Port : "+ serverSocket.getLocalPort());
            Helper.myPort = serverSocket.getLocalPort();

            while (true) {
                System.out.println("Waiting...");
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection : " + socket);

                if(Helper.breakTimeUpdate){
                    break;
                }

                Scanner sc = new Scanner(socket.getInputStream());

                String data = sc.next();
                String ck = data.split("#")[0];
                if(ck.equals("startTime")){
                    Helper.startTime = data.split("#")[1];
                }else if(ck.equals("duration")){
                    Helper.endTime = Helper.customDuration(Integer.parseInt(data.split("#")[1])); // Update Required !!
                }else{
                    Helper.startTime = data.split("#")[1];
                    Helper.endTime = Helper.customDuration(Integer.parseInt(data.split("#")[2]));
                }

                System.out.println("Time Updated");

                Helper.setTime();

                Helper.senderUpdate = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
