package com.spring.gates

import com.spring.parking.ParkingLot
import com.spring.ticket.Ticket
import com.spring.ticket.TicketStatus
import com.spring.vehicle.Vehicle

class EntryGate {
    private val parkingLot: ParkingLot
    private var ticketCounter = 0

    constructor(parkingLot: ParkingLot) {
        this.parkingLot = parkingLot
        resetTicketCounter()
    }

    private fun resetTicketCounter() {
        ticketCounter = 0
    }

    private fun getNextTicketId() : Int {
        ticketCounter += 1
        return ticketCounter
    }

    fun processVehicleEntry(vehicle: Vehicle): Result<Ticket> {
        val parkingResult = parkingLot.parkVehicle(vehicle)

        return if (parkingResult.isSuccess) {
            val spotData = parkingResult.getOrNull()
            if(spotData == null) return Result.failure(Exception("No spot available"))
            val ticket = Ticket(
                ticketId = getNextTicketId(),
                floor = spotData.floor,
                vehicle = vehicle,
                spot = spotData.parkingSpot,
                status = TicketStatus.ACTIVE,
                entryTimeInMillis = System.currentTimeMillis()
            )
            Result.success(value = ticket)
        } else {
            Result.failure(parkingResult.exceptionOrNull() ?: Exception("Parking failed"))
        }
    }
}