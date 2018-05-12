package com.to.aboomy.tinker_lib.patch;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.io.File;

/**
 * author   itxp
 * date     2018/5/12 20:47
 * des
 */

public class ServerUtils {

    public static final String TINKER_SERVER_DIR   = "tinker_server";

    public static final String TINKER_VERSION_FILE = "version.info";
    public static final String TINKER_LOCK_FILE = "version.lock";



    private ServerUtils() {
        // A TinkerServerUtils Class
    }

    public static Integer stringToInteger(String string) {
        if (string == null) {
            return null;
        }
        return Integer.parseInt(string);
    }

    public static File getServerDirectory(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            // Looks like running on a test Context, so just return without patching.
            return null;
        }
        return new File(applicationInfo.dataDir, TINKER_SERVER_DIR);
    }

    public static File getServerFile(Context context, String patchVersion) {
        return new File(getServerDirectory(context), patchVersion + ".apk");
    }
}
