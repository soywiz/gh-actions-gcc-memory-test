import kotlinx.cinterop.*
import platform.posix.*

fun main() {
    val base = arrayListOf<ByteArray>()
    for (n in 1..35) {
        println("Trying to allocate 2GB...")
        val ba = ByteArray((1.5 * 1024 * 1024 * 1024).toInt())
        ba.usePinned {
            memset(it.addressOf(0), (n  * 777777777).toInt(), ba.size.convert())
        }
        val ba2 = ByteArray((0.5 * 1024 * 1024 * 1024).toInt())
        memScoped {
            val mem = allocArray<ByteVar>(128 * 1024)
        }

        kotlin.native.internal.GC.collect()
        base.add(ba) // 1GB per ByteArray
        val totalSize = base.sumOf { it.size.toLong() }
        println("Memory: ${totalSize.toDouble() / 1024 / 1024 / 1024}gb")
    }
    println("COMPLETED")
}