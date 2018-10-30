package pt.onept.dropmusic.common.utililty;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public final class PropertiesReaderUtility {

	private PropertiesReaderUtility() {}
	public static Properties read(String propertiesFile) {

		Properties appProps = new Properties();
		try {
			File external = new File(propertiesFile);

			if (!external.exists()) {
				Files.copy(
						PropertiesReaderUtility.class.getClassLoader().getResourceAsStream(propertiesFile),
						external.toPath()
				);
			}
			appProps.load(new FileInputStream(external));
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
		return appProps;
	}
}
