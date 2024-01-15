class CLI {
    private val crudService = CRUDService()

    fun iniciar() {
        var opcion: String
        do {
            mostrarMenuPrincipal()
            println("Seleccione una opción: ")
            opcion = readLine() ?: ""
            manejarOpcion(opcion)
        } while (opcion != "0")
    }

    private fun mostrarMenuPrincipal() {
        println("\n---- Menú Principal ----")
        println("1. Operaciones con Inmobiliarias")
        println("2. Operaciones con Inmuebles")
        println("0. Salir")
    }

    private fun manejarOpcion(opcion: String) {
        when (opcion) {
            "1" -> menuInmobiliarias()
            "2" -> menuInmuebles()
            "0" -> println("Saliendo del programa...")
            else -> println("Opción no válida, intente de nuevo.")
        }
    }

    private fun menuInmobiliarias() {
        println("\n---- Menú Inmobiliarias ----")
        println("1. Agregar nueva inmobiliaria")
        println("2. Mostrar todas las inmobiliarias")
        println("3. Actualizar una inmobiliaria")
        println("4. Eliminar una inmobiliaria")
        println("0. Volver al menú principal")

        when (readLine()) {
            "1" -> addNuevaInmobiliaria()
            "2" -> mostrarInmobiliarias()
            "3" -> actualizarInmobiliaria()
            "4" -> eliminarInmobiliaria()
            "0" -> return
            else -> println("Opción no válida, intente de nuevo.")
        }
    }

    private fun menuInmuebles() {
        println("\n---- Menú Inmuebles ----")
        println("1. Agregar nuevo inmueble")
        println("2. Mostrar todos los inmuebles")
        println("3. Actualizar un inmueble")
        println("4. Eliminar un inmueble")
        println("0. Volver al menú principal")

        when (readLine()) {
            "1" -> addNuevoInmueble()
            "2" -> mostrarInmuebles()
            "3" -> actualizarInmueble()
            "4" -> eliminarInmueble()
            "0" -> return
            else -> println("Opción no válida, intente de nuevo.")
        }
    }

    private fun addNuevaInmobiliaria() {
        println("Ingrese el nombre de la inmobiliaria:")
        val nombre = readLine() ?: return
        println("Ingrese la dirección:")
        val direccion = readLine() ?: return

        val nuevaInmobiliaria = Inmobiliaria(nombre, direccion)
        crudService.addInmobiliaria(nuevaInmobiliaria)
        println("Inmobiliaria añadida con éxito.")
    }

    private fun mostrarInmobiliarias() {
        val inmobiliarias = crudService.leerInmobiliarias()
        if (inmobiliarias.isEmpty()) {
            println("No hay inmobiliarias registradas.")
        } else {
            inmobiliarias.forEach { println(it) }
        }
    }

    private fun actualizarInmobiliaria() {
        println("Ingrese el nombre de la inmobiliaria a actualizar:")
        val nombre = readLine() ?: return

        val inmobiliariaExistente = crudService.leerInmobiliarias().find { it.nombre.equals(nombre, ignoreCase = true) }
        if (inmobiliariaExistente == null) {
            println("Inmobiliaria no encontrada.")
            return
        }

        println("Ingrese la nueva dirección (anterior: ${inmobiliariaExistente.direccion}):")
        val nuevaDireccion = readLine() ?: inmobiliariaExistente.direccion
        val inmobiliariaActualizada = Inmobiliaria(
            nombre = nombre,
            direccion = nuevaDireccion,
        )

        crudService.actualizarInmobiliaria(nombre, inmobiliariaActualizada)
        println("Inmobiliaria actualizada con éxito.")
    }

    private fun eliminarInmobiliaria() {
        println("Ingrese el nombre de la inmobiliaria a eliminar:")
        val nombre = readLine() ?: return
        crudService.eliminarInmobiliaria(nombre)
        println("Inmobiliaria eliminada con éxito.")
    }

    private fun addNuevoInmueble() {
        println("Ingrese la dirección del inmueble:")
        val direccion = readLine() ?: return
        println("Ingrese el tipo de inmueble (CASA/APARTAMENTO/OFICINA):")
        val tipoInput = readLine()
        val tipo = when(tipoInput?.toUpperCase()) {
            "CASA" -> TipoInmueble.CASA
            "APARTAMENTO" -> TipoInmueble.APARTAMENTO
            "OFICINA" -> TipoInmueble.OFICINA
            else -> return
        }
        println("Ingrese el precio del inmueble:")
        val precio = readLine()?.toDoubleOrNull() ?: return

        println("Ingrese la antiguedad en años")
        val antiguedad = readLine()?.toInt()?: return

        println("Ingrese el nombre de la inmobiliaria a la que pertenece el inmueble:")
        val nombreInmobiliaria = readLine() ?: return

        val inmobiliariaExistente = crudService.leerInmobiliarias().find { it.nombre.equals(nombreInmobiliaria, ignoreCase = true) }
        if (inmobiliariaExistente == null) {
            println("Inmobiliaria no encontrada. Por favor, añada primero la inmobiliaria.")
            return
        }

        val nuevoInmueble = Inmueble(tipo.toString(), direccion, precio, antiguedad, nombreInmobiliaria)
        inmobiliariaExistente.inmuebles.add(nuevoInmueble)

        crudService.actualizarInmobiliaria(nombreInmobiliaria, inmobiliariaExistente)

        crudService.addInmueble(nuevoInmueble)

        println("Inmueble añadido con éxito.")
    }

    private fun mostrarInmuebles() {
        val inmuebles = crudService.leerInmuebles()
        if (inmuebles.isEmpty()) {
            println("No hay inmuebles registrados.")
        } else {
            inmuebles.forEach { inmueble ->
                println("Dirección: ${inmueble.direccion}")
                println("Tipo: ${inmueble.tipo}")
                println("Precio: ${inmueble.precio}")
                println("Antiguedad: ${inmueble.antiguedad}")
                println("Inmobiliaria: ${inmueble.nombreInmobiliaria}")
                println("----")
            }
        }
    }

    private fun actualizarInmueble() {
        println("Ingrese la dirección del inmueble a actualizar:")
        val direccion = readLine() ?: return

        val inmuebleExistente = crudService.leerInmuebles().find { it.direccion.equals(direccion, ignoreCase = true) }
        if (inmuebleExistente == null) {
            println("Inmueble no encontrado.")
            return
        }

        println("Ingrese el nuevo tipo (CASA/APARTAMENTO, tipo anterior: ${inmuebleExistente.tipo}):")
        val nuevoTipoInput = readLine()
        val nuevoTipo = when(nuevoTipoInput?.toUpperCase()) {
            "CASA" -> TipoInmueble.CASA
            "APARTAMENTO" -> TipoInmueble.APARTAMENTO
            else -> inmuebleExistente.tipo
        }
        println("Ingrese el nuevo precio (precio anterior: ${inmuebleExistente.precio}):")
        val nuevoPrecio = readLine()?.toDoubleOrNull() ?: inmuebleExistente.precio
        println("Ingrese la antiguedad (antiguedad anterior: ${inmuebleExistente.antiguedad})")
        val nuevaAntiguedad = readLine()?.toInt()?: inmuebleExistente.antiguedad
        println("Ingrese el nombre de la nueva inmobiliaria (anterior: ${inmuebleExistente.nombreInmobiliaria}):")
        val nuevaInmobiliaria = readLine() ?: inmuebleExistente.nombreInmobiliaria

        val inmuebleActualizado = Inmueble(
            direccion = direccion,
            tipo = nuevoTipo.toString(),
            precio = nuevoPrecio,
            antiguedad = nuevaAntiguedad,
            nombreInmobiliaria = nuevaInmobiliaria
        )

        crudService.actualizarInmueble(direccion, inmuebleActualizado)
        println("Inmueble actualizado con éxito.")
    }

    private fun eliminarInmueble() {
        println("Ingrese la dirección del inmueble a eliminar:")
        val direccion = readLine() ?: return

        val inmuebleExistente = crudService.leerInmuebles().any { it.direccion.equals(direccion, ignoreCase = true) }
        if (!inmuebleExistente) {
            println("Inmueble no encontrado.")
            return
        }

        crudService.eliminarInmueble(direccion)
        println("Inmueble eliminado con éxito.")
    }
}