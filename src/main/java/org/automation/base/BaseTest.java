package org.automation.base;

import static java.io.File.separator;
import static java.lang.System.getProperty;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.automation.config.DriverFactory.closeDriverObjects;
import static org.automation.config.DriverFactory.instantiateDriverObject;
import static org.automation.config.DriverFactory.clearCookies;
import static org.automation.logger.Log.error;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.automation.listeners.TestReporter;
import org.automation.listeners.TestRunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

/**
 * To extend every test class created.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 6/11/2020
 *
 */
@Listeners({ TestRunListener.class, TestReporter.class })
public class BaseTest {

	/**
	 * Method to execute at the start of the suite execution.
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		instantiateDriverObject();
	}

	/**
	 * Method to execute at the end of each test method execution.
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		clearCookies();
	}

	/**
	 * Method to execute at the end of the suite execution
	 */
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		closeDriverObjects();
	}

	/**
	 * Data Provider method to get data from Excel file.
	 * 
	 * @param method
	 *            test method executed
	 * @return excel data
	 */
	@DataProvider(name = "ExcelDataProvider")
	public Iterator<Object[]> provideData(Method method) {
		List<Object[]> excelData = new ArrayList<Object[]>();
		String pathName = "src" + separator + "test" + separator + "resources" + separator + "ExcelData.xlsx";
		Connection con = null;
		Recordset record = null;
		try {
			Fillo fillo = new Fillo();
			con = fillo.getConnection(pathName);
			record = con.executeQuery("Select * from TestData where TestCase = '" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "'");
			while (record.next()) {
				Map<String, String> data = new HashMap<String, String>();
				for (String field : record.getFieldNames())
					if (!record.getField(field).isEmpty())
						data.put(field, record.getField(field));
				excelData.add(new Object[] { data });
			}
		} catch (FilloException e) {
			error("Unable to get data from Excel", e);
			throw new RuntimeException("Could not read " + pathName + " file.\n" + e.getStackTrace().toString());
		} finally {
			con.close();
			record.close();
		}
		return excelData.iterator();
	}

	/**
	 * Data Provider method to get data from CSV file.
	 * 
	 * @param method
	 *            test method executed
	 * @return CSV data
	 */
	@DataProvider(name = "CsvDataProvider")
	public Iterator<Object[]> getCsvData(Method method) {
		List<Object[]> csvData = new ArrayList<Object[]>();
		String csvRegex = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		String pathName = "src" + separator + "test" + separator + "resources" + separator + "CsvData.csv";
		try {
			String[] keys = lines(get(pathName)).findFirst().orElseThrow(IOException::new).split(csvRegex);
			List<String[]> dataLines = lines(get(pathName))
					.filter(line -> line.startsWith(method.getDeclaringClass().getSimpleName() + "." + method.getName()))
					.map(line -> line.split(csvRegex)).collect(toList());
			for (String[] values : dataLines) {
				Map<String, String> data = new HashMap<String, String>();
				for (int i = 1; i < keys.length; i++)
					if (!values[i].isEmpty())
						data.put(keys[i], values[i]);
				csvData.add(new Object[] { data });
			}
		} catch (IOException e) {
			error("Unable to get data from Csv", e);
			throw new RuntimeException("Could not read " + pathName + " file.\n" + e.getStackTrace().toString());
		}
		return csvData.iterator();
	}

	/**
	 * Get the user name from the command line.
	 * 
	 * @return user name
	 */
	protected String getUsername() {
		return ofNullable(getProperty("username")).orElseThrow(() -> new NullPointerException("Username was not provided"));
	}

	/**
	 * Get the password from the command line.
	 * 
	 * @return password
	 */
	protected String getPassword() {
		return ofNullable(getProperty("password")).orElseThrow(() -> new NullPointerException("Password was not provided"));
	}

}
