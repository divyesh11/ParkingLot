package com.spring.parking

import com.spring.parkingspot.ParkingSpot
import com.spring.vehicle.Vehicle

class ParkingFloor {
    private val floor : Int
    private val parkingSpots: List<ParkingSpot>

    constructor(floor : Int, parkingSpots: List<ParkingSpot>) {
        this.floor = floor
        this.parkingSpots = parkingSpots
    }

    fun getFloor() : Int {
        return floor
    }

    fun findAvailableSpot(vehicle: Vehicle): ParkingSpot? {
        val spot = parkingSpots.find { it.canPark(vehicle) }
        return spot
    }
}