package wrh.learning.androidtesting.domain.service;

/**
 * @author bruce.wu
 * @date 2018/6/18
 */
public final class ServiceFactory {

    public static SlowService create() {
        return new SlowServiceImpl();
    }

}
