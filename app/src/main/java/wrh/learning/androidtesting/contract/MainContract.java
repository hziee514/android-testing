package wrh.learning.androidtesting.contract;

/**
 * @author bruce.wu
 * @date 2018/6/17
 */
public interface MainContract {

    interface View {

        void onFetchStarted();
        void onFetchFailed(Throwable e);
        void onFetchSuccess(String data);
        void onFetchCompleted();

    }

    interface Presenter {

        void fetch();

    }

}
