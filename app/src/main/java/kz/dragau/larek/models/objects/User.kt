package kz.dragau.larek.models.objects

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "u_id")
    var id: Int,
    @ColumnInfo
    var username: String
) : BaseObservable()
{
    @ColumnInfo
    var birthday: String? = null
        @Bindable get
        set(value) {
            field = value
            //notifyPropertyChanged(BR.birthday)
        }

    @ColumnInfo
    var phone: String? = null
        @Bindable get
        set(value) {
            field = value
            //notifyPropertyChanged(BR.phone)
        }
}