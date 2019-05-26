/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
    private File arch = null;
    private FileReader pf = null;
    private BufferedReader bf = null;

    
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
    
    
    public void lecturaInventario(String nameF) throws FileNotFoundException, IOException {
        System.out.println(nameF);
        String dato = nameF;
        nameF = dato.substring(15, 19);
        int contador = 3;
        String iniciales = nameF.substring(0,3);
//        int iden = Integer.parseInt(nameF.substring(3));
        int iden = 0;
        while (contador != 0){
            
            String narch = Integer.toString(iden+1);
            arch = new File(iniciales+narch+".txt");
            pf = new FileReader(arch);
            bf = new BufferedReader(pf);
            String info;
            while ((info = bf.readLine())!=null){
                System.out.println(info); 
            }
            contador--;
            iden +=1;
            pf.close();
        }

    }
    
    
        public void lecturaInventarioPorTienda(String nameF) throws FileNotFoundException, IOException {
        System.out.println(nameF);
        String dato = nameF;
        nameF = dato.substring(15, 19);
        int contador = 3;
        String iniciales = nameF.substring(0,3);
        int iden = Integer.parseInt(nameF.substring(3));
        while (contador != 0){
            
            
            if(iden == 4){
                System.out.println("    Revisar:"+iden);
                iden = 1;
            }
            String narch = Integer.toString(iden);
            arch = new File(iniciales+narch+".txt");
            pf = new FileReader(arch);
            bf = new BufferedReader(pf);
            String info;
            while ((info = bf.readLine())!=null){
                System.out.println(info); 
            }
            contador--;
            iden +=1;

            pf.close();
        }

    }
    
    
    public void guardarInventario(String nameF) throws IOException{
        File arch = new File(nameF+".txt");
        FileWriter pf = new FileWriter(arch);
        BufferedWriter bf = new BufferedWriter(pf);
        pf.append(nameF);
        pf.close();
    }
    
}
