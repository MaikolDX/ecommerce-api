package com.proyecto.api.ecommerceapiv2.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}") //opps
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES) * 60 * 1000 + issuedAt.getTime()); //A la fecha en que se emitió le sumaremos los 5 minutos de vida
        String jwt = Jwts.builder()
                //HEADER
                .header()
                .type("JWT")
                .and()
                //PAYLOAD
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                //FIRMA
                .signWith(generateKey(), Jwts.SIG.HS256) //Para la firma, mandaremos el Key y el tipo de algoritmo a utilizar para firmartodo el token
                .compact();
                //compact devuelve un STRING, que sería nuestro JWT, CODIFICA entero al token a BASE64, por partes
                // Lo que devuelve el compact() -> String jwt = base64UrlEncodedHeader + JwtParser.SEPARATOR_CHAR (es el punto :v) + base64UrlEncodedBody;
        return jwt;
    }

    private SecretKey generateKey() {

        byte[] passwordDecoder = Decoders.BASE64.decode(SECRET_KEY); //Decodifica la clave
//        byte[] key = SECRET_KEY.getBytes();

        return Keys.hmacShaKeyFor(passwordDecoder); // Genera una clave HMAC-SHA adecuada usando el array de bytes decodificado
    }

    public String extractUsername(String jwt) {
        //Extraeremos las propiedades del payload y agarrar el subject y devolverlo
        //Si el token tiene un formato malo nos dará una excepción, es decir, si solo se editó aunque sea una letra, todo cambia
        //por eso solo sacamos el subject
        //Si ya expiró error tambiém
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        //Este objeto de tipo claims ya tiene métodos para obtener las propiedades como el getSubject(), etc
        //Usaremos la clase utilería Jwts
        //La clave no viene en el token, esta está en el backend
        return Jwts.parser().verifyWith( generateKey() ).build()
                .parseSignedClaims(jwt).getPayload();
    }
}
