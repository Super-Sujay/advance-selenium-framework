package org.automation.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * To check the Hash string.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 08/31/2020
 *
 */
public final class CheckFileHash {

	private CheckFileHash() {
	}

	/**
	 * Generate the Hash string of the specified file.
	 * 
	 * @param fileToCheck file whose hash to be generated
	 * @param hashType    hash type used by the file
	 * @return the hash string
	 */
	public static String generateHashForFileOfType(File fileToCheck, HashType hashType) {
		Path path = fileToCheck.toPath();
		if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
			throw new RuntimeException(fileToCheck + " does not exists");
		}
		try (InputStream in = Files.newInputStream(path)) {
			switch (hashType) {
			case MD5:
				return DigestUtils.md5Hex(in);
			case SHA1:
				return DigestUtils.sha1Hex(in);
			default:
				throw new UnsupportedOperationException(hashType.toString() + " hash type is not supported!");
			}
		} catch (IOException e) {
			throw new RuntimeException("Issues with " + fileToCheck, e);
		}
	}

}
