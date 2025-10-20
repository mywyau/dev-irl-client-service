package services

import cats.data.Validated
import cats.data.Validated.Invalid
import cats.data.Validated.Valid
import cats.data.ValidatedNel
import cats.effect.Concurrent
import cats.implicits.*
import cats.syntax.all.*
import cats.Monad
import cats.NonEmptyParallel
import fs2.Stream
import java.util.UUID
import models.database.*
import models.database.DatabaseErrors
import models.database.DatabaseSuccess
import models.profile.ProfileData
import models.users.*
import models.UserType
import org.typelevel.log4cats.Logger

trait ClientProfileServiceAlgebra[F[_]] {}

class ClientProfileServiceImpl[F[_] : Concurrent : Monad : Logger]() extends ClientProfileServiceAlgebra[F] {}

object ClientProfileService {

  def apply[F[_] : Concurrent : NonEmptyParallel : Logger](): ClientProfileServiceAlgebra[F] = new ClientProfileServiceImpl[F]()
}
