package wrh.learning.androidtesting.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import wrh.learning.androidtesting.AppExecutors;
import wrh.learning.androidtesting.R;
import wrh.learning.androidtesting.contract.MainContract;
import wrh.learning.androidtesting.domain.presenter.PresenterFactory;

public class MainActivity extends Activity implements MainContract.View {

    private MainContract.Presenter presenter;
    private ProgressDialog progressDialog;

    private TextView lblText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblText2 = findViewById(R.id.lbl_text2);

        presenter = PresenterFactory.create(this, new AppExecutors());
    }

    @Override
    public void onFetchStarted() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
    }

    @Override
    public void onFetchFailed(Throwable e) {
        Log.e("MainActivity", "onFetchFailed: " + e.getMessage(), e);
    }

    @Override
    public void onFetchSuccess(String data) {
        lblText2.setText(data);
    }

    @Override
    public void onFetchCompleted() {
        progressDialog.dismiss();
    }

    public void onClickButton1(View view) {
        Toast.makeText(this, R.string.hell_world, Toast.LENGTH_SHORT).show();
    }

    public void onClickButton2(View view) {
        presenter.fetch();
    }

}
