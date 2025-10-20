package routes

import infrastructure.cache.PricingPlanCacheImpl
import infrastructure.cache.RedisCacheImpl
import infrastructure.cache.SessionCache
import infrastructure.cache.SessionCacheImpl
import cats.effect.*
import cats.NonEmptyParallel
import configuration.AppConfig
import controllers.*
import doobie.hikari.HikariTransactor
import java.net.URI
import org.http4s.client.Client
import org.http4s.HttpRoutes
import org.typelevel.log4cats.Logger
import repositories.*
import services.*
import services.stripe.StripeBillingServiceImpl

object RegistrationRoutes {

  def profileRoutes[F[_] : Concurrent : Temporal : NonEmptyParallel : Async : Logger](
    transactor: HikariTransactor[F],
    appConfig: AppConfig,
    client: Client[F]
  ): HttpRoutes[F] = {
    
    val profileService = ClientProfileService()

    val profileController = ClientProfileController(profileService)

    profileController.routes
  }
}
