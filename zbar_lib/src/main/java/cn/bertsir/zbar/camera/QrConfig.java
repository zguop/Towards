package cn.bertsir.zbar.camera;

import java.io.Serializable;

/**
 * Created by Bert on 2017/9/22.
 */

public class QrConfig implements Serializable {
    public static final int TYPE_QRCODE  = 1;//扫描二维码
    public static final int TYPE_BARCODE = 2;//扫描条形码（UPCA）
    public static final int TYPE_ALL     = 3;//扫描全部类型码
    public static final int TYPE_CUSTOM  = 4;//扫描用户定义类型码

    public static final int SCANVIEW_TYPE_QRCODE  = 0;//二维码框
    public static final int SCANVIEW_TYPE_BARCODE = 1;//条形码框
}
