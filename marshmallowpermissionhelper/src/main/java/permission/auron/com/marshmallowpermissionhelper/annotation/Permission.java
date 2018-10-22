package permission.auron.com.marshmallowpermissionhelper.annotation;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import permission.auron.com.marshmallowpermissionhelper.PermissionResult;

/*

   ____                _         _           
  / ___|_ __ ___  __ _| |_ ___  | |__  _   _ 
 | |   | '__/ _ \/ _` | __/ _ \ | '_ \| | | |
 | |___| | |  __/ (_| | |_  __/ | |_) | |_| |
  \____|_|  \___|\__,_|\__\___| |_.__/ \__, |
                                       |___/                                                                      

  ____             _         _                    
 |  _ \ _   _ _ __(_) ___   | |   _   _  ___ __ _ 
 | |_) | | | | '__| |/ _ \  | |  | | | |/ __/ _` |
 |  _ <| |_| | |  | | (_) | | |___ |_| | (__ (_| |
 |_| \_\\__,_|_|  |_|\___/  |_____\__,_|\___\__,_|
  
  
  19/10/2018                 
                                                   
*/

public class Permission {

    private static Activity activityContext;
    private static PermissionResult permissionResult;
    private final int KEY_PERMISSION = 200;
    private String permissionsAsk[];

    public static void init(Activity activity, PermissionResult permissionResult) {
        activityContext = activity;
        Permission.permissionResult = permissionResult;
        getField(activityContext, activityContext.getClass().getDeclaredMethods());
    }

    public static void init(Activity activity) {
        activityContext = activity;

    }

    public static void init(Fragment fragment) {
        //  activityContext=activity;
    }

    public static void askPermission() {

    }

    private static void getField(final Object obj, Method[] methods) {


        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(AskPermission.class);
            if (annotation != null) {
                AskPermission askPermission = (AskPermission) annotation;
                int key = askPermission.key();
                String permission = askPermission.permission();
                boolean callback = askPermission.callback();

            }
        }

    }

    private void askCompactPermission(String permission, PermissionResult permissionResult) {
        permissionsAsk = new String[]{permission};
        this.permissionResult = permissionResult;
        internalRequestPermission(permissionsAsk);

    }

    private void internalRequestPermission(String[] permissionAsk) {
        String arrayPermissionNotGranted[];
        ArrayList<String> permissionsNotGranted = new ArrayList<>();

        for (int i = 0; i < permissionAsk.length; i++) {
            if (!isPermissionGranted(Permission.activityContext, permissionAsk[i])) {
                permissionsNotGranted.add(permissionAsk[i]);
            }
        }


        if (permissionsNotGranted.isEmpty()) {

            if (permissionResult != null)
                permissionResult.permissionGranted();

        } else {

            arrayPermissionNotGranted = new String[permissionsNotGranted.size()];
            arrayPermissionNotGranted = permissionsNotGranted.toArray(arrayPermissionNotGranted);
            ActivityCompat.requestPermissions(Permission.activityContext, arrayPermissionNotGranted, KEY_PERMISSION);

        }


    }

    public boolean isPermissionGranted(Context context, String permission) {

        boolean granted = ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED));
        return granted;

    }

}
