import com.siavash.messenger.rest.RestApi;
import com.siavash.messenger.rest.Service;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by sia on 7/2/16.
 */
public class RestApiTest {
    private static Logger log = Logger.getLogger(RestApiTest.class.getSimpleName());

    public static void main(String[] args) throws IOException {
        RestApi restApi = Service.createService(RestApi.class, "http://127.0.0.1:8100");

        restApi.groupMessages("sia", "sias", true).execute();
    }
}
