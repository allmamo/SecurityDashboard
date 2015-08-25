/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author allenmamo
 */
public class BarChart {
    
    private int critical;
    private int high;
    private int medium;
    private int low;
    private int other;
    private String title;
    private String criticalValue;
    private String highValue;
    private String mediumValue;
    private String lowValue;
    private String noVulnerabilities = "No Vulnerabilities";
    private String [][] risks;
    private String [] columns = {"Risk Rating", "Issue", "Sort Order", "Solution"};
    private int riskCount = 0;    
    String riskCountTitle = "Risk Count";
    String riskCategory = "Risk Level";

    public BarChart(int critical, int high, int medium, int low,int other, String title, String [][] risks) 
    {
        this.critical = critical;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.title = title;
        this.other = other;
        criticalValue = "Critical";
        highValue = "High";
        mediumValue = "Medium";  
        lowValue = "Low"; 
        this.risks = risks;
        riskCount = critical+high+medium+low+other;
    }   
    
    public ChartPanel drawBarChart()
    {
        DefaultCategoryDataset bardataset = new DefaultCategoryDataset(); 
            bardataset.addValue(new Double(low), "Low (" + low + ")", lowValue); 
            bardataset.addValue(new Double(medium), "Medium (" + medium + ")", mediumValue); 
            bardataset.addValue(new Double(high), "High (" + high + ")", highValue); 
            bardataset.addValue(new Double(critical), "Critical (" + critical + ")", criticalValue);
        
        JFreeChart barchart = ChartFactory.createBarChart(  
         title,   // Title  
         riskCategory,
         riskCountTitle,
         bardataset             // Dataset   
        );  
       
        final CategoryPlot plot = barchart.getCategoryPlot(); 
        CategoryItemRenderer renderer = new CustomRenderer();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        renderer.setBaseItemLabelGenerator(
        new StandardCategoryItemLabelGenerator(riskCountTitle, NumberFormat.getInstance()));
        
        DecimalFormat df = new DecimalFormat("##");
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", df));
        Font m1Font;
        m1Font = new Font("Cambria", Font.BOLD, 16);
        renderer.setItemLabelFont(m1Font);
        renderer.setItemLabelPaint(null);
        
        //barchart.removeLegend();
        plot.setRenderer(renderer);
        //renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE5, TextAnchor.CENTER));
        //renderer.setItemLabelsVisible(true);
        barchart.getCategoryPlot().setRenderer(renderer);
        
        LegendItemCollection chartLegend = new LegendItemCollection();
        Shape shape = new Rectangle(10, 10);
        chartLegend.add(new LegendItem("Low (" + low + ")", null, null, null, shape, new Color(230, 219, 27)));
        chartLegend.add(new LegendItem("Medium (" + medium + ")", null, null, null, shape, new Color(85, 144, 176)));
        chartLegend.add(new LegendItem("High (" + high + ")", null, null, null, shape, new Color(230, 90, 27)));
        chartLegend.add(new LegendItem("Critical (" + critical + ")", null, null, null, shape, new Color(230, 27, 27)));
        plot.setFixedLegendItems(chartLegend);
        plot.setBackgroundPaint(new Color(210, 234, 243)); 
        ChartPanel chartPanel = new ChartPanel( barchart ); 
        return chartPanel;
    }

    public JPanel addCharts() {        
        ChartPanel barPanel = drawBarChart();        
        barPanel.setDomainZoomable(true);
        JPanel thisBarPanel = new JPanel();
        thisBarPanel.setLayout(new BorderLayout());
        
        String [][] finalRisks = new String [riskCount][4];
        for (int i=0; i<riskCount ;i++)
        {
            finalRisks[i][0] = risks[i][0];
            finalRisks[i][1] = risks[i][1];            
            finalRisks[i][2] = risks[i][2];
            finalRisks[i][3] = risks[i][3];
        }
        
        JTable table = new JTable( finalRisks, columns);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
 
        int columnIndexToSort = 2;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
 
        sorter.setSortKeys(sortKeys);
        sorter.sort();
        
        TableColumn tcol = table.getColumnModel().getColumn(2);
        table.removeColumn(tcol);
                
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getColumnModel().getColumn(1).setPreferredWidth(600);
        table.getColumnModel().getColumn(2).setPreferredWidth(600);
        
        table.setShowHorizontalLines(true);
        table.setRowHeight(40);
        table.setEnabled(false);
        
        JScrollPane tableScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        Dimension d = table.getPreferredSize();
        tableScrollPane.setPreferredSize(
            new Dimension((d.width-400),(table.getRowHeight()+1)*(riskCount+1)));
        
        JLabel right = new JLabel("                                                                                                    ");
        thisBarPanel.add(right, BorderLayout.EAST);
        thisBarPanel.add(barPanel, BorderLayout.CENTER);          
        thisBarPanel.add(tableScrollPane, BorderLayout.SOUTH);  
        return thisBarPanel;
    }
}