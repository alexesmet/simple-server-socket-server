import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;

public class Server {

    private static volatile Queue<String> messages = new ArrayDeque<String>();
    private static final int MESSAGE_DELAY = 1000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4434);
        System.out.println("Server is up!");

        new Thread(() -> {
            long lastLog = System.currentTimeMillis();
            while (!Thread.interrupted()) {
                if (messages.size() > 0 && (System.currentTimeMillis()-lastLog >= MESSAGE_DELAY)) {
                    synchronized (messages) {
                        System.out.println("> " + messages.poll());
                    }
                    lastLog = System.currentTimeMillis();
                }
            }

        }).start();
        while (!Thread.interrupted()) {
            Socket accept = serverSocket.accept();



            new Thread(() -> {
                try {
                    InputStream is = accept.getInputStream();
                    OutputStream os = accept.getOutputStream();

                    byte[] buff = new byte[is.read()];
                    is.read(buff);
                    String message = new String(buff);
                    synchronized (messages) {
                        messages.add(message);
                    }

                    String answer = "I have got your message, " + message.split(" ")[0];
                    os.write(answer.length());
                    os.write(answer.getBytes());
                    os.flush();


                    accept.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
