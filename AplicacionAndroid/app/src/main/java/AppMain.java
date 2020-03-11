import android.app.Application;

import com.google.firebase.functions.FirebaseFunctions;

public class AppMain extends Application {

    private FirebaseFunctions mFunctions;

    @Override
    public void onCreate() {
        super.onCreate();
        mFunctions = FirebaseFunctions.getInstance();
    }
}
