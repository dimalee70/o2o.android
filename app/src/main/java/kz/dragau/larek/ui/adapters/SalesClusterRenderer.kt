package kz.dragau.larek.ui.adapters

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class SalesClusterRenderer<T: ClusterItem>(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<T>?
) : DefaultClusterRenderer<T>(context, map, clusterManager) {

    public override fun shouldRenderAsCluster(cluster: Cluster<T>?): Boolean {
        return cluster!!.size >= 1
    }

    override fun onBeforeClusterItemRendered(item: T, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)
    }
}