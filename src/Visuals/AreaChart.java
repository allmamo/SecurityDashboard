/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visuals;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author allenmamo
 */
public class AreaChart {
    
   private int logCount;
   private int  successCount, failCount;
 
    public AreaChart(int logCount, int successCount, int failCount) 
    {
        this.logCount = logCount;
        this.successCount = successCount;
        this.failCount = failCount;
    }   
    
    public ChartPanel drawAreaChart()
    {
        DefaultCategoryDataset areadataset = new DefaultCategoryDataset();
            areadataset.addValue(new Double(successCount), "Success", "Success (" + successCount + ")"); 
            areadataset.addValue(new Double(failCount), "Fail", "Fail (" + failCount + ")"); 
        
        JFreeChart areachart = ChartFactory.createBarChart(
         "",   // Title  
         "Result",
         "Attempts",
         areadataset             // Dataset   
        );  
       
        final CategoryPlot plot = areachart.getCategoryPlot(); 
        CategoryItemRenderer renderer = new CustomRendererAudit();
        
        areachart.removeLegend();
        plot.setRenderer(renderer);
        areachart.getCategoryPlot().setRenderer(renderer);
//        plot.set("Success", new Color(230, 27, 27)); 
//        plot.setSectionPaint("Fail", new Color(230, 90, 27)); 
        final CategoryPlot plotx = areachart.getCategoryPlot();
        ((BarRenderer) plotx.getRenderer()).setBarPainter(new StandardBarPainter());  
        
        plotx.setBackgroundPaint(new Color(210, 234, 243));
        ChartPanel chartPanel = new ChartPanel( areachart ); 
        return chartPanel;
    }    
}