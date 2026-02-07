package com.spring.pricing

import com.spring.ticket.Ticket
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TimeBasedPricingStrategy : PricingStrategy {
    override fun calculateFee(ticket: Ticket): Double {
        val entryTime = ticket.entryTimeInMillis
        val exitTime = ticket.exitTimeInMillis
        val duration = exitTime - entryTime
        return (duration.toDuration(unit = DurationUnit.MILLISECONDS).inWholeSeconds) * 2.0
    }
}