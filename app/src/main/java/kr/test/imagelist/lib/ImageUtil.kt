package kr.test.imagelist.lib
import android.content.Context
import kr.test.imagelist.api.Api
import java.util.regex.Pattern

 object  ImageUtil{

      fun getHtmlImageList(text : String,filter : String) : List<String> {
          val list = ArrayList<String>()
          val pattern = Pattern.compile("(?i)<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출 정규표현식
          val matcher = pattern.matcher(text);

          while (matcher.find()) {
              val findImgUrl = matcher.group(1)
              if (findImgUrl.startsWith(filter)) {
                  list.add(Api.HOSTNAME+findImgUrl)
              }
          }
        return list
      }




      // @TODO : 화면사이즈에맞는 이미지뷰 크기

     fun calculateImageSize(context: Context): Int {
         val num=calculateNoOfColumns(context)
         val displayMetrics = context.getResources().getDisplayMetrics()
         val dpWidth = displayMetrics.widthPixels


         return (dpWidth / 3).toInt()
     }


      //  @TODO : 화면사이즈에맞는 열 갯수를 계산합니다.

     fun calculateNoOfColumns(context: Context): Int {
         val displayMetrics = context.getResources().getDisplayMetrics()
         val dpWidth = displayMetrics.widthPixels / displayMetrics.density


         return (dpWidth / 100).toInt()
     }
}

