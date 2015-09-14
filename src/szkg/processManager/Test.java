package szkg.processManager;

import org.apache.log4j.Logger;
import szkg.repositories.H2Repository;
import szkg.repositories.IRepository;

/**
 * Created by Gencay on 01.07.2015.
 */
public class Test {

    private Logger logger = Logger.getLogger(Test.class);
    private IRepository repository = new H2Repository();

    public static void main(String[] args) throws Exception {
        new ProcessCreator().getCollectionResultofAnIteration("C:\\Users\\Gencay\\Desktop\\g_0_UseG1GC_1500000_1_4.txt");
    }

    private void test(String[] args) {


    }


    private void run(String[] args) {

        try {
            logger.debug("Test Started");

            repository.open();

            repository.exportToScript();


        } catch (Exception ex) {
            logger.error("Test Error", ex);
        } finally {
            logger.debug("Test Ended");
        }
    }
}
