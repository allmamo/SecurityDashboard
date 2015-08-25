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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author allenmamo
 */
public class RingChart {
    
    private int critical;
    private int high;
    private int medium;
    private int low;
    private String title;
    public String criticalValue;
    public String highValue;
    public String mediumValue;
    public String lowValue;
    private String noVulnerabilities = "No Vulnerabilities";
    private String [][] risks;
    private String [] criticalColumns = {"Device Name", "Issue", "Sort Order", "Solution"};    
    private String [] updatedColumns = {"Device Name", "Info.", "Sort Order"};
    private String [] networkDefenceColumns = {"Risk Rating", "Issue", "Sort Order", "Solution"};
    private int riskCount = 0;

    public RingChart(int critical, int high, int medium, int low, String title, String [][] risks) 
    {
        this.critical = critical;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.title = title;
        criticalValue = "Critical ("+critical+")";
        highValue = "High ("+high+")";
        mediumValue = "Medium ("+medium+")";  
        lowValue = "Updated ("+low+")"; 
        this.risks = risks;
        riskCount = critical+high+medium+low;
    }
    
    
    
    public ChartPanel drawRingChart()
    {
        DefaultPieDataset dataset = new DefaultPieDataset(); 
        if(riskCount == 0)
        {
            dataset.setValue(noVulnerabilities, new Integer(1)); 
        }
        else
        {
            if(critical > 0) dataset.setValue(criticalValue, new Integer(critical));
            if(low > 0)dataset.setValue(lowValue, new Integer(low));
            if(medium > 0) dataset.setValue(mediumValue, new Integer(medium));
            if(high > 0) dataset.setValue(highValue, new Integer(high));
            
        }
        
        RingPlot plot=new RingPlot(dataset);
        //plot.setLabelGenerator(new StandardPieSectionLabelGenerator(locale));
        plot.setInsets(new RectangleInsets(0.0,5.0,5.0,5.0));
//        if (tooltips) {
//            plot.setToolTipGenerator(new StandardPieToolTipGenerator(locale));
//        }
        if(riskCount == 0)
        {
            plot.setSectionPaint(noVulnerabilities, new Color(47, 196, 6));
        }
        else
        {
            plot.setSectionPaint(criticalValue, new Color(230, 27, 27)); 
            plot.setSectionPaint(highValue, new Color(230, 90, 27)); 
            plot.setSectionPaint(mediumValue, new Color(85, 144, 176)); 
            if(lowValue.equals("Updated ("+low+")"))
            {
                plot.setSectionPaint(lowValue, new Color(47, 196, 6));
            }
            else plot.setSectionPaint(lowValue, new Color(230, 219, 27));
        }
        JFreeChart chart=new JFreeChart(title,JFreeChart.DEFAULT_TITLE_FONT,plot,true);
        CategoryItemRenderer renderer = new CustomRenderer();
        renderer.setItemLabelsVisible(false);
        plot.setLabelLinksVisible(false);
        plot.setLabelGenerator(null);
        plot.setBackgroundPaint(new Color(210, 234, 243)); 
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
   
//        if(isMain)
//        {
//            piechart.removeLegend();
//        }
    }

