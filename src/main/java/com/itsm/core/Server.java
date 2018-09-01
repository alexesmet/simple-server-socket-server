package com.itsm.core;

import com.itsm.util.ThreadSleeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final int messageDelay;
    private final int serverPort;
    private final int maxThreadCount;
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    /*public Server(int serverPort) {
        this.serverPort = serverPort;
        messageDelay = 1000;
        maxThreadCount = 4;
    }*/

    public Server(int messageDelay, int serverPort, int maxThreadCount) {
        this.messageDelay = messageDelay;
        this.serverPort = serverPort;
        this.maxThreadCount = maxThreadCount;

        //TODO: Move the following to @PostConstruct (when it gets repaired)
        executorService = Executors.newFixedThreadPool(maxThreadCount);
        while (true) {
            try {
                serverSocket = new ServerSocket(serverPort);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Could not create serverSocket. Retry in 5 sec");
                ThreadSleeper.sleep(5000);
            }
        }

    }

    @PostConstruct
    public void init() {
        System.out.println("Server.init");
    }

    @PreDestroy
    public void dest() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdownNow();
    }

    public void stop() {
        executorService.shutdownNow();
    }

    public void run() {
        System.out.println("Server is up!");

        while (!executorService.isShutdown()) {

            try {
                Socket accept = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        InputStream is = accept.getInputStream();
                        OutputStream os = accept.getOutputStream();

                        byte[] buff = new byte[is.read()];
                        is.read(buff);
                        String message = new String(buff);


                        String answer = "I have got your message, "; //TODO: Add response processor
                        os.write(answer.length());
                        os.write(answer.getBytes());
                        os.flush();

                        accept.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                System.err.println("Could not socket.accept");
                e.printStackTrace();
            }

        }

        System.out.println("Server was stopped.");
    }

    @Override
    public String toString() {
        return "Server{" +
                "messageDelay=" + messageDelay +
                ", serverPort=" + serverPort +
                ", maxThreadCount=" + maxThreadCount +
                '}';
    }
}
