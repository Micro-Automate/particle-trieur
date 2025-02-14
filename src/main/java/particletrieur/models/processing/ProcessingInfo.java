/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particletrieur.models.processing;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rossm
 */
@XmlRootElement(name = "processing")
@XmlAccessorType(XmlAccessType.NONE)
public class ProcessingInfo {

    //TODO - this should be in the view model
    //TODO - generic settings class...
    //TODO - parameters / morphology in filter view
    //

    //
    //
    // Type of image
    //
    //
    //Image Type
    private final ObjectProperty<ImageType> imageType = new SimpleObjectProperty<>(ImageType.DARKONLIGHT);
    @XmlElement(name = "imageType")
    public ImageType getImageType() {
        return imageType.get();
    }
    public void setImageType(ImageType value) {
        imageType.set(value);
    }
    public ObjectProperty imageTypeProperty() {
        return imageType;
    }

    //
    //
    // Initial fixes
    //
    //
    // White border
    private final BooleanProperty removeWhiteBorder = new SimpleBooleanProperty(false);
    @XmlElement(name = "removeWhiteBorder")
    public boolean isRemoveWhiteBorder() {
        return removeWhiteBorder.get();
    }
    public BooleanProperty removeWhiteBorderProperty() {
        return removeWhiteBorder;
    }
    public void setRemoveWhiteBorder(boolean removeWhiteBorder) {
        this.removeWhiteBorder.set(removeWhiteBorder);
    }
    // Black border
    private final BooleanProperty removeBlackBorder = new SimpleBooleanProperty(false);
    @XmlElement(name = "removeBlackBorder")
    public boolean isRemoveBlackBorder() {
        return removeBlackBorder.get();
    }
    public BooleanProperty removeBlackBorderProperty() {
        return removeBlackBorder;
    }
    public void setRemoveBlackBorder(boolean removeBlackBorder) {
        this.removeBlackBorder.set(removeBlackBorder);
    }
    // Make square
    private final BooleanProperty makeSquare = new SimpleBooleanProperty(false);
    @XmlElement(name = "makeSquareUsingReplicate")
    public boolean isMakeSquare() {
        return makeSquare.get();
    }
    public BooleanProperty makeSquareProperty() {
        return makeSquare;
    }
    public void setMakeSquare(boolean makeSquare) {
        this.makeSquare.set(makeSquare);
    }

    //
    //
    // Color changes
    //
    //
    //The type of normalisation to perform
    private final BooleanProperty normalise = new SimpleBooleanProperty(false);
    @XmlElement(name = "normalise")
    public boolean isNormalise() {
        return normalise.get();
    }
    public void setNormalise(boolean value) {
        normalise.set(value);
    }
    public BooleanProperty normaliseProperty() {
        return normalise;
    }
    //The normalisation amount
    private final DoubleProperty normalisationParameter = new SimpleDoubleProperty(0.7);
    @XmlElement(name = "normalisationParameter")
    public double getNormalisationParameter() {
        return normalisationParameter.get();
    }
    public void setNormalisationParameter(double value) {
        normalisationParameter.set(value);
    }
    public DoubleProperty normalisationParameterProperty() {
        return normalisationParameter;
    }
    //Colour or greyscale output
    private final BooleanProperty convertToGreyscale = new SimpleBooleanProperty(false);
    @XmlElement(name = "convertToGreyscale")
    public boolean isConvertToGreyscale() {
        return convertToGreyscale.get();
    }
    public void setConvertToGreyscale(boolean value) {
        convertToGreyscale.set(value);
    }
    public BooleanProperty convertToGreyscaleProperty() {
        return convertToGreyscale;
    }

