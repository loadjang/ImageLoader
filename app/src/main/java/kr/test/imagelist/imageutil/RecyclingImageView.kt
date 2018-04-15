package kr.test.imagelist.imageutil
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class RecyclingImageView : ImageView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    protected override fun onDetachedFromWindow() {

            setImageDrawable(null)

        super.onDetachedFromWindow()
    }



}

