package sample;

import java.io.PrintStream;
import java.net.Socket;

public class AnswerSender extends Thread {
    @Override
    public void run() {

        Thread.currentThread().setName("AnsSender");

        try {

            Socket socket = new Socket(Helper.hostIP,Integer.parseInt(Helper.port));

            PrintStream ps = new PrintStream(socket.getOutputStream());

            String st = Helper.regNo + "#" + Helper.ans;
            ps.println(st);

            socket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}