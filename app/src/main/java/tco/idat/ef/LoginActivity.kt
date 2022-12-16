package tco.idat.ef

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import tco.idat.ef.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{
            var email = binding.txtEmail.text.toString()
            var pass = binding.txtPassword.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailLink(email, pass)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        else{
                            Toast.makeText(this, "Correo y/o contraseña incorrecta", Toast.LENGTH_SHORT)
                        }
                    }
            }else{
                Toast.makeText(this, "Debe completar todo los campos", Toast.LENGTH_SHORT)
            }
        }

        binding.btnRegister.setOnClickListener{
            var email = binding.txtEmail.text.toString()
            var pass = binding.txtPassword.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this, "Se registró correctamente 😀", Toast.LENGTH_SHORT)
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        else{
                            Toast.makeText(this, "No se pudo registrar correctamente 😢", Toast.LENGTH_SHORT)
                        }
                    }
            }else{
                Toast.makeText(this, "Debe completar todo los campos", Toast.LENGTH_SHORT)
            }
        }
    }
}