package http.client.examples.selfishdopplerstocks

import java.math.BigDecimal

data class Symbol(
    val id: String,
    val description: String,
)

data class SymbolStatus(
    val symbol: Symbol,
    val lastPrice: BigDecimal,
    val pricePresentation: String,
)