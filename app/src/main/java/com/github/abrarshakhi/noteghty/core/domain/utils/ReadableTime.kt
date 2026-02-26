package com.github.abrarshakhi.noteghty.core.domain.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.toDayMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM")
    return this.atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
}