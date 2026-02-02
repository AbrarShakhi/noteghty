package com.github.abrarshakhi.noteghty.core.domain.listings

enum class ListingDirection {
    ASCENDING, DESCENDING
}

fun ListingDirection.toggle(): ListingDirection {
    return if (this == ListingDirection.ASCENDING) ListingDirection.DESCENDING
    else ListingDirection.ASCENDING
}

