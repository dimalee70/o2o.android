package kz.dragau.larek.ui.fragment.store

import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import kz.dragau.larek.R
import kz.dragau.larek.presentation.view.store.RegisterStoreView
import kz.dragau.larek.presentation.presenter.store.RegisterStorePresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.suke.widget.SwitchButton
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.reactivex.Observable
import kz.dragau.larek.App
import kz.dragau.larek.databinding.FragmentRegisterStoreBinding
import kz.dragau.larek.models.objects.Images
import kz.dragau.larek.models.objects.SalesOuter
import kz.dragau.larek.presentation.presenter.map.SaleSelector
import kz.dragau.larek.ui.adapters.images.ImageAdapter
import kz.dragau.larek.ui.fragment.BaseMvpFragment
import kz.dragau.larek.ui.rule.NotEmptyRule
import ru.terrakok.cicerone.Router
import ru.whalemare.rxvalidator.RxCombineValidator
import ru.whalemare.rxvalidator.RxValidator
import javax.inject.Inject

class StoreRegisterFragment: BaseMvpFragment(), RegisterStoreView {

    override fun onNameValid() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNameInvalid(errorMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkAddress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initGeocoder() {
//        saleSelector.salesOuter = SalesOuter(mRegisterStorePresenter.registerStoreRequestModel.name,
//        mRegisterStorePresenter.registerStoreRequestModel.address,
//        mRegisterStorePresenter.registerStoreRequestModel.latitude,
//        mRegisterStorePresenter.registerStoreRequestModel.longitute,
//        mRegisterStorePresenter.registerStoreRequestModel.isChecked)

        if(saleSelector.salesOuter == null) {
            val geocoder = Geocoder(context)
            try {
                val addresses = geocoder.getFromLocationName(mRegisterStorePresenter.registerStoreRequestModel.address, 1)

                if (addresses.size > 0) {
                    saleSelector.salesOuter = SalesOuter(mRegisterStorePresenter.registerStoreRequestModel.name,
                        mRegisterStorePresenter.registerStoreRequestModel.address,
                        addresses.get(0).latitude,
                        addresses.get(0).longitude,
                        mRegisterStorePresenter.registerStoreRequestModel.isChecked)
//                saleSelector.salesOuter = SalesOuter(null, null, null, null, true)
//                    saleSelector.salesOuter!!.latitude = addresses.get(0).latitude
//                    saleSelector.salesOuter!!.longitude = addresses.get(0).longitude
                    mRegisterStorePresenter.registerToServer()
                } else {
                    saleSelector.salesOuter = null
                    showError("Ошибка", -1)
                }
            }catch (e: Exception){
                saleSelector.salesOuter = null
                showError(e)
            }
        }
        else {
            mRegisterStorePresenter.registerToServer()
        }
    }


//    override fun initSalesOutlet() {
//        saleSelector.salesOuter!!.name = binding.storeTitleEt.editText!!.text.toString()
//        saleSelector.salesOuter!!.isAcceptOrders = binding.onlineBooking.isChecked
//
//            binding.storeTitleEt.editText!!.text = Editable.Factory.getInstance().newEditable(salesOuter.name)
////        binding.storeTitleEt.text = Editable.Factory.getInstance().newEditable(salesOuter.name)
//        binding.storeLegalTitleEt.editText!!.text  = Editable.Factory.getInstance().newEditable(salesOuter.name)
//        binding.storeAddressEt.editText!!.text  = Editable.Factory.getInstance().newEditable(salesOuter.address)
//    }


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
    lateinit var imageList: Images

    @Inject
    lateinit var saleSelector: SaleSelector

    @InjectPresenter
    lateinit var mRegisterStorePresenter: RegisterStorePresenter

    private var imageAdapter: ImageAdapter? = null

    private var width: Int? = null

    private var imageGridView: GridView? = null

    private var gridViewArrayAdapter: ArrayAdapter<Uri>? = null

    @ProvidePresenter
    fun providePresenter(): RegisterStorePresenter
    {
        return RegisterStorePresenter(router, saleSelector, imageList.images!!)
    }

    lateinit var binding: FragmentRegisterStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_store, container, false)
        binding.registerStoreViewModel = mRegisterStorePresenter.registerStoreRequestModel
        binding.presenter = mRegisterStorePresenter
        binding.onlineBooking.isChecked = mRegisterStorePresenter.registerStoreRequestModel.isChecked

        val nameObservable: Observable<Boolean> = RxValidator(binding.storeTitleEt).apply {
            add(NotEmptyRule(getString(R.string.empty_text_rule)))
//            add(MinLengthRule(5))
        }.asObservable()

        val nameLegalObservable = RxValidator(binding.storeLegalTitleEt).apply {
            add(NotEmptyRule(getString(R.string.empty_text_rule)))
        }.asObservable()

        val addressObservable = RxValidator(binding.storeAddressEt).apply {
            add(NotEmptyRule(getString(R.string.empty_text_rule)))
//            add(AddressRule(context!!, saleSelector))
        }.asObservable()

        RxCombineValidator(nameObservable, nameLegalObservable, addressObservable )
            .asObservable()
            .distinctUntilChanged()
            .subscribe({
                binding.finishButton.isEnabled = it
                binding.finishButton.visibility = if(it) View.VISIBLE else View.GONE
            },
        {
            binding.finishButton.isEnabled = false
            binding.finishButton.visibility = View.VISIBLE
        })

