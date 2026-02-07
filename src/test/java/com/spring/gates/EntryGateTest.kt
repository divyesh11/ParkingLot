package com.spring.gates

import com.spring.parking.ParkingFloor
import com.spring.parking.ParkingLot
import com.spring.parkingspot.TwoWheelerParkingSpot
import com.spring.vehicle.TwoWheelerVehicle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EntryGateTest {

    private lateinit var entryGate: EntryGate
    private lateinit var parkingLot: ParkingLot

    @BeforeEach
    fun setUp() {
        val spots = listOf(TwoWheelerParkingSpot(1))
        val floor = ParkingFloor(1, spots)
        parkingLot = ParkingLot(listOf(floor))
        entryGate = EntryGate(parkingLot)
    }

    @Test
    fun `should generate ticket when parking is successful`() {
        val vehicle = TwoWheelerVehicle()
        val result = entryGate.processVehicleEntry(vehicle)
        
        assertTrue(result.isSuccess)
        val ticket = result.getOrNull()
        assertNotNull(ticket)
        assertEquals(1, ticket?.ticketId)
        assertEquals(com.spring.ticket.TicketStatus.ACTIVE, ticket?.status)
    }

    @Test
    fun `should increment ticket id`() {
        entryGate.processVehicleEntry(TwoWheelerVehicle())
        
        // We need to free up space or have more space to park another one.
        // But here we only have 1 spot. So first parking succeeds.
        // To test increment, we need more spots or unpark.
        // Let's recreate setup with more spots for this test or just use a new setup.
        val spots = listOf(TwoWheelerParkingSpot(1), TwoWheelerParkingSpot(2))
        val floor = ParkingFloor(1, spots)
        val pl = ParkingLot(listOf(floor))
        val gate = EntryGate(pl)
        
        val t1 = gate.processVehicleEntry(TwoWheelerVehicle()).getOrNull()
        val t2 = gate.processVehicleEntry(TwoWheelerVehicle()).getOrNull()
        
        assertEquals(1, t1?.ticketId)
        assertEquals(2, t2?.ticketId)
    }

    @Test
    fun `should return failure when parking lot is full`() {
        // Park first vehicle
        entryGate.processVehicleEntry(TwoWheelerVehicle())
        
        // Try to park second vehicle (only 1 spot available)
        val result = entryGate.processVehicleEntry(TwoWheelerVehicle())
        
        assertTrue(result.isFailure)
        assertEquals("No spot available", result.exceptionOrNull()?.message)
    }
}