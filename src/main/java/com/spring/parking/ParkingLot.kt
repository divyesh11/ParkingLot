package com.spring.parking

import com.spring.ticket.Ticket
import com.spring.ticket.TicketStatus
import com.spring.vehicle.Vehicle

class ParkingLot {
    private val floors: List<ParkingFloor>
    private var ticketCounter = 0

    constructor(floors: List<ParkingFloor>) {
        this.floors = floors
    }

    fun getNextTicketId() : Int {
        ticketCounter += 1
        return ticketCounter
    }

    fun parkVehicle(vehicle: Vehicle): ParkingSpotData {
        for (floor in floors) {
            val spot = floor.findAvailableSpot(vehicle)
            if(spot == null) {
                continue
            }
            spot.park(vehicle)
            return ParkingSpotData(parkingSpot = spot, floor = floor.getFloor())
        }
        throw Exception("No spot available")
    }

    fun unparkVehicle(ticket: Ticket): Ticket {
        require(ticket.status == TicketStatus.ACTIVE) { "Ticket is not active" }
        ticket.spot.unparkVehicle()
        return ticket.copy(
            status = TicketStatus.CLOSED,
            exitTimeInMillis = System.currentTimeMillis()
        )
    }
}