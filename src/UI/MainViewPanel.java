/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Metrics.Metric1;
import Metrics.Metric2;
import Metrics.Metric3;
import Metrics.Metric4;
import Metrics.Metric5;
import Metrics.Metric6;
import Metrics.Metric7;
import Metrics.Metric8;
import Visuals.BarChart;
import Visuals.PieChart;
import Visuals.RingChart;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

/**
 *
 * @author allenmamo
 */
public class MainViewPanel 
{
    
    private final Color chartBackgroundColor = new Color(210, 234, 243);
    
    public ChartPanel getPanel1(Metric1 m1){
        
        int totalRiskCount = m1.totalCriticalCount+m1.totalHighCount+m1.totalLowCount+m1.totalMediumCount;
        String [][] risks = new String [totalRiskCount][3];
        PieChart pieChart = new PieChart(m1.totalCriticalCount, m1.totalHighCount, m1.totalMediumCount, m1.totalLowCount, "", risks, true);                 
        
        ChartPanel thisChart = pieChart.drawPieChart();
        thisChart.setBackground(Color.white);

        JFreeChart chart = thisChart.getChart();
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(chartBackgroundColor); 
        return thisChart;
    }
    
    public ChartPanel getPanel2(Metric2 m2){
        
        int totalRiskCount = m2.totalCriticalCount+m2.totalHighCount+m2.totalLowCount+m2.totalMediumCount;
        String [][] risks = new String [totalRiskCount][3];
        BarChart barChart = new BarChart(m2.totalCriticalCount, m2.totalHighCount, m2.totalMediumCount, m2.totalLowCount, 0, "", risks);                 
                
        ChartPanel thisChart = barChart.drawBarChart();
        thisChart.setBackground(Color.white);

        JFreeChart chart = thisChart.getChart();
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(chartBackgroundColor);     
        
        return thisChart;
    }
    
    public ChartPanel getPanel3(Metric3 m3){
        
        int totalRiskCount = m3.totalCriticalCount + m3.totalLowCount;
        String [][] risks = new String [totalRiskCount][3];
        RingChart ringChart = new RingChart(m3.totalCriticalCount, m3.totalHighCount, m3.totalMediumCount, m3.totalLowCount, "", risks);                 
               
        ChartPanel thisChart = ringChart.drawRingChart();
        thisChart.setBackground(Color.white);

        JFreeChart chart = thisChart.getChart();
        chart.setBackgroundPaint(Color.white);
        
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(chartBackgroundColor);          
        return thisChart;
    }
    
    public ChartPanel getPanel4(Metric4 m4){
        
        int totalRiskCount = m4.totalCriticalCount+m4.totalHighCount+m4.totalLowCount+m4.totalMediumCount;
        String [][] risks = new String [totalRiskCount][3];
        PieChart pieChart = new PieChart(m4.totalCriticalCount, m4.totalHighCount, m4.totalMediumCount, m4.totalLowCount, "", risks, true);                 
        
        ChartPanel thisChart = pieChart.drawPieChart();
        thisChart.setBackground(Color.white);

        JFreeChart chart = thisChart.getChart();
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(chartBackgroundColor);         
        return thisChart;
    }
    
    public ChartPanel getPanel5(Metric5 m5)
    {   
        int totalRiskCount = m5.totalCriticalCount+m5.totalHighCount+m5.totalLowCount+m5.totalMediumCount;
        String [][] risks = new String [totalRiskCount][3];
        BarChart barChart = new BarChart(m5.totalCriticalCount, m5.totalHighCount, m5.totalMediumCount, m5.totalLowCount, 0, "", risks);                 
                
        ChartPanel thisChart = barChart.drawBarChart();
        thisChart.setBackground(Color.white);

        JFreeChart chart = thisChart.getChart();
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(chartBackgroundColor);        
        return thisChart;
    }
    
    public ChartPanel getPanel6(Metric6 m6){
        
        int totalRiskCount = m6.totalCriticalCount+m6.totalHighCount+m6.totalLowCount+m6.totalMediumCount;
        String [][] risks = new String [totalRiskCount][3];
        RingChart ringChart = new RingChart(m6.totalCriticalCount, m6.totalHighCount, m6.totalMediumCount, m6.totalLowCount, "", risks);                 
        
        ringChart.lowValue = ("Low ("+m6.totalLowCount+")");
        ChartPanel thisChart = ringChart.drawRingChart();
        thisChart.setBackground(Color.white);

        JFreeChart chart = thisChart.getChart();
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(chartBackgroundColor);            
        return thisChart;
    }
    
    public JProgressBar getPanel7(Metric7 m7){
        
        JProgressBar openPortBar = new JProgressBar(0, 100);
        openPortBar.setValue(m7.totalCriticalCount);
        openPortBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        UIDefaults defaults = new UIDefaults();
        
        Painter foregroundPainter = new MyPainter(new Color(230, 219, 27));
        Painter backgroundPainter = new MyPainter(chartBackgroundColor);
        defaults.put("ProgressBar[Enabled].foregroundPainter", foregroundPainter);
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", foregroundPainter);
        defaults.put("ProgressBar[Enabled].backgroundPainter", backgroundPainter);

        openPortBar.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        openPortBar.putClientProperty("Nimbus.Overrides", defaults);

        openPortBar.setString(""+m7.totalCriticalCount);
        openPortBar.setStringPainted(true);     
                
        return openPortBar;
    }
    
    class MyPainter implements Painter<JProgressBar> {

    private final Color color;

    public MyPainter(Color c1) {
        this.color = c1;
    }
    @Override
    public void paint(Graphics2D gd, JProgressBar t, int width, int height) {
        gd.setColor(color);
        gd.fillRect(0, 0, width, height);
        
    }
    }
    
    public ChartPanel getPanel8(Metric8 m8){
        
        int totalRiskCount = m8.totalCriticalCount+m8.totalHighCount+m8.totalLowCount+m8.totalMediumCount;
        String [][] risks = new String [totalRiskCount][3];
        BarChart barChart = new BarChart(m8.totalCriticalCount, m8.totalHighCount, m8.totalMediumCount, m8.totalLowCount, m8.totalOtherCount, "", risks);                 

        ChartPanel thisChart = barChart.drawBarChart();      
        thisChart.setBackground(Color.white);

        JFreeChart chart = thisChart.getChart();
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(chartBackgroundColor);
        
        return thisChart;
    }
}
