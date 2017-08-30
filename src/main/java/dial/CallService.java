package dial;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wshuang on 29/08/2017.
 */
public class CallService {

    private int availableSlotsOfFresher = 10;
    private int availableSlotsOfTL = 1;
    private int availableSlotsOfPM = 1;
    private Lock lock = new ReentrantLock();
    private Condition fresherCond = lock.newCondition();
    private Condition callerCond = lock.newCondition();
    private Condition techLeadCond = lock.newCondition();
    private Condition productManagerCond = lock.newCondition();

    public void calling() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(availableSlotsOfFresher + " " + availableSlotsOfTL + " " + availableSlotsOfTL);
            waitIfNoAvailableSlot();
            System.out.println("Have a new call " + availableSlotsOfFresher);
            availableSlotsOfFresher--;
            fresherCond.signal();
        } finally {
            lock.unlock();
        }

    }

    public void fresherAnswering() throws InterruptedException {
        lock.lock();
        try {
            waitIfNoCall();
            double rnd = Math.random();
            if (rnd > 0.9 || availableSlotsOfFresher == 0) {
                availableSlotsOfFresher++;
                techLeadCond.signal();
                System.out.println("Forward the call to TL.");
            } else {
                System.out.println("Fresher is answering the call.");
                availableSlotsOfFresher++;
            }
            callerCond.signal();
        } finally {
            lock.unlock();
        }
    }

    public void techLeadAnswering() throws InterruptedException {
        lock.lock();
        try {
            waitIfNoTLCall();
            availableSlotsOfTL--;
            double rnd = Math.random();
            if (rnd > 0.9 || availableSlotsOfPM == 0) {
                availableSlotsOfTL++;
                productManagerCond.signal();
                System.out.println("Forward the call to PM.");
            } else {
                System.out.println("TL is answering the call.");
                availableSlotsOfTL++;
            }
            callerCond.signal();
        } finally {
            lock.unlock();
        }
    }

    public void productManagerAnswering() throws InterruptedException {
        lock.lock();
        try {
            waitIfNoPMCall();
            availableSlotsOfPM--;
            System.out.println("PM is answering the call.");
            availableSlotsOfPM++;
            callerCond.signal();
        } finally {
            lock.unlock();
        }
    }


    private void waitIfNoCall() throws InterruptedException {
        if (availableSlotsOfFresher == 10) {
            fresherCond.await();
        }
    }

    private void waitIfNoTLCall() throws InterruptedException {
        if (availableSlotsOfTL == 1) {
            techLeadCond.await();
        }
    }

    private void waitIfNoPMCall() throws InterruptedException {
        if (availableSlotsOfPM == 1) {
            productManagerCond.await();
        }
    }

    private void waitIfNoAvailableSlot() throws InterruptedException {
        if (availableSlotsOfFresher == 0) {
//            System.out.println("System is full:" + availableSlotsOfFresher + " " + availableSlotsOfTL + " " + availableSlotsOfTL);
            callerCond.await();
        }
    }


}
