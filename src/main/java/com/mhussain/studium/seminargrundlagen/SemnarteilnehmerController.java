package com.mhussain.studium.seminargrundlagen;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequiredArgsConstructor
@RequestMapping(path = "serminateilnehmer")
@RestController
public class SemnarteilnehmerController {

    private final SerminarteilnehmerService serminarteilnehmerService;

    @RequestMapping(method = GET)
    public Collection<Seminarteilnehmer> findAll() {
        return serminarteilnehmerService.findAll();
    }

    @RequestMapping(method = GET, path = "findByMatrikelnummer/{matrikelNummer}")
    public Seminarteilnehmer findByMatrikelnummer(@PathVariable("matrikelNummer") Long matrikelNummer) {
        return serminarteilnehmerService.findByMatrikelNummer(matrikelNummer)
                .orElseThrow(() -> new IllegalArgumentException("Es konnte kein Seminarteilmehmer mit der Matrikelnummer: " + matrikelNummer + " gefunden werden!"));
    }

    @RequestMapping(method = POST)
    public void create(@RequestBody Seminarteilnehmer seminarteilnehmer) {
        serminarteilnehmerService.save(Collections.singletonList(seminarteilnehmer));
    }

}