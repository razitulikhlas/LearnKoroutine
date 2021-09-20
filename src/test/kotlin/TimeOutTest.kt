import kotlinx.coroutines.*
import org.junit.Test
import java.util.*

class TimeOutTest {

    @Test
    fun testTimeOut(){
        runBlocking {
            val job = GlobalScope.launch {
                withTimeout(5_000){
                    repeat(1000){
                        delay(1_000)
                        println("start ${Date()}")
                    }
                }
                println("Finish")

            }
            job.join()
        }
    }

    @Test
    fun testTimeOutOrNull(){
        runBlocking {
            val job = GlobalScope.launch {
                val data = withTimeoutOrNull(5_000){
                    repeat(1000){
                        delay(1_000)
                        println("start ${Date()}")
                    }
                    "razit"
                }
                println(data)
                println("Finish")

            }
            job.join()
        }
    }
}