package univ.master.mql.subscriptionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.master.mql.subscriptionservice.entities.Offer;
import univ.master.mql.subscriptionservice.entities.Pack;
import univ.master.mql.subscriptionservice.services.OfferService;

import java.util.List;
import java.util.Optional;

@RestController

@Slf4j
@RequestMapping("/api/subscription/offer/")
public class PackageSportController {

}
