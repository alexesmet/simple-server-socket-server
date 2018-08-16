import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 4434;
    private static final String NAME = "Mr.Smith";

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Client <your message>");
            System.exit(1);
        }

        try {
            Socket socket = new Socket(HOST, PORT);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();


            String message = "{\"name\":\"" + NAME + "\",\"message\":\"" + args[0] + "\"}";
            os.write(message.length());
            os.write(message.getBytes());
            os.flush();
            System.out.println("Sent: " + message);

            byte[] buff = new byte[is.read()];
            is.read(buff);
            String answer = new String(buff);
            System.out.println("Server: " + answer);

            socket.close();

        } catch (java.net.ConnectException ce) {
            System.err.println("ERROR: Looks like server is not running. Start the server first.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}
