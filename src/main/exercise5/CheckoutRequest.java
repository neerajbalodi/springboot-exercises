package com.labs.systemdesign.exercise08saga;

import java.math.BigDecimal;

public record CheckoutRequest(String sku, int qty, String card, BigDecimal total) {}
