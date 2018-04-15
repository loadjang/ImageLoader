package kr.test.imagelist.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiList {

    @Headers("Contents-Type: application/json")
    @POST("/collections/archive/slim-aarons.aspx")
    fun getImageList(@Body body: String): Call<ResponseBody>






}