        binding.avaIv.setOnClickListener{
            showPictureDialog()
        }

        if(this.width == null) {
            this.width = binding.avaIv.layoutParams.width
        }

//        gridViewArrayAdapter = ArrayAdapter<Uri>(
//            this.context!!, android.R.layout.simple_list_item_1,
//            imageList.images!!
//        )
        imageGridView = binding.imageGv
        imageAdapter = ImageAdapter(context!!, imageList.images!!, router)
//        imageGridView!!.adapter =imageAdapter

        changeGridView()



        binding.onlineBooking.setOnCheckedChangeListener(this)
        return binding.root
    }

    private fun changeGridView() {

        imageAdapter!!.notifyDataSetChanged()
//        imageGridView!!.invalidateViews()
        imageGridView!!.adapter = imageAdapter

        if(imageList.images!!.size >= 3)
            binding.imageGv.numColumns = 3

//        imageGridView!!.numColumns = 3
        else if(imageList.images!!.size == 2){
            binding.imageGv.numColumns = 2
        }
        else if(imageList.images!!.size == 0) {
            binding.avaIv.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        }
        else if(imageList.images!!.size > 0){
            binding.imageGv.numColumns = 1
            binding.avaIv.layoutParams.width = width!!
        }


//        binding.avaIv.layoutParams.width = 204
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        App.appComponent.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        saleSelector.salesOuter?.let { showSale(it) }



//        Glide.with(context!!).load(bitmap?.let { saveImage(it) }).into(binding.avatarIv)


//        print(imageList.images)
//        println()
//        binding.avaIv.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
//        val imageGridView = binding.imageGv
//        imageGridView.adapter =
//            ImageAdapter(context!!, imageList.images!!, router)
//        if(imageList.images!!.size >= 3)
//            imageGridView.numColumns = 3
//        else if(imageList.images!!.size == 2){
//            imageGridView.numColumns = 2
//        }
//        else if(imageList.images!!.size == 0) {
//            binding.avaIv.layoutParams.width = width!!


//        }

        changeGridView()
//        if (saleSelector.imageSelector != null) {
//            binding.avatarIv.setImageURI(Uri.parse(saleSelector.imageSelector))
//        }
    }

    override fun onPause() {
        super.onPause()

//        binding.storeTitleEt.editText!!.text.let{
//            if(saleSelector.salesOuter == null)
//                saleSelector.salesOuter = SalesOuter(null, null, null, null, null)
//            saleSelector.salesOuter!!.name = it.toString()
//        }
//        binding.storeAddressEt.editText!!.text.let {
////            if(saleSelector.salesOuter == null)
////                saleSelector.salesOuter = SalesOuter(null, null, null, null, null)
////            saleSelector.salesOuter!!.name = it.toString()
//            saleSelector.salesOuter!!.address = it.toString()
//        }


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
//                .setMinCropResultSize(1920, 100.toPx())
//                .setAspectRatio(3,1)
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


//
    override fun showSale(salesOuter: SalesOuter) {
        mRegisterStorePresenter.registerStoreRequestModel.name = salesOuter.name
        mRegisterStorePresenter.registerStoreRequestModel.address = salesOuter.address
//        mRegisterStorePresenter.registerStoreRequestModel.latitude = salesOuter.latitude
//        mRegisterStorePresenter.registerStoreRequestModel.longitute = salesOuter.longitude

//        binding.storeTitleEt.editText!!.text = Editable.Factory.getInstance().newEditable(salesOuter.name)
//        binding.storeTitleEt.text = Editable.Factory.getInstance().newEditable(salesOuter.name)

//        binding.storeLegalTitleEt.editText!!.text  = Editable.Factory.getInstance().newEditable(salesOuter.name)
//        binding.storeAddressEt.editText!!.text  = Editable.Factory.getInstance().newEditable(salesOuter.address)

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


//    fun rotate(bitmap: Bitmap, degree: Int): Bitmap
//    {
//        val w = bitmap.width
//        val h = bitmap.height
//        val mtx = Matrix()
//        mtx.setRotate(degree.toFloat())
//        return Bitmap.createBitmap(bitmap, 0,0, w, h, mtx, true)
//    }

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

//    private var images = arrayOf(
//        "https://i.pinimg.com/originals/94/dd/57/94dd573e4b4de604ea7f33548da99fd6.jpg"
//        ,
//        "https://stimg.cardekho.com/images/carexteriorimages/930x620/Kia/Kia-Seltos/6232/1562746799300/front-left-side-47.jpg?tr=w-375,e-sharpen"
//        ,
//        "https://images.unsplash.com/photo-1518568814500-bf0f8d125f46?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
//        "https://www.nasa.gov/sites/default/files/styles/image_card_4x3_ratio/public/thumbnails/image/48181678987_dd26a6ed67_k.jpg"
//        )

    override fun changeSwitch() {
        updateSwitch(binding.onlineBooking.isChecked)
    }

    private fun updateSwitch(checked: Boolean) {
        binding.onlineBooking.isChecked = !checked
    }

    override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {
        mRegisterStorePresenter.registerStoreRequestModel.isChecked = isChecked
//        saleSelector.salesOuter?.let { it.isAcceptOrders = isChecked }
    }

//    fun getDisplayMetric(): Point {
//        val size = Point()
//        activity!!.windowManager.defaultDisplay.getSize(size)
//        return size
//    }



}
