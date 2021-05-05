package com.wix.katas.futures2

import java.util.{Timer => JTimer}

import com.wix.katas.futures2.FutureKata2.createDelayedFuture
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit

import scala.concurrent.duration._

class FutureKata2Test extends SpecWithJUnit with FutureMatchers {

  implicit private val timer: JTimer = new JTimer()

  "delay completes after the deadline" >> {
    val delay = 10.millis
    val fut = createDelayedFuture(delay)
    Thread.sleep(delay.toMillis + 10)
    fut.isCompleted must beTrue
  }

  "delay does not complete before the deadline" >> {
    val delay = 100.millis
    val fut = createDelayedFuture(delay)
    fut.isCompleted must beFalse
  }
}