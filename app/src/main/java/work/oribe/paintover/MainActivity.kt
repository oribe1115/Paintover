package work.oribe.paintover

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestOverlayPermission()
        Log.d(TAG, "after requestOverlayPermission")

        val intent = Intent(this@MainActivity, OverlayService::class.java)
        startService(intent)
    }

    // overlayをするための許可をリクエストする
    private fun requestOverlayPermission() {
        Log.d(TAG, "requestOverlayPermission")

        if (isOverlayGranted()) return

        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                Log.d(TAG, "here")
                if (result.resultCode == OVERLAY_PERMISSION_REQUEST_CODE) {
                    if (!isOverlayGranted()) {
                        Log.d(TAG, "failed to get the permission for overlay")
                        finish()
                    }
                }
            }

        startForResult.launch(intent)
    }

    private fun isOverlayGranted(): Boolean {
        Log.d(TAG, "isOverlayGranted")
        return Settings.canDrawOverlays(this)
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val OVERLAY_PERMISSION_REQUEST_CODE = 1
    }
}