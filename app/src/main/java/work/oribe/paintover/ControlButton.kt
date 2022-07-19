package work.oribe.paintover

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.core.view.GestureDetectorCompat

@SuppressLint("AppCompatCustomView")
class ControlButton(context: Context) : Button(context), GestureDetector.OnDoubleTapListener,
    GestureDetector.OnGestureListener {
    private lateinit var singleTapFunc: () -> Unit
    private lateinit var doubleTapFunc: () -> Unit
    private lateinit var mDetector: GestureDetectorCompat

    fun initialize() {
        mDetector = GestureDetectorCompat(context, this)
        mDetector.setOnDoubleTapListener(this)
    }

    fun setSingleTapFunc(func: () -> Unit) {
        singleTapFunc = func
    }

    fun setDoubleTapFunc(func: () -> Unit) {
        doubleTapFunc = func
    }

    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
        Log.d(TAG, "onSingleTapConfirmed")
        singleTapFunc()
        return true
    }

    override fun onDoubleTap(p0: MotionEvent?): Boolean {
        Log.d(TAG, "onDoubleTap")
        doubleTapFunc()
        return true
    }

    override fun onDoubleTapEvent(p0: MotionEvent?): Boolean {
        Log.d(TAG, "onDoubleTapEvent")
        return true
    }

    companion object {
        private val TAG = ControlButton::class.simpleName
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }
}