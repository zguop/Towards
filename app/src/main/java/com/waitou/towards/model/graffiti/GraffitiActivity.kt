package com.waitou.towards.model.graffiti

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.*
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.to.aboomy.statusbar_lib.StatusBarUtil
import com.waitou.basic_lib.util.GlideEngine
import com.waitou.normal_dialog_lib.IDialogView
import com.waitou.normal_dialog_lib.NormalDialog
import com.waitou.normal_dialog_lib.SheetAdapterDialog
import com.waitou.towards.R
import com.waitou.towards.model.graffiti.view.ColorPickerDialog
import com.waitou.towards.util.KitUtils
import com.waitou.wisdom_impl.ui.PhotoWallActivity
import com.waitou.wisdom_lib.Wisdom
import com.waitou.wisdom_lib.config.ofImage
import com.waitou.wt_library.base.BaseActivity
import com.xw.repo.BubbleSeekBar
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_graffiti.*
import kotlinx.android.synthetic.main.item_seek_bar.view.*
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by waitou on 17/3/19.
 * 随手涂鸦
 */

class GraffitiActivity : BaseActivity() {

    companion object {
        const val PHOTO_REQUEST = 0x01
    }

    private var disposable: Disposable? = null

    override fun immersiveStatusBar(): Boolean {
        return StatusBarUtil.transparencyBar(this, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graffiti)
        initGraffiti()
        actionSave()
        actionUndo()
        actionRedo()
        actionClean()
        actionUploadPic()
        actionColor()
        actionWidth()
        actionTool()
        actionMoveTop()
        actionMoveBottom()
        actionMoveLeft()
        actionMoveRight()
        actionRotatePic()
        actionScaleSmallPic()
        actionScaleBigPic()
        actionReset()
    }

    private fun initGraffiti(){
        graffiti.shapeType = GraffitiToolEnum.pencil.type
        graffiti.color = Color.parseColor("#99ff0000")
        graffiti.strokeWidth = 14
    }

    private fun actionReset() {
        actionReset.setOnClickListener {
            pic.scale = 1f
            pic.rotate = 0
            pic.transX = 0f
            pic.transY = 0f
        }
    }

    private fun actionScaleBigPic() {
        actionScaleBigPic.setOnClickListener {
            pic.scale += 0.05f
        }
    }

    private fun actionScaleSmallPic() {
        actionScaleSmallPic.setOnClickListener {
            val scale = this.pic.scale - 0.05f
            if (scale < 0.1) {
                return@setOnClickListener
            }
            this.pic.scale = scale
        }
    }

    private fun actionRotatePic() {
        actionRotatePic.setOnClickListener {
            this.pic.rotate = (pic.rotate + 90) % 360
        }
    }

    private fun actionMoveRight() {
        actionMoveRight.setOnTouchListener { v, event ->
            interval(v, event.action) {
                pic.transX += 1
            }
        }
    }

    private fun actionMoveLeft() {
        actionMoveLeft.setOnTouchListener { v, event ->
            interval(v, event.action) {
                pic.transX -= 1
            }
        }
    }

    private fun actionMoveBottom() {
        actionMoveBottom.setOnTouchListener { v, event ->
            interval(v, event.action) {
                pic.transY += 1
            }
        }
    }

    private fun actionMoveTop() {
        actionMoveTop.setOnTouchListener { v, event ->
            interval(v, event.action) {
                pic.transY -= 1
            }
        }
    }

    private fun actionTool() {
        var dialog: NormalDialog? = null
        actionTool.setOnClickListener {
            val graffitiToolAdapter = GraffitiToolAdapter(graffiti.shapeType)
            graffitiToolAdapter.callback = {
                graffiti.shapeType = it
                dialog?.dismiss()
            }
            dialog = SheetAdapterDialog()
                    .setTitle("工具选择")
                    .grid(3)
                    .setItemHeight(SizeUtils.dp2px(40f))
                    .setRecyclerAdapter(graffitiToolAdapter)
                    .setGravity(Gravity.CENTER)
            dialog!!.show(supportFragmentManager)
        }
    }

