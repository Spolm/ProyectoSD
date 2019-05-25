/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosd;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Spoon
 */
public class Thread implements Runnable {
    private String value = null;
    private String message = null;
    private Integer seconds = 10;
    public Runnable callback = null;
    
    public void setValue(String value) {
        this.value = value;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
    
    public void setRunnable(Runnable r){
        this.callback = r;
    }

    
    @Override
    public void run() {

        try {
            if (this.seconds > 0) {
                TimeUnit.SECONDS.sleep(10);
            }
            
            Client sendmessage = new Client();
            sendmessage.startConnection(value.substring(5), new Integer(value.substring(0, 4)));
            sendmessage.sendMessage(this.message);
            callback.run();

        } catch (InterruptedException ex) {
            System.out.println("Error (1001): sending -- " + value.substring(5) + " , " + value.substring(0, 4) + " , " + this.message);//Logger.getLogger(SendMessageThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Error (1002): sending -- " + value.substring(5) + " , " + value.substring(0, 4) + " , " + this.message);
        }
    }
}
