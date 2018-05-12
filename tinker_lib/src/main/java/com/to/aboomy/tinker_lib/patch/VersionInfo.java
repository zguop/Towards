package com.to.aboomy.tinker_lib.patch;

import android.content.Context;

import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.ShareFileLockHelper;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

/**
 * author   itxp
 * date     2018/5/12 23:48
 * des
 */

public class VersionInfo {
    private static final String TAG = "Tinker.VersionInfo";

    public static final String APP_VERSION = "app";
    public static final String UUID_VALUE = "uuid";
    public static final String CURRENT_VERSION = "version";
    public static final String CURRENT_MD5 = "md5";

    private static VersionInfo sVersionInfo;

    private final File    versionFile;
    private final File    lockFile;
    private       String  uuid;
    private       String  appVersion;
    private       Integer patchVersion;
    private       String  patchMd5;

    public static VersionInfo getInstance() {
        if (sVersionInfo == null) {
            throw new RuntimeException("Please invoke init VersionInfo first");
        } else {
            return sVersionInfo;
        }
    }

    public static void init(Context context) {
        if (sVersionInfo != null) {
            throw new RuntimeException("version info is already init");
        } else {
            sVersionInfo = new VersionInfo(context, BuildInfo.VERSION_NAME);
        }
    }

    private VersionInfo(Context context, String appVersion) {
        versionFile = new File(ServerUtils.getServerDirectory(context), ServerUtils.TINKER_VERSION_FILE);
        lockFile = new File(ServerUtils.getServerDirectory(context), ServerUtils.TINKER_LOCK_FILE);

        readVersionProperty();

        if (versionFile.exists() && this.uuid != null && this.appVersion != null && this.patchVersion != null) {
            if (!appVersion.equals(this.appVersion)) {
                updateVersionProperty(appVersion, 0, "", this.uuid);
            }
        } else {
            updateVersionProperty(appVersion, 0, "", UUID.randomUUID().toString());
        }
    }

    public boolean isUpdate(Integer version, String currentAppVersion) {
        if (!currentAppVersion.equals(appVersion)) {
            TinkerLog.d(TAG, "update return true, appVersion from %s to %s", appVersion, currentAppVersion);
            return true;
        }
        Integer current = getPatchVersion();
        if (version > current) {
            TinkerLog.d(TAG, "update return true, patchVersion from %s to %s", current, version);
            return true;
        } else {
            TinkerLog.d(TAG, "update return false, target version is not latest. current version is:" + version);
            return false;
        }
    }

    public void updateTinkerVersion(int patchVersion, String patchMd5) {
        updateVersionProperty(this.appVersion, patchVersion, patchMd5, this.uuid);
    }

    public String getUUID() {
        return uuid == null ? "" : uuid;
    }

    public String getAppVersion() {
        return appVersion == null ? "" : appVersion;
    }

    public Integer getPatchVersion() {
        return patchVersion == null ? 0 : patchVersion;
    }

    public String getPatchMd5() {
        return patchMd5 == null ? "" : patchMd5;
    }

    public String id() {
        return uuid;
    }

    private void readVersionProperty() {
        if (versionFile == null || !versionFile.exists() || versionFile.length() == 0) {
            return;
        }
        ShareFileLockHelper shareFileLockHelper = null;
        FileInputStream inputStream = null;
        try {
            shareFileLockHelper = ShareFileLockHelper.getFileLock(lockFile);
            Properties properties = new Properties();
            inputStream = new FileInputStream(versionFile);
            properties.load(inputStream);
            uuid = properties.getProperty(UUID_VALUE);
            appVersion = properties.getProperty(APP_VERSION);
            patchVersion = ServerUtils.stringToInteger(properties.getProperty(CURRENT_VERSION));
            patchMd5 = properties.getProperty(CURRENT_MD5);
            TinkerLog.i(TAG, "readVersionInfo file path:" + versionFile.getAbsolutePath() +
                    ", appVersion: " + appVersion + ", uuid:" + uuid +
                    ", patchVersion:" + patchVersion + ", patchMd5:" + patchMd5);
        } catch (IOException e) {
            TinkerLog.printErrStackTrace(TAG, e, "readVersionInfo fail");
        } finally {
            SharePatchFileUtil.closeQuietly(inputStream);
            try {
                if (shareFileLockHelper != null) {
                    shareFileLockHelper.close();
                }
            } catch (IOException e) {
                TinkerLog.printErrStackTrace("Tinker.VersionInfo", e, "releaseInfoLock error");
            }
        }
    }

    private void updateVersionProperty(String appVersion, int currentVersion,
                                       String patchMd5, String uuid) {
        TinkerLog.d(TAG, "updateVersionProperty file path:"
                + versionFile.getAbsolutePath()
                + " , appVersion: " + appVersion
                + " , uuid:" + uuid
                + " , patchVersion:" + currentVersion
                + " , patchMd5:" + patchMd5);

        File parentFile = versionFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            TinkerLog.e("Tinker.VersionInfo", "make mkdirs error: " + parentFile.getAbsolutePath());
        }

        ShareFileLockHelper shareFileLockHelper = null;
        try {
            shareFileLockHelper = ShareFileLockHelper.getFileLock(lockFile);
            Properties newProperties = new Properties();
            newProperties.put(CURRENT_VERSION, String.valueOf(currentVersion));
            newProperties.put(CURRENT_MD5, patchMd5);
            newProperties.put(APP_VERSION, appVersion);
            newProperties.put(UUID_VALUE, uuid);
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(versionFile, false);
                String comment = "from old version:" + getPatchVersion() + " to new version:" + currentVersion;
                newProperties.store(outputStream, comment);
            } catch (Exception e) {
                TinkerLog.e(TAG, "updateVersionInfo exception: %s", e.getMessage());
            } finally {
                SharePatchFileUtil.closeQuietly(outputStream);
            }
            //update value
            this.appVersion = appVersion;
            this.patchVersion = currentVersion;
            this.uuid = uuid;
            this.patchMd5 = patchMd5;
        } catch (Exception e) {
            TinkerLog.e("Tinker.VersionInfo", "updateVersionInfo fail %s", e.getMessage());
        } finally {
            try {
                if (shareFileLockHelper != null) {
                    shareFileLockHelper.close();
                }
            } catch (IOException e) {
                TinkerLog.printErrStackTrace("Tinker.VersionInfo", e, "releaseInfoLock error");
            }
        }
    }
}
