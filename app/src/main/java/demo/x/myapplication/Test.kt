package demo.x.myapplication

class Test {

    constructor(id: Int) {

    }

    constructor(id: Int, name: String) {
        val list = arrayListOf<String>()

       val boolean :Boolean= list.all { it.isNotEmpty() }
    }

    companion object {

        private const val ID = "ID"
    }


}
