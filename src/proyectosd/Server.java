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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Spoon
 */
public class Server {
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private HashMap<String, String> store = new HashMap<>();
    private String name = "";
    
    public void start(int port, String name) throws IOException {

        System.out.println("Im listening ... on " + port + " I'm " + name);
        this.name = name;

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        if (greeting.startsWith("regparticipante")) {
            //guardarEstado(greeting);
            int totallength = "regparticipante".length();

            String partipante = greeting.substring(totallength, totallength + 4);
            String puerto = greeting.substring(totallength + 4, totallength + 8);
            String ip = greeting.substring(totallength + 8);
            
            // agregar participante
            this.store.put(partipante, puerto + ":" + ip);

            // paseo por cada uno de los elementos de los participantes para enviar la lista
            for (Map.Entry<String, String> entry : store.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (!key.equals(this.name)) {

                    Client sendmessage = new Client();
                    sendmessage.startConnection(value.substring(5), new Integer(value.substring(0, 4)));
                    //sendmessage.sendMessage("actualizalista" + this.serializarLista());
                    
                }

            }

            out.println("agregadaTienda");

        } 
             else if (greeting.startsWith("actualizalista")) {
            
            String lista = greeting.substring("actualizalista".length());

            String[] listatmp = lista.split(",");
            this.store = new HashMap<String, String>();
            for (String tmp : listatmp) {
                String[] finaltmp = tmp.split("#");
                this.store.put(finaltmp[0], finaltmp[1]);
                //guardarEstado(tmp);
            }

        } else {
            System.out.println("Mensaje no reconocido");
            out.println("Mensaje corrupto");
        }

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        
        this.start(port, name);

    }
    
}
