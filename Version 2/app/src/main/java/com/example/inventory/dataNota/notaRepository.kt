package com.example.inventory.dataNota

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class notaRepository(
    private val notaDao: notaDao
) {
    fun getNotas(): Flow<List<Nota>> {
        return notaDao.getAllNotes() // getAllNotes debe retornar Flow<List<NotaEntity>>
            .map { entities ->
                entities.map { entity ->
                    Nota(
                        id = entity.id,
                        titulo = entity.titulo,
                        descripcion = entity.descripcion,
                        cuerpo = entity.cuerpo,
                        texto = entity.texto
                    )
                }
            }
    }

    fun getNota(id: Int): Flow<Nota> {
        return notaDao.getNote(id)
            .map { entity ->
                Nota(
                    id = entity.id,
                    titulo = entity.titulo,
                    descripcion = entity.descripcion,
                    cuerpo = entity.cuerpo,
                    texto = entity.texto
                )
            }
    }


    suspend fun insertNota(nota: Nota){
    val entity = notaEntity(
        id = nota.id,
        titulo = nota.titulo,
        descripcion = nota.descripcion,
        cuerpo = nota.cuerpo,
        texto = nota.texto
    )
        notaDao.insert(entity)
    }

    suspend fun deleteNota(nota: Nota){
        val entity = notaEntity(
            id = nota.id,
            titulo = nota.titulo,
            descripcion = nota.descripcion,
            cuerpo = nota.cuerpo,
            texto = nota.texto
        )
        notaDao.delete(entity)
    }

    suspend fun editNota(nota: Nota){
        val entity = notaEntity(
            id = nota.id,
            titulo = nota.titulo,
            descripcion = nota.descripcion,
            cuerpo = nota.cuerpo,
            texto = nota.texto
        )
        notaDao.update(entity)
    }

}