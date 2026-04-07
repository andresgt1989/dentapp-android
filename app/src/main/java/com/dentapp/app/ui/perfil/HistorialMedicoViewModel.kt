package com.dentapp.app.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.HealthProfileDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistorialMedicoState(
    val loading: Boolean = true,
    val saving: Boolean = false,
    val completeness: Int = 0,
    val form: HealthProfileDto = HealthProfileDto(),
    val error: String? = null,
    val successMsg: String? = null,
)

@HiltViewModel
class HistorialMedicoViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(HistorialMedicoState())
    val state: StateFlow<HistorialMedicoState> = _state.asStateFlow()

    init {
        cargar()
    }

    private fun cargar() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                val resp = api.getHealthProfile()
                if (resp.isSuccessful) {
                    val body = resp.body()!!
                    _state.value = _state.value.copy(
                        loading = false,
                        form = body.profile ?: HealthProfileDto(),
                        completeness = body.completeness,
                    )
                } else {
                    _state.value = _state.value.copy(loading = false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "Error cargando perfil: ${e.message}",
                )
            }
        }
    }

    fun update(block: HealthProfileDto.() -> HealthProfileDto) {
        _state.value = _state.value.copy(form = _state.value.form.block())
    }

    fun guardar() {
        viewModelScope.launch {
            _state.value = _state.value.copy(saving = true, error = null)
            try {
                val form = _state.value.form
                val body = buildMap<String, Any?> {
                    form.pregnancyStatus?.let { put("pregnancy_status", it) }
                    form.trimester?.let { put("trimester", it) }
                    form.cardiacCondition?.let { put("cardiac_condition", it) }
                    form.cardiacDetail?.let { put("cardiac_detail", it) }
                    form.takesBisphosphonates?.let { put("takes_bisphosphonates", it) }
                    form.bisphosphonateName?.let { put("bisphosphonate_name", it) }
                    form.bisphosphonateRoute?.let { put("bisphosphonate_route", it) }
                    form.renalInsufficiency?.let { put("renal_insufficiency", it) }
                    form.hepaticInsufficiency?.let { put("hepatic_insufficiency", it) }
                    form.hemophilia?.let { put("hemophilia", it) }
                    form.pacemaker?.let { put("pacemaker", it) }
                    form.hivStatus?.let { put("hiv_status", it) }
                    form.oncologyActive?.let { put("oncology_active", it) }
                    form.oncologyType?.let { put("oncology_type", it) }
                    form.epilepsy?.let { put("epilepsy", it) }
                    form.eatingDisorder?.let { put("eating_disorder", it) }
                    form.gerd?.let { put("gerd", it) }
                    form.sjogren?.let { put("sjogren", it) }
                    form.systemicMeds?.let { put("systemic_meds", it) }
                    form.brushingFreq?.let { put("brushing_freq", it) }
                    form.ageRange?.let { put("age_range", it) }
                    form.tobaccoType?.let { put("tobacco_type", it) }
                    form.tobaccoFreq?.let { put("tobacco_freq", it) }
                    form.dentalAnxiety?.let { put("dental_anxiety", it) }
                    form.usesFloss?.let { put("uses_floss", it) }
                    form.usesMouthwash?.let { put("uses_mouthwash", it) }
                    put("onboarding_complete", true)
                }

                val resp = api.updateHealthProfile(body)
                if (resp.isSuccessful) {
                    val b = resp.body()!!
                    _state.value = _state.value.copy(
                        saving = false,
                        form = b.profile ?: form,
                        completeness = b.completeness,
                        successMsg = "Perfil guardado correctamente",
                    )
                } else {
                    _state.value = _state.value.copy(
                        saving = false,
                        error = "Error al guardar: ${resp.code()}",
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    saving = false,
                    error = "Error: ${e.message}",
                )
            }
        }
    }

    fun clearMessages() {
        _state.value = _state.value.copy(error = null, successMsg = null)
    }
}
