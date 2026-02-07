package com.spring.vehicle

class FourWheelerVehicle : Vehicle {
    override fun getVehicleType(): VehicleType {
        return VehicleType.FOUR_WHEELER
    }
}