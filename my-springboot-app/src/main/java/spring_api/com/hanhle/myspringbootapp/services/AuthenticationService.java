package spring_api.com.hanhle.myspringbootapp.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import spring_api.com.hanhle.myspringbootapp.dto.request.AuthenticationRequest;
import spring_api.com.hanhle.myspringbootapp.dto.request.IntrospectRequest;
import spring_api.com.hanhle.myspringbootapp.dto.response.AuthenticationResponse;
import spring_api.com.hanhle.myspringbootapp.dto.response.IntrospectResponse;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import spring_api.com.hanhle.myspringbootapp.exception.AppException;
import spring_api.com.hanhle.myspringbootapp.exception.ErrorCode;
import spring_api.com.hanhle.myspringbootapp.repositories.UserRepository;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE  ,makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.secretkey}")
    private   String SIGNER_KEY ;

        PasswordEncoder passwordEncoder;
    public AuthenticationResponse login(AuthenticationRequest loginRequest)
    {

        UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(),userEntity.getPassword());
        if (!authenticated)
        {
            System.out.println(userEntity.getPassword()+ " - " + loginRequest.getPassword());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(userEntity);

        return AuthenticationResponse.builder()
                .token(token)
                .authenicated(true)
                .build();

}

public String generateToken(UserEntity userEntity)  {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet claim = new JWTClaimsSet.Builder()
            .subject(userEntity.getUsername())
            .issuer("MyDomain")
            .issueTime(new Date())
            .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
            .claim("scope",buildScope(userEntity))
            .build();
    Payload payload = new Payload(claim.toJSONObject());

    JWSObject jwsObject = new JWSObject(header,payload);
    try {
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    } catch (JOSEException e) {
        log.error("Can not generate token");
        throw new RuntimeException(e);
    }
    }
public IntrospectResponse Introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier("dLmXVphtJNsQEBZVEXrg7JmMhDBOZt82xpeVa3EaNouyhbRCcGU1C8tvsamu+Tvf");
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(verifier);
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verified && expiration.after(new Date()))
                .build();
}

private String buildScope(UserEntity userEntity){
    StringJoiner stringJoiner = new StringJoiner(" ");
    if(!CollectionUtils.isEmpty(userEntity.getRoles()))
        userEntity.getRoles().forEach(stringJoiner::add);
    return stringJoiner.toString();

}
}