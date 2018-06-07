import android.app.Activity;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * Created by 冯雪松 on 2018-04-24.
 */

public class BaseActivity extends Activity {
    public static LinkedList<Activity> sAllActivitys = new LinkedList<Activity>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sAllActivitys.add(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sAllActivitys.remove(this);
    }


    public static void finishAll() {
        for(Activity activity : sAllActivitys) {
            activity.finish();
        }

        sAllActivitys.clear();
    }

    public static void exit() {
        finishAll();
        // 这个主要是用来关闭进程的, 关把所有activity finish
        // 的话，进程是不会关闭的
        System.exit(0);
    }
}
