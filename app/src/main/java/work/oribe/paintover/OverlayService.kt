package work.oribe.paintover

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Button

class OverlayService : Service() {
    private lateinit var overlayView: OverlayView
    private lateinit var overlayViewGroup: OverlayViewGroup

    private lateinit var buttonLayerView: ButtonLayerView
    private lateinit var underlayLayerView: UnderlayLayerView

    private lateinit var controlButton: Button

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")

        super.onCreate()
        overlayView = OverlayView.create(this)
        buttonLayerView = ButtonLayerView.create(this)
        underlayLayerView = UnderlayLayerView.create(this)

        controlButton = buttonLayerView.findViewById(R.id.button_layer_view_button)
        controlButton.setOnClickListener { overlayView.addView(underlayLayerView) }

        overlayView.show()

        overlayView.addView(buttonLayerView)

//        overlayViewGroup = OverlayViewGroup.create(this)
//        overlayViewGroup.show()
    }


    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

        super.onDestroy()
        overlayView.clear()
//        overlayViewGroup.clear()
    }


    companion object {
        private val TAG = OverlayService::class.simpleName
    }
}