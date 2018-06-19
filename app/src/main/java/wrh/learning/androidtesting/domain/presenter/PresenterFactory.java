package wrh.learning.androidtesting.domain.presenter;

import wrh.learning.androidtesting.AppExecutors;
import wrh.learning.androidtesting.contract.MainContract;

/**
 * @author bruce.wu
 * @date 2018/6/18
 */
public final class PresenterFactory {

    public static MainContract.Presenter create(MainContract.View view, AppExecutors executors) {
        return new MainPresenter(view, executors);
    }

}
