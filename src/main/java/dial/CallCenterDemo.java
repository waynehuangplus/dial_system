package dial;

import dial.model.Caller;
import dial.model.Fresher;
import dial.model.ProductManager;
import dial.model.TechLead;

/**
 * Created by wshuang on 30/08/2017.
 */
public class CallCenterDemo {

    public static void main(String[] args) {
        CallService callService = new CallService();

        new Thread(new Caller(callService)).start();
        new Thread(new Fresher(callService)).start();
        new Thread(new TechLead(callService)).start();
        new Thread(new ProductManager(callService)).start();
    }
}
