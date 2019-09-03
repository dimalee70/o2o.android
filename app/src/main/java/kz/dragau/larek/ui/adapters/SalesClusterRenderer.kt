package kz.dragau.larek.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import kz.dragau.larek.R
import kz.dragau.larek.models.objects.SalesOutletResult



class SalesClusterRenderer<T: SalesOutletResult>(
    private var context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<T>?
) : DefaultClusterRenderer<T>(context, map, clusterManager) {

    private val iconGenerator: IconGenerator = IconGenerator(context)
    private val makerImageView: ImageView = ImageView(context)

    public override fun shouldRenderAsCluster(cluster: Cluster<T>?): Boolean {
        return cluster!!.size > 1
    }

    override fun onBeforeClusterRendered(cluster: Cluster<T>?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterRendered(cluster, markerOptions)
//        makerImageView.setImageResource(R.drawable.ic_marker)
//        val icon: Bitmap = iconGenerator.makeIcon()


//        val descriptor = bitmapDescriptorFromVector(context!!, R.drawable.ic_marker)
//        markerOptions!!.icon(descriptor)

//        markerOptions!!
//            .position(cluster!!.position)
//            .title(cluster.).snippet(item.address).visible(true)
//            .anchor(0f, 0.5f)
//            .icon(
//                bitmapDescriptorFromVector(context!!, R.drawable.ic_marker)
//            )
    }

    override fun onBeforeClusterItemRendered(item: T, markerOptions: MarkerOptions?) {
//        makerImageView.setImageResource(R.drawable.ic_marker)
//        val icon: Bitmap = iconGenerator.makeIcon()
//        markerOptions!!.icon(BitmapDescriptorFactory.fromBitmap(icon))
//        markerOptions.title(item.title)
//        markerOptions.snippet(item.snippet)

        val descriptor = bitmapDescriptorFromVector(context!!, R.drawable.ic_marker)
        markerOptions!!.icon(descriptor)

//        markerOptions!!
//            .position(item.position)
//                        .title(item.name).snippet(item.address).visible(true)
//                        .anchor(0f, 0.5f)
//                        .icon(
//                            bitmapDescriptorFromVector(context!!, R.drawable.ic_marker)
//                        )
    }


    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}