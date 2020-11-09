package org.automation.utilities;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.notExists;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * To check the Hash string.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 08/31/2020
 *
 */
public final class CheckFileHash {

	private CheckFileHash() { }

	/**
	 * Generate the Hash string of the specified file.
	 * 
	 * @param fileToCheck file whose hash to be generated
	 * @param hashType    hash type used by the file
	 * @return the hash string
	 */
	public static String generateHashForFileOfType(File fileToCheck, HashType hashType) {
		Path path = fileToCheck.toPath();
		if (notExists(path, LinkOption.NOFOLLOW_LINKS)) {
			throw new RuntimeException(fileToCheck + " does not exists");
		}
		try (InputStream in = newInputStream(path)) {
			switch (hashType) {
			case MD5:
				return md5Hex(in);
			case SHA1:
				return sha1Hex(in);
			default:
				throw new UnsupportedOperationException(hashType.toString() + " hash type is not supported!");
			}
		} catch (IOException e) {
			throw new RuntimeException("Issues with " + fileToCheck, e);
		}
	}

}
