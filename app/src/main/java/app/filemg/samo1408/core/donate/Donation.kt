package app.filemg.samo1408.core.donate

data class Wallet(val id: String, val network: String, val address: String)

object Donation {
    val wallets = listOf(
        Wallet("tron", "TRX / USDT · Tron (TRC-20)", ""),
        Wallet("bnb", "USDT · BNB Smart Chain (BEP-20)", ""),
        Wallet("ton", "TON (Gram)", ""),
    )
}
