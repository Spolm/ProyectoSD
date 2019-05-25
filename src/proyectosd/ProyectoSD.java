/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Spoon
 */
public class ProyectoSD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if(args[0].startsWith("server")) {
            try {
                Server server = new Server();
                server.start(new Integer(args[1]), args[2]);
            } catch (IOException ex) {
                System.out.println("Server error");
            }
        } else {
            try {
                Client message = new Client();
                message.startConnection(args[1], new Integer(args[2]));
                
                String response = message.sendMessage(args[3]);
                
                System.out.println("Respuesta server: " + response);
                
            } catch (IOException ex) {
                System.out.println("error client message");
            }
            
            
        
        }
    }
    
}
