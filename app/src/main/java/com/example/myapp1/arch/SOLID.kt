package com.example.myapp1.arch

fun main() {
    println("Welcome to SOLID World!!")
    val dr = Car()
    print("${dr.make} - ${dr.yom} - is ").also { dr.d() }
}

open class Drive {
    fun d() {
        println("driving -> in DRIVE mode")
    }

    fun r() {
        println("driving -> in REVERSE mode")
    }

    fun n() {
        println("driving -> in NEUTRAL mode")
    }
}


class Car() : Drive() {
    var make: String = "Honda"
    var yom: Int = 2016
}

interface DriveMode {
    fun drive()
}

open class Forward : DriveMode {
    override fun drive() {
        println("from->${javaClass.name}")
    }

}

open class Reverse : DriveMode {
    override fun drive() {
        println("from->${javaClass.name}")
    }
}