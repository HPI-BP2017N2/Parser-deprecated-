package de.hpi.parser.controller;

import de.hpi.parser.service.ParserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService service;

    @Autowired
    public ParserController(ParserService service){
        setService(service);
    }

}
