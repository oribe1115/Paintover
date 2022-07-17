package work.oribe.paintover

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

// トグル用のボタンを表示するレイヤー
class ButtonLayerView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    companion object {
        private val TAG = ButtonLayerView::class.simpleName

        fun create(context: Context): ButtonLayerView {
            return View.inflate(context, R.layout.button_layer_view, null) as ButtonLayerView
        }
    }
}