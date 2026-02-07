package com.spring.parking

import com.spring.parkingspot.FourWheelerParkingSpot
import com.spring.parkingspot.ParkingSpot
import com.spring.parkingspot.TwoWheelerParkingSpot
import com.spring.vehicle.FourWheelerVehicle
import com.spring.vehicle.TwoWheelerVehicle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ParkingLotTest {

    private lateinit var parkingLot: ParkingLot
    private lateinit var floor1: ParkingFloor
    private lateinit var floor2: ParkingFloor

    @BeforeEach
    fun setUp() {
        val floor1Spots = ArrayList<ParkingSpot>()
        for (i in 1..2) {
            floor1Spots.add(TwoWheelerParkingSpot(100 + i))
        }
        for (i in 1..2) {
            floor1Spots.add(FourWheelerParkingSpot(200 + i))
        }
        floor1 = ParkingFloor(1, floor1Spots)

        val floor2Spots = ArrayList<ParkingSpot>()
        for (i in 1..2) {
            floor2Spots.add(TwoWheelerParkingSpot(300 + i))
        }
        floor2 = ParkingFloor(2, floor2Spots)

        parkingLot = ParkingLot(listOf(floor1, floor2))
    }

    @Test
    fun `should park two wheeler successfully`() {
        val vehicle = TwoWheelerVehicle()
        val result = parkingLot.parkVehicle(vehicle)
        
        assertTrue(result.isSuccess)
        val spotData = result.getOrNull()
        assertNotNull(spotData)
        assertEquals(1, spotData?.floor)
    }

    @Test
    fun `should park four wheeler successfully`() {
        val vehicle = FourWheelerVehicle()
        val result = parkingLot.parkVehicle(vehicle)
        
        assertTrue(result.isSuccess)
        val spotData = result.getOrNull()
        assertNotNull(spotData)
        assertEquals(1, spotData?.floor)
    }

    @Test
    fun `should fail when no spot available`() {
        // Fill up all 4 wheeler spots (2 spots)
        parkingLot.parkVehicle(FourWheelerVehicle())
        parkingLot.parkVehicle(FourWheelerVehicle())
        
        // Try to park 3rd car
        val result = parkingLot.parkVehicle(FourWheelerVehicle())
        assertTrue(result.isFailure)
        assertEquals("No spot available", result.exceptionOrNull()?.message)
    }

    @Test
    fun `should park on second floor when first floor is full`() {
        // Fill up floor 1 two wheeler spots (2 spots)
        parkingLot.parkVehicle(TwoWheelerVehicle())
        parkingLot.parkVehicle(TwoWheelerVehicle())
        
        // Park on floor 2
        val result = parkingLot.parkVehicle(TwoWheelerVehicle())
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.floor)
    }

    @Test
    fun `should unpark vehicle successfully`() {
        val vehicle = TwoWheelerVehicle()
        val result = parkingLot.parkVehicle(vehicle)
        val spotData = result.getOrNull()
        
        // Create a dummy ticket for unparking (since ParkingLot.unparkVehicle takes a Ticket)
        // In a real integration test we would get this from EntryGate, but here we test ParkingLot directly
        // We need to mock or create a ticket. Since Ticket is a data class we can create it.
        // However, ParkingLot.unparkVehicle relies on ticket.spot.unparkVehicle().
        // So we need to pass the ticket with the correct spot.
        
        val ticket = com.spring.ticket.Ticket(
            ticketId = 1,
            floor = spotData!!.floor,
            vehicle = vehicle,
            spot = spotData.parkingSpot,
            status = com.spring.ticket.TicketStatus.ACTIVE,
            entryTimeInMillis = System.currentTimeMillis()
        )

        val closedTicket = parkingLot.unparkVehicle(ticket)
        
        assertEquals(com.spring.ticket.TicketStatus.CLOSED, closedTicket.status)
        assertTrue(closedTicket.exitTimeInMillis > 0)
        
        // Verify spot is free again
        assertTrue(spotData.parkingSpot.canPark(vehicle))
    }
}