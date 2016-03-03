package ua.home.postalcode.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ua.home.postalcode.data.DistanceResult;
import ua.home.postalcode.data.PostalCode;
import ua.home.postalcode.service.PostalCodeService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PostalCodeController {

    @Autowired
    private PostalCodeService service;

    @RequestMapping(value = "/distance/{postalCodeA}/{postalCodeB}")
    public DistanceResult calculateDistance(@PathVariable("postalCodeA") String postalCodeA,
                                            @PathVariable("postalCodeB")  String postalCodeB) {
        return service.getDistance(postalCodeA, postalCodeB);
    }

    @RequestMapping(value = "/postalcode", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody PostalCode code) {
        service.update(code);
        return new ResponseEntity("OK", HttpStatus.OK);
    }
}