    /**
     * 选择画笔宽度
     */
    private fun actionWidth() {
        var dialog: NormalDialog? = null
        actionWidth.setOnClickListener {
            dialog = NormalDialog()
                    .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                    .setDialogView(object : IDialogView() {
                        override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
                            val inflate = inflater.inflate(R.layout.item_seek_bar, container, false)
                            inflate.seekBar.setProgress(graffiti.strokeWidth.toFloat())
                            inflate.seekBar.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListenerAdapter() {
                                override fun getProgressOnActionUp(progress: Int, progressFloat: Float) {
                                    graffiti.strokeWidth = progress
                                    dialog?.dismiss()
                                }
                            }
                            return inflate
                        }
                    })
            dialog!!.show(supportFragmentManager)
        }
    }

    /**
     * 上传图片
     */
    private fun actionUploadPic() {
        actionUploadPic.setOnClickListener {
            Wisdom.of(this)
                    .config(ofImage())
                    .isCamera(false)
                    .selectLimit(1)
                    .imageEngine(GlideEngine())
                    .forResult(PHOTO_REQUEST, PhotoWallActivity::class.java)
        }
    }

    /**
     * 清屏
     */
    private fun actionClean() {
        actionClean.setOnClickListener {
            graffiti.clean()
        }
    }

    /**
     * 前进
     */
    private fun actionRedo() {
        actionRedo.setOnClickListener {
            graffiti.redo()
        }
    }

    /**
     * 回退
     */
    private fun actionUndo() {
        actionUndo.setOnClickListener {
            graffiti.undo()
        }
    }

    /**
     * 保存
     */
    private fun actionSave() {
        actionSave.setOnClickListener {
            PermissionUtils.permission(PermissionConstants.STORAGE)
                    .rationale { shouldRequest -> shouldRequest.again(true) }
                    .callback(object : PermissionUtils.FullCallback {
                        override fun onGranted(permissionsGranted: List<String>) {
                            val bitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), Bitmap.Config.ARGB_8888)
                            val bitCanvas = Canvas(bitmap)
                            if (graffiti.checkSave() || pic.checkSave()) {
                                graffiti.doDraw(bitCanvas)
                                pic.doDraw(bitCanvas)
                                val imageCacheSavePath = PathUtils.getExternalPicturesPath() + File.separator + "IMAGE_" + System.currentTimeMillis() + ".jpg"
                                val save = ImageUtils.save(bitmap, imageCacheSavePath, Bitmap.CompressFormat.JPEG, true)
                                if (save) {
                                    KitUtils.saveImageToGallery(File(imageCacheSavePath))
                                    ToastUtils.showShort("图片成功保存到相册O(∩_∩)O~")
                                }
                            } else {
                                ToastUtils.showShort("先绘制点什么吧!╮(╯▽╰)╭")
                            }
                        }

                        override fun onDenied(permissionsDeniedForever: List<String>, permissionsDenied: List<String>) {
                            ToastUtils.showShort(if (ObjectUtils.isEmpty(permissionsDeniedForever)) "保存图片需要授权该权限！" else "请到应用设置中开启存储权限!") //拒绝了权限
                        }
                    }).request()
        }
    }


    /**
     * 选择颜色
     */
    private fun actionColor() {
        actionColor.setOnClickListener {
            ColorPickerDialog(this, graffiti.color) {
                graffiti.color = it
            }.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK != resultCode || data == null) {
            return
        }
        if (PHOTO_REQUEST == requestCode) {
            val media = Wisdom.obtainResult(data)
            val bitmap = ImageUtils.getBitmap(media[0].path, 480, 800)
            if (bitmap != null) {
                pic.setBitmap(bitmap)
                picMenu.visibility = View.VISIBLE
            }
        }
    }


    private fun interval(target: View, actionMasked: Int, action: ((Long) -> Unit)): Boolean {
        try {
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                disposable = Flowable.interval(0, 10, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                        .onBackpressureDrop()
                        .subscribe(action)
            }
            if (actionMasked == MotionEvent.ACTION_UP) {
                disposable?.dispose()
                target.performClick()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }
}
