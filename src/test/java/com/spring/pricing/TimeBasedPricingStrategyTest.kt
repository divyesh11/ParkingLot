package com.spring.pricing

import com.spring.parkingspot.TwoWheelerParkingSpot
import com.spring.ticket.Ticket
import com.spring.ticket.TicketStatus
import com.spring.vehicle.TwoWheelerVehicle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeBasedPricingStrategyTest {

    @Test
    fun `should calculate fee correctly based on duration`() {
        val strategy = TimeBasedPricingStrategy()
        val entryTime = System.currentTimeMillis() - (60 * 60 * 1000) // 1 hour ago
        val exitTime = System.currentTimeMillis()
        
        val ticket = Ticket(
            ticketId = 1,
            floor = 1,
            vehicle = TwoWheelerVehicle(),
            spot = TwoWheelerParkingSpot(1),
            status = TicketStatus.CLOSED,
            entryTimeInMillis = entryTime,
            exitTimeInMillis = exitTime
        )

        val fee = strategy.calculateFee(ticket)
        
        // 60 minutes * 2.0 = 120.0
        // Allow small delta for time precision
        assertEquals(7200.0, fee, 1.0)
    }
}