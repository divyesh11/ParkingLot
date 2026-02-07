package com.spring.parkingspot

import com.spring.vehicle.FourWheelerVehicle
import com.spring.vehicle.TwoWheelerVehicle
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ParkingSpotTest {

    @Test
    fun `TwoWheelerParkingSpot should accept TwoWheeler`() {
        val spot = TwoWheelerParkingSpot(1)
        val vehicle = TwoWheelerVehicle()
        
        assertTrue(spot.canFit(vehicle))
        assertTrue(spot.canPark(vehicle))
    }

    @Test
    fun `TwoWheelerParkingSpot should reject FourWheeler`() {
        val spot = TwoWheelerParkingSpot(1)
        val vehicle = FourWheelerVehicle()
        
        assertFalse(spot.canFit(vehicle))
        assertFalse(spot.canPark(vehicle))
    }

    @Test
    fun `FourWheelerParkingSpot should accept FourWheeler`() {
        val spot = FourWheelerParkingSpot(1)
        val vehicle = FourWheelerVehicle()
        
        assertTrue(spot.canFit(vehicle))
        assertTrue(spot.canPark(vehicle))
    }

    @Test
    fun `FourWheelerParkingSpot should reject TwoWheeler`() {
        val spot = FourWheelerParkingSpot(1)
        val vehicle = TwoWheelerVehicle()
        
        assertFalse(spot.canFit(vehicle))
        assertFalse(spot.canPark(vehicle))
    }

    @Test
    fun `should not allow parking if spot is occupied`() {
        val spot = TwoWheelerParkingSpot(1)
        val vehicle1 = TwoWheelerVehicle()
        val vehicle2 = TwoWheelerVehicle()
        
        spot.park(vehicle1)
        
        assertFalse(spot.canPark(vehicle2))
        spot.park(vehicle2)
    }

    @Test
    fun `should allow parking after unparking`() {
        val spot = TwoWheelerParkingSpot(1)
        val vehicle = TwoWheelerVehicle()
        
        spot.park(vehicle)
        spot.unparkVehicle()
        
        assertTrue(spot.canPark(vehicle))
        spot.park(vehicle)
    }
}