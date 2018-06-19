package wrh.learning.androidtesting.domain.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author bruce.wu
 * @date 2018/6/18
 */
public class SlowServiceImplTest {

    @Before
    public void setUp() {
    }

    @Test
    public void fetch_data() {
        SlowServiceImpl impl = new SlowServiceImpl();
        String data = impl.fetch();
        assertEquals("from slow service", data);
    }

}