import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private Properties appProps;

    public Config() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "config.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        this.appProps = appProps;
    }

    public String get(String key) {
        return this.appProps.getProperty(key);
    }
}
