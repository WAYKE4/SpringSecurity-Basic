import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {
    static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Start main");
        log.error("error");
        log.info("End main");
    }
}
