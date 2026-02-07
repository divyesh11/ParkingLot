package com.spring.gates

import com.spring.parking.ParkingLot
import com.spring.pricing.PricingStrategy
import com.spring.ticket.Ticket
import com.spring.ticket.TicketStatus

class ExitGate {
    private val parkingLot: ParkingLot
    private val pricingStrategy: PricingStrategy

    constructor(parkingLot: ParkingLot, pricingStrategy: PricingStrategy) {
        this.parkingLot = parkingLot
        this.pricingStrategy = pricingStrategy
    }

    fun processVehicleExit(ticket: Ticket): Double {
        require(ticket.status == TicketStatus.ACTIVE) { "Ticket is not active" }
        val closedTicket = parkingLot.unparkVehicle(ticket)
        return pricingStrategy.calculateFee(closedTicket)
    }
}