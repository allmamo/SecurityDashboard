package DataManagement;
 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 
public class ConfigReader {
    Properties prop = new Properties();
	InputStream input = null;
        private String fileLocation = "";
        private String metric1Name = "";
        
    
  public void readConfigFile () {
      
	try {
 
		input = new FileInputStream("config.properties");
 
		// load a properties file
		prop.load(input);
 
		setFileLocation(prop.getProperty("location"));
                setMetric1Name(prop.getProperty("metric1name"));
 
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
  }

    /**
     * @return the fileLocation
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * @param fileLocation the fileLocation to set
     */
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * @return the metric1Name
     */
    public String getMetric1Name() {
        return metric1Name;
    }

    /**
     * @param metric1Name the metric1Name to set
     */
    public void setMetric1Name(String metric1Name) {
        this.metric1Name = metric1Name;
    }
}