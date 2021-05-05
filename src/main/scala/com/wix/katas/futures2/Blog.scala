package com.wix.katas.futures2

import scala.concurrent.Future

object Blog {

  type BlogId = Int

  type PostId = Int

  case class BlogPost(postId: PostId, author: String, content: String)

  case class PostNotFound(postId: PostId) extends Exception(s"post [$postId] not found")
}

trait Blog {

  import Blog._

  def getPost(postId: PostId): Future[BlogPost]

  def getRelatedPosts(postId: PostId): Future[List[BlogPost]]
}