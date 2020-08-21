package org.automation.utilities;

import static org.automation.logger.Log.error;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public final class Configuration {

	private static Properties properties;

	private Configuration() { }

	private static void load() {
		properties = new Properties();
		Path config = Paths.get("config.properties");
		try (BufferedReader reader = Files.newBufferedReader(config, StandardCharsets.UTF_8)) {
			properties.load(reader);
		} catch (IOException e) {
			error("Unable to find config.properties file...", e);
		}
	}

	public static String get(String option) {
		if (properties == null) load();
		return Optional.ofNullable(properties.getProperty(option)).orElse("");
	}

	public static void print() {
		properties.forEach((k, v) -> System.out.println(k + "=" + v));
	}

}
