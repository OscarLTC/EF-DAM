package tco.idat.ef

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tco.idat.ef.databinding.ActivityHomeBinding
import tco.idat.ef.databinding.ActivityStartBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddProduct.setOnClickListener {
            setContentView(R.layout.activity_new_product)
        }

    }
}