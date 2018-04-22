package pay.test.main;

import org.apache.log4j.Logger;
import pay.test.controller.PayclipController;

/**
 * @author Marco Murakami (marco.murakami@movile.com) on 4/20/18.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main (String args []) {
        LOG.info("Welcome to Playclip Test Project");
        LOG.info("params: \n");
        printArgs(args);
        PayclipController payclipController = new PayclipController();
        payclipController.selectService(args);
    }

    public static void printArgs(String [] args) {
        for(String arg : args) {
            LOG.info(arg);
        }
    }
}
