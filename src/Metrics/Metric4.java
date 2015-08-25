/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;
 
import javax.swing.JPanel;

public class Metric4 extends Metric{
    
    public JPanel run() 
    {    
        Metric metric4 = new Metric();
        JPanel m4Panel = metric4.run("Malware_Detection_Scan_", "PIE", "Plugin Output");
        totalCriticalCount = metric4.totalCriticalCount;
        totalHighCount = metric4.totalHighCount;
        totalMediumCount = metric4.totalMediumCount;
        totalLowCount = metric4.totalLowCount;
        return m4Panel;
    }
}