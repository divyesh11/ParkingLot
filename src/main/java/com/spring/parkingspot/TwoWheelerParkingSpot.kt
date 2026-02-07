package com.spring.parkingspot

import com.spring.vehicle.VehicleType
import com.spring.vehicle.Vehicle

class TwoWheelerParkingSpot : ParkingSpot {
    constructor(spotId: Int) : super(spotId)
    constructor(spotId: Int, parkingState: ParkingState) : super(spotId, parkingState)

    override fun canFit(vehicle: Vehicle): Boolean {
        return vehicle.getVehicleType() == VehicleType.TWO_WHEELER
    }
}