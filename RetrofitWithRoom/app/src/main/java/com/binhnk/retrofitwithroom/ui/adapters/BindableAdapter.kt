package com.binhnk.retrofitwithroom.ui.adapters

interface BindableAdapter<T> {
    fun setData(items: List<T>)
    fun changedPositions(positions: Set<Int>)
}