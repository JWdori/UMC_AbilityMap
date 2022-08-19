package com.abilitymap.ui.marker

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PATCH
import retrofit2.http.Path

data class ReportPatchResponse(val isSuccess : Boolean, val code: Int, val message : String)


interface WrongReportService {
    @FormUrlEncoded
    @PATCH("report/{reportIdx}")
    fun patchReport(
        @Path("reportIdx") reportIdx: String?,
        @Field("wrong") wrong: Int
    ) : Call<ReportPatchResponse>


}