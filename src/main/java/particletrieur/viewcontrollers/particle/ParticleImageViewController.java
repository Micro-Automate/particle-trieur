/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particletrieur.viewcontrollers.particle;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import particletrieur.AbstractController;
import particletrieur.App;
import particletrieur.FxmlLocation;
import particletrieur.controls.ImageViewPane;
import particletrieur.controls.SymbolLabel;
import particletrieur.models.Supervisor;
import particletrieur.models.project.Particle;
import particletrieur.viewmodels.SelectionViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Ross Marchant <ross.g.marchant@gmail.com>
 */
@FxmlLocation("/views/particle/ImageDetailView.fxml")
public class ParticleImageViewController extends AbstractController implements Initializable {

    @FXML
    Button buttonLock;
    @FXML
    ImageViewPane imageViewPane;
    @FXML
    BorderPane borderPane;
    @FXML
    StackPane stackPaneImage;
    @FXML
    SymbolLabel symbolValidated;
    @FXML
    Label labelValidator;
    @FXML
    Label labelClassifierId;
    @FXML
    ScrollPane scrollPane;
    @FXML
    GridPane gridPane;
    @FXML
    Label labelImageLabel;
    @FXML
    ImageView imageView;
    @FXML
    Label labelImageNumber;

    @FXML
    Label labelError;
    @FXML
    Label labelClass;
    @FXML
    Label labelTag;
    @FXML
    Label labelSample;
    @FXML
    Label labelFilename;
    @FXML
    Label labelPath;
    @FXML
    Label labelInfo;
    @FXML
    Label labelGUID;

    @Inject
    private SelectionViewModel selectionViewModel;
    @Inject
    private Supervisor supervisor;

    public ParticleImageViewController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        borderPane.getCenter().
//        stackPaneImage.maxWidthProperty().bind(borderPane.widthProperty());
//        imageView.fitHeightProperty().bind(Bindings.subtract(stackPaneImage.heightProperty(), 10));
//        imageView.fitWidthProperty().bind(Bindings.subtract(borderPane.widthProperty(), 14));
//        scrollPane.maxWidthProperty().bind(Bindings.add(imageView.fitWidthProperty(),14));
//        gridPane.maxWidthProperty().bind(imageView.fitWidthProperty());
//        scrollPane.minWidthProperty().bind(Bindings.add(imageView.fitWidthProperty(),14));
//        gridPane.minWidthProperty().bind(imageView.fitWidthProperty());
//        borderPane.widthProperty().addListener((observable, oldValue, newValue) -> {
//            imageView.setFitWidth((double)newValue);
//        });
        selectionViewModel.currentParticleProperty().addListener((observable, oldValue, newValue) -> {
            setData(newValue);
        });
        gridPane.setVisible(false);
        symbolValidated.setVisible(false);
        labelValidator.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals("")) symbolValidated.setVisible(false);
            else symbolValidated.setVisible(true);
        }));

        imageViewPane.addZoomEvents();

        selectionViewModel.decreaseSizeRequested.addListener(v -> {
            handleZoomOut(null);
        });

        selectionViewModel.increaseSizeRequested.addListener(v -> {
            handleZoomIn(null);
        });
        imageViewPane.lockScaleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                buttonLock.setStyle("-fx-background-color: lightgreen");
            }
            else {
                buttonLock.setStyle("");
            }
        }));
    }    
    
    public void setImage(Image image) {
        imageViewPane.setImage(image);
    }

    //TODO on error write error image
    public void setData(Particle particle) {
        if (particle == null) {
            setImage(new Image(App.class.getResourceAsStream("/icons/icon-thin.png"),512, 512,true,true));
            gridPane.setVisible(false);
            labelImageNumber.setText("No Images");
            try {
                labelImageLabel.textProperty().unbind();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            labelImageLabel.setText("");
            return;
        }
        gridPane.setVisible(true);
        try {
            if (particle.getFile().exists()) {
                Image image = particle.getImage();
                supervisor.project.setParticleShape(particle, (int) image.getWidth(), (int) image.getHeight());
                setImage(image);
            }
            else {
                setImage(new Image(App.class.getResourceAsStream("/icons/missing-image-512.png"),512, 512,true,true));
            }
//            labelError.setVisible(false);
//            imageView.setVisible(true);
        } catch (IOException e) {
            setImage(new Image(App.class.getResourceAsStream("/icons/missing-image-512.png"),512, 512,true,true));
//            e.printStackTrace();
//            labelError.setVisible(true);
//            imageView.setVisible(false);
        }

        labelImageNumber.setText(String.format("Image #%d", supervisor.project.particles.indexOf(particle) + 1));

        //Had some weird intermittent bug with unbinding that makes no sense (ArrayIndexOutOfBounds)
        try {
            labelClass.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelClass.textProperty().bind(particle.classification);

        try {
            labelTag.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelTag.textProperty().bind(particle.tagUIProperty);

        try {
            labelImageLabel.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelImageLabel.textProperty().bind(particle.classification);

        try {
            labelSample.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelSample.textProperty().bind(particle.sampleIDProperty());

        try {
            labelClassifierId.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelClassifierId.textProperty().bind(particle.classifierIdProperty);

        try {
            labelValidator.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelValidator.textProperty().bind(particle.validatorProperty());

        labelFilename.setText(particle.getShortFilename());
        labelPath.setText(particle.getFolder());

        labelInfo.setText(String.format("%d x %d pixels", particle.getImageHeight(), particle.getImageWidth()));

        labelGUID.setText(particle.getGUID());

    }

    public void handlePrevious(ActionEvent actionEvent) {
        selectionViewModel.requestPreviousImage();
    }

    public void handleNext(ActionEvent actionEvent) {
        selectionViewModel.requestNextImage();
    }

    public void handleZoomIn(ActionEvent actionEvent) {
        imageViewPane.zoom(imageViewPane.getWidth() / 2, imageViewPane.getHeight() / 2, -52);
    }

    public void handleZoomOut(ActionEvent actionEvent) {
        imageViewPane.zoom(imageViewPane.getWidth() / 2, imageViewPane.getHeight() / 2, 52);    }

    public void handleResetZoom(ActionEvent actionEvent) {
        imageViewPane.reset();
    }

    public void handleLock(ActionEvent actionEvent) {
        imageViewPane.setLockScale(!imageViewPane.isLockScale());
    }
}
