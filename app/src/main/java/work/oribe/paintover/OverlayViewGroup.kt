package work.oribe.paintover

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible

class OverlayViewGroup(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val windowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val buttonLayerView = ButtonLayerView.create(context)
    private val controlButton = ControlButton(context, null)
//    private lateinit var underlayLayerView: UnderlayLayerView

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
//        buttonLayerView = ButtonLayerView.create(context)
//        underlayLayerView = UnderlayLayerView.create(context)

//        addView(underlayLayerView)
//        addView(buttonLayerView)

        Log.d(TAG, "onLayout")
        controlButton.text = "ControlButton"
        addView(controlButton)
    }

    private val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Overlayレイヤに表示
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  // フォーカスを奪わない
                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // 画面外への拡張を許可
        PixelFormat.TRANSLUCENT // 半透明
    )

    fun show() {
        Log.d(TAG, "show")

//        controlButton.text = "ControlButton"
//        addView(controlButton)

        addView(buttonLayerView)
        buttonLayerView.isVisible = true

        windowManager.addView(this, layoutParams)
    }

    fun clear() {
        Log.d(TAG, "clear")

        windowManager.removeView(this)
    }

    companion object {
        private val TAG = OverlayViewGroup::class.simpleName

        fun create(context: Context): OverlayViewGroup {
            return View.inflate(context, R.layout.overlay_view_group, null) as OverlayViewGroup
        }
    }
}