    public JPanel addAVCharts() {        
        JPanel thisPanel = new JPanel();
        thisPanel.setLayout(new BorderLayout());
        int tempCriticalCount = 0, tempUpdatedCount = 0;
        String [][] criticalList = new String [critical][4];
        String [][] updatedList = new String [low][4];
        for (int i=0; i<riskCount ;i++)
        {
            if(risks[i][2].equals("Critical"))
            {
                criticalList[tempCriticalCount][0] = risks[i][0];
                criticalList[tempCriticalCount][1] = risks[i][1];
                criticalList[tempCriticalCount][3] = risks[i][3];
                tempCriticalCount++;
            }
            if(risks[i][2].equals("Low"))
            {
                updatedList[tempUpdatedCount][0] = risks[i][0];
                updatedList[tempUpdatedCount][1] = risks[i][1];
                tempUpdatedCount++;
            }            
        }
        
        JTable criticalTable = new JTable( criticalList, criticalColumns);
        JTable updatedTable = new JTable( updatedList, updatedColumns);
        TableColumn tcol = criticalTable.getColumnModel().getColumn(2);
        criticalTable.removeColumn(tcol);
        TableColumn tcol2 = updatedTable.getColumnModel().getColumn(2);
        updatedTable.removeColumn(tcol2);
        
        criticalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        updatedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        criticalTable.setEnabled(false);
        updatedTable.setEnabled(false);
        
        int width = 0; 
        for(int i = 0; i <3; i++)
        {
            for (int row = 0; row < criticalTable.getRowCount(); row++) 
            {
                TableCellRenderer renderer = criticalTable.getCellRenderer(row, i);
                Component comp = criticalTable.prepareRenderer(renderer, row, i);
                width = Math.max (comp.getPreferredSize().width, width);
            }
            criticalTable.getColumnModel().getColumn(i).setPreferredWidth(width);
            width = 0;
        }
        
        for(int i = 0; i <2; i++)
        {
            for (int row = 0; row < updatedTable.getRowCount(); row++) 
            {
                TableCellRenderer renderer = updatedTable.getCellRenderer(row, i);
                Component comp = updatedTable.prepareRenderer(renderer, row, i);
                width = Math.max (comp.getPreferredSize().width, width);
            }
            updatedTable.getColumnModel().getColumn(i).setPreferredWidth(width);
            width = 0;
        }
        
        criticalTable.setShowHorizontalLines(true);
        criticalTable.setRowHeight(40);
        updatedTable.setShowHorizontalLines(true);
        updatedTable.setRowHeight(40);
        
        JScrollPane criticalTableScrollPane = new JScrollPane(criticalTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane updatedTableScrollPane = new JScrollPane(updatedTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       
        Dimension d = criticalTable.getPreferredSize();
        criticalTableScrollPane.setPreferredSize(
            new Dimension((d.width-400),(criticalTable.getRowHeight()+1)*(tempCriticalCount+1)));
        
        Dimension d2 = updatedTable.getPreferredSize();
        updatedTableScrollPane.setPreferredSize(
            new Dimension((d.width-400),(updatedTable.getRowHeight()+1)*(tempUpdatedCount+1)));
        
        Font titleFonts = new Font("Calibri", Font.BOLD, 30);
        
        
        JLabel criticalLabel = new JLabel(" Critical    ");
        criticalLabel.setFont(titleFonts);
        criticalLabel.setForeground(new Color(230, 27, 27));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(criticalLabel, BorderLayout.WEST);
        leftPanel.add(criticalTableScrollPane, BorderLayout.CENTER); 
        
        JLabel updatedLabel = new JLabel(" Updated ");
        updatedLabel.setFont(titleFonts);
        updatedLabel.setForeground(new Color(47, 196, 6));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(updatedLabel, BorderLayout.WEST);
        rightPanel.add(updatedTableScrollPane, BorderLayout.CENTER); 
   
        if(tempCriticalCount!=0) thisPanel.add(leftPanel, BorderLayout.NORTH);
        if(tempUpdatedCount!=0) thisPanel.add(rightPanel, BorderLayout.SOUTH);
        return thisPanel;
    }
    
    public JPanel addDefenceCharts() {  
        lowValue = "Low ("+low+")"; 
        ChartPanel ringPanel = drawRingChart();        
        ringPanel.setDomainZoomable(true);
        JPanel thisRingPanel = new JPanel();
        thisRingPanel.setLayout(new BorderLayout());
        String [][] finalRisks = new String [riskCount][4];
        for (int i=0; i<riskCount ;i++)
        {
            finalRisks[i][0] = risks[i][0];
            finalRisks[i][1] = risks[i][1];            
            finalRisks[i][2] = risks[i][2];
            finalRisks[i][3] = risks[i][3];
        }
        
        JTable table = new JTable( finalRisks, networkDefenceColumns);
        TableColumn tcol = table.getColumnModel().getColumn(2);
        table.removeColumn(tcol);
        table.setEnabled(false);
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        int width = 0; 
        for (int row = 0; row < table.getRowCount(); row++) 
        {
            TableCellRenderer renderer = table.getCellRenderer(row, 1);
            Component comp = table.prepareRenderer(renderer, row, 1);
            width = Math.max (comp.getPreferredSize().width, width);
        }
        
        table.getColumnModel().getColumn(1).setPreferredWidth(width);
        
        width = 0;
        for (int row = 0; row < table.getRowCount(); row++) 
        {
            TableCellRenderer renderer = table.getCellRenderer(row, 2);
            Component comp = table.prepareRenderer(renderer, row, 2);
            width = Math.max (comp.getPreferredSize().width, width);
        }
        
        table.getColumnModel().getColumn(2).setPreferredWidth(width);
        table.setShowHorizontalLines(true);
        table.setRowHeight(40);
        
        JScrollPane tableScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        Dimension d = table.getPreferredSize();
        tableScrollPane.setPreferredSize(
            new Dimension((d.width-400),(table.getRowHeight()+1)*(riskCount+1)));
        
        JLabel right = new JLabel("                                                                                                    ");
        thisRingPanel.add(right, BorderLayout.EAST);
        thisRingPanel.add(ringPanel, BorderLayout.CENTER);          
        thisRingPanel.add(tableScrollPane, BorderLayout.SOUTH); 
        return thisRingPanel;
    }
}