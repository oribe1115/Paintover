package work.oribe.paintover

import android.content.Context
import android.util.AttributeSet
import android.view.View

// 背景のアプリケーションを操作できないようにする下敷きのレイヤー
class UnderlayLayerView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    companion object {
        private val TAG = UnderlayLayerView::class.simpleName

        fun create(context: Context): UnderlayLayerView {
            return inflate(context, R.layout.underlay_layer_view, null) as UnderlayLayerView
        }
    }

}