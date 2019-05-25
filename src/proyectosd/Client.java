/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Spoon
 */
public class Client {
    
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    
    public String sendMessage(String msg) throws IOException {
        
        out.println(msg);
        String res = in.readLine();
        return res;
    }
 
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    
}
