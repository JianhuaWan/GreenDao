package o2o.shop.com.testjsoup;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;


public final class CrashHandler
{
    private static final int TIMESTAMP_DIFFERENCE_TO_AVOID_RESTART_LOOPS_IN_MILLIS = 2000;

    // Shared preferences
    private static final String SHARED_PREFERENCES_FILE = "custom_activity_on_crash";

    private static final String SHARED_PREFERENCES_FIELD_TIMESTAMP = "last_crash_timestamp";

    private static Application mApplication;

    public static void init(Context ctx)
    {
        // INSTALL!
        final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

        mApplication = (Application) ctx.getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable)
            {
                throwable.printStackTrace();

                if(hasCrashedInTheLastSeconds(mApplication))
                {
                    if(oldHandler != null)
                    {
                        oldHandler.uncaughtException(thread, throwable);
                        return;
                    }
                }
                else
                {
                    setLastCrashTimestamp(mApplication, new Date().getTime());

                    /*
                     * if (isStackTraceLikelyConflictive(throwable,
                     * ErrorActivity.class)) { if (oldHandler != null) {
                     * oldHandler.uncaughtException(thread, throwable); return;
                     * } } else
                     */
                    {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        throwable.printStackTrace(pw);
                        SaveErrorLog("CrashHandler|| " + sw.toString());


                        // Intent intent = new Intent(mApplication,
                        // ErrorActivity.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        // Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        // mApplication.startActivity(intent);
                        showToast();
                    }
                }
                // releaseUI();
                // killCurrentProcess();
            }
        });
    }


    private static void killCurrentProcess()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * INTERNAL method that tells if the app has crashed in the last seconds.
     * This is used to avoid restart loops.
     *
     * @return true if the app has crashed in the last seconds, false otherwise.
     */
    private static boolean hasCrashedInTheLastSeconds(Context context)
    {
        long lastTimestamp = getLastCrashTimestamp(context);
        long currentTimestamp = new Date().getTime();

        return (lastTimestamp <= currentTimestamp && currentTimestamp - lastTimestamp < TIMESTAMP_DIFFERENCE_TO_AVOID_RESTART_LOOPS_IN_MILLIS);
    }

    /**
     * INTERNAL method that stores the last crash timestamp
     *
     * @param timestamp The current timestamp.
     */
    private static void setLastCrashTimestamp(Context context, long timestamp)
    {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE).edit()
                .putLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, timestamp).commit();
    }

    /**
     * INTERNAL method that gets the last crash timestamp
     *
     * @return The last crash timestamp, or -1 if not set.
     */
    private static long getLastCrashTimestamp(Context context)
    {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
                .getLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, -1);
    }

    /**
     * INTERNAL method that checks if the stack trace that just crashed is
     * conflictive. This is true in the following scenarios: - The application
     * has crashed while initializing (handleBindApplication is in the stack) -
     * The error activity has crashed (activityClass is in the stack)
     *
     * @param throwable     The throwable from which the stack trace will be checked
     * @param activityClass The activity class to launch when the app crashes
     * @return true if this stack trace is conflictive and the activity must not
     * be launched, false otherwise
     */
    private static boolean isStackTraceLikelyConflictive(Throwable throwable, Class<? extends Activity> activityClass)
    {
        do
        {
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            for(StackTraceElement element : stackTrace)
            {
                if((element.getClassName().equals("android.app.ActivityThread") && element.getMethodName()
                        .equals("handleBindApplication"))
                        || element.getClassName().equals(activityClass.getName()))
                {
                    return true;
                }
            }
        } while((throwable = throwable.getCause()) != null);
        return false;
    }

    public static void SaveErrorLog(Throwable throwable)
    {
        String errorStrings = getErrorStrings(throwable);
        SaveErrorLog(errorStrings);
    }

    private static String getErrorStrings(Throwable ex)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTraceString = sw.toString();

        return stackTraceString;
    }

    public static void SaveErrorLog(String msg)
    {
        String error = msg;
    }

    public static void showToast()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                Looper.prepare();
                Toast.makeText(mApplication, "error", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
    }
}
