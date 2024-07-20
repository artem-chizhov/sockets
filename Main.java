package ru.platon;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8000);
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            readLog(e.toString());
            throw new RuntimeException(e);
        }
        PrintWriter out;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            readLog(e.toString());
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            readLog(e.toString());
            throw new RuntimeException(e);
        }

        System.out.println("New connection accepted");

        final String name;
        try {
            name = in.readLine();
        } catch (IOException e) {
            readLog(e.toString());
            throw new RuntimeException(e);
        }
        out.println(String.format("Hi %s, your port is %d", name, clientSocket.getPort()));
    }

    public static void readLog(String strLog) {
        strLog = "\n" + new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime())
                + strLog;
        if (new File("./").canWrite()) {
            try (FileOutputStream fos = new FileOutputStream("log.txt")) {
                byte[] bytes = strLog.getBytes();
                fos.write(bytes, 0, bytes.length);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("нет доступа к записи в коревой каталог.");
        }


    }
}