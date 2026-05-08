package com.lazy.express.service;

import com.lazy.express.entity.Station;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @descrption: StationService 服务网点服务
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@Service
public class StationService {

    private final List<Station> data = new ArrayList<>();

    @PostConstruct
    public void init() {
        data.add(buildStation(1L, "营业部", "北京市站", "777m", "朝阳区望京街道东湖国际广场1层"));
        data.add(buildStation(2L, "营业部", "望京营业部", "1.2km", "朝阳区望京西园三区"));
        data.add(buildStation(3L, "便民站", "京东便民站(望京店)", "320m", "朝阳区望京中环南路18号院"));
        data.add(buildStation(4L, "便民站", "京东便民站(广顺南大街)", "560m", "朝阳区广顺南大街8号"));
    }

    public List<Station> listByType(String type) {
        if (type == null || type.isEmpty()) {
            return new ArrayList<>(data);
        }
        return data.stream()
                .filter(s -> type.equals(s.getType()))
                .collect(Collectors.toList());
    }

    public Station nearest(String type) {
        return listByType(type).stream().findFirst().orElse(null);
    }

    private Station buildStation(Long id, String type, String name, String distance, String address) {
        Station s = new Station();
        s.setId(id);
        s.setType(type);
        s.setName(name);
        s.setDistance(distance);
        s.setAddress(address);
        return s;
    }
}
