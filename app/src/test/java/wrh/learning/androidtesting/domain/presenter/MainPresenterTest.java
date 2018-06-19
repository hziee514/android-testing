package wrh.learning.androidtesting.domain.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import wrh.learning.androidtesting.AppExecutors;
import wrh.learning.androidtesting.ImmediateExecutor;
import wrh.learning.androidtesting.contract.MainContract;
import wrh.learning.androidtesting.domain.service.ServiceFactory;
import wrh.learning.androidtesting.domain.service.SlowService;

import static org.junit.Assert.assertEquals;

/**
 * @author bruce.wu
 * @date 2018/6/18
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ServiceFactory.class})
public class MainPresenterTest {

    private AppExecutors executors = new AppExecutors(new ImmediateExecutor(), new ImmediateExecutor());

    @Before
    public void setUp() {
        PowerMockito.mockStatic(ServiceFactory.class);
    }

    @Test
    public void fetch_failed() {
        RuntimeException exception = new RuntimeException("fetch failed");

        SlowService service = Mockito.mock(SlowService.class);
        Mockito.when(service.fetch()).thenThrow(exception);
        PowerMockito.when(ServiceFactory.create())
                .thenReturn(service);

        MainContract.View view = Mockito.mock(MainContract.View.class);
        MainPresenter presenter = new MainPresenter(view, executors);

        presenter.fetch();

        Mockito.verify(service, Mockito.times(1)).fetch();
        Mockito.verify(view, Mockito.times(1)).onFetchStarted();
        Mockito.verify(view, Mockito.times(1)).onFetchCompleted();
        ArgumentCaptor<Throwable> captor = ArgumentCaptor.forClass(Throwable.class);
        Mockito.verify(view, Mockito.times(1)).onFetchFailed(captor.capture());
        assertEquals(exception, captor.getValue());
        Mockito.verify(view, Mockito.times(0)).onFetchSuccess(Mockito.anyString());
    }

    @Test
    public void fetch_success() {
        String expected = "hello world";
        SlowService service = Mockito.mock(SlowService.class);
        Mockito.when(service.fetch()).thenReturn(expected);
        PowerMockito.when(ServiceFactory.create())
                .thenReturn(service);

        MainContract.View view = Mockito.mock(MainContract.View.class);
        MainPresenter presenter = new MainPresenter(view, executors);

        presenter.fetch();

        Mockito.verify(service, Mockito.times(1)).fetch();
        Mockito.verify(view, Mockito.times(1)).onFetchStarted();
        Mockito.verify(view, Mockito.times(1)).onFetchCompleted();
        Mockito.verify(view, Mockito.times(0)).onFetchFailed(Mockito.anyObject());
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.times(1)).onFetchSuccess(captor.capture());
        assertEquals(expected, captor.getValue());
    }

}