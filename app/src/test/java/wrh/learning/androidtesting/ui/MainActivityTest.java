package wrh.learning.androidtesting.ui;

import android.content.Context;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowProgressDialog;
import org.robolectric.shadows.ShadowToast;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import wrh.learning.androidtesting.AppExecutors;
import wrh.learning.androidtesting.BuildConfig;
import wrh.learning.androidtesting.R;
import wrh.learning.androidtesting.contract.MainContract;
import wrh.learning.androidtesting.domain.presenter.PresenterFactory;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author bruce.wu
 * @date 2018/6/18
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({PresenterFactory.class})
public class MainActivityTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private Context appContext;

    @Before
    public void setUp() {
        appContext = RuntimeEnvironment.application.getApplicationContext();
        PowerMockito.mockStatic(PresenterFactory.class);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void onCreate_text1() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        String expect = appContext.getString(R.string.hell_world);
        assertEquals(expect, ((TextView)activity.findViewById(R.id.lbl_text1)).getText());
    }

    @Test
    public void btn1_click() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.btn_1).performClick();
        String expect = appContext.getString(R.string.hell_world);
        assertEquals(expect, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void btn2_click() {
        MainContract.Presenter presenter = Mockito.mock(MainContract.Presenter.class);
        PowerMockito.when(PresenterFactory.create(Mockito.any(MainContract.View.class), Mockito.any(AppExecutors.class)))
                .thenReturn(presenter);

        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.btn_2).performClick();

        Mockito.verify(presenter, Mockito.times(1))
                .fetch();
    }

    @Test
    public void fetch_started() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.onFetchStarted();
        assertTrue(ShadowProgressDialog.getLatestAlertDialog().isShowing());
    }

    @Test
    public void fetch_completed() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.onFetchStarted();
        assertTrue(ShadowProgressDialog.getLatestAlertDialog().isShowing());
        activity.onFetchCompleted();
        assertFalse(ShadowProgressDialog.getLatestAlertDialog().isShowing());
    }

    @Test
    public void fetched() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.onFetchSuccess("hello");
        assertEquals("hello", ((TextView)activity.findViewById(R.id.lbl_text2)).getText());
    }

    @Test
    public void fetch_error() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ShadowLog.stream = new PrintStream(out);

        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.onFetchFailed(new RuntimeException("fetch_error"));

        assertThat(out.toString(), containsString("onFetchFailed: fetch_error"));
    }

}