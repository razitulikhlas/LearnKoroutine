import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class SequentialSuspendTest {

    suspend fun getFoo(): Int {
        delay(1000)
        return 4
    }

    suspend fun getBar(): Int {
        delay(1000)
        return 10
    }

    @Test
    fun testSequential() {
        runBlocking {
            val time = measureTimeMillis {
                getBar()
                getFoo()
            }
            println("time ${time}")
        }
    }

    @Test
    fun testSequentialCoroutine() {
        runBlocking {
            val job = GlobalScope.launch {
                val time = measureTimeMillis {
                    getBar()
                    getFoo()
                }
                println("time ${time}")
            }
            job.join()
        }
    }

    @Test
    fun testConcurrent() {
        runBlocking {
            val time = measureTimeMillis {
                val job1 = GlobalScope.launch { getFoo() }
                val job2 = GlobalScope.launch { getBar() }
                joinAll(job1, job2)
            }
            println("time $time")
        }
    }

    @Test
    fun testConcurrentReturn(){
        runBlocking {
            val time  = measureTimeMillis {
                val bar = GlobalScope.async { getBar() }
                val foo = GlobalScope.async { getFoo() }
                val result = bar.await() + foo.await()
                println("Result ${result}")
            }
            println("time  $time")
        }
    }
}