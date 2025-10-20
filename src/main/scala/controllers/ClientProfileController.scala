package controllers

import cats.data.Validated.Invalid
import cats.data.Validated.Valid
import cats.effect.kernel.Async
import cats.effect.Concurrent
import cats.implicits.*
import fs2.Stream
import infrastructure.cache.*
import io.circe.syntax.EncoderOps
import io.circe.Json
import models.database.UpdateSuccess
import models.responses.*
import models.Completed
import models.Failed
import models.InProgress
import models.NotStarted
import models.Review
import models.Submitted
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.`WWW-Authenticate`
import org.http4s.syntax.all.http4sHeaderSyntax
import org.http4s.Challenge
import org.typelevel.log4cats.Logger
import scala.concurrent.duration.*
import services.ClientProfileServiceAlgebra

trait ClientProfileControllerAlgebra[F[_]] {
  def routes: HttpRoutes[F]
}

class ClientProfileControllerImpl[F[_] : Async : Concurrent : Logger](
  clientProfileService: ClientProfileServiceAlgebra[F],
  sessionCache: SessionCacheAlgebra[F]
) extends Http4sDsl[F]
    with ClientProfileControllerAlgebra[F] {

  private def extractCookieSessionToken(req: Request[F]): Option[String] =
    req.cookies
      .find(_.name == "auth_session")
      .map(_.content)

  private def withValidSession(userId: String, token: String)(onValid: F[Response[F]]): F[Response[F]] =
    Logger[F].debug(s"[ClientProfileControllerImpl][withValidSession] UserId: $userId, token: $token") *>
      sessionCache.getSession(userId).flatMap {
        case Some(userSession) if userSession.cookieValue == token =>
          Logger[F].debug(s"[ClientProfileControllerImpl][withValidSession] User session: $userSession") *>
            onValid
        case Some(session) =>
          Logger[F].debug(s"[ClientProfileControllerImpl][withValidSession] User session does not match request user session token value from redis. $session") *>
            Forbidden("User session does not match request user session token value from redis.")
        case None =>
          Logger[F].debug("[ClientProfileControllerImpl][withValidSession] Invalid or expired session")
          Forbidden("Invalid or expired session")
      }

  val routes: HttpRoutes[F] = HttpRoutes.of[F] { 
    case req @ GET -> Root / "profile" / "health" =>
      Logger[F].debug(s"[ClientProfileControllerImpl] GET - Health check for backend ClientProfileControllerImpl") *>
        Ok(GetResponse("/dev-irl-client-service/skill/health", "I am alive - ClientProfileControllerImpl").asJson)  
  }


}

object ClientProfileController {
  def apply[F[_] : Async : Concurrent](
    clientProfileService: ClientProfileServiceAlgebra[F]
  )(implicit logger: Logger[F]): ClientProfileControllerAlgebra[F] =
    new ClientProfileControllerImpl[F](clientProfileService)
}
