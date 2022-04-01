package view;

import controller.BiletController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Bilet;
import repositories.RepositoryException;

public class BileteView {
    BorderPane pane;
    BiletController biletcontroller;
    TextField biletIdText, nrLocText, nrRandText, pretText;

    public BileteView(BiletController biletcontroller) {
        this.biletcontroller = biletcontroller;
        initView();
    }

    private void initView() {
        pane = new BorderPane();
        pane.setRight(createBilet());
        pane.setCenter(createTable());
    }

    public BorderPane getView() {
        return pane;
    }

    private TableView<Bilet> table = new TableView<>();

    protected StackPane createTable() {
        StackPane pane = new StackPane();
        initBileteView();
        pane.getChildren().add(table);
        return pane;
    }

    private void initBileteView() {
        TableColumn<Bilet, Integer> idCol = new TableColumn<>("Id");
        TableColumn<Bilet, Integer> pretCol = new TableColumn<>("Pret");
        TableColumn<Bilet, Integer> locCol = new TableColumn<>("NrLoc");
        TableColumn<Bilet, Integer> randCol = new TableColumn<>("NrRand");

        table.getColumns().addAll(idCol, pretCol, locCol, randCol);

        //stabilirea valorilor asociate unei celule
        idCol.setCellValueFactory(new PropertyValueFactory<Bilet, Integer>("id")); //
        pretCol.setCellValueFactory(new PropertyValueFactory<Bilet, Integer>("pret"));
        locCol.setCellValueFactory(new PropertyValueFactory<Bilet, Integer>("nr_loc"));
        randCol.setCellValueFactory(new PropertyValueFactory<Bilet, Integer>("nr_rand"));

        table.setItems(biletcontroller.getBiletModel());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
// Listen for selection changes and show the SortingTask details when changed.
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newValue) -> showSortingTaskDetails(newValue));
    }

    private void showSortingTaskDetails(Bilet value) {
        if (value == null)
            clearFields();
        else {
            biletIdText.setText("" + value.getId());
            pretText.setText("" + value.getPret());
            nrLocText.setText("" + value.getNr_loc());
            nrRandText.setText("" + value.getNr_rand());
        }
    }

    protected GridPane createBilet() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        Text scenetitle = new Text("Bilete");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        scenetitle.setFill(Color.BLUE);
        grid.add(scenetitle, 0, 0, 2, 1);
        //id
        Label biletID = new Label("Id:");
        grid.add(biletID, 0, 1);
        biletIdText = new TextField();
        grid.add(biletIdText, 1, 1);
        //pret
        Label pret = new Label("Pret:");
        grid.add(pret, 0, 2);
        pretText = new TextField();
        grid.add(pretText, 1, 2);
        //nr loc
        Label nrLoc = new Label("Numar Loc:");
        grid.add(nrLoc, 0, 3);
        nrLocText = new TextField();
        grid.add(nrLocText, 1, 3);
        //nr Rand
        Label nrRand = new Label("Numar Rand:");
        grid.add(nrRand, 0, 4);
        nrRandText = new TextField();
        grid.add(nrRandText, 1, 4);


        Button addBilet = new Button("Add");
        Button deleteBilet = new Button("Delete");
        Button updateBilet = new Button("Update");
        Button cancel = new Button("Cancel");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        addBilet.setOnAction(x -> addButton());
        //deleteBilet.setOnAction(x -> handleDelete());
        //updateBilet.setOnAction(x -> handleUpdateTask());
        cancel.setOnAction(x -> cancelButton());
        hbBtn.getChildren().addAll(addBilet, deleteBilet, updateBilet, cancel);
        grid.add(hbBtn, 0, 6, 2, 1);


        return grid;
    }

    private void cancelButton() {
        //table.getSelectionModel().clearSelection();
        clearFields();
    }

    private void clearFields() {
        biletIdText.setText("");
        pretText.setText("");
        nrLocText.setText("");
        nrRandText.setText("");

    }

    private void addButton() {
        String id = biletIdText.getText();
        String pret = pretText.getText();
        String loc = nrLocText.getText();
        String rand = nrRandText.getText();

        try {

            biletcontroller.add(new Bilet(Integer.valueOf(id), Integer.valueOf(pret), Integer.valueOf(rand), Integer.valueOf(loc)));
            clearFields();

        } catch (NumberFormatException ex) {
            showErrorMessage("Id-ul si nr elem trebuie sa fie numere intregi! " + ex.getMessage());
        } catch (RepositoryException ex) {
            showErrorMessage("Eroare la adaugare: " + ex.getMessage());
        }
    }

    static void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}