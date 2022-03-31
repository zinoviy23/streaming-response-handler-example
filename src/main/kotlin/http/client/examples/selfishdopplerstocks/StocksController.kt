package http.client.examples.selfishdopplerstocks

import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stocks")
class StocksController(
    private val symbolService: SymbolService,
) {
    @GetMapping("subscribe", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun subscribe(@RequestParam symbol: String): Flow<SymbolStatus> {
        val source = requireNotNull(symbolService.findSymbolSourceByName(symbol)) { "Unknown symbol '$symbol'" }
        return source.updates
    }
}