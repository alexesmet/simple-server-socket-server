package com.itsm.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsm.parse.JsonRequest;
import com.itsm.parse.JsonResponse;
import com.itsm.processors.RequestProcessor;
import com.itsm.util.ThreadSleeper;
import javax.inject.Provider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final int messageDelay;
    private final int serverPort;
    private final int maxThreadCount;
    private final ObjectMapper objectMapper;
    private final Provider<List<RequestProcessor>> requestProcessorProvider;
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public Server(int serverPort, Provider<List<RequestProcessor>> requestProcessorProvider) {
        this.serverPort = serverPort;
        this.objectMapper = new ObjectMapper();
        this.requestProcessorProvider = requestProcessorProvider;
        messageDelay = 1000;
        maxThreadCount = 4;
    }

    public Server(int messageDelay, int serverPort, int maxThreadCount, ObjectMapper objectMapper, Provider<List<RequestProcessor>> requestProcessorProvider) {
        this.messageDelay = messageDelay;
        this.serverPort = serverPort;
        this.maxThreadCount = maxThreadCount;
        this.objectMapper = objectMapper;
        this.requestProcessorProvider = requestProcessorProvider;
    }

    @PostConstruct
    public void init() {
        System.out.println("# Debug: Server.init");

        executorService = Executors.newFixedThreadPool(maxThreadCount);
        while (true) {
            try {
                serverSocket = new ServerSocket(serverPort);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("# Could not create serverSocket. Retry in 5 sec");
                ThreadSleeper.sleep(5000);
            }
        }
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
        System.out.println("# Server is up!");

        while (!executorService.isShutdown()) {

            try {
                Socket accept = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        InputStream is = accept.getInputStream();
                        OutputStream os = accept.getOutputStream();
                        DataInputStream dis = new DataInputStream(is);
                        DataOutputStream dos = new DataOutputStream(os);

                        JsonRequest request = objectMapper.readValue(dis.readUTF(), JsonRequest.class);

                        JsonResponse response = null;

                        List<RequestProcessor> requestProcessors = requestProcessorProvider.get();
                        for (RequestProcessor rp:requestProcessors) {
                            if(rp.canProcess(request)){
                                response = rp.process(request);
                                break;
                            }
                        }

                        if (response == null) {
                            response = new JsonResponse("We can't process such kind of a message yet.");
                        }

                        String answer = objectMapper.writeValueAsString(response);
                        dos.writeUTF(answer);
                        dos.flush();

                        accept.close();

                        ThreadSleeper.sleep(messageDelay);
                        System.out.println("> "+request.getName()+": "+request.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                System.err.println("# Could not socket.accept");
                e.printStackTrace();
            }

        }

        System.out.println("# Server was stopped.");
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
