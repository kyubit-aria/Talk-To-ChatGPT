package neko.kyuubit.thread;

public class ThreadHelper {
    /**
     * Tries to start a new thread with the runnable
     * @param runnable
     */
    public static void run(Runnable runnable) {
        try {
            new Thread(runnable).start();
        } catch (IllegalThreadStateException e) {
            e.printStackTrace();
        }
    }
}
