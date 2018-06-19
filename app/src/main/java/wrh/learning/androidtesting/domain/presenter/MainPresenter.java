package wrh.learning.androidtesting.domain.presenter;

import wrh.learning.androidtesting.AppExecutors;
import wrh.learning.androidtesting.contract.MainContract;
import wrh.learning.androidtesting.domain.service.ServiceFactory;

/**
 * @author bruce.wu
 * @date 2018/6/17
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private final AppExecutors executors;

    MainPresenter(MainContract.View view, AppExecutors executors) {
        this.view = view;
        this.executors = executors;
    }

    @Override
    public void fetch() {
        executors.io(() -> {
            executors.main(view::onFetchStarted);
            try {
                String result = ServiceFactory.create()
                        .fetch();
                executors.main(() -> view.onFetchSuccess(result));
            } catch (Exception e) {
                executors.main(() -> view.onFetchFailed(e));
            } finally {
                executors.main(view::onFetchCompleted);
            }
        });
    }
}
