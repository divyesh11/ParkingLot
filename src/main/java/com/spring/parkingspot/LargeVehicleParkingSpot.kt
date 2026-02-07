package com.spring.parkingspot

import com.spring.vehicle.VehicleType

class LargeVehicleParkingSpot : ParkingSpot {
    constructor(spotId: Int) : super(spotId)
    constructor(spotId: Int, parkingState: ParkingState) : super(spotId, parkingState)

    override fun canFit(vehicle: com.spring.vehicle.Vehicle): Boolean {
        return vehicle.getVehicleType() == VehicleType.LARGE
    }
}