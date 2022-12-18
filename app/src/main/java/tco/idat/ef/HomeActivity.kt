package tco.idat.ef

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import tco.idat.ef.adapter.ProductoAdapter
import tco.idat.ef.databinding.ActivityHomeBinding
import tco.idat.ef.databinding.ActivityStartBinding
import tco.idat.ef.model.LastId
import tco.idat.ef.model.Producto
import kotlin.math.cos

class HomeActivity : AppCompatActivity() {

    var productos:MutableList<Producto> = mutableListOf()

    lateinit var binding: ActivityHomeBinding
    lateinit var db: FirebaseFirestore
    lateinit var productoAdapter: ProductoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setProducts()
        binding.recyclerviewProducts.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerviewProducts.setHasFixedSize(true)

        binding.btnAddProduct.setOnClickListener {
            startActivity(Intent(this,NewProductoActivity::class.java))
        }

        binding.btnClose.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val r=Intent(this,LoginActivity::class.java)
            r.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(r)
        }

    }

    fun setProducts(){
        db =FirebaseFirestore.getInstance()
        db.collection("Productos")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result){
                    var cod = doc.data["codigo"].toString()
                    var desc = doc.data["descripcion"].toString()
                    var marca = doc.data["marca"].toString()
                    var preciocompra = doc.data["preciocompra"].toString()
                    var precioventa = doc.data["precioventa"].toString()
                    var stock = doc.data["stock"].toString()
                    var img = doc.data["imagen"].toString()

                    var prod = Producto(cod.toInt(),desc, marca, preciocompra.toDouble(),
                        precioventa.toDouble(), stock.toInt(), img )
                    productos.add(prod)
                }
                productos.sortBy { it.codigo }
                LastId.id = productos.last().codigo
                productoAdapter = ProductoAdapter(productos)
                binding.recyclerviewProducts.adapter = productoAdapter
            }
    }
}