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
import java.util.List;
import java.util.ArrayList;
import javax.swing.BorderFactory;
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
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author allenmamo
 */
public class PieChart {
    
    private int critical;
    private int high;
    private int medium;
    private int low;
    private String title;
    private String criticalValue;
    private String highValue;
    private String mediumValue;
    private String lowValue;
    private String noVulnerabilities = "No Vulnerabilities";
    private String [][] risks;
    private String [] columns = {"Risk Rating", "Issue", "Sort Order", "Solution"};
    private int riskCount = 0;
    private boolean isMain;

    public PieChart(int critical, int high, int medium, int low, String title, String [][] risks, boolean isMain) 
    {
        this.critical = critical;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.title = title;
        criticalValue = "Critical ("+critical+")";
        highValue = "High ("+high+")";
        mediumValue = "Medium ("+medium+")";  
        lowValue = "Low ("+low+")"; 
        this.risks = risks;
        riskCount = critical+high+medium+low;
        this.isMain = isMain;
    }
    
    
    
    public ChartPanel drawPieChart()
    {
        DefaultPieDataset piedataset = new DefaultPieDataset(); 
        if(riskCount == 0)
        {
            title = "";
            piedataset.setValue(noVulnerabilities, new Integer(1)); 
        }
        else
        {
            piedataset.setValue(lowValue, new Integer(low));
            piedataset.setValue(mediumValue, new Integer(medium));
            piedataset.setValue(highValue, new Integer(high));
            piedataset.setValue(criticalValue, new Integer(critical));
        }
        
        JFreeChart piechart = ChartFactory.createPieChart(  
         title,   // Title  
         piedataset,             // Dataset  
         true,                   // Show legend  
         true,                   // Use tooltips  
         false                   // Generate URLs  
        );  
        
        
        PiePlot plot = (PiePlot) piechart.getPlot();
        //plot.setSimpleLabels(true); 
        if(riskCount == 0)
        {
            plot.setSectionPaint(noVulnerabilities, new Color(47, 196, 6));
            plot.setLabelLinksVisible(false);
            plot.setLabelGenerator(null);
            piechart.removeLegend();
        }
        else
        {
            plot.setSectionPaint(criticalValue, new Color(230, 27, 27)); 
            plot.setSectionPaint(highValue, new Color(230, 90, 27)); 	
            plot.setSectionPaint(mediumValue, new Color(85, 144, 176)); 
            plot.setSectionPaint(lowValue, new Color(230, 219, 27));
        }
        
        if(isMain)
        {
            piechart.removeLegend();
        }
        plot.setBackgroundPaint(new Color(210, 234, 243)); 
        ChartPanel chartPanel = new ChartPanel(piechart);
        chartPanel.setEnabled(false);
        return chartPanel;
    }

    public JPanel addCharts() {        
        ChartPanel piePanel = drawPieChart();        
        piePanel.setDomainZoomable(true);
        JPanel thisPiePanel = new JPanel();
        
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
        
        JLabel right = new JLabel("                                                                                                    ");
        thisPiePanel.add(right, BorderLayout.EAST);
        
        table.setShowHorizontalLines(true);
        table.setRowHeight(40);
        
        JScrollPane tableScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        Dimension d = table.getPreferredSize();
        tableScrollPane.setPreferredSize(
            new Dimension((d.width-400),(table.getRowHeight()+1)*(riskCount+1)));
        table.setEnabled(false);
        thisPiePanel.setLayout(new BorderLayout());
        if(riskCount == 0)
        {
            
            thisPiePanel.add(piePanel, BorderLayout.CENTER);  
        }
        else
        {
            thisPiePanel.add(right, BorderLayout.EAST);
            thisPiePanel.add(piePanel, BorderLayout.CENTER);          
            thisPiePanel.add(tableScrollPane, BorderLayout.SOUTH); 
        }
        thisPiePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return thisPiePanel;
    }
}