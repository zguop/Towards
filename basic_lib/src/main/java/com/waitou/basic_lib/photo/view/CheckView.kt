package com.waitou.basic_lib.photo.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Checkable
import com.waitou.basic_lib.R

/**
 * auth aboom
 * date 2019-06-02
 */
@SuppressLint("CustomViewStyleable")
class CheckView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr), Checkable {

    private val density = context.resources.displayMetrics.density

    private val strokePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
    }

    private val bgPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    private var checkNum = UNCHECKED
    private var isChecked: Boolean = false
    private var center: Float = 0f

    private var listener: OnCheckedChangeListener? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.bs_CheckView)
        val ta = context.theme.obtainStyledAttributes(intArrayOf(R.attr.colorPrimary))
        bgPaint.color = a.getColor(R.styleable.bs_CheckView_bs_check_color, ta.getColor(0, Color.parseColor("#FB4846")))
        strokePaint.color = a.getColor(R.styleable.bs_CheckView_bs_border_color, Color.WHITE)
        textPaint.textSize = a.getDimension(R.styleable.bs_CheckView_bs_check_text_size, 14 * density)
        textPaint.color = a.getColor(R.styleable.bs_CheckView_bs_check_text_color, Color.WHITE)
        a.recycle()
        ta.recycle()
        setOnClickListener { toggle() }
    }

    private fun measureSize(measureSpec: Int): Int {
        val defSize = density * 48
        val specSize = MeasureSpec.getSize(measureSpec)
        val specMode = MeasureSpec.getMode(measureSpec)
        var result = 0
        when (specMode) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> result = Math.min(defSize.toInt(), specSize)
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        center = measuredWidth / 2.0f
        strokePaint.strokeWidth = center / 10.0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = Math.max(Math.max(paddingLeft, paddingRight), Math.max(paddingTop, paddingBottom))
        canvas.drawCircle(center, center, center - strokePaint.strokeWidth / 2 - padding, strokePaint)
        if (isChecked && checkNum != UNCHECKED) {
            canvas.drawCircle(center, center, center - strokePaint.strokeWidth - padding, bgPaint)
            val text = checkNum.toString()
            val baseX = (width - textPaint.measureText(text)) / 2
            val baseY = (height - textPaint.descent() - textPaint.ascent()) / 2
            canvas.drawText(text, baseX, baseY, textPaint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.e("aa", " onDetachedFromWindow " + hashCode())
    }

    override fun toggle() {
        setChecked(!isChecked)
    }

    override fun isChecked(): Boolean {
        return isChecked && checkNum != UNCHECKED
    }

    override fun setChecked(checked: Boolean) {
        isChecked = checked
        Log.e("aa", "  toggle code " + hashCode() + " -- " + isChecked)
        listener?.onCheckedChanged(this, checked)
    }

    fun setCheckedNum(checkedNum: Int) {
        Log.e("aa", "  setCheckedNum code " + hashCode() + " -- " + isChecked)

        //已经勾选了，checkNum = UNCHECKED 表示第一次勾选，开启动画
        if (isChecked && checkedNum != UNCHECKED && checkNum == UNCHECKED) {
            val loadAnimation = AnimationUtils.loadAnimation(context, R.anim.bs_check_in)
            startAnimation(loadAnimation)
        }
        this.checkNum = checkedNum
        if (checkNum == UNCHECKED) {
            isChecked = false
        }
        invalidate()
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(checkView: CheckView, isChecked: Boolean)
    }

    fun setOnCheckedChangeListener(l: OnCheckedChangeListener.(checkView: CheckView, isChecked: Boolean) -> Unit) {
        listener = object : OnCheckedChangeListener {
            override fun onCheckedChanged(checkView: CheckView, isChecked: Boolean) {
                l.invoke(this, checkView, isChecked)
            }
        }
    }

    companion object {
        const val UNCHECKED = -1
    }
}
