package com.wix.katas.futures2

import com.wix.katas.futures2.Blog._
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

import scala.concurrent.{ExecutionContext, Future}

class FutureKata1Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  class Kata1Scope extends FakeBlog with FutureKata1 with Scope

  "both getPost and getRelatedPosts complete successfully" in new Kata1Scope() {
    val expected = (post1, List(2, 3))
    getPostAndRelatedPosts(postId = 1) must beEqualTo(expected).await
  }

  "getPost completes successfully and getRelatedPosts fails" in new Kata1Scope() {
    override def getRelatedPosts(postId: PostId): Future[List[PostId]] = Future.failed(new Exception())
    val expected = (post1, Nil)
    getPostAndRelatedPosts(postId = 1) must beEqualTo(expected).await
  }

  "getPost completes successfully and getRelatedPosts still does not complete" in new Kata1Scope() {
    override def getRelatedPosts(postId: PostId): Future[List[PostId]] = Future.never
    val expected = (post1, Nil)
    getPostAndRelatedPosts(postId = 1) must beEqualTo(expected).await
  }
}
