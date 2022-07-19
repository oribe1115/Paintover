package work.oribe.paintover

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout

class OverlayView(ctx: Context, attrs: AttributeSet) :
    FrameLayout(ctx, attrs) {
    private val windowManager: WindowManager =
        ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private lateinit var underlayLayerView: UnderlayLayerView
    private lateinit var drawLayerView: DrawLayerView

    private lateinit var button: Button

    private val underlayParams = createUnderlayParams()
    private val buttonParams = createButtonParams()

    private val drawableParams = createDrawableParams()
    private val undrawableParams = createUndrawableParams()

    private var isShowingUnderlay = false

    private var lastClickTime: Long = 0

    fun show() {
        Log.d(TAG, "show")
        underlayLayerView = UnderlayLayerView.create(context)

        val windowMetrics = windowManager.currentWindowMetrics
        drawLayerView =
            DrawLayerView.create(
                context,
                windowMetrics.bounds.width(),
                windowMetrics.bounds.height()
            )

        button = ControlButton(context)
        button.text = "Button"
        button.setBackgroundColor(Color.BLUE)

        button.setOnClickListener { toggle() }
        button.setOnLongClickListener {
            toggle()
            drawLayerView.clear()
            toggle()
            false
        }

        windowManager.addView(drawLayerView, undrawableParams)
        windowManager.addView(button, buttonParams)
    }

    fun clear() {
        Log.d(TAG, "clear")

        windowManager.removeView(this)
    }

    private fun toggle() {
        if (isShowingUnderlay) {
            drawLayerView.isDrawable = false
            windowManager.removeView(button)
            windowManager.removeView(drawLayerView)
            windowManager.removeView(underlayLayerView)

            windowManager.addView(drawLayerView, undrawableParams)
            windowManager.addView(button, buttonParams)
        } else {
            windowManager.removeView(button)
            windowManager.removeView(drawLayerView)

            windowManager.addView(underlayLayerView, underlayParams)
            windowManager.addView(drawLayerView, drawableParams)
            windowManager.addView(button, buttonParams)
            drawLayerView.isDrawable = true
        }

        isShowingUnderlay = !isShowingUnderlay
    }

    private fun createUnderlayParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Overlayレイヤに表示
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // 画面外への拡張を許可
            PixelFormat.TRANSLUCENT // 半透明
        )
    }

    private fun createButtonParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        params.width = 200
        params.height = 100
        params.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT

        return params
    }

    private fun createDrawableParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Overlayレイヤに表示
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // 画面外への拡張を許可
            PixelFormat.TRANSLUCENT // 半透明
        )
    }

    private fun createUndrawableParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
    }

    companion object {
        private val TAG = OverlayView::class.simpleName
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300

        fun create(context: Context): OverlayView {
            return View.inflate(context, R.layout.overlay_view, null) as OverlayView
        }

    }
}