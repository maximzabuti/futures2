package com.wix.katas.futures2

import scala.concurrent.Future

class FakeBlog extends Blog {

  import Blog._

  protected final val post1 = BlogPost(1, "author1", "content1")
  protected final val post2 = BlogPost(2, "author2", "content2")
  protected final val post3 = BlogPost(3, "author3", "content3")
  protected final val post4 = BlogPost(4, "author4", "content4")

  protected final val blogPosts = Map(1 -> post1, 2 -> post2, 3 -> post3, 4 -> post4)

  protected final val relatedPosts = Map(1 -> List(2, 3), 2 -> List(1), 3 -> List(1), 4 -> Nil)

  override def getPost(postId: PostId): Future[BlogPost] = blogPosts.get(postId) match {
    case Some(post) => Future.successful(post)
    case None => Future.failed(PostNotFound(postId))
  }

  override def getRelatedPosts(postId: PostId): Future[List[PostId]] =
    Future.successful(relatedPosts.getOrElse(postId, Nil))
}