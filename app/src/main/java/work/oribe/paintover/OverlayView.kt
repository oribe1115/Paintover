package work.oribe.paintover

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout

class OverlayView(ctx: Context, attrs: AttributeSet) :
    FrameLayout(ctx, attrs) {
    private val windowManager: WindowManager =
        ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private lateinit var buttonLayerView: ButtonLayerView
    private lateinit var underlayLayerView: UnderlayLayerView

    private val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Overlayレイヤに表示
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  // フォーカスを奪わない
                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // 画面外への拡張を許可
        PixelFormat.TRANSLUCENT // 半透明
    )

    private val buttonParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // 画面外への拡張を許可
        PixelFormat.TRANSLUCENT
    )

    fun show() {
        Log.d(TAG, "show")

        buttonLayerView = ButtonLayerView.create(context)
        underlayLayerView = UnderlayLayerView.create(context)

        addView(underlayLayerView)
//        addView(buttonLayerView)

        windowManager.addView(this, layoutParams)
        windowManager.addView(buttonLayerView, buttonParams)
    }

    fun clear() {
        Log.d(TAG, "clear")

        windowManager.removeView(this)
    }

    companion object {
        private val TAG = OverlayView::class.simpleName

        fun create(context: Context): OverlayView {
            return View.inflate(context, R.layout.overlay_view, null) as OverlayView
        }

    }
}