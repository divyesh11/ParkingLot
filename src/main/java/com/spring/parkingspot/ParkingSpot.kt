package com.spring.parkingspot

import com.spring.vehicle.Vehicle

abstract class ParkingSpot {
    protected var spotId : Int
    protected var vehicle : Vehicle? = null
    protected var parkingState : ParkingState

    constructor(spotId : Int, parkingState: ParkingState) {
        this.spotId = spotId
        this.parkingState = parkingState
    }

    constructor(spotId : Int) {
        this.spotId = spotId
        this.parkingState = ParkingState.FREE
    }

    abstract fun canFit(vehicle: Vehicle) : Boolean

    open fun canPark(vehicle: Vehicle) : Boolean {
        if(this.parkingState == ParkingState.OCCUPIED) {
            return false
        }
        if(!canFit(vehicle)) {
            return false
        }
        return true
    }

    fun park(vehicle: Vehicle) : Boolean {
        if(!canPark(vehicle)) { return false }
        this.vehicle = vehicle
        this.parkingState = ParkingState.OCCUPIED
        return true
    }

    fun unparkVehicle() {
        this.vehicle = null
        this.parkingState = ParkingState.FREE
    }
}