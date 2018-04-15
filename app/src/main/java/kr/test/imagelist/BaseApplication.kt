package kr.test.imagelist

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatDialog


class BaseApplication : Application() {
    internal var progressDialog: AppCompatDialog? = null

    override fun onCreate() {
        super.onCreate()


        instance = this
    }

    fun progressON(activity: Activity?) {

        if (activity == null || activity.isFinishing) {
            return
        }


        progressDialog = AppCompatDialog(activity)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setContentView(R.layout.progress_loading)
        progressDialog!!.show()


    }


    fun progressOFF() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }


    }

    companion object {

        var instance: BaseApplication? = null
            private set
    }

}
