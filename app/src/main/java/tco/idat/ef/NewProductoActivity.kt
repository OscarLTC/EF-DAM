package tco.idat.ef

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import tco.idat.ef.databinding.ActivityHomeBinding
import tco.idat.ef.databinding.ActivityNewProductBinding

class NewProductoActivity  : AppCompatActivity() {

    lateinit var binding: ActivityNewProductBinding
    var foto: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        binding = ActivityNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}