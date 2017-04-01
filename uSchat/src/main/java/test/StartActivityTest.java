package test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;

import com.robotium.solo.Solo;
import com.xiangyue.act.GuideActivity;
import com.xiangyue.act.LoginActivity;
import com.xiangyue.act.MainActivity;
import com.xiangyue.act.R;


public class StartActivityTest extends ActivityInstrumentationTestCase2<GuideActivity> {


    private Solo solo;

    private Instrumentation mIns;
    private Button startMainBtn;
    private Button loginBtn;

    private String tab1 = "";
    private String tab2 = "";
    private String tab3 = "";
    private String tab4 = "";

    private String userID = "18702082007";
    private String password = "666";

    public StartActivityTest() {
        super(GuideActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        mWelcomeActivity = getActivity();
        mIns = getInstrumentation();
        solo = new Solo(mIns, mWelcomeActivity);
        initView();

    }

    private void initView() {
        tab1 = mWelcomeActivity.getString(R.string.money);
        tab2 = mWelcomeActivity.getString(R.string.message);
        tab3 = mWelcomeActivity.getString(R.string.study);
        tab4 = mWelcomeActivity.getString(R.string.me);
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        //		solo.finishOpenedActivities();
    }

    private GuideActivity mWelcomeActivity;
    private MainActivity mLepusLoginActivity;
    private LoginActivity loginActivity;

    public void testAutoLogin() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();

        if (solo.waitForActivity("GuideActivity")) {
            mWelcomeActivity = (GuideActivity) solo.getCurrentActivity();
            solo.drag(480, 80, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            solo.drag(480, 80, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            startMainBtn = (Button) mWelcomeActivity.findViewById(R.id.login);
            solo.clickOnView(startMainBtn);
        }

        if (solo.waitForActivity("LoginActivity")) {
            loginActivity = (LoginActivity) solo.getCurrentActivity();
            loginBtn = (Button) loginActivity.findViewById(R.id.btn_login);
            solo.enterText(0, userID);
            solo.sleep(1000);
            solo.enterText(1, password);
            solo.sleep(1000);
            solo.clickOnView(loginBtn);
            solo.sleep(5000);

        }


        if (solo.waitForActivity("MainActivity")) {
//            boolean tabNameFound = solo.searchText(tab1) && solo.searchText(tab2) && solo.searchText(tab3) && solo.searchText(tab4);
//            //Assert tab name are found
//            assertTrue("我 are not found", tabNameFound);
            mLepusLoginActivity = (MainActivity) solo.getCurrentActivity();
//            for (int i = 0; i < 3; i++) {
//            solo.clickOnText(tab1);
//            solo.sleep(1000);
//            solo.clickOnText(tab2);
//            solo.sleep(2000);
//            solo.clickOnText(tab3);
//            solo.sleep(3000);
//            solo.clickOnText(tab4);
//            solo.sleep(2000);
//            }
            solo.drag(480, 80, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            solo.drag(480, 80, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            solo.drag(480, 80, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            solo.drag(80, 480, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            solo.drag(80, 480, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            solo.drag(80, 480, 1920 / 2, 1920 / 2, 1);
            solo.sleep(2000);
            Log.e("================", "自动化测试OK");
        }
    }
}