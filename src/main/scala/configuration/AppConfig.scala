package configuration

import cats.kernel.Eq
import configuration.models.*
import pureconfig.ConfigReader
import pureconfig.generic.derivation.*

case class AppConfig(
  featureSwitches: FeatureSwitches,
  pricingPlanConfig: PricingPlanConfig,
  kafka: KafkaConfig,
  redisConfig: RedisConfig, 
  devIrlFrontendConfig: DevIrlFrontendConfig,
  serverConfig: ServerConfig,
  postgresqlConfig: PostgresqlConfig,
  awsS3Config: S3Config,
  stripeConfig: StripeConfig
) derives ConfigReader
