import kotlinx.coroutines.*
import org.junit.Test
import java.util.*

class JobTest {

//    Run blocking tidak akan memblock GlobalScope.launch{}

    @Test
    fun test(){
        runBlocking {
            helloCoroutine()
            hello()
        }
    }

    suspend fun helloCoroutine(){
        GlobalScope.launch {
            delay(1000)
            println("hello coroutine ${Date()}")
        }
    }

    suspend fun hello(){
        println("Start ${Date()}")
        delay(2000)
        println("Stop ${Date()}")
    }

//    job hanya jalan ketika job start di panggil
    @Test
    fun jobLazy(){
        runBlocking {
            val job : Job = GlobalScope.launch(start = CoroutineStart.LAZY) {
//                delay(1000)
                println("Hello ${Date()}")
            }
            job.start()
            println("Start ${Date()}")
            delay(1000)
        }
    }

    @Test
    fun jobNotLazy(){
        runBlocking {
            val job : Job = GlobalScope.launch(start = CoroutineStart.LAZY) {
//                delay(1000)
                println("Hello ${Date()}")
            }
            job.start()
            println("Start ${Date()}")
            delay(1000)
        }
    }

    @Test
    fun jobJoin(){
        runBlocking {
            println("Start ${Date()}")
            join()
            println("Stop ${Date()}")
        }
    }

    suspend fun join(){
        val job : Job = GlobalScope.launch {
            delay(5000)
            println("Hello ${Date()}")
        }
        job.join()
    }

    @Test
    fun jobCancel(){
        runBlocking {
            val job : Job = GlobalScope.launch {
                delay(2000)
                println("Hello ${Date()}")
            }
            job.cancel()
            println("Start ${Date()}")
            delay(2000)
        }
    }

}