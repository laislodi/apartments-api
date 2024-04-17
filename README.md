# Apartment API

## Some tips

### JWT Configs

Create certs folder. In that folder, run the command to create the private key:

`openssl genrsa -out keypair.pem 2048`

Run the command to create the public key:

`openssl rsa -in keypair.pem -pubout -out public.pem`

Run the command to make sure the private key is in the right format:

`openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem`

Now we can delete the `keypair.pem`, because we will use just the `private.pem` and the `public.pem`

Now we create a new configuration class called RsaKeyProperties:

```
@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {}
```

And add `RsaKeyProperties.class` into the `@EnableConfigurationProperties` in the `ApartmentsApplication` class.

Add the following to the application.properties:

```
rsa.private-key=classpath:certs/private.pem
rsa.public-key=classpath:certs/public.pem
```

Last thing we need is to create the JwtDecoder, so we need to add the following function to the `SecurityConfiguration` class:

```
@Bean
JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
}
```

NimbusJwtDecoder has a lot of useful methods in it.
