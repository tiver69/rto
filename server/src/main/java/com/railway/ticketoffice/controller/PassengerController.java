package com.railway.ticketoffice.controller;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PageableDto;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.dto.security.JwtLoginSuccessResponse;
import com.railway.ticketoffice.dto.security.LoginRequest;
import com.railway.ticketoffice.security.JwtTokenProvider;
import com.railway.ticketoffice.service.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.railway.ticketoffice.security.Constants.TOKEN_PREFIX;

@RestController
@CrossOrigin
@RequestMapping("/api/passenger")
public class PassengerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "/page", produces = "application/json")
    public ResponseEntity<?> findPagePassengersForManaging(@RequestParam("page") Integer page) {
        LOGGER.debug("Passengers page#{} request for manage page", page);

        PageableDto<PassengerDto> response = passengerService.findPageForManaging(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPassenger(@RequestBody LoginRequest loginRequest) {
        LOGGER.debug("Request for login from passenger {}", loginRequest.getLogin());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerPassenger(@RequestBody Passenger passenger) {
        LOGGER.debug("Request for creating new passenger {}", passenger.getLogin());

        Passenger response =  passengerService.save(passenger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/update", produces = "application/json")
    public ResponseEntity<?> updatePassenger(@RequestBody Passenger passenger) {
        LOGGER.debug("Request for update passenger#{}", passenger.getId());

        Boolean response = passengerService.updatePassenger(passenger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{passengerId}")
    public ResponseEntity<?> removePassenger(@PathVariable Long passengerId) {
        LOGGER.debug("Request for deleting passenger#{}", passengerId);

        Boolean response = passengerService.removePassenger(passengerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
