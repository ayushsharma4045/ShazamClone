package com.shazamclone.audiomatch.repository;

import com.shazamclone.audiomatch.model.DataPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FingerprintRepository extends MongoRepository<DataPoint, String> {
    List<DataPoint> findByHash(long hash);
}
