package com.example.myapp1.arch

fun main() {
    println("Welcome to SOLID World!!")
    //Composition of
    val car = Car()
    val jeep = Jeep()
    val subCar = SubCar()
    val mode = Forward()
    val mode2 = Reverse()
    print("${car.make} - ${car.yom} - is ").also { mode.drive() }
    print("${car.make} - ${car.yom} - is ").also { mode2.drive() }
    washVehicleLiskov(car = subCar)
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
open class Car {
    open var make: String = "Honda"
    open var yom: Int = 2016
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

//LISKOV, SUBSTITUTION Principle
fun washVehicle(car: Car) {
    if (car is Jeep) {
        println("washing Car with-> ${car.numberOfTire} tires")
    } else {
        println("washing Car of -> ${car.yom} made")
    }
}

//Here Jeep class is not a substitution for Car since it has a different member variable.
class Jeep : Car() {
    override var make: String = "Jeep"
    override var yom: Int = 2017
    var numberOfTire: Int = 4
}

//this new class is adhering the Liskov's Substitution Principle
class SubCar : Car() {
    override var make: String = "Hyndai"
    override var yom: Int = 2016
}

fun washVehicleLiskov(car: Car) {
    println("washing ${car.make} of -> ${car.yom} made")
}

interface ServiceCar {
    //payment for different Services.
    fun payService()

    //regular low cost Services
    fun washService()
    fun vacuumService()

    //regular must have Service
    fun fillGas()
    fun fillAir()

    //yearly Services
    fun tireExchangeService()
    fun generalService()
}
//Here the functions are LOW cohesive
class CarYearlyServiceDue : ServiceCar {
    override fun payService() { println("Payment Done") }
    override fun washService() { println("N/A") }
    override fun vacuumService() { println("N/A") }
    override fun fillGas() { println("N/A") }
    override fun fillAir() { println("N/A") }
    override fun tireExchangeService() { println("Seasonal Tire Change Done") }
    override fun generalService() { println("General Service Done") }
}