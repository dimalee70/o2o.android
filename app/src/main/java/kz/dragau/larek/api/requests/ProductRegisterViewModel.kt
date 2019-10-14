package kz.dragau.larek.api.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class ProductRegisterViewModel: BaseObservable() {

    var isEnable: Boolean = true
        @Bindable get
        set(value){
            field = value
            notifyChange()
        }

    var productCategoryId: String? = null

    var categoryName: String? = null
        @Bindable get
        set(value){
            field = value
            notifyChange()
        }
    var produserName: String? = null
        @Bindable get
        set(value){
            field = value
            notifyChange()
        }
    var title: String? = null
        @Bindable get
        set(value){
            field = value
            notifyChange()
        }
    var barCode: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyChange()
        }
    var describe: String? = null
        @Bindable get
        set(value){
            field = value
            notifyChange()
        }
    var imageUri: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyChange()
        }

    var isVisiblePhoto: Boolean = false
        set(value){
            field = value
            notifyChange()
        }

    fun clearObject(){
        categoryName = null
        produserName = null
        title = null
        barCode = null
        describe = null
        imageUri = null
        isVisiblePhoto = false
    }
}