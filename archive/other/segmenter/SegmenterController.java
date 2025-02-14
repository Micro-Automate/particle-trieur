/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.segmenter;

import particletrieur.App;
import particletrieur.AppPreferences;
import particletrieur.controls.AlertEx;
import particletrieur.controls.BasicDialogs;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.RangeSlider;

/**
 * FXML Controller class
 *
 * @author Ross Marchant <ross.g.marchant@gmail.com>
 */
public class SegmenterController implements Initializable {
    
    @FXML Pane root;
    @FXML ImageView imageViewPre;
    @FXML FlowPane flowPanePost;
    @FXML Slider sliderBorder;
    @FXML Slider sliderErosion;
    @FXML RangeSlider rangeSliderBinary;
    @FXML RangeSlider rangeSliderSize;
    @FXML CheckBox checkBoxDiscardEdge;
    @FXML VBox vboxImagePre;
    @FXML ToggleButton toggleButtonShowThreshold;
    
    private static AppPreferences appPrefs = new AppPreferences();
    
    ObservableList<RawSlide> slides;
    
    Segmenter segmenter = new Segmenter();
    
    int currentIdx = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        slides = FXCollections.observableArrayList();
        
        imageViewPre.fitWidthProperty().bind(vboxImagePre.widthProperty());
        imageViewPre.fitHeightProperty().bind(vboxImagePre.heightProperty());
  
        //Show thresholded view
        segmenter.showThresholdedImage.set(toggleButtonShowThreshold.isSelected());
        toggleButtonShowThreshold.selectedProperty().addListener(event -> {
            segmenter.showThresholdedImage.set(toggleButtonShowThreshold.isSelected());
            settingsChanged();
        });
                
        //Discard edge
        segmenter.discardEdgeProperty.set(checkBoxDiscardEdge.isSelected());
        checkBoxDiscardEdge.selectedProperty().addListener(event -> {
            segmenter.discardEdgeProperty.set(checkBoxDiscardEdge.isSelected());
            settingsChanged();
        });
        
        //Border margin
        segmenter.setMargin(sliderBorder.getValue()/100);
        sliderBorder.valueProperty().addListener(event -> {
            segmenter.setMargin(sliderBorder.getValue()/100);
            if(!sliderBorder.isValueChanging()) {
                settingsChanged();
            }
        });
        sliderBorder.setOnMouseReleased(event -> settingsChanged());
        
        //Erosion
        segmenter.setErosion((int)sliderErosion.getValue());
        sliderErosion.valueProperty().addListener(event -> {
            segmenter.setErosion((int)sliderErosion.getValue());
            if(!sliderErosion.isValueChanging()) {
                settingsChanged();
            }
        });
        sliderErosion.setOnMouseReleased(event -> settingsChanged());
        
        //Size range          
        rangeSliderSize.adjustHighValue(segmenter.getUpperSizeThreshold());  
        rangeSliderSize.adjustLowValue(segmenter.getLowerSizeThreshold()); 
        rangeSliderSize.highValueProperty().addListener(event -> {
            segmenter.setUpperSizeThreshold(rangeSliderSize.getHighValue());
            if (!rangeSliderSize.highValueChangingProperty().get()) {
                settingsChanged();
            }
        });
        rangeSliderSize.lowValueProperty().addListener(event -> {
            segmenter.setLowerSizeThreshold(rangeSliderSize.getLowValue());
            if (!rangeSliderSize.lowValueChangingProperty().get()) {
                settingsChanged();
            }
        });
        rangeSliderSize.setOnMouseReleased(event -> settingsChanged());        
        
        //Binary threshold range
        rangeSliderBinary.adjustHighValue(segmenter.getUpperBinaryThreshold()*100/255);
        rangeSliderBinary.adjustLowValue(segmenter.getLowerBinaryThreshold()*100/255);        
        rangeSliderBinary.highValueProperty().addListener(event -> {
            segmenter.setUpperBinaryThreshold(rangeSliderBinary.getHighValue()*255/100);
            if (!rangeSliderBinary.highValueChangingProperty().get()) {
                settingsChanged();
            }
        });
        rangeSliderBinary.lowValueProperty().addListener(event -> {
            segmenter.setLowerBinaryThreshold(rangeSliderBinary.getLowValue()*255/100);
            if (!rangeSliderBinary.lowValueChangingProperty().get()) {
                settingsChanged();
            }
        });
        rangeSliderBinary.setOnMouseReleased(event -> settingsChanged());        
    }
    
    private void settingsChanged() {
        updateImage();
    }
    
    private void updateImage() {
        if (slides.size() > 0) {
            showSlide();
        }
    }
    
    public void showSlide() {
        Image toShow = segmenter.Process(slides.get(currentIdx));
        imageViewPre.setImage(toShow);
    }
    
    @FXML
    private void handleLoadImages(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        String path = App.getPrefs().getSegmenterPath();
        if(path != null && Files.exists(Paths.get(path))) {
            dc.setInitialDirectory(new File(path));
        }
        dc.setTitle("Select directory of images to add");
        File dir = dc.showDialog(root.getScene().getWindow());
        if (dir == null) return;
        
        appPrefs.setSegmenterPath(dir.getAbsolutePath());
        appPrefs.save();
        
        Collection<File> fileCollection = FileUtils.listFiles(dir, new String[]{"bmp","png","tiff","tif","jpg","jpeg"}, true);
        for (File f : fileCollection) {
            slides.add(new RawSlide(f));
        }
        updateImage();
    }
    
    @FXML
    private void handlePrevious(ActionEvent event) {
        if (currentIdx > 0) {
            currentIdx--;
            updateImage();
        }
    }
    
    @FXML
    private void handleNext(ActionEvent event) {
        if (currentIdx < slides.size()-1) {
            currentIdx++;
            updateImage();
        }
    }
    
    @FXML
    private void handleExport(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select directory to output segmented images");
        File dir = dc.showDialog(root.getScene().getWindow());
        if (dir == null) return;
                        
        AlertEx alert = new AlertEx(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Export labeled images.");
        alert.setContentText("Exporting files to "  + dir.getAbsolutePath() + ".\n\n This will take a long time.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showEmbedded();
        alert.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                Service<Void> service = new Service<Void>() {
                    @Override
                    protected Task<Void> createTask() {
                        return new Task<Void>() {
                            @Override
                            protected Void call() throws InterruptedException {
                                updateMessage("Exporting...");
                                int index = 0;
                                for (RawSlide r : slides) {
                                    segmenter.Export(r,dir,index);
                                    index++;
                                    updateProgress(index, slides.size());

                                    if (this.isCancelled()) {
                                        break;
                                    }
                                }
                                updateMessage("Complete");
                                return null;
                            }
                        };
                    }
                };
                BasicDialogs.ProgressDialogWithCancel2(
                        "Operation",
                        "Exporting images",
                        root,
                        service).start();
            }
            return null;
        });
    }
}
