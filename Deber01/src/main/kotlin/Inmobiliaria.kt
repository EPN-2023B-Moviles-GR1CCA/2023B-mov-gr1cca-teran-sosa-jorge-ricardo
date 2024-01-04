data class Inmobiliaria(
    val nombre: String,
    val direccion: String,
    val inmuebles: MutableList<Inmueble> = mutableListOf()
) {
    fun agregarInmueble(inmueble: Inmueble){
        inmuebles.add(inmueble)
    }
}