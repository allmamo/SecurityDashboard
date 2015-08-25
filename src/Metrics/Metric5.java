/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;
 
import javax.swing.JPanel;

public class Metric5 extends Metrics.Metric{

    public JPanel run() 
    {    
        Metric metric5 = new Metric();
        JPanel m5Panel = metric5.run("Web_Application_Scan_", "BAR", "Plugin Output");
        totalCriticalCount = metric5.totalCriticalCount;
        totalHighCount = metric5.totalHighCount;
        totalMediumCount = metric5.totalMediumCount;
        totalLowCount = metric5.totalLowCount;
        return m5Panel;
    }
}