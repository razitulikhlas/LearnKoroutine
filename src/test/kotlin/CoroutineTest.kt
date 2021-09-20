import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class CoroutineTest {
    @Test
    fun test(){
        GlobalScope.launch {
            delay(2000)
            println("hello ${Date()}")
        }
        runBlocking {
            delay(2000)
        }
        println("Stop ${Date()}")
    }
}