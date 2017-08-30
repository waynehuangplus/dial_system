package dial.model;

import dial.CallService;

/**
 * Created by wshuang on 29/08/2017.
 */
public class TechLead implements Runnable {

    private CallService callService;

    public TechLead(CallService callService) {
        this.callService = callService;
    }

    public void run() {
        System.out.println("Start TL");
        for (int i = 1; i <= 500; i++) {
            try {
                callService.techLeadAnswering();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
