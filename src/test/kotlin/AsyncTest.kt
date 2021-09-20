import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class AsyncTest {
    suspend fun getFoo(): Int {
        delay(1000)
        return 4
    }

    suspend fun getBar(): Int {
        delay(1000)
        return 10
    }
    suspend fun getFoo1(): Int {
        delay(1000)
        return 14
    }

    suspend fun getBar1(): Int {
        delay(1000)
        return 5
    }


    @Test
    fun sequentialTest(){
        runBlocking {
            val time = measureTimeMillis {
                val foo = getFoo()
                val bar = getBar()
                println("Total ${foo + bar}")
            }
            println("time $time")
        }
    }
    @Test
    fun asyncTest(){
        runBlocking {
            val time = measureTimeMillis {
                val foo = GlobalScope.async { getFoo() }
                val bar = GlobalScope.async { getBar() }
                println("total ${foo.await() + bar.await()}")
            }
            println("time $time")
        }
    }

    @Test
    fun testAwaitAll(){
        runBlocking {
            val time = measureTimeMillis {
                val foo = GlobalScope.async { getFoo() }
                val bar = GlobalScope.async { getBar() }
                val foo1 = GlobalScope.async { getFoo1() }
                val bar1 = GlobalScope.async { getBar1() }
                println("total ${awaitAll(foo,bar,foo1,bar1).sum()}")
            }
            println("time $time")
        }
    }
}