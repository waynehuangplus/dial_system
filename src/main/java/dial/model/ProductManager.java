package dial.model;

import dial.CallService;

/**
 * Created by wshuang on 29/08/2017.
 */
public class ProductManager implements Runnable {

    private CallService callService;

    public ProductManager(CallService callService) {
        this.callService = callService;
    }

    public void run() {
        System.out.println("Start PM");
        for (int i = 1; i <= 500; i++) {
            try {
                callService.productManagerAnswering();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
