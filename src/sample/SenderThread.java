package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SenderThread extends Thread {

    Socket socket;

    public SenderThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {

        while(true){
            if(Helper.sendFile){
                String s = "File nao .";
                System.out.println(s);
                PrintStream ps = null;
                try {
                    ps = new PrintStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ps.println(s);
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

/*

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;

        while(true){
                // send file
                try{
                if(true){
                try{
                File myFile = new File (FILE_TO_SEND);
                byte [] mybytearray  = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray,0,mybytearray.length);
                os = sock.getOutputStream();
                System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
                System.out.println("Done.");
                }catch (Exception e){
                System.out.println("Vul hoise... Mara Khaiso ...");
                }finally {
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (sock!=null) sock.close();
                }
                }
                }catch(Exception e) {

                }
                }*/
