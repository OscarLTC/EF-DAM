package tco.idat.ef.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tco.idat.ef.R
import tco.idat.ef.databinding.ItemProductBinding
import tco.idat.ef.model.Producto

class ProductoAdapter(private val dataSet: MutableList<Producto>):
RecyclerView.Adapter<ProductoAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var binding = ItemProductBinding.bind(view)
        var context : Context = view.context
        fun setItems(producto: Producto){
            var url = producto.imagen.toString()
            binding.itemProductImage.setImageURI(null)
            if(url.length > 0){
                println(url)
                println(producto)
                Picasso.get().load(url).into(binding.itemProductImage)
            }else{
                binding.itemProductImage.setImageDrawable(ContextCompat.getDrawable(this.context ,R.drawable.icon_product))
            }
            binding.itemProductId.text = producto.codigo.toString()
            binding.itemProductStock.text = "${producto.stock} productos"
            binding.itemProductDescription.text = producto.descripcion
            binding.itemProductBrand.text = producto.marca
            binding.itemProductPricePurchasee.text = producto.preciocompra.toString()
            binding.itemProductPriceSale.text = producto.precioventa.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductoAdapter.ViewHolder, position: Int) {
        val item: Producto = dataSet.get(position)
        holder.setItems(item)
    }
    override fun getItemCount()=dataSet.size
}

