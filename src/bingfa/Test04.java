package bingfa;

public class Test04 {
	public static void main(String[] args) {
        String[] names = { "���", "����", "������", "���", "��Ī��" };   // 5λ��ѧ�ҵ�����
//      ExecutorService es = Executors.newFixedThreadPool(AppContext.NUM_OF_PHILO); // �����̶���С���̳߳�
//      for(int i = 0, len = names.length; i < len; ++i) {
//          es.execute(new Philosopher(i, names[i]));   // �����߳�
//      }
//      es.shutdown();
        for(int i = 0, len = names.length; i < len; ++i) {
            new Thread(new Philosopher(i, names[i])).start();
        }
    }
}
