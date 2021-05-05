package com.wix.katas.futures2

import java.util.{Timer => JTimer}

import scala.concurrent.Future
import scala.concurrent.duration._

object FutureKata2 {

  // return a new Future[Unit] that completes successfully after "delay"
  def createDelayedFuture(delay: FiniteDuration)(implicit timer: JTimer): Future[Unit] = ???
}