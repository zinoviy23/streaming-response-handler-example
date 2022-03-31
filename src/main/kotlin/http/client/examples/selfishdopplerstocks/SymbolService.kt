package http.client.examples.selfishdopplerstocks

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.MathContext
import java.text.MessageFormat
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

private val GOLD_SYMBOL = Symbol(
    "GOLD",
    "Gold price per gram on market"
)

@Service
class SymbolService {
    fun findSymbolSourceByName(symbolName: String): SymbolSource? {
        if (symbolName == "GOLD") return SymbolSource(GOLD_SYMBOL)
        return null
    }
}

private val mathContext = MathContext(2)
private val priceFormat = NumberFormat.getCurrencyInstance(Locale.US)

class SymbolSource(private val symbol: Symbol) {
    val updates: Flow<SymbolStatus>
        get() = flow {
            var price = getPrice()
            emit(SymbolStatus(symbol, price, price.presentation))
            while (true) {
                delay(1.seconds)
                price += getDelta()
                emit(SymbolStatus(symbol, price, price.presentation))
            }
        }

    private val BigDecimal.presentation: String
        get() = priceFormat.format(this)

    private fun getPrice(): BigDecimal {
        return BigDecimal(Random.nextDouble(40.0, 45.0), mathContext)
    }

    private fun getDelta(): BigDecimal {
        return BigDecimal(Random.nextDouble(-3.0, +3.0), mathContext)
    }
}