package com.spring.parking

import com.spring.ticket.Ticket
import com.spring.ticket.TicketStatus
import com.spring.vehicle.Vehicle

class ParkingLot {
    private val floors: List<ParkingFloor>

    constructor(floors: List<ParkingFloor>) {
        this.floors = floors
    }

    fun parkVehicle(vehicle: Vehicle): Result<ParkingSpotData> {
        for (floor in floors) {
            val spotResult = floor.findAvailableSpot(vehicle)
            if (spotResult.isSuccess) {
                val spot = spotResult.getOrNull()
                if(spot == null) return Result.failure(Exception("No spot available"))
                if (spot.park(vehicle)) {
                    return Result.success(value = ParkingSpotData(parkingSpot = spot, floor = floor.getFloor()))
                }
            }
        }
        return Result.failure(Exception("No spot available"))
    }

    fun unparkVehicle(ticket: Ticket): Ticket {
        ticket.spot.unparkVehicle()
        return ticket.copy(
            status = TicketStatus.CLOSED,
            exitTimeInMillis = System.currentTimeMillis()
        )
    }
}