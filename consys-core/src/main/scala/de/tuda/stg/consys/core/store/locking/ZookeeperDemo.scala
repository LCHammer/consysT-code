package de.tuda.stg.consys.core.store.locking

import java.util.concurrent.CountDownLatch

import de.tuda.stg.consys.core.store.cassandra.CassandraStore

import scala.concurrent.duration.Duration

/**
 * Created on 07.01.20.
 *
 * @author Mirko Köhler
 */
object ZookeeperDemo {

	val store1 = CassandraStore.fromAddress("127.0.0.1", 9042, 2181, withTimeout = Duration(60, "s"))
	val store2 = CassandraStore.fromAddress("127.0.0.2", 9042, 2182, withTimeout = Duration(60, "s"))
	val store3 = CassandraStore.fromAddress("127.0.0.3", 9042, 2183, withTimeout = Duration(60, "s"))





	def main(args : Array[String]) : Unit = {

		val latch1 = new CountDownLatch(1)
		val latch2 = new CountDownLatch(1)
		val latch3 = new CountDownLatch(1)


//		new Thread(new Runnable {
//			override def run() : Unit = {
//				try {
//
//
//					println("1 start")
//					val client1 = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy)
//					client1.start()
//					client1.create().forPath("/consys/locks")
//					println("1 started")
//					val lock = new InterProcessMutex(client1, "/consys/locks")
//					println("1 try acquire")
//					lock.acquire()
//					println("1 acquired")
//					Thread.sleep(1000)
//					latch1.countDown()
//					Thread.sleep(5000)
//					lock.release()
//					println("1 released")
//					Thread.sleep(1000)
//					latch3.countDown()
//					client1.close()
//				} catch {
//					case x => x.printStackTrace()
//				}
//			}
//		}).start()
//
//
//		new Thread(new Runnable {
//			override def run() : Unit = {
//				println("2 start")
//				val client2 = CuratorFrameworkFactory.newClient("127.0.0.2:2182", retryPolicy)
//				client2.start()
//				println("2 started")
//				val lock = new InterProcessMutex(client2, "/consys/locks")
//
//				latch1.await()
//				println("2 try acquire")
//				lock.acquire()
//				println("2 acquired")
//				latch3.await()
//			}
//		}).start()

		Thread.sleep(100000)

	}


}