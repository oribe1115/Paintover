package work.oribe.paintover

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class OverlayService : Service() {
    private lateinit var overlayView: OverlayView
    private lateinit var overlayViewGroup: OverlayViewGroup

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")

        super.onCreate()
//        overlayView = OverlayView.create(this)
//        overlayView.show()
        overlayViewGroup = OverlayViewGroup.create(this)
        overlayViewGroup.show()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

        super.onDestroy()
//        overlayView.clear()
        overlayViewGroup.clear()
    }

    companion object {
        private val TAG = OverlayService::class.simpleName
    }
}