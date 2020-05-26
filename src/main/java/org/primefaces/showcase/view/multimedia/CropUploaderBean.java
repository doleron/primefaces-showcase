/*
 * Copyright 2009-2020 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.showcase.view.multimedia;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

@Named
@SessionScoped
public class CropUploaderBean implements Serializable {
    
    private CroppedImage croppedImage;
    
    private UploadedFile originalImageFile;

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }
    
    public UploadedFile getOriginalImageFile() {
        return originalImageFile;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        this.originalImageFile = null;
        this.croppedImage = null;
        UploadedFile file = event.getFile();
        if(file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            this.originalImageFile = file;
            FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void crop() {
        if(this.croppedImage == null || this.croppedImage.getBytes() == null || this.croppedImage.getBytes().length == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cropping failed."));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Cropped successfully."));
        }
    }
    
    public StreamedContent getImage() {
        
        FacesContext context = FacesContext.getCurrentInstance();

        StreamedContent result = null;
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE || this.originalImageFile == null
                || this.originalImageFile.getContent() == null || this.originalImageFile.getContent().length == 0) {
            result = new DefaultStreamedContent();
        }
        else {
            result = DefaultStreamedContent.builder().contentType(this.originalImageFile.getContentType()).stream(() -> {
                try {
                    return new ByteArrayInputStream(this.originalImageFile.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }).build();
        }
        
        return result;
    }

    public StreamedContent getCropped() {
        
        FacesContext context = FacesContext.getCurrentInstance();

        StreamedContent result = null;
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE || this.croppedImage == null
                || this.croppedImage.getBytes() == null || this.croppedImage.getBytes().length == 0) {
            result = new DefaultStreamedContent();
        }
        else {
            result = DefaultStreamedContent.builder().contentType(this.originalImageFile.getContentType()).stream(() -> {
                try {
                    return new ByteArrayInputStream(this.croppedImage.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }).build();
        }
        
        return result;
    }
    
}