package dial.model;

import dial.CallService;

/**
 * Created by wshuang on 29/08/2017.
 */
public class Caller implements Runnable {

    private CallService callService;

    public Caller(CallService callService) {
        this.callService = callService;
    }

    public void run() {
        System.out.println("Start to call");
        for (int i = 1; i <= 500; i++) {
            try {
                callService.calling();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
