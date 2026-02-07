package com.spring

import com.spring.gates.EntryGate
import com.spring.gates.ExitGate
import com.spring.parking.ParkingFloor
import com.spring.parking.ParkingLot
import com.spring.parkingspot.FourWheelerParkingSpot
import com.spring.parkingspot.ParkingSpot
import com.spring.parkingspot.TwoWheelerParkingSpot
import com.spring.pricing.TimeBasedPricingStrategy
import com.spring.vehicle.FourWheelerVehicle
import com.spring.vehicle.TwoWheelerVehicle

fun main() {
    println("Starting Parking Lot System Test...")

    // 1. Setup Parking Lot
    // Create spots for Floor 1
    val floor1Spots = ArrayList<ParkingSpot>()
    for (i in 1..4) {
        floor1Spots.add(TwoWheelerParkingSpot(100 + i))
    }
    for (i in 1..5) {
        floor1Spots.add(FourWheelerParkingSpot(200 + i))
    }
    val floor1 = ParkingFloor(1, floor1Spots)

    // Create spots for Floor 2
    val floor2Spots = ArrayList<ParkingSpot>()
    for (i in 1..5) {
        floor2Spots.add(TwoWheelerParkingSpot(300 + i))
    }
    val floor2 = ParkingFloor(2, floor2Spots)

    val floors = listOf(floor1, floor2)

    val parkingLot = ParkingLot(floors)

    // 2. Setup Gates
    val entryGate = EntryGate(parkingLot)
    val exitGate = ExitGate(parkingLot, TimeBasedPricingStrategy())

    // 3. Test Cases

    // Test Case 1: Park a Two Wheeler
    println("\n--- Test Case 1: Park a Two Wheeler ---")
    val bike1 = TwoWheelerVehicle()
    val ticketResult1 = entryGate.processVehicleEntry(bike1)
    val ticket1 = ticketResult1.getOrNull()
    if (ticketResult1.isSuccess && ticket1 != null) {
        println("Bike 1 parked successfully. Ticket ID: ${ticket1.ticketId}, Floor: ${ticket1.floor}")
    } else {
        println("Failed to park Bike 1: ${ticketResult1.exceptionOrNull()?.message}")
    }

    // Test Case 2: Park a Four Wheeler
    println("\n--- Test Case 2: Park a Four Wheeler ---")
    val car1 = FourWheelerVehicle()
    val ticketResult2 = entryGate.processVehicleEntry(car1)
    val ticket2 = ticketResult2.getOrNull()
    if (ticketResult2.isSuccess && ticket2 != null) {
        println("Car 1 parked successfully. Ticket ID: ${ticket2.ticketId}, Floor: ${ticket2.floor}")
    } else {
        println("Failed to park Car 1: ${ticketResult2.exceptionOrNull()?.message}")
    }

    // Test Case 3: Unpark Bike 1
    println("\n--- Test Case 3: Unpark Bike 1 ---")
    if (ticket1 != null) {
        // Simulate some time passing
        try {
            Thread.sleep(1001)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val fee1 = exitGate.processVehicleExit(ticket1)
        println("Bike 1 unparked. Fee: $fee1")
    }

    // Test Case 4: Fill up Two Wheeler spots on Floor 1 and check overflow to Floor 2
    println("\n--- Test Case 4: Fill up Two Wheeler spots ---")
    // We already parked 1 bike (bike1) on Floor 1.
    // Floor 1 has 5 spots. So 4 more should fill it.
    for (i in 0 until 4) {
        val bike = TwoWheelerVehicle()
        val res = entryGate.processVehicleEntry(bike)
        if (res.isSuccess) {
            println("Bike parked on Floor: ${res.getOrNull()?.floor}")
        } else {
            println("Failed to park bike: ${res.exceptionOrNull()?.message}")
        }
    }

    // Now park one more, it should go to Floor 2
    println("Parking one more bike (should be on Floor 2)...")
    val overflowBike = TwoWheelerVehicle()
    val overflowRes = entryGate.processVehicleEntry(overflowBike)
    if (overflowRes.isSuccess) {
        println("Overflow Bike parked on Floor: ${overflowRes.getOrNull()?.floor}")
    } else {
        println("Failed to park overflow bike: ${overflowRes.exceptionOrNull()?.message}")
    }

    println("\nParking Lot System Test Completed.")
}