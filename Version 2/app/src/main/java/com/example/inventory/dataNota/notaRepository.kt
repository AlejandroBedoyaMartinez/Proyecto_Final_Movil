package com.example.inventory.dataNota

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class notaRepository(
    private val notaDao: notaDao
) {
    fun getNotas(): Flow<List<Nota>> {
        return notaDao.getAllNotes()
            .map { entities ->
                entities.map { entity ->
                    Nota(
                        id = entity.id,
                        titulo = entity.titulo,
                        descripcion = entity.descripcion,
                        cuerpo = entity.cuerpo,
                        texto = entity.texto,
                        imagenes = entity.imagenes,
                        videos = entity.videos
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
                    texto = entity.texto,
                    imagenes = entity.imagenes,
                    videos = entity.videos
                )
            }
    }


    suspend fun insertNota(nota: Nota){
        val entity = notaEntity(
            id = nota.id,
            titulo = nota.titulo,
            descripcion = nota.descripcion,
            cuerpo = nota.cuerpo,
            texto = nota.texto,
            imagenes = nota.imagenes,
            videos = nota.videos
        )
        notaDao.insert(entity)
    }

    suspend fun deleteNota(nota: Nota){
        val entity = notaEntity(
            id = nota.id,
            titulo = nota.titulo,
            descripcion = nota.descripcion,
            cuerpo = nota.cuerpo,
            texto = nota.texto,
            imagenes = nota.imagenes,
            videos = nota.videos
        )
        notaDao.delete(entity)
    }

    suspend fun editNota(nota: Nota){
        val entity = notaEntity(
            id = nota.id,
            titulo = nota.titulo,
            descripcion = nota.descripcion,
            cuerpo = nota.cuerpo,
            texto = nota.texto,
            imagenes = nota.imagenes,
            videos = nota.videos
        )
        notaDao.update(entity)
    }

}