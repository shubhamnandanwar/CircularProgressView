package com.shunan.circularprogressbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator


class CircularProgressBar : View {

    companion object {
        const val ROUND = 0
        const val FLAT = 1

        const val LINEAR = 0
        const val ACCELERATE = 1
        const val DECELERATE = 2

        private const val DEFAULT_PROGRESS = 25
        private const val DEFAULT_MAX_PROGRESS = 100
        private const val DEFAULT_PROGRESS_COLOR = Color.BLUE
        private const val DEFAULT_PROGRESS_WIDTH = 16f

        private const val DEFAULT_PROGRESS_BACKGROUND_WIDTH = 4f
        private const val DEFAULT_PROGRESS_BACKGROUND_COLOR = Color.LTGRAY

        private const val DEFAULT_TEXT_COLOR = Color.BLACK
        private const val DEFAULT_TEXT_SIZE = 14f

        private const val DEFAULT_START_ANGLE = 0

        private const val DEFAULT_ENABLE_BACKGROUND_DASH_EFFECT = false
        private const val DEFAULT_SHOW_DOT = true

        private const val DEFAULT_PROGRESS_CAP = ROUND

        private const val DEFAULT_DOT_WIDTH = 20f

        private const val DEFAULT_DASH_LINE_LENGTH = 8f
        private const val DEFAULT_DASH_LINE_OFFSET = 3f

        private const val DEFAULT_ANIMATION_DURATION = 1000
        private const val DEFAULT_INTERPOLATOR = LINEAR

        private const val DEFAULT_SIZE_DP = 128f

        private const val DEFAULT_ALPHA = 255
        private const val DEFAULT_FADE_REPEAT_COUNT = 3
        private const val DEFAULT_MIN_FADE_ALPHA = 32
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        currentAnimatedProgress = 0f
        refresh()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }

    private val mProgressAnimator = ValueAnimator()
    private val mAlphaAnimator = ValueAnimator()
    private var currentAnimatedProgress = 0f
    private var options = CircularProgressOptions()

    var progress
        set(value) {
            options.progress = if (value < 0) 0 else value
            refresh()
        }
        get() = options.progress


    var maxProgress
        set(value) {
            if (value < 0) throw IllegalArgumentException("Maximum Progress can't be negative")
            else {
                options.maxProgress = value
                currentAnimatedProgress = 0f
                refresh()
            }
        }
        get() = options.maxProgress

    var progressColor
        set(value) {
            options.progressColor = value
            refresh()
        }
        get() = options.progressColor

    var progressWidth
        set(value) {
            if (value < 0) throw IllegalArgumentException("Progress Width can't be negative")
            else {
                options.progressWidth = value
                refresh()
            }
        }
        get() = options.progressWidth

    var progressBackgroundWidth
        set(value) {
            if (value < 0) throw IllegalArgumentException("Progress Background Width can't be negative")
            else {
                options.progressBackgroundWidth = value
                refresh()
            }
        }
        get() = options.progressBackgroundWidth

    var progressBackgroundColor
        set(value) {
            options.progressBackgroundColor = value
            refresh()
        }
        get() = options.progressBackgroundColor


    //TODO - add text
    private var textColor
        set(value) {
            options.textColor = value
            refresh()
        }
        get() = options.textColor

    private var textSize
        set(value) {
            options.textSize = value
            refresh()
        }
        get() = options.textSize

    var startAngle
        set(value) {
            options.startAngle = value
            refresh()
        }
        get() = options.startAngle

    var enableBackgroundDashEffect
        set(value) {
            options.enableBackgroundDashEffect = value
            refresh()
        }
        get() = options.enableBackgroundDashEffect

    var showDot
        set(value) {
            options.showDot = value
            refresh()
        }
        get() = options.showDot

    var progressCap
        set(value) {
            if (value == 0 || value == 1) {
                options.progressCap = value
                refresh()
            } else throw IllegalArgumentException("Invalid Progress Cap")
        }
        get() = options.progressCap

    var dotWidth
        set(value) {
            if (value < 0) throw IllegalArgumentException("Dot Width can't be negative")
            else {
                options.dotWidth = value
                refresh()
            }
        }
        get() = options.dotWidth

