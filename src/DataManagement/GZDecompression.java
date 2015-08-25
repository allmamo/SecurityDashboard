package DataManagement;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
 
public class GZDecompression
{
    private static final String INPUT_GZIP_FILE = "/private/var/log/";
    private int successCount;
    private int failCount = 0;
    String [][] counts = new String[500][1];
    private int logCount = 0;
    private int [] successLog = new int[20];
    private int [] failLog = new int[20];
    public String[][] getMetric9()
    {
        gunzipIt("accountpolicy.log");
        gunzipIt("authd.log");
        gunzipIt("system.log");
        return counts;
    }
    
    public void gunzipIt(final String fileName)
    {
        
        final String thisFileName = fileName;
        try
        {
            File dir = new File(INPUT_GZIP_FILE);
            File[] foundFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(thisFileName);
            }
            });

            long currentLastModified = 0;
            for (File file : foundFiles) {
                if(file.lastModified()>currentLastModified)
                {   
                    BufferedReader br = null;
                    String logFile = INPUT_GZIP_FILE+"/" + file.getName();
                    if(file.getName().endsWith(".log"))
                    {
                        FileInputStream fis = new FileInputStream(logFile);
                        br = new BufferedReader(new InputStreamReader(fis));
                    }
                    else
                    {
                        GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(logFile));
                        br = new BufferedReader(new InputStreamReader(gzip));
                    }
                    
                    while(br.ready())
                    {
                        if(!fileName.startsWith("system"))
                        {
                            String thisLine = br.readLine();
                            if(thisLine.contains("Success") || thisLine.contains("Succeeded"))
                            {
                                successCount++;
                            }
                            if(thisLine.contains("Failed") || thisLine.contains("Failure"))
                            {
                                counts[getFailCount()][0] = thisLine;
                                failCount++;
                            }
                        }
                        else
                        {
                            String thisLine = br.readLine();
                            if(thisLine.contains("Failed to authenticate user"))
                            {
                                counts[getFailCount()][0] = thisLine;
                                failCount++;
                            }
                        }
                    }
                }
                setLogCount(getLogCount() + 1);
            } 

            

        }catch(IOException ex){ ex.printStackTrace(); }  
    } 

    /**
     * @return the successCount
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * @return the failCount
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * @return the successLog
     */
    public int[] getSuccessLog() {
        return successLog;
    }

    /**
     * @return the failLog
     */
    public int[] getFailLog() {
        return failLog;
    }

    /**
     * @return the logCount
     */
    public int getLogCount() {
        return logCount;
    }

    /**
     * @param logCount the logCount to set
     */
    public void setLogCount(int logCount) {
        this.logCount = logCount;
    }
}