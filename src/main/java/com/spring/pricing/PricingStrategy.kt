package com.spring.pricing

import com.spring.ticket.Ticket

interface PricingStrategy {
    fun calculateFee(ticket: Ticket) : Double
}