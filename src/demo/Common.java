package demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

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
	public static final String COMMAND_TIMEOUT = "command.line.timeoutMillis";

	public static final String REPO_NAME = "repo.name";
	public static final String REPO_PATH = "repo.path";
	public static final String REPO_URL = "repo.url";
	public static final String COMMITS_URL = "rest.commits.url";
	public static final String COMMITS_SINCE_DATE_TAG = "rest.commits.sincedatetag";
	public static final String COMMITS_UNTIL_DATE_TAG = "rest.commits.untildatetag";
	
	public static final String API_DATE_FORMAT = "api.dateFormat";
	public static final String API_MAX_THREADS = "api.maxThreads";
	public static final String API_MIN_THREADS = "api.minThreads";
	public static final String API_TIMEOUT = "api.timeoutMillis";
	public static final String API_PORT = "api.port";

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
     * Validate if string is null or has a length equal to zero
     * @param text to validate 
     * @return if empty
     */
    public static boolean isEmpty(String text) {
    	return text == null || text.isEmpty();
    }

    /**
     * Validate if a date is valid
     * @param date to validate
     * @param format to validate against
     * @return if the date is valid or not
     */
    public static boolean isDateValid(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        //dateFormat.setLenient(false);
        try {
        	dateFormat.parse(date.trim());
        } catch (ParseException pe) {
        	return false;
        }
        return true;
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
    
    /**
     * Method to invoke an api through an URL and return a JSON object
     * @param url to invoke
     * @return a json object from the api output
     * @throws IOException if an I/O exception occurs.
     * @throws MalformedInputException  if no protocol is specified, or anunknown protocol is found, or spec is null.
     */
	public JSONArray invokeJsonAPI(String url) throws IOException, MalformedInputException {
		String jsonData = "";
		JSONArray jsonArray;

		Common.getLogger().debug("Invoking JSON API " + url + " ... ");

		InputStream istream = new URL(url).openStream();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(istream, Charset.forName("UTF-8")));

			String jsonLine = null;
			while ((jsonLine = reader.readLine()) != null) {
				jsonData += jsonLine;
			}
			jsonArray = new JSONArray(jsonData);
		} finally {
			istream.close();
		}

		Common.getLogger().debug("JSON API Response: " + jsonData);
		return jsonArray;
	}
}
