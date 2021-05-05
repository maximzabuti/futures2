package com.wix.katas.futures2

import java.util.{Timer => JTimer}

import com.wix.katas.futures2.FutureKata2.createDelayedFuture
import com.wix.katas.futures2.FutureKata3._
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class FutureKata3Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  implicit private val timer: JTimer = new JTimer()

  "completed future with a time out" >> {
    val fut = Future.successful(0).withTimeOut(10.millis)
    fut.isCompleted must beTrue
  }

  "future with a time out completes before the timeout" >> {
    val delay = 10.millis
    val fut = createDelayedFuture(delay).withTimeOut(100.millis)
    Thread.sleep(delay.toMillis + 10)
    fut must beEqualTo(()).await
  }

  "future with a time out completes after the timeout" >> {
    val delay = 100.millis
    val timeout = 10.millis
    val fut = createDelayedFuture(delay).withTimeOut(timeout)
    Thread.sleep(delay.toMillis + 10)
    fut must throwA(TimeoutException(timeout)).await
  }
}