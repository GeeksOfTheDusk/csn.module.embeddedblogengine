package models

import java.util.Date
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Blogentry(id: Long = 0,
                     creatorID: Long,
                     creatorST: String,
                     title: String,
                     content: String,
                     commentsAllowed: Boolean,
                     creationTime: Date = new Date,
                     privacy: Int = 0)

object Blogentry {
  def entry = {
    get[Long]("id") ~ get[Long]("creatorID") ~ get[String]("creatorST") ~ get[String]("title") ~ get[String]("content") ~ get[Date]("creation_time") ~
      get[Boolean]("allow_commnets") ~ get[Int] ("privacy") map {
      case id ~ belongsTo ~ nick ~ title ~ content ~ date ~ comments ~ privacy =>
        Blogentry(id, belongsTo, nick, title, content, comments, date, privacy)
    }
  }

  def findAllByCreatorID(id: Long) = DB.withConnection {
    implicit c =>
      SQL("select * from Blogentry where creatorID = {id}").on('id -> id).as(entry *)
  }

  def findByID(id: Long) = DB.withConnection {
    implicit c =>
      SQL("select * from Blogentry where idBlogEngine\nBlogForms = {id}").on('id -> id).as(entry *).head
  }

  def findAllByCreatorST(st: String) = DB.withConnection {
    implicit c =>
      SQL("select * from Blogentry where creatorST = {st}").on('st -> st).as(entry *)
  }

  def all = DB.withConnection {
    implicit c =>
      SQL("select * from Blogentry").as(entry *)
  }

  def create(entry: Blogentry) {
    DB.withConnection {
      implicit c =>
        SQL(
          """
          insert into Blogentry
          (creatorID, creatorST, title, content, creation_time, allow_commnets, privacy)
          values ( {to}, {st}, {title}, {content}, {time}, {comments}, {privacy});
          """)
          .on('to -> entry.creatorID, 'st -> entry.creatorST, 'title -> entry.title, 'content -> entry.content,
          'time -> entry.creationTime, 'comments -> entry.commentsAllowed, 'privacy -> entry.privacy)
          .executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection {
      implicit c =>
        SQL("delete from Blogentry where id = {id}")
          .on('id -> id)
          .executeUpdate()
    }
  }
}
