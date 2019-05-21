package com.binhnk.retrofitwithroom.ui.adapter

interface BindableAdapter<T> {
    fun setData(items: List<T>)
    fun changedPositions(positions: Set<Int>)
}