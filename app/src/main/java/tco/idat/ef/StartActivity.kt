package tco.idat.ef

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tco.idat.ef.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartLogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }
}