package kz.dragau.larek.models.objects

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class SalesClusterItem(var latLng: LatLng, var titleText: String, var snippetText: String): ClusterItem {
    override fun getSnippet(): String {
        return snippetText
    }

    override fun getTitle(): String {
        return titleText
    }

    override fun getPosition(): LatLng {
        return latLng
    }
}