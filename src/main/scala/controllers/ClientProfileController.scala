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
  clientProfileService: ClientProfileServiceAlgebra[F]
) extends Http4sDsl[F]
    with ClientProfileControllerAlgebra[F] {

  // implicit val stripeDevUserDataDecoder: EntityDecoder[F, StripeDevUserData] = jsonOf[F, StripeDevUserData]

  val routes: HttpRoutes[F] = HttpRoutes.of[F] { case req @ GET -> Root / "profile" / "health" =>
    Logger[F].debug(s"[ClientProfileControllerImpl] GET - Health check for backend ClientProfileControllerImpl") *>
      Ok(GetResponse("/dev-quest-service/skill/health", "I am alive - ClientProfileControllerImpl").asJson)

  // case req @ POST -> Root / "stripe" / "onboarding" =>
  //   for {
  //     _ <- Logger[F].debug(s"[ProfileController] POST - Trying to get stripe link for user")
  //     userData <- req.as[StripeDevUserData] // or decode from session/cookie.   // this is a simple json body to return the devId
  //     _ <- Logger[F].debug(s"[ProfileController] POST - UserData Recieved: $userData")
  //     link <- stripeRegistrationService.createAccountLink(userData.userId)
  //     resp <- {
  //       Logger[F].debug(s"[ProfileController] POST - Stripe account creation Link created generated: ${link.asJson}") *>
  //         Ok(link.asJson)
  //     }
  //   } yield resp

  // case req @ POST -> Root / "stripe" / "onboarding" / "complete" =>
  //   for {
  //     userData <- req.as[StripeDevUserData] // or decode from session/cookie.   // this is a simple json body to return the devId
  //     _ <- stripeRegistrationService.fetchAndUpdateAccountDetails(userData.userId)
  //     resp <- Ok(Json.obj("status" -> Json.fromString("Stripe account status updated")))
  //   } yield resp
  }
}

object ClientProfileController {
  def apply[F[_] : Async : Concurrent](
    clientProfileService: ClientProfileServiceAlgebra[F]
  )(implicit logger: Logger[F]): ClientProfileControllerAlgebra[F] =
    new ClientProfileControllerImpl[F](clientProfileService)
}
