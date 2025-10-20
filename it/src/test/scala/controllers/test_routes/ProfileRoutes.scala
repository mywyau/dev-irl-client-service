package controllers.test_routes

import cats.data.Validated
import cats.data.ValidatedNel
import cats.effect.*
import cats.implicits.*
import configuration.AppConfig
import configuration.BaseAppConfig
import controllers.mocks.*
import controllers.UserDataController
import dev.profunktor.redis4cats.RedisCommands
import doobie.util.transactor.Transactor
import infrastructure.cache.*
import java.net.URI
import java.time.Duration
import java.time.Instant
import models.auth.UserSession
import models.cache.*
import models.pricing.Active
import models.pricing.PlanFeatures
import models.pricing.PlanSnapshot
import org.http4s.server.Router
import org.http4s.HttpRoutes
import org.http4s.Uri
import org.typelevel.log4cats.slf4j.Slf4jLogger
import org.typelevel.log4cats.SelfAwareStructuredLogger
import repositories.*
import services.*
import services.stripe.StripeBillingServiceAlgebra
import services.stripe.StripeSubState

object ProfileRoutes extends BaseAppConfig {

  implicit val testLogger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger[IO]


  def fakeUserSession(userId: String) = {
    val sessionToken = "test-session-token"
    UserSession(
      userId = userId,
      cookieValue = sessionToken,
      email = s"$userId@example.com",
      userType = "Dev"
    )
  }

  val mockCachedAuthSessions =
    Ref.of[IO, Map[String, UserSession]](
      Map(
        s"auth:session:USER001" -> fakeUserSession("USER001"),
        s"auth:session:USER002" -> fakeUserSession("USER002"),
        s"auth:session:USER003" -> fakeUserSession("USER003"),
        s"auth:session:USER004" -> fakeUserSession("USER004"),
        s"auth:session:USER005" -> fakeUserSession("USER005"),
        s"auth:session:USER006" -> fakeUserSession("USER006"),
        s"auth:session:USER008" -> fakeUserSession("USER008"),
        s"auth:session:USER009" -> fakeUserSession("USER009"),
        s"auth:session:USER010" -> fakeUserSession("USER010")
      )
    )

  def userDataRoutes(appConfig: AppConfig, transactor: Transactor[IO]): Resource[IO, HttpRoutes[IO]] =
    for {
      ref <- Resource.eval(mockCachedAuthSessions)
      mockSessionCache = new MockSessionCache(ref)
      userDataRepository = UserDataRepository(transactor)
      userDataService = UserDataService(userDataRepository)
      userDataController = UserDataController(userDataService, mockSessionCache)
    } yield userDataController.routes

}
