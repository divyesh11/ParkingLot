package com.spring.parkingspot

import com.spring.vehicle.Vehicle
import com.spring.vehicle.VehicleType

class FourWheelerParkingSpot : ParkingSpot {
    constructor(spotId: Int) : super(spotId)
    constructor(spotId: Int, parkingState: ParkingState) : super(spotId, parkingState)

    override fun canFit(vehicle: Vehicle): Boolean {
        return vehicle.getVehicleType() == VehicleType.FOUR_WHEELER
    }
}