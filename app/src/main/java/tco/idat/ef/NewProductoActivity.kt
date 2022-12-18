package tco.idat.ef

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import tco.idat.ef.databinding.ActivityHomeBinding
import tco.idat.ef.databinding.ActivityNewProductBinding
import tco.idat.ef.model.LastId
import tco.idat.ef.model.Producto
import java.util.UUID

class NewProductoActivity  : AppCompatActivity() {

    lateinit var binding: ActivityNewProductBinding
    lateinit var db: FirebaseFirestore
    var photo: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println(LastId.id)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        db =FirebaseFirestore.getInstance()

        binding.btnPhoto.isEnabled = true
        binding.btnAddProduct.isEnabled = true

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
        binding.btnPhoto.setOnClickListener{
            startActivityForResult(Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                ,1001)
        }


        binding.btnAddProduct.setOnClickListener{
            if(validar()){
                val message = AlertDialog.Builder(this)
                message.setTitle("Registrar Producto")
                message.setMessage("¿Desea registrar este producto?")
                message.setPositiveButton("Si", DialogInterface.OnClickListener{dialogInterface, i ->
                    if(photo != null){
                        val filename = UUID.randomUUID().toString()
                        val ref = FirebaseStorage.getInstance().getReference("/imagenes/$filename")
                        ref.putFile(photo!!).addOnSuccessListener {
                            ref.downloadUrl.addOnSuccessListener {
                                postProduct(it.toString())
                            }
                        }
                    }else{
                        postProduct("")
                    }
                })
                message.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
                message.show()
            }else{
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnNewProduct.setOnClickListener{
            binding.txtDescription.setText("")
            binding.txtBrand.setText("")
            binding.txtStock.setText("")
            binding.txtPriceSale.setText("")
            binding.txtPricePurchase.setText("")
            binding.imgFoto.setImageBitmap(null)

            binding.btnPhoto.isEnabled = true
            binding.btnAddProduct.isEnabled = true
        }

        binding.iconSearchProduct.setOnClickListener{
            val dialog: AlertDialog.Builder= AlertDialog.Builder(this)
            dialog.setTitle("Ingresar Dato")
            val codigo = EditText(this)
            codigo.setHint("Ingrese Código")
            codigo.inputType= InputType.TYPE_CLASS_NUMBER
            dialog.setView(codigo)
            dialog.setPositiveButton("Buscar", DialogInterface.OnClickListener{ dialogInterface, i ->
                if(codigo.text.isNotEmpty()){
                    binding.btnPhoto.isEnabled = false
                    binding.btnAddProduct.isEnabled = false
                    db.collection("Productos")
                        .whereEqualTo("codigo", codigo.text.toString().toInt())
                        .get()
                        .addOnSuccessListener {
                            docs ->
                            binding.txtDescription.setText("")
                            binding.txtBrand.setText("")
                            binding.txtStock.setText("")
                            binding.txtPricePurchase.setText("")
                            binding.txtPriceSale.setText("")
                            var sw = 0
                            binding.imgFoto.setImageBitmap(null)
                            for(doc in docs){
                                binding.txtDescription.setText(doc.data["descripcion"].toString())
                                binding.txtBrand.setText(doc.data["marca"].toString())
                                binding.txtStock.setText(doc.data["stock"].toString())
                                binding.txtPricePurchase.setText(doc.data["preciocompra"].toString())
                                binding.txtPriceSale.setText(doc.data["precioventa"].toString())
                                var img = doc.data["imagen"].toString()
                                if(img.length>0){
                                    Picasso.get().load(img).into(binding.imgFoto)
                                }
                                sw=1
                                break
                            }
                            if (sw==0){
                                Toast.makeText(this,"El producto no existe 😢", Toast.LENGTH_LONG).show()
                            }

                        }
                }
            })
            dialog.show()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 1001){
            binding.imgFoto.setImageURI(data?.data)
            photo = data?.data
        }
    }

    fun validar():Boolean{
        var isVal = false
        isVal = (binding.txtBrand.text.isNotEmpty() && binding.txtDescription.text.isNotEmpty()
                && binding.txtPriceSale.text.isNotEmpty() && binding.txtStock.text.isNotEmpty()
                && binding.txtPricePurchase.text.isNotEmpty())
        return isVal
    }

    fun postProduct(uri:String){
        val producto= db.collection("Productos")
        var descripcion=binding.txtDescription.text.toString()
        var marca=binding.txtBrand.text.toString()
        var stock=binding.txtStock.text.toString()
        var precioventa=binding.txtPriceSale.text.toString()
        var preciocompra=binding.txtPricePurchase.text.toString()
        var newProduct= Producto((LastId.id+1).toInt(), descripcion, marca, preciocompra.toDouble(),
            precioventa.toDouble(), stock.toInt(), uri)
        producto.add(
            newProduct
        ).addOnSuccessListener {
            Toast.makeText(this,"Se registró el nuevo producto",Toast.LENGTH_LONG).show()
            binding.txtDescription.setText("")
            binding.txtBrand.setText("")
            binding.txtStock.setText("")
            binding.txtPriceSale.setText("")
            binding.txtPricePurchase.setText("")
            binding.imgFoto.setImageBitmap(null)

            binding.btnPhoto.isEnabled = true
            binding.btnAddProduct.isEnabled = true
            LastId.id += 1

        }.addOnFailureListener { exception ->
            Toast.makeText(this,"Error al registrar $exception",Toast.LENGTH_LONG).show()
        }
    }
}