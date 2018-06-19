package wrh.learning.androidtesting;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author bruce.wu
 * @date 2018/6/17
 */
public class AppExecutors {

    private final Executor io;

    private final Executor main;

    public AppExecutors() {
        this(new ThreadPoolExecutor(0,
                2,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()), new MainThreadExecutor());
    }

    public AppExecutors(Executor io, Executor main) {
        this.io = io;
        this.main = main;
    }

    public void io(Runnable r) {
        io.execute(r);
    }

    public void main(Runnable r) {
        main.execute(r);
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
