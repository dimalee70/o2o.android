package kz.dragau.larek.models.objects

data class SalesOuter(var name: String?,
                      var legacyName: String ?,
                      var address: String?){
    override fun toString(): String {
        return "SalesOuter(name=$name, legacyName=$legacyName, address=$address)"
    }
}