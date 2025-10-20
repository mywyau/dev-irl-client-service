package configuration.constants

import configuration.models.*
import configuration.AppConfig

object ProdAppConfigConstants {

  val featureSwitches =
    FeatureSwitches(
      useDockerHost = false,
      localTesting = false,
      useCors = false,
      useHttpsLocalstack = true,
      useProdStripe = false
    )

  val pricingPlanConfig =
    PricingPlanConfig(
      cacheTtlMinutes = 60
    )

  val kafkaConfig =
    KafkaConfig(
      bootstrapServers = "localhost:9092",
      clientId = "dev-irl-client-service",
      acks = "all",
      lingerMs = 5,
      retries = 10,
      topic = KafkaTopicConfig(
        "quest.created.v1",
        "esimtation.finalized.v1"
      )
    )

  val devIrlFrontendConfig =
    DevIrlFrontendConfig(
      host = "0.0.0.0",
      port = 8080,
      baseUrl = "https://devirl.com"
    )

  val appServerConfig =
    ServerConfig(
      host = "0.0.0.0",
      port = 8080
    )

  val containerPostgreSqlConfig =
    PostgresqlConfig(
      dbName = "dev_irl_client_db",
      dockerHost = "dev-irl-client-container",
      host = "localhost",
      port = 5432,
      username = "dev_irl_client_db_user",
      password = "turnip",
      maxPoolSize = 42
    )

  val redisConfig =
    RedisConfig(
      dockerHost = "redis-container",
      host = "localhost",
      port = 6379
    )

  val s3Config =
    S3Config(
      awsRegion = "us-east-1",
      bucketName = "dev-submissions",
      dockerName = "localstack",
      host = "localhost",
      port = 4566
    )

  val stripeConfig =
    StripeConfig(
      registrationRefreshUrl = "https://devirl.com/dev/stripe/onboarding/refresh",
      registrationReturnUrl = "https://devirl.com/dev/stripe/onboarding/success",
      paymentSuccessUrl = "https://devirl.com/payment/success",
      paymentCancelUrl = "https://devirl.com/payment/error",
      stripeUrl = "https://api.stripe.com/v1",
      platformFeePercent = 2.5
    )

  val prodAppConfig =
    ProdAppConfig(
      devIrlFrontendConfig = devIrlFrontendConfig,
      serverConfig = appServerConfig,
      postgresqlConfig = containerPostgreSqlConfig,
      redisConfig = redisConfig,
      awsS3Config = s3Config,
      stripeConfig = stripeConfig
    )

  val prodAppConfigConstant =
    AppConfig(
      featureSwitches = featureSwitches,
      pricingPlanConfig = pricingPlanConfig,
      kafka = kafkaConfig,
      devIrlFrontendConfig = devIrlFrontendConfig,
      serverConfig = appServerConfig,
      postgresqlConfig = containerPostgreSqlConfig,
      redisConfig = redisConfig,
      awsS3Config = s3Config,
      stripeConfig = stripeConfig
    )
}
