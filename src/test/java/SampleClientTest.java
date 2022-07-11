import org.junit.jupiter.api.Test;

/**
 * SampleClientTest
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
class SampleClientTest {

    @Test
    void basicTask_runsSuccessfully() {
        SampleClient client = new SampleClient();
        client.basicTask();
    }

    @Test
    void intermediateTask_runsSuccessfully() {
        SampleClient client = new SampleClient();
        client.intermediateTask(true);
    }
}