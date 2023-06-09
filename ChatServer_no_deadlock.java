import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Worker implements Runnable {
    Socket sock;
    PrintWriter out;
    BufferedReader in;
    ChatServer chatServer;
    int idx;

    public Worker(Socket s, ChatServer cs) {
        chatServer = cs;
        sock = s;
        idx = chatServer.n;
    }

    public void run() {
        System.out.println("Thread running: " + Thread.currentThread());
        try {
            out = new PrintWriter(sock.getOutputStream(), true);
            assert(out != null);
            synchronized (chatServer.lock) {
            assert(chatServer.workers[idx] == null);
            chatServer.workers[idx] = this;
            }
            System.out.println("Registered worker " + idx + ".");
            in = new BufferedReader(new
                                    InputStreamReader(sock.getInputStream()));
            String s = null;
            while ((s = in.readLine()) != null) {
                chatServer.sendAll("[" + idx + "]" + s);
            }
            sock.close();
        } catch(IOException ioe) {
            System.out.println("Worker thread " + idx + ": " + ioe);
        } finally {
            chatServer.remove(idx);
        }
    }

    public void send(String s) {
        out.println(s);
    }
}

public class ChatServer {
    Worker workers[];
    int n = 0;
    final Object lock = new Object();

    public ChatServer(int maxServ) {
        int port = 4444;
        workers = new Worker[maxServ];
        Socket sock;
        ServerSocket servsock = null;
        try {
            servsock = new ServerSocket(port);
            while (maxServ-- != 0) {
                sock = servsock.accept();
                Worker worker = new Worker(sock, this);
                new Thread(worker).start();
                n++;
            }
            servsock.close();
        } catch(IOException ioe) {
            System.err.println("Server: " + ioe);
        }
        System.out.println("Server shutting down.");
    }

    public static void main(String args[]) throws IOException {
        if (args.length == 0) {
            new ChatServer(-1);
        } else {
            new ChatServer(Integer.parseInt(args[0]));
        }
    }

    public void sendAll(String s) {
        int i;
        synchronized (lock) {
        for (i = 0; i < n; i++) {
            if (workers[i] != null) {
                workers[i].send(s);
            }
        }
        }
    }

    public synchronized void remove(int i) {
        synchronized (lock) {
        workers[i] = null;
        sendAll("Client " + i + " quit.");
        }
    }
}