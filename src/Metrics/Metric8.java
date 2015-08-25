/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;
 
import javax.swing.JPanel;

public class Metric8 extends Metrics.Metric{

    public JPanel run() 
    {    
        Metric metric8 = new Metric();
        JPanel m8Panel = metric8.run("User_Account_Check_Scan_", "BAR", "Plugin Output");
        totalCriticalCount = metric8.totalCriticalCount;
        totalHighCount = metric8.totalHighCount;
        totalMediumCount = metric8.totalMediumCount;
        totalLowCount = metric8.totalLowCount;
        otherCount = metric8.otherCount;
        
        return m8Panel;
    }
}