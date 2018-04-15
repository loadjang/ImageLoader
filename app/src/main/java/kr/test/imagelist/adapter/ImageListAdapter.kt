package kr.test.imagelist.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import kr.test.imagelist.R
import kr.test.imagelist.imageutil.ImageLoader
import kr.test.imagelist.lib.ImageUtil.calculateImageSize

class ImageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private lateinit var listImageUrl: List<String>
    private lateinit var activity: Activity

    constructor(activity: Activity, listImageUrl: List<String>) {
        this.listImageUrl = listImageUrl
        this.activity = activity
        ImageLoader.init(activity)
        ImageLoader.placeHolder=activity.resources.getDrawable(R.drawable.placeholder)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent,false)
        var img=view.findViewById<ImageView>(R.id.img)

        var size=calculateImageSize(activity!!.applicationContext)
        img.layoutParams.width=size
        img.layoutParams.height=size


        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return listImageUrl!!.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewHolder = holder as ViewHolder

        val imgUrl = listImageUrl?.get(position)


        imgUrl.let {

            ImageLoader.setImageView(activity,imgUrl,viewHolder.img)
        }


    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView


        init {
            img = view.findViewById(R.id.img) as ImageView


        }
    }


}