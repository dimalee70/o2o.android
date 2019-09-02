package kz.dragau.larek.ui.adapters

import com.google.android.gms.maps.model.LatLng
import kz.dragau.larek.models.objects.SalesClusterItem
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class MyItemReader{
    companion object{
        private const val REGEX_INPUT_BOUNDARY_BEGINNING: String = "\\A"
    }

    fun read(inputStream: InputStream): List<SalesClusterItem>{
        var items: ArrayList<SalesClusterItem> = ArrayList()
        var json: String = Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next()
        var array: JSONArray = JSONArray(json)
        for (i in 0..(array.length() - 1)) {
            var title: String? = null
            var snippet: String? = null
            var jsonObject: JSONObject = array.getJSONObject(i)
            var lat = jsonObject.getDouble("lat")
            var lng = jsonObject.getDouble("lng")
            if (!jsonObject.isNull("title")){
                title = jsonObject.getString("title")
            }
            if (!jsonObject.isNull("snippet")){
                snippet = jsonObject.getString("snippet")
            }
            items.add(SalesClusterItem(LatLng(lat, lng), title?:"", snippet?:""))
        }
        return items
    }
}