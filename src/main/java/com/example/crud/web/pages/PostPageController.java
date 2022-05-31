package com.example.crud.web.pages;

import com.example.crud.model.Post;
import com.example.crud.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@ManagedBean
@ViewScoped
public class PostPageController {
    private final PostService service;
    private List<Post> posts;

    @PostConstruct
    public void init() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        posts = service.findAll(null, sort).stream().limit(100).toList();
    }

    public List<Post> getPosts() {
        return posts;
    }
}