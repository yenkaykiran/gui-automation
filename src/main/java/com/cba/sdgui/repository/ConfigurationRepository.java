package com.cba.sdgui.repository;

import com.cba.sdgui.model.entity.Configuration;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends BaseRepository<Configuration, Integer> {

    public Configuration findByName(String name);

    public List<Configuration> findByAutoLoad(boolean autoLoad);

}
