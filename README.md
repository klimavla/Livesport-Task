# Livesport-Task

# Libs
App used several of libraries for making development more pleasant.
Short summary of them.

## Catalogs
for easy managing of libs version with few nice features i have used version catalog via toml file.
## Networking
Here was us already more than standard and famous lib from Square [Retrofit](https://square.github.io/retrofit/) and to serialize/deserialize json object there is [Moshi](https://github.com/square/moshi)

## Testing
Some more libraries was used for testing like [MockK](https://mockk.io) for mocking data and [Turbine](https://github.com/cashapp/turbine) to have make testing of flows easy.

# Static analysis
For purpose of check code style i have used [Detekt](https://github.com/detekt/detekt) rules for formatting and [compose](https://twitter.github.io/compose-rules/detekt/)
This can be started locally with command:
>./gradlew detekt

# Small bonus
As little bonus i have prepared two simple scenarios for automated testing with use of [Maestro](https://github.com/mobile-dev-inc/maestro).
For making it run it is needed to [instal](https://maestro.mobile.dev/getting-started/installing-maestro) Maestro first. then with started emulator and installed app it can be started by all these variant for scenario 1 or 2 in cz or eng version of app.

>maestro test uitests/cz/scenario1.yml
>
>maestro test uitests/cz/scenario2.yml
>
>maestro test uitests/en/scenario1.yml
>
>maestro test uitests/en/scenario2.yml
