package com.example.ipadress.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "devices",
    foreignKeys = [
        ForeignKey(
            entity = Department::class,
            parentColumns = ["id"],
            childColumns = ["departmentId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["departmentId"]),
        Index(value = ["modelName"]),
        Index(value = ["ipAddress"]),
    ],
)
data class Device(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val departmentId: Long,
    val modelName: String,
    val ipAddress: String,
    val loginId: String = "",
    val loginPassword: String = "",
    val memo: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)
