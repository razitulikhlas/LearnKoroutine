import kotlinx.coroutines.*
import org.junit.Test
import java.util.*

class CancellableCoroutineTest {

    @Test
    fun tesNotCancellable(){
        runBlocking {
            val job : Job = GlobalScope.launch {
                println("start ${Date()}")
//                delay(1000)
                Thread.sleep(2000)
                println("stop ${Date()}")
            }
            job.cancelAndJoin()
        }
    }

    @Test
    fun tesCancellable(){
        runBlocking {
            val job : Job = GlobalScope.launch {
                if(!isActive) throw CancellationException()
                println("start ${Date()}")
                ensureActive()
                if(!isActive) throw CancellationException()
                println("stop ${Date()}")
            }
           job.cancelAndJoin()
        }
    }

    @Test
    fun tesCancellableFinally(){
        runBlocking {
            val job : Job = GlobalScope.launch {
               try {
                   println("start ${Date()}")
                   delay(2000)
                   println("stop ${Date()}")
               }finally {
                   println("Finish")
               }
            }
            job.cancelAndJoin()
        }
    }
}