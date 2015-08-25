/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;
 
import javax.swing.JPanel;

public class Metric6 extends Metrics.Metric{

    public JPanel run() 
    {    
        Metric metric7 = new Metric();
        JPanel m6Panel = metric7.run("Network_Defence_Scan_", "RING", "Plugin Output");
        totalCriticalCount = metric7.totalCriticalCount;
        totalHighCount = metric7.totalHighCount;
        totalMediumCount = metric7.totalMediumCount;
        totalLowCount = metric7.totalLowCount;
        
        return m6Panel;
    }
}