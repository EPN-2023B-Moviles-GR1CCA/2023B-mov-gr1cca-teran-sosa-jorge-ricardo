data class Inmueble(
    val tipo: String,
    val direccion: String,
    val precio: Double,
    val antiguedad: Int,
    val nombreInmobiliaria: String
)

enum class TipoInmueble {
    CASA,
    APARTAMENTO,
    OFICINA
}