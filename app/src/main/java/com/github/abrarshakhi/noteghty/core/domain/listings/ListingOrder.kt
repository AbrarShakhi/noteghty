package com.github.abrarshakhi.noteghty.core.domain.listings

sealed interface ListingOrder {
    object Ascending : ListingOrder {
        override fun <T, R : Comparable<R>> sort(
            iterable: Iterable<T>, selector: (T) -> R
        ): List<T> = iterable.sortedBy(selector)
    }

    object Descending : ListingOrder {
        override fun <T, R : Comparable<R>> sort(
            iterable: Iterable<T>, selector: (T) -> R
        ): List<T> = iterable.sortedByDescending(selector)
    }

    fun <T, R : Comparable<R>> sort(iterable: Iterable<T>, selector: (T) -> R): List<T>
}


