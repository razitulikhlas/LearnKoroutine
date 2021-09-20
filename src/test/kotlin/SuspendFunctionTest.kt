import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class SuspendFunctionTest {

    @Test
    fun testSuspend(){
        runBlocking {
            helloWord()
        }
    }

    suspend fun helloWord(){
        println("Helloword ${Date()} : ${Thread.currentThread().name}")
        delay(5000)
        println("Helloword ${Date()} : ${Thread.currentThread().name}")
    }
}