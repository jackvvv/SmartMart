package sinia.com.smartmart.utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class ActivityManager extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();

    private static ActivityManager instance;

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void finishAllActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    public void finishFrontActivity() {
        if (activityList.size() > 0) {
            int size = activityList.size() - 1;
            for (int i = 0; i < size; i++) {
                activityList.get(0).finish();
                activityList.remove(activityList.get(0));
            }

        }
    }

    public Activity getFrontActivity() {
        if (activityList.size() > 0) {
            return activityList.get(activityList.size() - 2);
        }
        return null;
    }

    public void finishCurrentActivity() {
        if (activityList.size() > 0) {
            activityList.get(activityList.size() - 1).finish();
            activityList.remove(activityList.size() - 1);
        }

    }

    public void finishCurrentActivity(Activity activity) {
        if (activityList.size() > 0) {
            activity.finish();
            activityList.remove(activity);
        }

    }

    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getClass().equals(cls)) {
                activityList.get(i).finish();

                activityList.remove(i);
            }
        }

    }

    public boolean isContainsActivity(Class<?> cls) {
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public Activity currentActivity() {
        if (activityList.size() > 0) {
            return activityList.get(activityList.size() - 1);
        }
        return null;
    }

    public void allActivity() {
        String className = "";
        for (Activity activity : activityList) {
            className += activity.getClass() + "--";
        }
    }

}
