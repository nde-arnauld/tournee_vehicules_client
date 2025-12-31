package principale.model;

import java.io.*;
import java.net.Socket;

public class ClientReseau {
    private static ClientReseau instance;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private ClientReseau() {}

    public static ClientReseau getInstance() {
        if (instance == null) instance = new ClientReseau();
        return instance;
    }

    public boolean connecter(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        
        if (this.socket != null) return true;
        return false;
    }

    public void envoyerMessage(String json) throws IOException {
        if (socket == null || socket.isClosed()) throw new IOException("Non connecté");
        byte[] data = json.getBytes("UTF-8");
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }

    public String recevoirMessage() throws IOException {
        if (socket == null || socket.isClosed()) throw new IOException("Non connecté");
        int taille = in.readInt();
        byte[] buffer = new byte[taille];
        in.readFully(buffer);
        return new String(buffer, "UTF-8");
    }
    
    public Socket getSocket() { return socket; }
}