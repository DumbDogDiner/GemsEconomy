package vault

import StickyWallet
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import utils.ServerUtils
import java.lang.Exception

class VaultManager(private val plugin: StickyWallet) {

    private var hook: VaultHook? = null

    fun hook() {
        try {
            if (this.hook == null) this.hook = VaultHook()

            if (plugin.currencyManager.getDefaultCurrency() == null) {
                ServerUtils.log("No default currency found; Vault linking has been disabled")
                return
            }

            val sm = Bukkit.getServicesManager()
            sm.register(Economy::class.java, this.hook!!, plugin, ServicePriority.Highest)

            ServerUtils.log("Vault linking has been enabled")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun unhook() {
        val sm = Bukkit.getServicesManager()
        if (this.hook != null) {
            sm.unregister(Economy::class.java, this.hook!!)
            this.hook = null
        }
    }

}