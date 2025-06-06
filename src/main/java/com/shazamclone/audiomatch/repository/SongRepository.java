package com.shazamclone.audiomatch.repository;

import com.shazamclone.audiomatch.model.SongMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongRepository extends MongoRepository<SongMetadata, Integer> {}