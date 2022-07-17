package work.oribe.paintover

import android.content.Context
import android.util.AttributeSet
import android.view.View

// トグル用のボタンを表示するレイヤー
class ButtonLayerView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private val TAG = ButtonLayerView::class.simpleName

//        fun create(context: Context): ButtonLayerView {
//            return inflate(context, R.layout.button_layer_view, null) as ButtonLayerView
//        }
    }
}