    var dotColor
        set(value) {
            options.dotColor = value
            refresh()
        }
        get() = options.dotColor

    var dashLineLength
        set(value) {
            if (value < 0) throw IllegalArgumentException("Dash Line Length can't be negative")
            else {
                options.dashLineLength = value
                refresh()
            }
        }
        get() = options.dashLineLength

    var dashLineOffset
        set(value) {
            if (value < 0) throw IllegalArgumentException("Dash Line Offset can't be negative")
            else {
                options.dashLineOffset = value
                refresh()
            }
        }
        get() = options.dashLineOffset

    var interpolator
        set(value) {
            if (value == 0 || value == 1 || value == 2) {
                options.interpolator = value
            } else throw IllegalArgumentException("Invalid Interpolator")
        }
        get() = options.interpolator

    var animationDuration
        set(value) {
            if (value < 0) throw IllegalArgumentException("Animation Duration can't be negative")
            else options.animationDuration = value
        }
        get() = options.animationDuration

    var fadeRepeatCount
        set(value) {
            options.fadeRepeatCount = value
        }
        get() = options.fadeRepeatCount

    var disableDefaultSweep
        set(value) {
            options.disableDefaultSweep = value
        }
        get() = options.disableDefaultSweep


    private var circularProgressBarAlpha = 255
        set(value) {
            field = when {
                value < 0 -> 0
                value > 255 -> 255
                else -> value
            }
        }

    var minFadeAlpha
        set(value) {
            if (value < 0 || value > 255) throw IllegalArgumentException("Alpha value should be in range of 0 to 255 inclusive")
            else {
                options.minFadeAlpha = value
            }
        }
        get() = options.minFadeAlpha

    private var mSize = 0
    private var mRadius = 0f
    private var mDefaultSize: Int = 0

