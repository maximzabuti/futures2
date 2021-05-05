package com.wix.katas.futures2

import java.util.{Timer => JTimer, TimerTask => JTimerTask}

import scala.concurrent.duration._
import scala.concurrent.{Future, Promise}

object FutureKata2 {

  // return a new Future[Unit] that completes successfully after "delay"
  def createDelayedFuture(delay: FiniteDuration)(implicit timer: JTimer): Future[Unit] = {
    val promise = Promise[Unit]()
    val task = new JTimerTask {
      override def run(): Unit = promise.success(())
    }
    timer.schedule(task, delay.toMillis)
    promise.future
  }
}