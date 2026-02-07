package com.spring.gates

import com.spring.parking.ParkingFloor
import com.spring.parking.ParkingLot
import com.spring.parkingspot.TwoWheelerParkingSpot
import com.spring.pricing.PricingStrategy
import com.spring.ticket.Ticket
import com.spring.ticket.TicketStatus
import com.spring.vehicle.TwoWheelerVehicle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExitGateTest {

    private lateinit var exitGate: ExitGate
    private lateinit var parkingLot: ParkingLot
    private lateinit var pricingStrategy: PricingStrategy

    @BeforeEach
    fun setUp() {
        val spot = TwoWheelerParkingSpot(1)
        val floor = ParkingFloor(1, listOf(spot))
        parkingLot = ParkingLot(listOf(floor))
        
        // Mock pricing strategy or use a simple one
        pricingStrategy = object : PricingStrategy {
            override fun calculateFee(ticket: Ticket): Double {
                return 10.0
            }
        }
        
        exitGate = ExitGate(parkingLot, pricingStrategy)
    }

    @Test
    fun `should process exit and return fee`() {
        val vehicle = TwoWheelerVehicle()
        // Manually park to get a valid spot state
        val parkResult = parkingLot.parkVehicle(vehicle)
        val spotData = parkResult.getOrNull()!!
        
        val ticket = Ticket(
            ticketId = 1,
            floor = 1,
            vehicle = vehicle,
            spot = spotData.parkingSpot,
            status = TicketStatus.ACTIVE,
            entryTimeInMillis = System.currentTimeMillis()
        )

        val fee = exitGate.processVehicleExit(ticket)
        
        assertEquals(10.0, fee)
        // Verify spot is free
        assert(spotData.parkingSpot.canPark(vehicle))
    }
}