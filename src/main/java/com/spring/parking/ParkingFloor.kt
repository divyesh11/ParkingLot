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

    fun findAvailableSpot(vehicle: Vehicle): Result<ParkingSpot> {
        val spot = parkingSpots.find { it.canPark(vehicle) }
        if(spot != null) {
            return Result.success(value = spot)
        }
        return Result.failure(Exception("No spot available"))
    }
}