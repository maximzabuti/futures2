package com.wix.katas.futures2

import com.wix.katas.futures2.Blog.{BlogPost, PostId}

import scala.concurrent.{ExecutionContext, Future}

trait FutureKata1 extends Blog {

  // run "getPost" and "getRelatedPosts" in parallel;
  // return the result of "getPost" and
  // either the result of "getRelatedPosts" if it has completed successfully before "getPost" or empty list otherwise
  def getPostAndRelatedPosts(postId: PostId)
                            (implicit ec: ExecutionContext): Future[(BlogPost, List[BlogPost])] = {
    val relatedPostsFuture = getRelatedPosts(postId)
    getPost(postId).map(_ -> relatedPostsFuture.value.flatMap(_.toOption).getOrElse(Nil))
  }
}
