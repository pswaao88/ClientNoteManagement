package com.example.ipadress.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pc_entries",
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
        Index(value = ["seatSuffix"]),
        Index(value = ["ipAddress"]),
    ],
)
data class PcEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val departmentId: Long,
    val seatSuffix: String? = null,
    val ipAddress: String? = null,
    val loginId: String? = null,
    val loginPassword: String? = null,
    val memo: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)
