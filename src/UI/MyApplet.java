/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author allenmamo
 */
public class MyApplet extends JApplet {

   @Override
   public void init() {
      try {
         SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
               JFrame myFrame = new SecurityDashboard();
               Container contentPane = myFrame.getContentPane();
               setContentPane(contentPane);
            }
         });
      } catch (InterruptedException e) {
         e.printStackTrace();
      } catch (InvocationTargetException e) {
         e.printStackTrace();
      }
   }
}
