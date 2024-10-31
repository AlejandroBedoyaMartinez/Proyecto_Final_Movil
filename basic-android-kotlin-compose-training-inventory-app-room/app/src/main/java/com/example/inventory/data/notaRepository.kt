package com.example.inventorydata

import com.example.inventory.Nota

class notaRepository(
    private val notaDao: notaDao
) {
    suspend fun getNotas():List<Nota>{
        val entities = notaDao.getAllNotes()
        return entities.map { Nota(
            titulo = it.titulo,
            descripcion = it.descripcion,
            cuerpo = it.cuerpo,
            texto = it.texto
            ) }
    }

    suspend fun insertNota(nota:Nota){
    val entity =notaEntity(
        titulo = nota.titulo,
        descripcion = nota.descripcion,
        cuerpo = nota.cuerpo,
        texto = nota.texto
    )
        notaDao.insert(entity)
    }
}