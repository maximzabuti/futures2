package com.wix.katas.futures2

import java.util.{Timer => JTimer}

import com.wix.katas.futures2.Blog.{BlogPost, PostId}
import com.wix.katas.futures2.FutureKata3._

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{ExecutionContext, Future}

trait FutureKata4 extends Blog {

  // run "getPost" and "getRelatedPosts" in parallel;
  // return the result of "getPost" if the "timeout" did not exceed or a failure and
  // either the result of "getRelatedPosts" if it has completed successfully before "getPost" or empty list otherwise
  def getPostAndRelatedPosts(postId: PostId, timeout: FiniteDuration)
                            (implicit timer: JTimer, ec: ExecutionContext): Future[(BlogPost, List[BlogPost])] = {
    getPost(postId).withTimeOut(timeout) zip getRelatedPosts(postId).withTimeOut(timeout).recover { case _ => Nil }
  }
}
