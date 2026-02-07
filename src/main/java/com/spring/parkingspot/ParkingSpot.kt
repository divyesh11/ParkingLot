package com.spring.parkingspot

import com.spring.vehicle.Vehicle

abstract class ParkingSpot {
    private var spotId : Int
    private var vehicle : Vehicle? = null
    private var parkingState : ParkingState

    constructor(spotId : Int, parkingState: ParkingState) {
        this.spotId = spotId
        this.parkingState = parkingState
    }

    constructor(spotId : Int) {
        this.spotId = spotId
        this.parkingState = ParkingState.FREE
    }

    abstract fun canFit(vehicle: Vehicle) : Boolean

    fun canPark(vehicle: Vehicle) : Boolean {
        if(this.parkingState == ParkingState.OCCUPIED) {
            return false
        }
        if(!canFit(vehicle)) {
            return false
        }
        return true
    }

    fun park(vehicle: Vehicle) {
        require(canPark(vehicle)) { "Cannot park vehicle" }
        this.vehicle = vehicle
        this.parkingState = ParkingState.OCCUPIED
    }

    fun unparkVehicle() {
        require(parkingState == ParkingState.OCCUPIED) { "Spot already free" }
        this.vehicle = null
        this.parkingState = ParkingState.FREE
    }
}