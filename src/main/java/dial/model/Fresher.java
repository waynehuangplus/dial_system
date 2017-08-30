package dial.model;

import dial.CallService;

/**
 * Created by wshuang on 29/08/2017.
 */
public class Fresher implements Runnable{
        private CallService callService;

        public Fresher(CallService callService) {
            this.callService = callService;
        }

        public void run() {
            System.out.println("Fresher start");
            for (int i = 1; i <= 500; i++) {
                try {
                    callService.fresherAnswering();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
}
