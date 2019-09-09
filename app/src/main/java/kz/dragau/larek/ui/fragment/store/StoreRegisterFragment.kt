package kz.dragau.larek.ui.fragment.store

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import kz.dragau.larek.presentation.presenter.store.RegisterStorePresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.suke.widget.SwitchButton
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kz.dragau.larek.App
import kz.dragau.larek.Constants
import kz.dragau.larek.Screens
import kz.dragau.larek.databinding.FragmentRegisterStoreBinding
import kz.dragau.larek.extensions.toDp
import kz.dragau.larek.extensions.toPx
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.models.objects.SalesOutletResult
import kz.dragau.larek.presentation.presenter.MainAppPresenter
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.ui.activity.store.StoreActivity
import photograd.kz.photograd.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class StoreRegisterFragment : BaseMvpFragment(), RegisterStoreView {

    companion object {
        const val TAG = "StoreRegisterFragment"

        fun newInstance(): StoreRegisterFragment {
            val fragment: StoreRegisterFragment = StoreRegisterFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var saleSelector: SaleSelector

    @InjectPresenter
    lateinit var mRegisterStorePresenter: RegisterStorePresenter

    @ProvidePresenter
    fun providePresenter(): RegisterStorePresenter
    {
        return RegisterStorePresenter(router, saleSelector)
    }

    lateinit var binding: FragmentRegisterStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_store, container, false)
        binding.presenter = mRegisterStorePresenter

        binding.avaIv.setOnClickListener{
            showPictureDialog()
        }

        binding.onlineBooking.setOnCheckedChangeListener(this)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        saleSelector.salesOuter?.let { showSale(it) }
//        Glide.with(context!!).load(bitmap?.let { saveImage(it) }).into(binding.avatarIv)

        if (saleSelector.imageSelector != null) {
            binding.avatarIv.setImageURI(Uri.parse(saleSelector.imageSelector))
        }
    }

    override fun onPause() {
        super.onPause()
//        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                binding.avatarIv.drawable.constantState!! != context!!.getDrawable(R.drawable.splash_screen)
//            } else {
//                binding.avatarIv.drawable.constantState!! != ContextCompat.getDrawable(this.activity!!, R.drawable.splash_screen)
//            }
//        ) {
////            (binding.avatarIv.drawable as BitmapDrawable).bitmap.let { bitmap = it }
////            val bitmapDrawable = binding.avatarIv.drawable as BitmapDrawable
////            bitmap = bitmapDrawable.bitmap
//        }
    }
    override fun showPictureDialog() {

//        Toast.makeText(context!!, "showPictureDialog", Toast.LENGTH_SHORT).show()


        this.activity?.let {
            CropImage.activity(null)
//                .setMaxCropResultSize(1920,1080)
    //            .setMinCropResultSize(1920, 100.toPx())
                .setAspectRatio(3,1)
//                .setRequestedSize(150,50, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(it)
        }
//        CropImage.startPickImageActivity(this.activity!!)

//            .start(context!! as Activity)


//        val pictureDialog = AlertDialog.Builder(context)
//        pictureDialog.setTitle(Constants.seletImageTitle)
//        val pictureDialogItems =
//            arrayOf(Constants.selectFromGallery, Constants.selectFromCamera)
//        pictureDialog.setItems(
//            pictureDialogItems
//        ) { dialogInterface: DialogInterface, i: Int ->
//            run {
//                when (i) {
//                    0 -> choseFromGallery()
//                    1 -> takePhotoFromCamera()
//                    else -> println("error")
//                }
//            }
//        }.setCancelable(true)
//        pictureDialog.show()
    }



    override fun showSale(salesOuter: SalesOutletResult) {
        binding.storeTitleEt.text = Editable.Factory.getInstance().newEditable(salesOuter.name)
        binding.storeLegalTitleEt.text = Editable.Factory.getInstance().newEditable(salesOuter.name)
        binding.storeAddressEt.text = Editable.Factory.getInstance().newEditable(salesOuter.address)

    }
//    override fun choseFromGallery() {
//        val galleryIntent = Intent(Intent.ACTION_PICK,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, Constants.GALLERY)
////        router.navigateTo(Screens.GalleryScreen())
//
//    }

//    override fun takePhotoFromCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, Constants.CAMERA)
////        router.navigateTo(Screens.CameraScreen())
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            val result = CropImage.getActivityResult(data)
//            if(resultCode == Activity.RESULT_OK){
//                binding.avatarIv.setImageURI(result.uri)
//            }
//            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Toast.makeText(context!!, "Croppinf failed: " + result.error, Toast.LENGTH_LONG).show()
//            }
//        }
//
//
////        if(resultCode == Activity.RESULT_CANCELED)
////            return
////        if(requestCode == Constants.GALLERY){
////            if(data != null){
////                val contentUri = data.data
////                try{
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
////                        bitmap = ImageDecoder.createSource(context!!.contentResolver, contentUri!!) as Bitmap
////                    else
////                        bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentUri)
////
////                    Glide.with(context!!).load(getRealPathFromURI(contentUri!!)).into(binding.avatarIv)
////                    Timber.i("Image saved")
////                }catch (e: Exception){
////                    e.printStackTrace()
////                }
////            }
////        }else if(requestCode == Constants.CAMERA){
////            bitmap = data!!.extras!!.get("data") as Bitmap
////            Glide.with(context!!).load(getRealPathFromURI(getImageUri(context!!, bitmap!!))).into(binding.avatarIv)
//////            binding.avatarIv.setImageBitmap(bitmap)
//////            saveImage(bitmap!!)
////            Timber.i( "Image Saved!")
////        }
//
//    }


    fun rotate(bitmap: Bitmap, degree: Int): Bitmap
    {
        val w = bitmap.width
        val h = bitmap.height
        val mtx = Matrix()
        mtx.setRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0,0, w, h, mtx, true)
    }

//    override fun onDestroy() {
//        saleSelector.listener = null
//        super.onDestroy()
//    }

//    fun getRealPathFromURI(uri: Uri): String {
//        val cursor = context!!.contentResolver.query(uri, null, null, null, null)
//        cursor!!.moveToFirst()
//        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//        return cursor.getString(idx)
//    }
//
//    fun getImageUri(context: Context, image: Bitmap): Uri{
//        val bytes = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        var path = MediaStore.Images.Media.insertImage(context.contentResolver, image, "Title", null)
//        return Uri.parse(path)
//    }

    override fun changeSwitch() {
        updateSwitch(binding.onlineBooking.isChecked)
    }

    private fun updateSwitch(checked: Boolean) {
        binding.onlineBooking.isChecked = !checked
    }

    override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
        saleSelector.salesOuter?.let { it.isAcceptOrders = isChecked }
    }

//    fun getDisplayMetric(): Point {
//        val size = Point()
//        activity!!.windowManager.defaultDisplay.getSize(size)
//        return size
//    }



}
