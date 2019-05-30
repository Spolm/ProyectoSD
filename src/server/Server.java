/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.Client;
import common.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private ArrayList<Product> products;
    private HashMap<String, String> store = new HashMap<>();
    private String name = "";
    public String storeName;
    public void start(int port, String name) throws IOException {
        
        System.out.println("Im listening ... on " + port + " I'm " + name);
        this.name = name;

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        if (greeting.startsWith("regtienda")) {
            //guardarEstado(greeting);
            int totallength = "regtienda".length();

            String tienda = greeting.substring(totallength, totallength + 4);
            String puerto = greeting.substring(totallength + 4, totallength + 8);
            String ip = greeting.substring(totallength + 8);
            
            // agregar tienda
            this.store.put(tienda, puerto + ":" + ip);

            // paseo por cada uno de los elementos de las tiendas para enviar la lista
            for (Map.Entry<String, String> entry : store.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (!key.equals(this.name)) {

                    Client sendmessage = new Client();
                    sendmessage.startConnection(value.substring(5), new Integer(value.substring(0, 4)));
                    sendmessage.sendMessage("actualizalista" + this.serializarLista());
                    
                }

            }

            out.println("agregadaTienda");
            //guardarEstado("Tiendas");
        } else if (greeting.startsWith("addproducto")) {

            int totallength = "addproducto".length();

            String codProducto = greeting.substring(totallength, totallength + 3);
            System.out.println("Codigo:" + codProducto);
            String cantidadProducto = greeting.substring(totallength + 4, totallength + 6);
            System.out.println("cantidad: " + cantidadProducto);
            String producto = codProducto+"#"+cantidadProducto;
            //this.tengopapa = true;
            
            actualizarInventario("Inventario"+this.name, producto);
            
            System.out.println("Agregado producto: "+ "codprod en la tienda "+ this.name);
            // agregar participante

        } else if (greeting.startsWith("listarproductosempresa")) {
            int totallength = "listarproductosempresa".length();
            storeName = greeting.substring(totallength, totallength + 4);
            String nombre = storeName.substring(0,3);
            String index = storeName.substring(nombre.length());
            int indice = Integer.parseInt(index);
            int contador = 3;
            while (contador != 0){
                index = Integer.toString(indice);
                File arch = new File("Inventario"+nombre+index+".txt");
                BufferedReader bf;
                try (FileReader pf = new FileReader(arch)) {
                    bf = new BufferedReader(pf);
                    String info;
                    while ((info = bf.readLine()) != null) {
                        System.out.println(info);
                    }   contador--;
                    indice++;
                }
                bf.close();
            }
        } else if (greeting.startsWith("listarproductosportienda")) {
            int totallength = "listarproductosportienda".length();
            storeName = greeting.substring(totallength, totallength + 4);
                File arch = new File("Inventario"+storeName+".txt");
                BufferedReader bf;
            try (FileReader pf = new FileReader(arch)) {
                bf = new BufferedReader(pf);
                String info;
                ArrayList<String> response = new ArrayList();
                while ((info = bf.readLine()) != null) {
                    System.out.println(info);
                    response.add(info);
                }
                    out.println(response);
            }
            bf.close();
            
        } else if (greeting.startsWith("actualizalista")) {
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
        Server.this.guardarEstado(this.name);
        this.start(port, name);

    }
    
    private String serializarLista() {

        String finalLista = "";

        for (Map.Entry<String, String> entry : store.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            finalLista += key + "#" + value + ",";
        }

        return finalLista;
    }
    
    public void guardarEstado(String nombreArchivo){
        File f;
        f = new File(nombreArchivo + ".txt");
        
        try{
            FileWriter w = new FileWriter(f);
            PrintWriter pw = new PrintWriter(w);
            pw.println(serializarLista());
            pw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
        public void actualizarInventario(String nombreArchivo, String producto){
        File f;
        f = new File(nombreArchivo + ".txt");
        
        try{
            FileWriter w = new FileWriter(f, true);
            PrintWriter pw = new PrintWriter(w);
            pw.println(producto);
            pw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean leerEstado(String nombreArchivo){
        try{
            File file = new File(nombreArchivo +".txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int i = 0;
            while ((st = br.readLine()) != null){
                System.out.println(st);
                if(i == 0){
                    String[] listatmp = st.split(",");
                    this.store = new HashMap<String, String>();
                for (String tmp : listatmp) {
                    String[] finaltmp = tmp.split("#");
                    this.store.put(finaltmp[0], finaltmp[1]);
                }    
            }
            i++;
        }
        } catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
