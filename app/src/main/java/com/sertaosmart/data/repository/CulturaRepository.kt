package com.sertaosmart.data.repository

import com.sertaosmart.data.DAO.CulturaDao
import com.sertaosmart.data.model.Cultura
import kotlinx.coroutines.flow.Flow

class CulturaRepository(private val culturaDao: CulturaDao) {
    fun getAllCulturas(): Flow<List<Cultura>> = culturaDao.getAllCulturas()

    fun getCultura(id: Int): Flow<Cultura> = culturaDao.getCultura(id)

    suspend fun insert(cultura: Cultura) {
        culturaDao.insert(cultura)
    }

    suspend fun update(cultura: Cultura) {
        culturaDao.update(cultura)
    }

    suspend fun delete(cultura: Cultura) {
        culturaDao.delete(cultura)
    }
}
