package com.example.mydiary

data class HealthData(
    val id: Int,
    val date: String,
    val water: String
) {
    override fun toString(): String {
        return "Дата: $date, Выпито стаканов воды: $water"
    }
}