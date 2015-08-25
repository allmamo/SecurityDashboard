/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;
 
import javax.swing.JPanel;

public class Metric2 extends Metrics.Metric{

    public JPanel run() 
    {    
        Metric metric2 = new Metric();
        JPanel m2Panel = metric2.run("Unpatched_Software_Scan_", "BAR", "Plugin Output");
        totalCriticalCount = metric2.totalCriticalCount;
        totalHighCount = metric2.totalHighCount;
        totalMediumCount = metric2.totalMediumCount;
        totalLowCount = metric2.totalLowCount;
        
        return m2Panel;
    }
}