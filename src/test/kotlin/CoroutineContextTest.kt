import kotlinx.coroutines.*
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class CoroutineContextTest {

    @ExperimentalStdlibApi
    @Test
    fun testCoroutineContext(){
        runBlocking {
            val job = GlobalScope.launch {
                val context:CoroutineContext = coroutineContext
                println(context)
                println(context[Job])
                println(context[CoroutineDispatcher])
            }
            job.join()
        }
    }
}