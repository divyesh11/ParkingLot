package com.spring.ticket

import com.spring.parkingspot.ParkingSpot
import com.spring.vehicle.Vehicle

data class Ticket(
    val ticketId: Int,
    val floor : Int,
    val vehicle: Vehicle,
    val spot : ParkingSpot,
    val status : TicketStatus,
    val entryTimeInMillis : Long,
    val exitTimeInMillis : Long = 0L
)