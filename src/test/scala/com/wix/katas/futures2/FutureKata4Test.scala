package com.wix.katas.futures2

import java.util.{Timer => JTimer}

import com.wix.katas.futures2.Blog._
import com.wix.katas.futures2.FutureKata3.TimeoutException
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class FutureKata4Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  implicit private val timer: JTimer = new JTimer()

  class Kata4Scope extends FakeBlog with FutureKata4 with Scope

  "both getPost and getRelatedPosts complete successfully before timeout" in new Kata4Scope() {
    val expected = (post1, List(post2, post3))
    getPostAndRelatedPosts(postId = 1, timeout = 10.millis) must beEqualTo(expected).await
  }

  "both getPost and getRelatedPosts don't complete before timeout" in new Kata4Scope() {
    override def getPost(postId: PostId): Future[BlogPost] = Future.never

    override def getRelatedPosts(postId: PostId): Future[List[BlogPost]] = Future.never

    val timeout = 10.millis
    val fut = getPostAndRelatedPosts(postId = 1, timeout)
    Thread.sleep(timeout.toMillis + 10)
    fut must throwA(TimeoutException(timeout)).await
  }

  "getPost completes successfully before timeout but getRelatedPosts has not completed yet" in new Kata4Scope() {
    override def getRelatedPosts(postId: PostId): Future[List[BlogPost]] = Future.never
    val expected = (post1, Nil)
    getPostAndRelatedPosts(postId = 1, timeout = 10.millis) must beEqualTo(expected).await
  }
}
