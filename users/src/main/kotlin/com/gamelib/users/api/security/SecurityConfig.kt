package com.gamelib.users.api.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
class SecurityConfig(
    @Value("\${auth0.audience}")
    private val audience: String,
    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private val issuer: String
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .oauth2ResourceServer()
            .jwt()
            .decoder(jwtDecoder())
            .jwtAuthenticationConverter(jwtAuthenticationConverter())
    }

    fun jwtDecoder(): JwtDecoder {
        val withAudience = AudienceValidator(audience)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuer)

        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation<JwtDecoder>(issuer) as NimbusJwtDecoder
        jwtDecoder.setJwtValidator(DelegatingOAuth2TokenValidator(withAudience, withIssuer))
        return jwtDecoder
    }

    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtGrantedAuthoritiesConverter().apply {
            setAuthoritiesClaimName("permissions")
            setAuthorityPrefix("")
        }

        return JwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(converter)
        }
    }
}