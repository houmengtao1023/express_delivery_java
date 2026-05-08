package com.lazy.express.controller;

import com.lazy.express.common.R;
import com.lazy.express.entity.Station;
import com.lazy.express.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @descrption: StationController 服务网点接口
 * @author: lazy
 * @date: 2026-05-08 14:00
 */
@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/list")
    public R<List<Station>> list(@RequestParam(required = false) String type) {
        return R.ok(stationService.listByType(type));
    }

    @GetMapping("/nearest")
    public R<Station> nearest(@RequestParam String type) {
        return R.ok(stationService.nearest(type));
    }
}