    private val mForegroundStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBackgroundStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
        setAnimators()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawOutlineArc(canvas)
        if (showDot) drawDot(canvas)
    }

    private fun drawOutlineArc(canvas: Canvas) {
        mBackgroundStrokePaint.alpha = circularProgressBarAlpha
        mForegroundStrokePaint.alpha = circularProgressBarAlpha
        dotPaint.alpha = circularProgressBarAlpha
        canvas.drawCircle(mSize / 2.0f, mSize / 2.0f, mRadius, mBackgroundStrokePaint)
        val pad = mSize / 2 - mRadius
        val outerOval = RectF(pad, pad, mSize.toFloat() - pad, mSize.toFloat() - pad)
        mForegroundStrokePaint.strokeCap = if (progressCap == ROUND) Paint.Cap.ROUND else Paint.Cap.BUTT

        val angle = (currentAnimatedProgress * 360) / maxProgress
        canvas.drawArc(outerOval, startAngle.toFloat() - 90, angle, false, mForegroundStrokePaint)
    }

    private fun drawDot(canvas: Canvas) {
        val angle: Double = (currentAnimatedProgress * 360) / maxProgress.toDouble() + startAngle
        val x = mSize / 2 + mRadius * Math.sin(Math.toRadians(angle))
        val y = mSize / 2 - mRadius * Math.cos(Math.toRadians(angle))
        canvas.drawCircle(x.toFloat(), y.toFloat(), dotWidth / 2, dotPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val defaultSize = mDefaultSize
        val defaultWidth = Math.max(suggestedMinimumWidth, defaultSize)
        val defaultHeight = Math.max(suggestedMinimumHeight, defaultSize)
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(defaultWidth, widthSize)
            MeasureSpec.UNSPECIFIED -> defaultWidth
            else -> defaultWidth
        }
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> width//Math.min(defaultHeight, heightSize)
            MeasureSpec.UNSPECIFIED -> width//defaultHeight
            else -> width//defaultHeight
        }
        mSize = Math.min(width, height)
        mRadius = (mSize - Math.max(Math.max(progressBackgroundWidth, progressWidth), dotWidth)) / 2.0f
        setMeasuredDimension(mSize, mSize)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val displayMetrics = context.resources.displayMetrics
        mDefaultSize = Math.round(DEFAULT_SIZE_DP * displayMetrics.density)

        mForegroundStrokePaint.isAntiAlias = true
        mBackgroundStrokePaint.isAntiAlias = true
        dotPaint.isAntiAlias = true
        mForegroundStrokePaint.style = Paint.Style.STROKE
        mBackgroundStrokePaint.style = Paint.Style.STROKE
        dotPaint.style = Paint.Style.FILL

        if (attrs == null) {
            options.progress = DEFAULT_PROGRESS
            options.maxProgress = DEFAULT_MAX_PROGRESS
            options.progressColor = DEFAULT_PROGRESS_COLOR
            options.progressWidth = DEFAULT_PROGRESS_WIDTH * displayMetrics.density

            options.progressBackgroundColor = DEFAULT_PROGRESS_BACKGROUND_COLOR
            options.progressBackgroundWidth = DEFAULT_PROGRESS_BACKGROUND_WIDTH * displayMetrics.density

            options.textColor = DEFAULT_TEXT_COLOR
            options.textSize = DEFAULT_TEXT_SIZE
            options.startAngle = DEFAULT_START_ANGLE

            options.dotWidth = DEFAULT_DOT_WIDTH * displayMetrics.density
            options.dotColor = DEFAULT_PROGRESS_COLOR

            options.enableBackgroundDashEffect = DEFAULT_ENABLE_BACKGROUND_DASH_EFFECT
            options.showDot = DEFAULT_SHOW_DOT

            options.progressCap = DEFAULT_PROGRESS_CAP
            options.animationDuration = DEFAULT_ANIMATION_DURATION
            options.interpolator = DEFAULT_INTERPOLATOR
        } else {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0)

            options.progress = typedArray.getInt(R.styleable.CircularProgressBar_progress, DEFAULT_PROGRESS)
            options.maxProgress = typedArray.getInt(R.styleable.CircularProgressBar_maxProgress, DEFAULT_MAX_PROGRESS)
            options.progressColor = typedArray.getColor(R.styleable.CircularProgressBar_progressColor, DEFAULT_PROGRESS_COLOR)
            options.progressWidth = typedArray.getDimension(R.styleable.CircularProgressBar_progressWidth, DEFAULT_PROGRESS_WIDTH * displayMetrics.density)

            options.progressBackgroundWidth = typedArray.getDimension(R.styleable.CircularProgressBar_progressBackgroundWidth, DEFAULT_PROGRESS_BACKGROUND_WIDTH * displayMetrics.density)
            options.progressBackgroundColor = typedArray.getInt(R.styleable.CircularProgressBar_progressBackgroundColor, DEFAULT_PROGRESS_BACKGROUND_COLOR)

            //textColor = typedArray.getColor(R.styleable.CircularProgressBar_textColor, DEFAULT_TEXT_COLOR)
            //Todo - convert it to sp
            //textSize = typedArray.getDimension(R.styleable.CircularProgressBar_textSize, DEFAULT_TEXT_SIZE)
            options.startAngle = typedArray.getInteger(R.styleable.CircularProgressBar_startAngle, DEFAULT_START_ANGLE)

            options.enableBackgroundDashEffect = typedArray.getBoolean(R.styleable.CircularProgressBar_enableBackgroundDashEffect, DEFAULT_ENABLE_BACKGROUND_DASH_EFFECT)
            options.showDot = typedArray.getBoolean(R.styleable.CircularProgressBar_showDot, DEFAULT_SHOW_DOT)

            options.dotWidth = typedArray.getDimension(R.styleable.CircularProgressBar_dotWidth, DEFAULT_DOT_WIDTH * displayMetrics.density)
            options.dotColor = typedArray.getColor(R.styleable.CircularProgressBar_progressColor, DEFAULT_PROGRESS_COLOR)

            options.animationDuration = typedArray.getInteger(R.styleable.CircularProgressBar_animationDuration, DEFAULT_ANIMATION_DURATION)

            options.progressCap = typedArray.getInt(R.styleable.CircularProgressBar_progressCap, DEFAULT_PROGRESS_CAP)
            options.interpolator = typedArray.getInt(R.styleable.CircularProgressBar_interpolator, DEFAULT_INTERPOLATOR)
            typedArray.recycle()
        }


        circularProgressBarAlpha = DEFAULT_ALPHA
        options.dashLineLength = DEFAULT_DASH_LINE_LENGTH * displayMetrics.density
        options.dashLineOffset = DEFAULT_DASH_LINE_OFFSET * displayMetrics.density

        if (enableBackgroundDashEffect)
            mBackgroundStrokePaint.pathEffect = DashPathEffect(arrayOf(dashLineLength * displayMetrics.density, dashLineOffset * displayMetrics.density).toFloatArray(), 1f)
        else mBackgroundStrokePaint.pathEffect = null

        options.fadeRepeatCount = DEFAULT_FADE_REPEAT_COUNT
        options.minFadeAlpha = DEFAULT_MIN_FADE_ALPHA
        mBackgroundStrokePaint.color = progressBackgroundColor
        mForegroundStrokePaint.color = progressColor
        dotPaint.color = dotColor
        mBackgroundStrokePaint.strokeWidth = progressBackgroundWidth
        mForegroundStrokePaint.strokeWidth = progressWidth
        dotPaint.strokeWidth = dotWidth
    }

    private fun setAnimators() {
        mProgressAnimator.setFloatValues(0f, progress.toFloat())
        mProgressAnimator.addUpdateListener { animation ->
            currentAnimatedProgress = animation.animatedValue.toString().toFloat()
            invalidate()
        }

        mAlphaAnimator.addUpdateListener { animation ->
            circularProgressBarAlpha = (Math.abs(animation.animatedValue.toString().toFloat() * 2 / animationDuration - 1) * (255 - minFadeAlpha) + minFadeAlpha).toInt()
            invalidate()
        }
    }


    fun refresh() {
        this.circularProgressBarAlpha = 255
        mBackgroundStrokePaint.color = progressBackgroundColor
        mForegroundStrokePaint.color = progressColor
        this.dotPaint.color = dotColor
        this.mBackgroundStrokePaint.strokeWidth = progressBackgroundWidth
        this.mForegroundStrokePaint.strokeWidth = progressWidth
        this.dotPaint.strokeWidth = dotWidth
        this.mRadius = (mSize - Math.max(Math.max(progressBackgroundWidth, progressWidth), dotWidth)) / 2.0f

        val displayMetrics = context.resources.displayMetrics
        if (enableBackgroundDashEffect)
            this.mBackgroundStrokePaint.pathEffect = DashPathEffect(arrayOf(dashLineLength * displayMetrics.density, dashLineOffset * displayMetrics.density).toFloatArray(), 1f)
        else this.mBackgroundStrokePaint.pathEffect = null
        if (progress != currentAnimatedProgress.toInt() && !disableDefaultSweep)
            sweep()
        else {
            currentAnimatedProgress = progress.toFloat()
            invalidate()
        }
    }

    fun stopAnimation() {
        this.circularProgressBarAlpha = 255
        currentAnimatedProgress = progress.toFloat()
        if (mProgressAnimator.isRunning) mProgressAnimator.cancel()
        if (mAlphaAnimator.isRunning) mAlphaAnimator.cancel()
        invalidate()
    }

    fun sweep() {
        if (currentAnimatedProgress.toInt() == progress)
            currentAnimatedProgress = 0f
        mProgressAnimator.setFloatValues(currentAnimatedProgress, progress.toFloat())
        mProgressAnimator.duration = animationDuration.toLong()
        mProgressAnimator.interpolator = when (interpolator) {
            ACCELERATE -> AccelerateInterpolator()
            DECELERATE -> DecelerateInterpolator()
            else -> LinearInterpolator()
        }
        mProgressAnimator.start()
    }

    fun fade() {
        currentAnimatedProgress = progress.toFloat()
        mAlphaAnimator.duration = animationDuration.toLong() //* fadeRepeatCount
        mAlphaAnimator.interpolator = when (interpolator) {
            ACCELERATE -> AccelerateInterpolator()
            DECELERATE -> DecelerateInterpolator()
            else -> LinearInterpolator()
        }
        mAlphaAnimator.setFloatValues(0f, animationDuration.toFloat())
        mAlphaAnimator.repeatCount = fadeRepeatCount
        mAlphaAnimator.start()
    }


}
