package work.oribe.paintover

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class DrawLayerView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mPaint = createPaint()
    private val mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    private val mCanvas = Canvas(mBitmap)
    private val multiLinePathManager = MultiLinePathManager(MAX_POINTERS)

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                val id = event.getPointerId(index)

                val linePath = multiLinePathManager.addLinePathWithPointer(id)
                linePath?.touchStart(event.getX(index), event.getX(index))
            }

            MotionEvent.ACTION_MOVE -> {
                for (i in 0..event.pointerCount) {
                    val id = event.getPointerId(i)
                    val index = event.findPointerIndex(id)

                    val linePath = multiLinePathManager.findLinePathFromPointer(id)
                    linePath?.touchMove(event.getX(index), event.getX(index))
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                val index = event.actionIndex
                val id = event.getPointerId(index)

                val linePath = multiLinePathManager.findLinePathFromPointer(id)
                if (linePath != null) {
                    linePath.lineTo(linePath.lastX, linePath.lastY)
                    mCanvas.drawPath(linePath, mPaint)
                    linePath.reset()
                    linePath.disassociateFromPointer()
                }
            }
        }

        invalidate()
        return true
    }

    private fun createPaint(): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.alpha = 0xff

        return paint
    }

    private class LinePath : Path() {
        private var mIdPointer: Int? = null
        var lastX: Float = 0f
            private set
        var lastY: Float = 0f
            private set

        fun touchStart(x: Float, y: Float) {
            reset()
            moveTo(x, y)
            lastX = x
            lastY = y
        }

        fun touchMove(x: Float, y: Float) {
            val dx = abs(x - lastX)
            val dy = abs(y - lastY)
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                quadTo(lastX, lastY, (x + lastY) / 2, (y + lastY) / 2)
                lastX = x
                lastY = y
            }
        }

        fun isDisassociatedFromPointer(): Boolean {
            return mIdPointer == null
        }

        fun isAssociatedToPointer(idPointer: Int): Boolean {
            return mIdPointer != null && mIdPointer == idPointer
        }

        fun disassociateFromPointer() {
            mIdPointer = null
        }

        fun associateToPointer(idPointer: Int) {
            mIdPointer = idPointer
        }
    }

    private class MultiLinePathManager(maxPointers: Int) {
        var linePathList = initializeLinePathList(maxPointers)

        private fun initializeLinePathList(maxPointers: Int): ArrayList<LinePath> {
            val list = ArrayList<LinePath>()
            for (i in 0..maxPointers) {
                linePathList.add(LinePath())
            }
            return list
        }

        fun findLinePathFromPointer(idPointer: Int): LinePath? {
            for (linePath in linePathList) {
                if (linePath.isAssociatedToPointer(idPointer)) {
                    return linePath
                }
            }
            return null
        }

        fun addLinePathWithPointer(idPointer: Int): LinePath? {
            for (linePath in linePathList) {
                if (linePath.isDisassociatedFromPointer()) {
                    linePath.associateToPointer(idPointer)
                    return linePath
                }
            }
            return null
        }
    }

    companion object {
        private const val TOUCH_TOLERANCE = 4f
        private const val MAX_POINTERS = 10

        fun create(context: Context): DrawLayerView {
            return inflate(context, R.layout.draw_layer_view, null) as DrawLayerView
        }
    }
}