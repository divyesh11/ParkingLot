package com.spring.gates

import com.spring.parking.ParkingLot
import com.spring.ticket.Ticket
import com.spring.ticket.TicketStatus
import com.spring.vehicle.Vehicle

class EntryGate {
    private val parkingLot: ParkingLot

    constructor(parkingLot: ParkingLot) {
        this.parkingLot = parkingLot
    }

    fun processVehicleEntry(vehicle: Vehicle): Result<Ticket> {
        try {
            val spotData = parkingLot.parkVehicle(vehicle)
            val ticket = Ticket(
                ticketId = parkingLot.getNextTicketId(),
                floor = spotData.floor,
                vehicle = vehicle,
                spot = spotData.parkingSpot,
                status = TicketStatus.ACTIVE,
                entryTimeInMillis = System.currentTimeMillis()
            )
            return Result.success(ticket)
        } catch (e : Exception) {
            return Result.failure(e)
        }
    }
}