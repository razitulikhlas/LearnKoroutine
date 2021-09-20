import kotlinx.coroutines.*
import org.junit.Test
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CoroutineDispatcherTest {

    @Test
    fun testDispatcher(){
        runBlocking {
            println("run blocking ${Thread.currentThread().name}")
            val job1 = GlobalScope.launch(Dispatchers.Default) { println("job 1 ${Thread.currentThread().name}") }
            val job2 = GlobalScope.launch(Dispatchers.IO) { println("job 2 ${Thread.currentThread().name}") }

            joinAll(job1,job2)
        }
    }

    @Test
    fun testDispatcherConfineAndUnconfined(){
        runBlocking {
            println("run blocking ${Thread.currentThread().name}")
            GlobalScope.launch(Dispatchers.Unconfined) {
                println("job unconfined ${Thread.currentThread().name}")
                delay(1000)
                println("job unconfined ${Thread.currentThread().name}")
                delay(1000)
                println("job unconfined ${Thread.currentThread().name}")
            }
            GlobalScope.launch() {
                println("job confined ${Thread.currentThread().name}")
                delay(1000)
                println("job confined ${Thread.currentThread().name}")
                delay(1000)
                println("job confined ${Thread.currentThread().name}")
            }

            delay(2000)
        }
    }


    @Test
    fun testExecutorService(){
        val dispatcherService = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val dispatcherWeb = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        runBlocking {
            println("run blocking ${Thread.currentThread().name}")
            GlobalScope.launch(dispatcherService) {
                println("job1 unconfined ${Thread.currentThread().name}")
                delay(1000)
                println("job unconfined ${Thread.currentThread().name}")
                delay(1000)
                println("job unconfined ${Thread.currentThread().name}")
            }
            GlobalScope.launch(dispatcherWeb) {
                println("job confined ${Thread.currentThread().name}")
                delay(1000)
                println("job confined ${Thread.currentThread().name}")
                delay(1000)
                println("job confined ${Thread.currentThread().name}")
            }

            delay(3000)
        }
    }

    @Test
    fun testWithContext(){
        runBlocking {
            val makeDispatcher  = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
            val job =GlobalScope.launch(Dispatchers.Default) {
                println("Hello1 ${Date()} ${Thread.currentThread().name}")
                withContext(makeDispatcher){
                    println("Hello2 ${Date()} ${Thread.currentThread().name}")
                    delay(5000)
                }
                withContext(Dispatchers.IO){
                    println("Hello3 ${Date()} ${Thread.currentThread().name}")
                }

            }
            job.join()
        }
    }

    @Test
    fun testCancelLabel(){
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("start ${Thread.currentThread().name}")
                    delay(1000)
                    println("stop ${Thread.currentThread().name}")
                }finally {
                    println(isActive)
                    delay(1000)
                    println("Finally")
                }
            }

            job.cancelAndJoin()
        }
    }

    @Test
    fun testNotCancelLabel(){
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("start ${Thread.currentThread().name}")
                    delay(1000)
                    println("stop ${Thread.currentThread().name}")
                }finally {
                    withContext(NonCancellable){
                        println(isActive)
                        delay(1000)
                        println("Finally")
                    }
                }
            }

            job.cancelAndJoin()
        }
    }

}