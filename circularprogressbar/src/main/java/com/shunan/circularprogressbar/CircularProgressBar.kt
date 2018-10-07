package com.shunan.circularprogressview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.shunan.circularprogressbar.R


class CircularProgressBar : View {

    companion object {

        const val NONE = 0
        const val SWEEP = 1
        const val FADE = 2

        const val ROUND = 0
        const val FLAT = 1

        const val LINEAR = 0
        const val ACCELERATE = 1
        const val DECELERATE = 2

        private const val DEFAULT_PROGRESS = 25
        private const val DEFAULT_MAX_PROGRESS = 100
        private const val DEFAULT_PROGRESS_COLOR = Color.BLUE
        private const val DEFAULT_PROGRESS_WIDTH = 32f

        private const val DEFAULT_PROGRESS_BACKGROUND_WIDTH = 16f
        private const val DEFAULT_PROGRESS_BACKGROUND_COLOR = Color.LTGRAY

        private const val DEFAULT_TEXT_COLOR = Color.BLACK
        private const val DEFAULT_TEXT_SIZE = 14f

        private const val DEFAULT_START_ANGLE = 0

        private const val DEFAULT_SHOW_PROGRESS_BACKGROUND = true
        private const val DEFAULT_ENABLE_BACKGROUND_DASH_EFFECT = false
        private const val DEFAULT_SHOW_DOT = true

        private const val DEFAULT_PROGRESS_CAP = ROUND

        private const val DEFAULT_DOT_WIDTH = 20f

        private const val DEFAULT_DASH_LINE_LENGTH = 12f
        private const val DEFAULT_DASH_LINE_OFFSET = 4f

        private const val DEFAULT_ANIMATION_DURATION = 500
        private const val DEFAULT_INTERPOLATOR = LINEAR
        private const val DEFAULT_ANIMATION_EFFECT = NONE

        private const val DEFAULT_SIZE_DP = 48f

        private const val DEFAULT_ALPHA = 255
        private const val DEFAULT_FADE_REPEAT_COUNT = 3
        private const val DEFAULT_MIN_FADE_ALPHA = 85

    }

    private val mProgressAnimator = ValueAnimator()
    private val mAlphaAnimator = ValueAnimator()
    private var currentAnimatedProgress = 0f

    var progress = 0
        set(value) {
            if (value < 0)
                throw IllegalArgumentException("Progress can't be negative")
            field = value
            currentAnimatedProgress = value.toFloat()
        }


    var maxProgress = 0
        set(value) {
            if (value < 0) throw IllegalArgumentException("Maximum Progress can't be negative")
            else field = value
        }

    var progressColor = Color.BLUE
    var progressWidth = 0f
        set(value) {
            if (value < 0) throw IllegalArgumentException("Progress Width can't be negative")
            else field = value
        }

    var progressBackgroundWidth = 0f
        set(value) {
            if (value < 0) throw IllegalArgumentException("Progress Background Width can't be negative")
            else field = value
        }

    var progressBackgroundColor = Color.LTGRAY

    //TODO - add text
    private var textColor = Color.BLACK
    private var textSize = 0f

    var startAngle = 0
        set(value) {
            field = value - 90
        } get() = field + 90

    var showProgressBackground = false
    var enableBackgroundDashEffect = false
    var showDot = false

    var progressCap = ROUND
        set(value) {
            if (value == 0 || value == 1) field = value
            else throw IllegalArgumentException("Invalid Progress Cap")
        }

    var dotWidth = 0f
        set(value) {
            if (value < 0)throw IllegalArgumentException("Dot Width can't be negative")
            else  field = value
        }
    var dotColor = Color.BLUE

    var dashLineLength = 8f
        set(value) {
            if (value < 0)throw IllegalArgumentException("Dash Line Length can't be negative")
            else  field = value
        }
    var dashLineOffset = 0f
        set(value) {
            if (value < 0)throw IllegalArgumentException("Dash Line Offset can't be negative")
            else  field = value
        }

    var interpolator = LINEAR
        set(value) {
            if (value == 0 || value == 1 || value == 2) field = value
            else throw IllegalArgumentException("Invalid Interpolator")
        }

    var animationDuration = 0
        set(value) {
            if (value < 0)throw IllegalArgumentException("Animation Duration can't be negative")
            else  field = value
        }

    var animationEffect = NONE
        set(value) {
            if (value == 0 || value == 1 || value == 2) field = value
            else throw IllegalArgumentException("Invalid Animation Effect")
        }

    var fadeRepeatCount = 0
        set(value) {
            if (value < 1)throw IllegalArgumentException("Fade Repeat Count should be greater than or equal to 1")
            else  field = value
        }


    var circularProgressBarAlpha = 255
        set(value) {
            if (value < 0 || value > 255)throw IllegalArgumentException("Alpha value should be in range of 0 to 255 inclusive")
            else  field = value
        }

