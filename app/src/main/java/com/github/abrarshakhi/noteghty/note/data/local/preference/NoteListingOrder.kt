package com.github.abrarshakhi.noteghty.note.data.local.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.abrarshakhi.noteghty.core.domain.listings.ListingDirection
import com.github.abrarshakhi.noteghty.note.domain.listings.NoteOrder

private enum class NoteOrderType(val key: String) {
    TITLE("title"), DATE("date"), COLOR("color"), PINNED("pinned");

    companion object {
        fun fromKey(key: String): NoteOrderType = entries.find { it.key == key } ?: DATE

        /** Domain → Data layer */
        fun toType(noteOrder: NoteOrder): NoteOrderType = when (noteOrder) {
            is NoteOrder.Title -> TITLE
            is NoteOrder.Date -> DATE
            is NoteOrder.Color -> COLOR
            is NoteOrder.Pinned -> PINNED
        }

    }

    /** Data layer → Domain */
    fun toDomain(listingDirection: ListingDirection): NoteOrder = when (this) {
        TITLE -> NoteOrder.Title(listingDirection)
        DATE -> NoteOrder.Date(listingDirection)
        COLOR -> NoteOrder.Color(listingDirection)
        PINNED -> NoteOrder.Pinned(listingDirection)
    }
}

private const val KEY_NOTE_ORDER_TYPE = "key_note_order_type"
private const val KEY_NOTE_ORDER_DIRECTION = "key_note_order_direction"

/**
 * Retrieves the current NoteOrder from SharedPreferences.
 * Defaults to [NoteOrder.Date] with [ListingDirection.DESCENDING] if no value is stored.
 */
fun SharedPreferences.getNoteListingOrder(): NoteOrder {
    val typeKey = getString(KEY_NOTE_ORDER_TYPE, NoteOrderType.DATE.key) ?: NoteOrderType.DATE.key
    val direction = getString(
        KEY_NOTE_ORDER_DIRECTION,
        ListingDirection.DESCENDING.name
    )?.let { runCatching { ListingDirection.valueOf(it) }.getOrDefault(ListingDirection.DESCENDING) }
        ?: ListingDirection.DESCENDING

    val type = NoteOrderType.fromKey(typeKey)
    return type.toDomain(direction)
}

/**
 * Saves the given [NoteOrder] to SharedPreferences.
 */
fun SharedPreferences.setNoteListingOrder(order: NoteOrder) {
    edit() {
        putString(KEY_NOTE_ORDER_TYPE, NoteOrderType.toType(order).key)
        putString(KEY_NOTE_ORDER_DIRECTION, order.direction.name)
    }
}