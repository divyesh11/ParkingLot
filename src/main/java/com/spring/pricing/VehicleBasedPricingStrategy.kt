package com.spring.pricing

import com.spring.ticket.Ticket
import com.spring.vehicle.VehicleType

class VehicleBasedPricingStrategy : PricingStrategy {
    override fun calculateFee(ticket: Ticket): Double {
        val vehicle = ticket.vehicle
        return when(vehicle.getVehicleType()) {
            VehicleType.TWO_WHEELER -> 10.0
            VehicleType.FOUR_WHEELER -> 20.0
            VehicleType.LARGE -> 40.0
        }
    }
}