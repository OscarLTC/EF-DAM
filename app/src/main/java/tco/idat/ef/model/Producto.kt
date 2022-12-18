package tco.idat.ef.model

data class Producto(
    var codigo:Int,
    var descripcion: String,
    var marca: String,
    var preciocompra: Double,
    var precioventa: Double,
    var stock: Int,
    var imagen: String
)
