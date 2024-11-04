package com.example.inventory.dataTarea

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class tareaRepository(
    private val tareaDao: tareaDao
) {
    fun getTareas(): Flow<List<Tarea>> {
        return tareaDao.getAllTareas()
            .map { entities ->
                entities.map { entity ->
                    Tarea(
                        id = entity.id,
                        titulo = entity.titulo,
                        descripcion = entity.descripcion,
                        cuerpo = entity.cuerpo,
                        texto = entity.texto,
                        fechaIncio = entity.fechaIncio,
                        fechaFin = entity.fechaFin,
                        recordar = entity.recordar,
                        hecho = entity.hecho
                    )
                }
            }
    }

    fun getTarea(id: Int): Flow<Tarea> {
        return tareaDao.getTarea(id)
            .map { entity ->
                Tarea(
                    id = entity.id,
                    titulo = entity.titulo,
                    descripcion = entity.descripcion,
                    cuerpo = entity.cuerpo,
                    texto = entity.texto,
                    fechaIncio = entity.fechaIncio,
                    fechaFin = entity.fechaFin,
                    recordar = entity.recordar,
                    hecho = entity.hecho
                )
            }
    }


    suspend fun insertTarea(tarea: Tarea){
        val entity = tareaEntity(
            id = tarea.id,
            titulo = tarea.titulo,
            descripcion = tarea.descripcion,
            cuerpo = tarea.cuerpo,
            texto = tarea.texto,
            fechaIncio = tarea.fechaIncio,
            fechaFin = tarea.fechaFin,
            recordar = tarea.recordar,
            hecho = tarea.hecho
        )
        tareaDao.insert(entity)
    }

    suspend fun deleteTarea(tarea: Tarea){
        val entity = tareaEntity(
            id = tarea.id,
            titulo = tarea.titulo,
            descripcion = tarea.descripcion,
            cuerpo = tarea.cuerpo,
            texto = tarea.texto,
            fechaIncio = tarea.fechaIncio,
            fechaFin = tarea.fechaFin,
            recordar = tarea.recordar,
            hecho = tarea.hecho
        )
        tareaDao.delete(entity)
    }

    suspend fun editTarea(tarea: Tarea){
        val entity = tareaEntity(
            id = tarea.id,
            titulo = tarea.titulo,
            descripcion = tarea.descripcion,
            cuerpo = tarea.cuerpo,
            texto = tarea.texto,
            fechaIncio = tarea.fechaIncio,
            fechaFin = tarea.fechaFin,
            recordar = tarea.recordar,
            hecho = tarea.hecho
        )
        tareaDao.update(entity)
    }

}