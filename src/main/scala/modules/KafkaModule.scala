// modules/KafkaModule.scala
package modules

import cats.effect._
import fs2.kafka._
// import services.kafka.producers._
import infrastructure.KafkaProducerProvider
import configuration.AppConfig
import org.typelevel.log4cats.Logger

final case class KafkaProducers[F[_]]()

object KafkaModule {

  def make[F[_]: Async: Logger](appConfig: AppConfig): Resource[F, KafkaProducers[F]] = {
    for {
      // ✅ Use your existing provider
      producer <- KafkaProducerProvider.make[F](
        bootstrap = appConfig.kafka.bootstrapServers,
        clientId = appConfig.kafka.clientId,
        acks = appConfig.kafka.acks,
        lingerMs = appConfig.kafka.lingerMs,
        retries = appConfig.kafka.retries
      )
    } yield KafkaProducers()
  }
}
