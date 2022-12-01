package com.josh.security.controller;

import com.josh.security.entity.UserCredentials;
import com.josh.security.models.AuthenticationRequest;
import com.josh.security.models.AuthenticationResponse;
import com.josh.security.services.MyUserDetailsService;
import com.josh.security.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class SecurityController {


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    public final MyUserDetailsService userDetailsService;



    @PostMapping(value = "/registration",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody UserCredentials userCredentials){
        this.userDetailsService.addUser(userCredentials);
        return ResponseEntity.ok("{}");
    }

    @PostMapping(value = "/authenticate",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        System.out.println("authentication details : " + authenticationRequest);
		 try { authenticationManager.authenticate( new
		    UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
		    authenticationRequest.getPassword()) );
         } catch (BadCredentialsException e) {

             throw  new BadCredentialsException("Bad Credentials.");

         }


		  final UserDetails userDetails = userDetailsService
		  .loadUserByUsername(authenticationRequest.getUsername());

		  final String jwt=jwtTokenUtil.generateToken(userDetails);
          return  ResponseEntity.ok((new AuthenticationResponse(jwt)));
    }
}
