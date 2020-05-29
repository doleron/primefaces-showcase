package org.primefaces.showcase.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class BasicPatternDetectionView implements Serializable {

    private String someText;

    private Object model;

    private Object detection;
    
    @PostConstruct
    public void init() {
        this.detection = "No detection yet";
    }

    public String getSomeText() {
        return someText;
    }

    public void setSomeText(String someText) {
        this.someText = someText;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public Object getDetection() {
        return detection;
    }

    public void setDetection(Object detection) {
        this.detection = detection;
    }

}
