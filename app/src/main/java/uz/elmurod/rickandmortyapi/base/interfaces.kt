package uz.elmurod.rickandmortyapi.base

fun interface OnActionListener<Data> {
    fun onClick(data: Data)
}