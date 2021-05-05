package com.wix.katas.futures2

import java.util.{Timer => JTimer, TimerTask => JTimerTask}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}

object FutureKata3 {

  // return a new future that
  // either completes with the result of "future" if "future" completes before "timeout" or
  // fails with "TimeoutException" if "future" completes after "timeout"
  def createFutureWithTimeout[A](future: Future[A], timeout: FiniteDuration)
                                (implicit timer: JTimer, ec: ExecutionContext): Future[A] = {
    if (future.isCompleted) future
    else {
      val promise = Promise[A]()
      val task = new JTimerTask {
        override def run(): Unit = promise.tryFailure(TimeoutException(timeout))
      }
      timer.schedule(task, timeout.toMillis)
      future.onComplete(_ => task.cancel())
      promise.completeWith(future).future
    }
  }

  case class TimeoutException(timeout: FiniteDuration) extends Exception(s"timeout [$timeout] is exceeded")

  implicit class TimedOutFuture[A](future: Future[A]) {
    def withTimeOut(timeout: FiniteDuration)
                   (implicit timer: JTimer, ec: ExecutionContext): Future[A] = createFutureWithTimeout(future, timeout)
  }

}