    var minFadeAlpha = 85
        set(value) {
            if (value < 0 || value > 255)throw IllegalArgumentException("Alpha value should be in range of 0 to 255 inclusive")
            else  field = value
        }

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
        drawDot(canvas)
    }

    private fun drawOutlineArc(canvas: Canvas) {
        mBackgroundStrokePaint.alpha = circularProgressBarAlpha
        mForegroundStrokePaint.alpha = circularProgressBarAlpha
        dotPaint.alpha = circularProgressBarAlpha
        canvas.drawCircle(mSize / 2.0f, mSize / 2.0f, mRadius, mBackgroundStrokePaint)
        val pad = mSize / 2 - mRadius
        val outerOval = RectF(pad, pad, mSize.toFloat() - pad, mSize.toFloat() - pad)
        mForegroundStrokePaint.strokeCap = Paint.Cap.ROUND

        val angle = (currentAnimatedProgress * 360) / maxProgress
        canvas.drawArc(outerOval, startAngle.toFloat() - 90, angle.toFloat(), false, mForegroundStrokePaint)
    }

    private fun drawDot(canvas: Canvas) {
        val angle: Double = (currentAnimatedProgress * 360) / maxProgress.toDouble() + startAngle
        val x = mSize / 2 + mRadius * Math.sin(Math.toRadians(angle))
        val y = mSize / 2 - mRadius * Math.cos(Math.toRadians(angle))
        canvas.drawCircle(x.toFloat(), y.toFloat(), dotWidth / 2, dotPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val defaultSize = mDefaultSize
        val defaultWidth = Math.max(suggestedMinimumWidth, defaultSize)
        val defaultHeight = Math.max(suggestedMinimumHeight, defaultSize)
        val width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> Math.min(defaultWidth, widthSize)
            View.MeasureSpec.UNSPECIFIED -> defaultWidth
            else -> defaultWidth
        }
        val height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> width//Math.min(defaultHeight, heightSize)
            View.MeasureSpec.UNSPECIFIED -> width//defaultHeight
            else -> width//defaultHeight
        }
        mSize = Math.min(width, height)
        mRadius = (mSize - Math.max(Math.max(progressBackgroundWidth, progressWidth), dotWidth)) / 2.0f
        setMeasuredDimension(mSize, mSize)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val displayMetrics = context.resources.displayMetrics
        mDefaultSize = Math.round(DEFAULT_SIZE_DP * displayMetrics.density)

        val dashPath = DashPathEffect(arrayOf(12f * displayMetrics.density, 4f * displayMetrics.density).toFloatArray(), 1f)
        mBackgroundStrokePaint.pathEffect = dashPath

        mForegroundStrokePaint.isAntiAlias = true
        mBackgroundStrokePaint.isAntiAlias = true
        dotPaint.isAntiAlias = true
        mForegroundStrokePaint.style = Paint.Style.STROKE
        mBackgroundStrokePaint.style = Paint.Style.STROKE
        dotPaint.style = Paint.Style.FILL

        if (attrs == null) {
            progress = DEFAULT_PROGRESS
            maxProgress = DEFAULT_MAX_PROGRESS
            progressColor = DEFAULT_PROGRESS_COLOR
            progressWidth = DEFAULT_PROGRESS_WIDTH * displayMetrics.density

            progressBackgroundColor = DEFAULT_PROGRESS_BACKGROUND_COLOR
            progressBackgroundWidth = DEFAULT_PROGRESS_BACKGROUND_WIDTH * displayMetrics.density

            textColor = DEFAULT_TEXT_COLOR
            textSize = DEFAULT_TEXT_SIZE
            startAngle = DEFAULT_START_ANGLE

            dotWidth = DEFAULT_DOT_WIDTH * displayMetrics.density
            dotColor = DEFAULT_PROGRESS_COLOR

            showProgressBackground = DEFAULT_SHOW_PROGRESS_BACKGROUND
            enableBackgroundDashEffect = DEFAULT_ENABLE_BACKGROUND_DASH_EFFECT
            showDot = DEFAULT_SHOW_DOT
            circularProgressBarAlpha = DEFAULT_ALPHA

            progressCap = DEFAULT_PROGRESS_CAP
            animationDuration = DEFAULT_ANIMATION_DURATION
            interpolator = DEFAULT_INTERPOLATOR
            animationEffect = DEFAULT_ANIMATION_EFFECT
        } else {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0)

            progress = typedArray.getInt(R.styleable.CircularProgressBar_progress, DEFAULT_PROGRESS)
            maxProgress = typedArray.getInt(R.styleable.CircularProgressBar_maxProgress, DEFAULT_MAX_PROGRESS)
            progressColor = typedArray.getColor(R.styleable.CircularProgressBar_progressColor, DEFAULT_PROGRESS_COLOR)
            progressWidth = typedArray.getDimension(R.styleable.CircularProgressBar_progressWidth, DEFAULT_PROGRESS_WIDTH * displayMetrics.density)

            progressBackgroundWidth = typedArray.getDimension(R.styleable.CircularProgressBar_progressBackgroundWidth, DEFAULT_PROGRESS_BACKGROUND_WIDTH * displayMetrics.density)
            progressBackgroundColor = typedArray.getInt(R.styleable.CircularProgressBar_progressBackgroundColor, DEFAULT_PROGRESS_BACKGROUND_COLOR)

            //textColor = typedArray.getColor(R.styleable.CircularProgressBar_textColor, DEFAULT_TEXT_COLOR)
            //Todo - convert it to sp
            //textSize = typedArray.getDimension(R.styleable.CircularProgressBar_textSize, DEFAULT_TEXT_SIZE)
            startAngle = typedArray.getInteger(R.styleable.CircularProgressBar_startAngle, DEFAULT_START_ANGLE)

            animationEffect = typedArray.getInt(R.styleable.CircularProgressBar_animationEffect, DEFAULT_ANIMATION_EFFECT)
            showProgressBackground = typedArray.getBoolean(R.styleable.CircularProgressBar_showProgressBackground, DEFAULT_SHOW_PROGRESS_BACKGROUND)
            enableBackgroundDashEffect = typedArray.getBoolean(R.styleable.CircularProgressBar_enableBackgroundDashEffect, DEFAULT_ENABLE_BACKGROUND_DASH_EFFECT)
            showDot = typedArray.getBoolean(R.styleable.CircularProgressBar_showDot, DEFAULT_SHOW_DOT)

            dotWidth = typedArray.getDimension(R.styleable.CircularProgressBar_dotWidth, DEFAULT_DOT_WIDTH * displayMetrics.density)
            dotColor = typedArray.getColor(R.styleable.CircularProgressBar_progressColor, DEFAULT_PROGRESS_COLOR)

            animationDuration = typedArray.getInteger(R.styleable.CircularProgressBar_animationDuration, DEFAULT_ANIMATION_DURATION)

            progressCap = typedArray.getInt(R.styleable.CircularProgressBar_progressCap, DEFAULT_PROGRESS_CAP)
            interpolator = typedArray.getInt(R.styleable.CircularProgressBar_interpolator, DEFAULT_INTERPOLATOR)
            circularProgressBarAlpha = typedArray.getInt(R.styleable.CircularProgressBar_cir_alpha, DEFAULT_ALPHA)
            typedArray.recycle()
        }


        dashLineLength = DEFAULT_DASH_LINE_LENGTH * displayMetrics.density
        dashLineOffset = DEFAULT_DASH_LINE_OFFSET * displayMetrics.density
        fadeRepeatCount = DEFAULT_FADE_REPEAT_COUNT
        minFadeAlpha = DEFAULT_MIN_FADE_ALPHA
        mBackgroundStrokePaint.color = progressBackgroundColor
        mForegroundStrokePaint.color = progressColor
        dotPaint.color = dotColor
        mBackgroundStrokePaint.strokeWidth = progressBackgroundWidth
        mForegroundStrokePaint.strokeWidth = progressWidth
        dotPaint.strokeWidth = dotWidth
    }

    private fun setAnimators() {
        mProgressAnimator.setFloatValues(0f, progress.toFloat())
        mProgressAnimator.addUpdateListener({ animation ->
            currentAnimatedProgress = animation.animatedValue.toString().toFloat()
            invalidate()
        })

        mAlphaAnimator.addUpdateListener({ animation ->
            circularProgressBarAlpha = (Math.abs(animation.animatedValue.toString().toFloat() * 2 / animationDuration - 1) * (255 - minFadeAlpha) + minFadeAlpha).toInt()
            invalidate()
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    fun refresh() {
        circularProgressBarAlpha = 255
        currentAnimatedProgress = progress.toFloat()
        if (mProgressAnimator.isRunning) mProgressAnimator.cancel()
        if (mAlphaAnimator.isRunning) mAlphaAnimator.cancel()
        invalidate()
    }

    fun stopAnimation() {
        refresh()
    }

    fun animateView() {
        circularProgressBarAlpha = 255
        if (mProgressAnimator.isRunning) {
            mProgressAnimator.cancel()
            circularProgressBarAlpha = progress
        }
        if (mAlphaAnimator.isRunning) mAlphaAnimator.cancel()
        when (animationEffect) {
            SWEEP -> {
                mProgressAnimator.setFloatValues(0f, progress.toFloat())
                mProgressAnimator.duration = animationDuration.toLong()
                mProgressAnimator.interpolator = when (interpolator) {
                    ACCELERATE -> AccelerateInterpolator()
                    DECELERATE -> DecelerateInterpolator()
                    else -> LinearInterpolator()
                }
                mProgressAnimator.start()
            }
            FADE -> {
                mAlphaAnimator.duration = animationDuration.toLong() * fadeRepeatCount
                mAlphaAnimator.interpolator = when (interpolator) {
                    ACCELERATE -> AccelerateInterpolator()
                    DECELERATE -> DecelerateInterpolator()
                    else -> LinearInterpolator()
                }
                mAlphaAnimator.setFloatValues(0f, animationDuration.toFloat())
                mAlphaAnimator.repeatCount = fadeRepeatCount
                mAlphaAnimator.start()
            }
            NONE -> invalidate()
        }
    }


}

//TODO - Attribute validation