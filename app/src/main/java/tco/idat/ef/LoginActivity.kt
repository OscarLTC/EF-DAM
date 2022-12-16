package tco.idat.ef

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import tco.idat.ef.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnLogin.setOnClickListener{
            var email = binding.txtEmail.text.toString()
            var pass = binding.txtPassword.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                if(validarEmail(email)){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                            else{
                                Toast.makeText(this, "Correo y/o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "El correo electronico no es valido", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Debe completar todo los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener{
            var email = binding.txtEmail.text.toString()
            var pass = binding.txtPassword.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                if(validarEmail(email)){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                Toast.makeText(this, "Se registró correctamente 😀", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                            else{
                                Toast.makeText(this, "No se pudo registrar correctamente 😢", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "El correo electronico no es valido", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Debe completar todo los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validarEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}