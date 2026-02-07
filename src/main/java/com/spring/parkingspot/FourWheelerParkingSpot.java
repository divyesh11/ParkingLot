package com.spring.parkingspot;

import com.spring.vehicle.VehicleType;
import com.spring.vehicle.Vehicle;
import org.jetbrains.annotations.NotNull;

public class FourWheelerParkingSpot extends ParkingSpot {

    public FourWheelerParkingSpot(int spotId) {
        super(spotId);
    }

    public FourWheelerParkingSpot(int spotId, ParkingState parkingState) {
        super(spotId, parkingState);
    }

    @Override
    public boolean canFit(@NotNull Vehicle vehicle) {
        return vehicle.getVehicleType() == VehicleType.FOUR_WHEELER;
    }
}