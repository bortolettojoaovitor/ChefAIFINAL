import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracao {
    private static Properties props = new Properties();

    static {
        try {
            FileInputStream in = new FileInputStream("config.properties");
            props.load(in);
            in.close();
        } catch (IOException e) {
            System.err.println("Arquivo config.properties não encontrado na pasta");
        }
    }

    public static String getApiKey() { return props.getProperty("api.key"); }
    public static String getApiUrl() { return props.getProperty("api.url"); }
    public static String getModel() { return props.getProperty("api.model"); }
}