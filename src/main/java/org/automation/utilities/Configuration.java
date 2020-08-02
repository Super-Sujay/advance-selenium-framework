package org.automation.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import org.automation.logger.Log;

public final class Configuration {
	
	private static Properties properties;
	
	private Configuration() {}
	
	public static void load() {
		properties = new Properties();
		Path config = Paths.get("config.properties");
		try(BufferedReader reader = Files.newBufferedReader(config, StandardCharsets.UTF_8)) {
			properties.load(reader);
		} catch (IOException e) {
			Log.error("Unable to find config.properties file...", e);
		}
	}
	
	public static String get(String option) {
		return Optional.ofNullable(properties.getProperty(option)).orElse("");
	}
	
	public static void print() {
		properties.forEach((k,v) -> System.out.println(k + "=" + v));
	}

}
