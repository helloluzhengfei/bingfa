package bingfa;
import java.util.concurrent.Semaphore;

public class AppContext {
	public static final int NUM_OF_FORKS = 5;   // ��������(��Դ)
    public static final int NUM_OF_PHILO = 5;   // ��ѧ������(�߳�)

    public static Semaphore[] forks;    // ���ӵ��ź���
    public static Semaphore counter;    // ��ѧ�ҵ��ź���

    static {
        forks = new Semaphore[NUM_OF_FORKS];

        for (int i = 0, len = forks.length; i < len; ++i) {
            forks[i] = new Semaphore(1);    // ÿ�����ӵ��ź���Ϊ1
        }

        counter = new Semaphore(NUM_OF_PHILO - 1);  // �����N����ѧ�ң����ֻ����N-1��ͬʱȡ����
    }

    /**
     * ȡ�ò���
     * @param index �ڼ�����ѧ��
     * @param leftFirst �Ƿ���ȡ����ߵĲ���
     * @throws InterruptedException
     */
    public static void putOnFork(int index, boolean leftFirst) throws InterruptedException {
        if(leftFirst) {
            forks[index].acquire();
            forks[(index + 1) % NUM_OF_PHILO].acquire();
        }
        else {
            forks[(index + 1) % NUM_OF_PHILO].acquire();
            forks[index].acquire();
        }
    }

    /**
     * �Żز���
     * @param index �ڼ�����ѧ��
     * @param leftFirst �Ƿ��ȷŻ���ߵĲ���
     * @throws InterruptedException
     */
    public static void putDownFork(int index, boolean leftFirst) throws InterruptedException {
        if(leftFirst) {
            forks[index].release();
            forks[(index + 1) % NUM_OF_PHILO].release();
        }
        else {
            forks[(index + 1) % NUM_OF_PHILO].release();
            forks[index].release();
        }
    }
}

/**
 * ��ѧ��
 * @author ���
 *
 */
class Philosopher implements Runnable {
    private int index;      // ���
    private String name;    // ����

    public Philosopher(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public void run() {
        while(true) {
            try {
                AppContext.counter.acquire();
                boolean leftFirst = index % 2 == 0;
                AppContext.putOnFork(index, leftFirst);
                System.out.println(name + "���ڳ�������棨ͨ�ķۣ�...");   // ȡ���������ӾͿ��Խ�ʳ
                AppContext.putDownFork(index, leftFirst);
                AppContext.counter.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


