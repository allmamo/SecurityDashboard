/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visuals;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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

/**
 *
 * @author allenmamo
 */
public class Table {
    
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

    public Table(int critical, int high, int medium, int low, String title, String [][] risks) 
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

    public JPanel addTables() {        
        
        JPanel thisGraphPanel = new JPanel();
        thisGraphPanel.setLayout(new BorderLayout());
        
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
        
        Font titleFonts = new Font("Calibri", Font.BOLD, 30);
        JLabel ipLabel = new JLabel(title);
        ipLabel.setFont(titleFonts);
        thisGraphPanel.add(ipLabel, BorderLayout.NORTH);
        thisGraphPanel.add(tableScrollPane, BorderLayout.CENTER);
        return thisGraphPanel;
    }
}