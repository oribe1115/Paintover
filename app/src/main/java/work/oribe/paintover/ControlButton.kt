package work.oribe.paintover

import android.content.Context
import android.util.AttributeSet
import android.view.View

class ControlButton(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatButton(context, attrs) {
    companion object {
        fun create(context: Context): ControlButton {
            return View.inflate(context, R.layout.control_button, null) as ControlButton
        }
    }
}