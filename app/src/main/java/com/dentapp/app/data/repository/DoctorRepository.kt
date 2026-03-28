package com.dentapp.app.data.repository

import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.DoctorDto
import com.dentapp.app.data.model.UpdateBankInfoRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoctorRepository @Inject constructor(private val api: ApiService) {

    suspend fun getDoctors(specialty: String? = null): Result<List<DoctorDto>> = try {
        val r = api.getDoctors(specialty = specialty)
        if (r.isSuccessful) Result.Success(r.body()!!.doctors)
        else Result.Error("Error al cargar doctores")
    } catch (e: Exception) { Result.Error("Sin conexión") }

    suspend fun getMyProfile(): Result<DoctorDto> = try {
        val r = api.getMyDoctorProfile()
        if (r.isSuccessful) Result.Success(r.body()!!.doctor)
        else Result.Error("Perfil no encontrado")
    } catch (e: Exception) { Result.Error("Sin conexión") }

    suspend fun updateProfile(fields: Map<String, String>): Result<DoctorDto> = try {
        val r = api.updateDoctorProfile(fields)
        if (r.isSuccessful) Result.Success(r.body()!!.doctor)
        else Result.Error("Error al actualizar")
    } catch (e: Exception) { Result.Error("Sin conexión") }

    suspend fun updateBankInfo(request: UpdateBankInfoRequest): Result<DoctorDto> = try {
        val r = api.updateBankInfo(request)
        if (r.isSuccessful) Result.Success(r.body()!!.doctor)
        else Result.Error("Error al guardar datos bancarios")
    } catch (e: Exception) { Result.Error("Sin conexión") }
}