    //
    //
    // Segmentation
    //
    //
    //Enhance edges
    private final BooleanProperty segmentationEnhanceEdges = new SimpleBooleanProperty(false);
    @XmlElement(name = "segmentationEnhanceEdges")
    public boolean isSegmentationEnhanceEdges() {
        return segmentationEnhanceEdges.get();
    }
    public BooleanProperty segmentationEnhanceEdgesProperty() {
        return segmentationEnhanceEdges;
    }
    public void setSegmentationEnhanceEdges(boolean segmentationEnhanceEdges) {
        this.segmentationEnhanceEdges.set(segmentationEnhanceEdges);
    }
    private final BooleanProperty segmentationRescale = new SimpleBooleanProperty(true);
    @XmlElement(name = "segmentationRescale")
    public boolean isSegmentationRescale() {
        return segmentationRescale.get();
    }
    public BooleanProperty segmentationRescaleProperty() {
        return segmentationRescale;
    }
    public void setSegmentationRescale(boolean segmentationRescale) {
        this.segmentationRescale.set(segmentationRescale);
    }
    //Segmentation method
    private final ObjectProperty<SegmentationType> segmentationMethod = new SimpleObjectProperty<>(SegmentationType.OTSU);
    @XmlElement(name = "segmentationMethod")
    public SegmentationType getSegmentationMethod() {
        return segmentationMethod.get();
    }
    public void setSegmentationMethod(SegmentationType value) {
        segmentationMethod.set(value);
    }
    public ObjectProperty segmentationMethodProperty() {
        return segmentationMethod;
    }
    //Segmentation threshold
    private final DoubleProperty segmentationThreshold = new SimpleDoubleProperty(0.4);
    @XmlElement(name = "segmentationThreshold")
    public double getSegmentationThreshold() {
        return segmentationThreshold.get();
    }
    public void setSegmentationThreshold(double value) {
        segmentationThreshold.set(value);
    }
    public DoubleProperty segmentationThresholdProperty() {
        return segmentationThreshold;
    }
    //
    //
    // Segmentation adjustments
    //
    //
    //Centre image?
    private final BooleanProperty centre = new SimpleBooleanProperty(false);
    @XmlElement(name = "centre")
    public boolean getCentre() {
        return centre.get();
    }
    public BooleanProperty centreProperty() {
        return centre;
    }
    public void setCentre(boolean centre) {
        this.centre.set(centre);
    }
    //Rotate to major axis
    private final BooleanProperty rotateToMajorAxis = new SimpleBooleanProperty(false);
    @XmlElement(name = "rotateToMajorAxis")
    public boolean isRotateToMajorAxis() {
        return rotateToMajorAxis.get();
    }
    public BooleanProperty rotateToMajorAxisProperty() {
        return rotateToMajorAxis;
    }
    public void setRotateToMajorAxis(boolean rotateToMajorAxis) {
        this.rotateToMajorAxis.set(rotateToMajorAxis);
    }
    //Remove background
    private final BooleanProperty removeBackground = new SimpleBooleanProperty(false);
    @XmlElement(name = "removeBackground")
    public boolean isRemoveBackground() {
        return removeBackground.get();
    }
    public void setRemoveBackground(boolean value) {
        removeBackground.set(value);
    }
    public BooleanProperty removeBackgroundProperty() {
        return removeBackground;
    }  
    //Background removal margin
    private final IntegerProperty backgroundRemovalMargin = new SimpleIntegerProperty(16);
    @XmlElement(name = "backgroundRemovalMargin")
    public int getBackgroundRemovalMargin() {
        return backgroundRemovalMargin.get();
    }
    public void setBackgroundRemovalMargin(int value) {
        backgroundRemovalMargin.set(value);
    }
    public IntegerProperty backgroundRemovalMarginProperty() {
        return backgroundRemovalMargin;
    }
    //Show Ellipse Fitting
    private final BooleanProperty showEllipseFitting = new SimpleBooleanProperty(false);
    @XmlElement(name = "segmentationEllipseFitting")
    public boolean isShowEllipseFitting() {
        return showEllipseFitting.get();
    }
    public BooleanProperty showEllipseFittingProperty() {
        return showEllipseFitting;
    }
    public void setShowEllipseFitting(boolean showEllipseFitting) {
        this.showEllipseFitting.set(showEllipseFitting);
    }
    //
    //
    // Classification
    //
    //
    //Process before classification
    private final BooleanProperty processBeforeClassification = new SimpleBooleanProperty(true);
    @XmlElement(name = "processBeforeCnn")
    public boolean getProcessBeforeClassification() {
        return processBeforeClassification.get();
    }
    public void setProcessBeforeClassification(boolean value) {
        processBeforeClassification.set(value);
    }
    public BooleanProperty processBeforeClassificationProperty() {
        return processBeforeClassification;
    }
    //Threshold - CNN
    private final DoubleProperty cnnThreshold = new SimpleDoubleProperty(0.8);
    @XmlElement(name = "cnnThreshold")
    public double getCnnThreshold() {
        return cnnThreshold.get();
    }
    public void setCnnThreshold(double value) {
        cnnThreshold.set(value);
    }
    public DoubleProperty cnnThresholdProperty() {
        return cnnThreshold;
    }
    //Threshold - kNN
    private final DoubleProperty knnThreshold = new SimpleDoubleProperty(0.8);
    @XmlElement(name = "knnThreshold")
    public double getKnnThreshold() {
        return knnThreshold.get();
    }
    public void setKnnThreshold(double value) {
        knnThreshold.set(value);
    }
    public DoubleProperty knnThresholdProperty() {
        return knnThreshold;
    }

    public ProcessingInfo() {
        resetToDefaults();
    }

    public void resetToDefaults() {
        //Image
        setImageType(ImageType.DARKONLIGHT);
        //Initial fixes
        setRemoveBlackBorder(false);
        setRemoveWhiteBorder(false);
        setMakeSquare(false);
        //Colour
        setNormalise(false);
        setNormalisationParameter(0.7);
        setConvertToGreyscale(false);
        //Segmentation
        setSegmentationMethod(SegmentationType.INTENSITY);
        setSegmentationEnhanceEdges(false);
        setSegmentationThreshold(0.5);
        //Segmentation adjustments
        setCentre(false);
        setRotateToMajorAxis(false);
        setRemoveBackground(false);
        setBackgroundRemovalMargin(16);
        setShowEllipseFitting(false);
        //Classification settings
        setProcessBeforeClassification(false);
        setCnnThreshold(0.8);
        setKnnThreshold(0.8);
    }
}
