package com.edgardo.a00813103_p1k_mascotas

class MacostaData {


    var listamascotas: ArrayList<Mascota> = ArrayList()

    init {
        dataList()
    }

    fun dataList(){

        listamascotas.add(Mascota("Perrito1",7,"Calle numero 1","123456",
                "test@test.com",R.drawable.perrito))
        listamascotas.add(Mascota("Perrito2",2,"Calle numero 4","123456",
                "test@test.com",R.drawable.perrito))
        listamascotas.add(Mascota("Perrito3",8,"Calle numero 3","123456",
                "test@test.com",R.drawable.perrito))

    }
}