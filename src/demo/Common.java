package demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to implement common functionalities for the program
 * @author fernandojribeiro
 */
public class Common {
	/**
	 * Program Constants
	 */
	public static final String COMMIT_SEPARATOR = ";";
	public static final String COMMIT_EMPTY = "N/A";

	public static final String COMMAND_SEPARATOR = "command.line.separator";
	public static final String REPO_NAME = "repo.name";
	public static final String REPO_PATH = "repo.path";
	public static final String REPO_URL = "repo.url";
	public static final String MAX_THREADS = "api.maxThreads";
	public static final String MIN_THREADS = "api.minThreads";
	public static final String TIMEOUT = "api.timeoutMillis";
	public static final String PORT = "api.port";

	private static final Logger LOGGER = LogManager.getLogger("demo");
    private static final String PROPERTIES_FILE = "config.properties";
    private Properties properties = new Properties();
    
    public Common() throws FileNotFoundException, IOException {
    	loadProperties();
    }
    
    /**
     * Obtain the logger instance for logging purposes
     * @author fernandojribeiro
     * @return the logger instance
     */
    public static Logger getLogger() {
    	return LOGGER;
    }

    /**
     * Load the properties of the program
     * @author fernandojribeiro
     * @throws FileNotFoundException if file is not found
     * @throws IOException if the content has a bad format for example 
     */
    public void loadProperties() throws FileNotFoundException, IOException {
    	 InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);

    	 if (inputStream != null) {
    		 properties.load(inputStream);
    	 } else {
    		 throw new FileNotFoundException("Property file '" + PROPERTIES_FILE + "' not found in the classpath");
    	 }
    }
    
    /**
     * Obtain a program property
     * @author fernandojribeiro
     * @param name the property name to obtain
     * @return the property value
     */
    public String getProperty(String name) {
    	return properties.getProperty(name);
    }
}
