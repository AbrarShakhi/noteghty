package com.github.abrarshakhi.noteghty.note.domain.utils


sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
