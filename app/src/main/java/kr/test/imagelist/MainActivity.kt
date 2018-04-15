package kr.test.imagelist

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kr.test.imagelist.adapter.ImageListAdapter
import kr.test.imagelist.api.Api
import kr.test.imagelist.api.ImageResultCallback
import kr.test.imagelist.lib.ImageUtil

class MainActivity : AppCompatActivity(), ImageResultCallback {

    var permission: Permissions? = null

    lateinit var imgAdapter: ImageListAdapter
    lateinit var imageList: List<String>
    fun setAdapter(imageList: List<String>) {


        val imgAdapter = ImageListAdapter(this@MainActivity, imageList)
        recyclerview.adapter = imgAdapter
    }

    //이미지 로드완료 콜백
    override fun successImageLoaded(imageList: List<String>) {
        this.imageList = imageList
        setAdapter(imageList)


    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        when (newConfig?.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> setAdapter(imageList)
            Configuration.ORIENTATION_PORTRAIT -> setAdapter(imageList)
            Configuration.ORIENTATION_UNDEFINED -> setAdapter(imageList)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permission!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val build = Permissions.Build()
        val callback = object : Permissions.permissionsCallback {
            override fun onRequestPermissionsResult_GRANTED() {

                Api.getHttpImageList(this@MainActivity)
            }

            override fun onRequestPermissionsResult_DENIED() {

            }
        }


        permission = build.setContext(this).setMessage("모든권한을 설정후에 이용이가능합니다.").setAllFlag(true).setCallback(callback).setForceFlag(true).build()
        permission!!.checkPermissions(100)


        val mLayoutManager = GridLayoutManager(this, ImageUtil.calculateNoOfColumns(this), GridLayoutManager.VERTICAL, false);
        recyclerview.layoutManager = mLayoutManager



    }
}
