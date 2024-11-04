package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.server.StreamResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUploader extends VerticalLayout {

    private static final Logger LOGGER = LogManager.getLogger(ImageUploader.class);

    private final MemoryBuffer buffer1 = new MemoryBuffer();
    private final MemoryBuffer buffer2 = new MemoryBuffer();

    private final Upload upload1 = new Upload(buffer1);
    private final Upload upload2 = new Upload(buffer2);

    private final boolean isSingleImageMode;

    private Image uploadedImage1;
    private Image uploadedImage2;

    private HorizontalLayout upload1Layout = new HorizontalLayout();
    private HorizontalLayout upload2Layout = new HorizontalLayout();

    private Button removeButton1;
    private Button removeButton2;

    public ImageUploader(boolean isSingleImageMode) {

        this.isSingleImageMode = isSingleImageMode;

        setSpacing(true);
        setPadding(false);

        configureImageUpload1();
        configureImageUpload2();
    }

    private void configureImageUpload1() {

        upload1.setDropLabel(new Span(""));
        upload1.addSucceededListener(event -> {

            uploadedImage1 = visibleAndGetUploadedImage(buffer1, event.getFileName());
            upload1Layout.add(uploadedImage1);

            if (!isSingleImageMode && uploadedImage2 == null) {
                handleImageUpload2Visibility(upload2, upload2Layout, true);
            }
        });

        upload1.addFileRemovedListener(event -> {

            uploadedImage1.setVisible(false);
            uploadedImage1 = null;

            if (uploadedImage2 == null) {
                handleImageUpload2Visibility(upload2, upload2Layout, false);
            }
        });

        upload1Layout = new HorizontalLayout(upload1);
        upload1Layout.setAlignItems(Alignment.CENTER);

        add(upload1Layout);
    }

    private void configureImageUpload2() {

        upload2.setVisible(false);
        upload2.setDropLabel(new Span(""));

        upload2.addSucceededListener(event -> {
            uploadedImage2 = visibleAndGetUploadedImage(buffer2, event.getFileName());
            upload2Layout.add(uploadedImage2);
        });

        upload2.addFileRemovedListener(event -> {

            uploadedImage2.setVisible(false);
            uploadedImage2 = null;

            if (uploadedImage1 == null) {
                handleImageUpload2Visibility(upload2, upload2Layout, false);
            }
        });

        upload2Layout = new HorizontalLayout(upload2);
        upload2Layout.setAlignItems(Alignment.CENTER);

        if (!isSingleImageMode) {
            add(upload2Layout);
        }
    }

    private Image visibleAndGetUploadedImage(MemoryBuffer buffer, String fileName) {

        Image image = createImageFromBuffer(buffer, fileName);

        image.setWidth("150px");
        image.setHeight("150px");
        image.setVisible(true);

        return image;
    }

    private void handleImageUpload2Visibility(Upload upload, HorizontalLayout layout, boolean visible) {

        upload.setVisible(visible);
        layout.setEnabled(visible);
        layout.setVisible(visible);
    }

    private Image createImageFromBuffer(MemoryBuffer buffer, String fileName) {
        StreamResource resource = new StreamResource(fileName, buffer::getInputStream);
        return new Image(resource, "Uploaded image");
    }

    public boolean isImage1Present() {
        return uploadedImage1 != null;
    }

    public boolean isImage2Present() {
        return uploadedImage2 != null;
    }

    public byte[] getUploadedImage1AsBytes() {

        if (uploadedImage1 != null) {

            try {
                return buffer1.getInputStream().readAllBytes();

            } catch (IOException e) {
                LOGGER.error("Convert input stream to byte array, Error: ", e);
            }
        }

        return null;
    }

    public byte[] getUploadedImage2AsBytes() {

        if (uploadedImage2 != null) {

            try {
                return buffer2.getInputStream().readAllBytes();

            } catch (IOException e) {
                LOGGER.error("Convert input stream to byte array, Error: ", e);
            }
        }

        return null;
    }

    public void loadImage1(byte[] imageData) {

        if (imageData != null) {
            removeButton1 = new Button("Remove", event -> clearImage1());

            showImage2(imageData);
            upload1.setVisible(false);
            upload1Layout.add(removeButton1);

            if (!isSingleImageMode) {
                handleImageUpload2Visibility(upload2, upload2Layout, true);
            }
        }
    }

    public void loadImage2(byte[] imageData) {

        if (imageData != null) {
            removeButton2 = new Button("Remove", event -> clearImage2());

            showImage1(imageData);
            upload2.setVisible(false);
            upload2Layout.add(removeButton2);
        }
    }

    private void showImage2(byte[] imageData) {
        StreamResource resource = new StreamResource("image1", () -> new ByteArrayInputStream(imageData));
        uploadedImage1 = new Image(resource, "Image 1");
        uploadedImage1.setWidth("150px");
        uploadedImage1.setHeight("150px");
        upload1Layout.add(uploadedImage1);
    }

    private void showImage1(byte[] imageData) {
        StreamResource resource = new StreamResource("image2", () -> new ByteArrayInputStream(imageData));
        uploadedImage2 = new Image(resource, "Image 2");
        uploadedImage2.setWidth("150px");
        uploadedImage2.setHeight("150px");
        upload2Layout.add(uploadedImage2);
    }

    private void clearImage1() {
        uploadedImage1.setVisible(false);
        removeButton1.setVisible(false);
        uploadedImage1 = null;
        upload1.setVisible(true);
    }

    private void clearImage2() {
        uploadedImage2.setVisible(false);
        removeButton2.setVisible(false);
        uploadedImage2 = null;
        upload2.setVisible(true);
    }

}