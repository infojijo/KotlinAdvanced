package com.example.myapp1.arch

fun main() {
    println("Welcome to SOLID World!!")
    //Composition of
    val car = Car()
    val mode = Forward()
    val mode2 = Reverse()
    print("${car.make} - ${car.yom} - is ").also { mode.drive() }
    print("${car.make} - ${car.yom} - is ").also { mode2.drive() }
}

// not a SOLID design principle approach - NOT RECOMMENDED
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

//Car class has a DriveMode
class Car {
    var make: String = "Honda"
    var yom: Int = 2016
}

//DriveMode is implemented in Forward and Reverse Classes
interface DriveMode {
    fun drive()
}

//Individual behaviors / usecases for Forward functionalities
class Forward : DriveMode {
    override fun drive() {
        println("driving -> in DRIVE mode")
    }
}

//Individual behaviors / usecases for Reverse functionalities
class Reverse : DriveMode {
    override fun drive() {
        println("driving -> in REVERSE mode")
    }
}