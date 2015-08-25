/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;
 
import javax.swing.JPanel;

public class Metric1 extends Metrics.Metric{  
    
    public JPanel run() 
    {    
        Metric metric1 = new Metric();
        JPanel m1Panel = metric1.run("Network_Vulnerability_Scan_", "PIE", "Plugin Output");
        totalCriticalCount = metric1.totalCriticalCount;
        totalHighCount = metric1.totalHighCount;
        totalMediumCount = metric1.totalMediumCount;
        totalLowCount = metric1.totalLowCount;
        deviceCount = metric1.deviceCount;
        
        return m1Panel;
    }
}