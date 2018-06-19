package wrh.learning.androidtesting.domain.service;

/**
 * @author bruce.wu
 * @date 2018/6/18
 */
public class SlowServiceImpl implements SlowService {

    @Override
    public String fetch() {
        slowly(2000);
        return "from slow service";
    }

    private void slowly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
