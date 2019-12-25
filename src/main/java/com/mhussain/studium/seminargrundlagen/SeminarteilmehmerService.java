package com.mhussain.studium.seminargrundlagen;

import java.util.Collection;
import java.util.Optional;

public interface SeminarteilmehmerService {

    Optional<Seminarteilmehmer> findByMatrikelNummer(Long matrikelNummer);

    Collection<Seminarteilmehmer> findAll();

    void save(Collection<Seminarteilmehmer> seminarteilmehmers);

}