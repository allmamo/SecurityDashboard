/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metrics;

import DataManagement.ConfigReader;
import Visuals.BarChart;
import Visuals.PieChart;
import Visuals.RingChart;
import Visuals.Table;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JPanel;

import com.csvreader.CsvReader;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.BoxLayout;

public class Metric {

    int criticalCount = 0, highCount = 0, mediumCount = 0, lowCount = 0, riskCount = 0, otherCount = 0;
    String title = "";
    String fileName = "", tempRisk = "";
    String[][] risks = null;
    public int deviceCount = 0;
    public int totalCriticalCount = 0, totalHighCount = 0, totalMediumCount = 0, totalLowCount = 0, totalOtherCount = 0;
    String[][] allRisks = null;
    int avCheckCount = 0;
    String currentTitle = "";

    public JPanel run(String fileName, String chartType, String columnName) {

        final String thisFileName = fileName;
        ConfigReader reader = new ConfigReader();
        reader.readConfigFile();

        JPanel chartPanels = new JPanel();
        chartPanels.setLayout(new BoxLayout(chartPanels, BoxLayout.PAGE_AXIS));

        File dir = new File(reader.getFileLocation());
        File[] foundFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(thisFileName) && name.endsWith(".csv");
            }
        });

        long currentLastModified = 0;
        for (File file : foundFiles) {
            if (file.lastModified() > currentLastModified) {
                fileName = file.getName();
                currentLastModified = file.lastModified();
            }
        }

        String csvFile = reader.getFileLocation() + "/" + fileName;
        try {

            CsvReader vulnerabilities = new CsvReader(csvFile);

            vulnerabilities.readHeaders();

            risks = new String[100][4];
            allRisks = new String[100][4];

            while (vulnerabilities.readRecord()) {

                currentTitle = vulnerabilities.get("Host");
                if (currentTitle.equals("169.254.157.17")) {
                    currentTitle = "Device1";
                } else if (currentTitle.equals("169.254.197.189")) {
                    currentTitle = "Device2";
                } else if (currentTitle.equals("169.254.248.143")) {
                    currentTitle = "Device3";
                } else if (currentTitle.equals("169.254.54.51")) {
                    currentTitle = "Device4";
                } else if (currentTitle.equals("169.254.6.46")) {
                    currentTitle = "Device5";
                } else if (currentTitle.equals("169.254.81.3")) {
                    currentTitle = "ADMIN-PC";
                }
                if (!currentTitle.equals(title) && !(title.equals(""))) {
                    switch (chartType) {
                        case "PIE":
                            PieChart pieChart = new PieChart(criticalCount, highCount, mediumCount, lowCount, title, risks, false);
                            chartPanels.add(pieChart.addCharts());
                            break;
                        case "BAR":
                            if (riskCount > 0) {
                                BarChart barChart = new BarChart(criticalCount, highCount, mediumCount, lowCount, otherCount, title, risks);
                                chartPanels.add(barChart.addCharts());
                            }
                            break;
                        case "RING":
                            if (fileName.startsWith("Antivirus_Check_Scan_")) {
                                allRisks[avCheckCount][0] = currentTitle;
                                allRisks[avCheckCount][1] = vulnerabilities.get(columnName);
                                allRisks[avCheckCount][2] = vulnerabilities.get("Risk");
                                allRisks[avCheckCount][3] = vulnerabilities.get("Solution");
                                avCheckCount++;
                            }
                            if (fileName.startsWith("Network_Defence_Scan_")) {
                                if (riskCount > 0) {
                                    RingChart ringChart = new RingChart(criticalCount, highCount, mediumCount, lowCount, title, risks);
                                    chartPanels.add(ringChart.addDefenceCharts());
                                }
                            }
                            break;
                        case "TABLE":
                            if (riskCount > 0) {
                                Table table = new Table(criticalCount, highCount, mediumCount, lowCount, title, risks);
                                chartPanels.add(table.addTables());
                            }
                            break;
                    }
                    if (!fileName.startsWith("Antivirus_Check_Scan_")) {
                        resetCounts();
                    }
                    deviceCount++;
                }

                if (!vulnerabilities.get(columnName).equals(tempRisk) || chartType.equals("RING")) {
                    title = currentTitle;

                    risks[riskCount][0] = vulnerabilities.get("Risk");
                    risks[riskCount][1] = vulnerabilities.get(columnName);
                    risks[riskCount][3] = vulnerabilities.get("Solution");
                    tempRisk = vulnerabilities.get(columnName);

                    riskCount++;

                    String riskRating = vulnerabilities.get("Risk");

                    if (chartType.equals("RING") && fileName.startsWith("Antivirus_Check_Scan_")) {
                        if (avCheckCount == 0) {
                            allRisks[avCheckCount][0] = currentTitle;
                            allRisks[avCheckCount][1] = vulnerabilities.get(columnName);
                            allRisks[avCheckCount][2] = riskRating;
                            allRisks[avCheckCount][3] = vulnerabilities.get("Solution");
                            avCheckCount++;
//                        } else {
//                            if (riskRating.equals("Critical") || riskRating.equals("Low") && !(currentTitle.equals(allRisks[avCheckCount - 1][0]))) {
//                                allRisks[avCheckCount][0] = currentTitle;
//                                allRisks[avCheckCount][1] = vulnerabilities.get(columnName);
//                                allRisks[avCheckCount][2] = riskRating;
//                                allRisks[avCheckCount][3] = vulnerabilities.get("Solution");
//                                avCheckCount++;
//                            }
                        }
                    }

                    // perform program logic here
                    switch (riskRating) {
                        case "Critical":
                            risks[riskCount - 1][2] = "1";
                            criticalCount++;
                            break;
                        case "High":
                            risks[riskCount - 1][2] = "2";
                            highCount++;
                            break;
                        case "Medium":
                            risks[riskCount - 1][2] = "3";
                            mediumCount++;
                            break;
                        case "Low":
                            risks[riskCount - 1][2] = "4";
                            lowCount++;
                            break;
                        case "None":
                            if (chartType.equals("TABLE") && (vulnerabilities.get("Plugin ID").equals("14272")|vulnerabilities.get("Plugin ID").equals("11219"))) {
                                risks[riskCount - 1][2] = "1";
                                criticalCount++;
                                break;
                            } else if (chartType.equals("BAR") && fileName.startsWith("User_Account_Check_Scan_") && !(vulnerabilities.get("Plugin ID").equals("14272"))) {
                                risks[riskCount - 1][2] = "5";
                                otherCount++;
                                break;
                            }
                            riskCount--;
                            break;
                        default:
                            riskCount--;
                            break;
                    }
                }
            }

            vulnerabilities.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (chartType) {
            case "PIE":
                PieChart pieChart = new PieChart(criticalCount, highCount, mediumCount, lowCount, title, risks, false);
                chartPanels.add(pieChart.addCharts());
                break;
            case "BAR":
                if (riskCount > 0) {
                    BarChart barChart = new BarChart(criticalCount, highCount, mediumCount, lowCount, otherCount, title, risks);
                    chartPanels.add(barChart.addCharts());
                }
                break;
            case "RING":
                if (riskCount > 0 && fileName.startsWith("Antivirus_Check_Scan_")) {
                    RingChart ringChart = new RingChart(criticalCount, highCount, mediumCount, lowCount, title, allRisks);
                    chartPanels.add(ringChart.addAVCharts());
                }
                if (riskCount > 0 && fileName.startsWith("Network_Defence_Scan_")) {
                    RingChart ringChart = new RingChart(criticalCount, highCount, mediumCount, lowCount, title, risks);
                    chartPanels.add(ringChart.addDefenceCharts());
                }
                break;
            case "TABLE":
                if (riskCount > 0) {
                    Table table = new Table(criticalCount, highCount, mediumCount, lowCount, title, risks);
                    chartPanels.add(table.addTables());
                }
                break;
        }
        deviceCount++;
        resetCounts();
        return chartPanels;
    }

    private void resetCounts() {
        totalCriticalCount += criticalCount;
        criticalCount = 0;

        totalHighCount += highCount;
        highCount = 0;

        totalMediumCount += mediumCount;
        mediumCount = 0;

        totalLowCount += lowCount;
        lowCount = 0;

        otherCount = 0;

        riskCount = 0;
        risks = new String[100][4];

        tempRisk = "";
        //currentTitle = "";
    }

    public int getTotalCount() {
        return totalCriticalCount + totalHighCount + totalMediumCount + totalLowCount;
    }
}
