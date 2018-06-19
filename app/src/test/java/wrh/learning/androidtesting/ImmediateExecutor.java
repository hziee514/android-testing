package wrh.learning.androidtesting;

import java.util.concurrent.Executor;

/**
 * @author bruce.wu
 * @date 2018/6/18
 */
public class ImmediateExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
