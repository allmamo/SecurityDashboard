/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;
 
import javax.swing.JPanel;

public class Metric3 extends Metrics.Metric{
    
    public JPanel run() 
    {    
        Metric metric3 = new Metric();
        JPanel m3Panel = metric3.run("Antivirus_Check_Scan_", "RING", "Plugin Output");
        totalCriticalCount = metric3.totalCriticalCount;
        totalHighCount = metric3.totalHighCount;
        totalMediumCount = metric3.totalMediumCount;
        totalLowCount = metric3.totalLowCount;
        
        return m3Panel;
    }
}