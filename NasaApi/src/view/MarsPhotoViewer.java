package view;

import controller.NasaApiViewerController;
import model.MarsPhoto;
import service.NasaApiService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MarsPhotoViewer extends JFrame {
    private static final String[] ROVER_OPTIONS = {"Curiosity", "Opportunity", "Spirit"};
    private static final String[] CAMERA_OPTIONS = {"FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM", "PANCAM", "MINITES"};

    private JComboBox<String> roverComboBox;
    private JComboBox<String> cameraComboBox;
    private JTextField solTextField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton fetchButton;
    private JPanel photoPanel;
    private JLabel statusLabel;

    private final NasaApiViewerController viewerController;

    public MarsPhotoViewer() {
        setTitle("Mars Photo Viewer Gallery (API)");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS)); // Cambiado a BoxLayout vertical

        // Panel para Rover
        JPanel roverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roverPanel.add(new JLabel("Rover:"));
        roverComboBox = new JComboBox<>(ROVER_OPTIONS);
        roverComboBox.setToolTipText("Seleccione el explorador que desea consultar.");
        roverPanel.add(roverComboBox);

        // Panel para Camera
        JPanel cameraPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cameraPanel.add(new JLabel("Camera:"));
        cameraComboBox = new JComboBox<>(CAMERA_OPTIONS);
        cameraComboBox.setToolTipText("Seleccione la cámara que capturó las fotos.");
        cameraPanel.add(cameraComboBox);

        // Panel para Sol
        JPanel solPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        solPanel.add(new JLabel("Sol:"));
        solTextField = new JTextField(5);
        solTextField.setToolTipText("Ingrese el número de sol (día marciano).");
        solPanel.add(solTextField);

        // Panel para Start Date
        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDatePanel.add(new JLabel("Fecha de Inicio (YYYY-MM-DD):"));
        startDateField = new JTextField(10);
        startDateField.setText("YYYY-MM-DD");
        startDateField.setToolTipText("Ingrese la fecha de inicio en formato YYYY-MM-DD.");
        startDateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (startDateField.getText().equals("YYYY-MM-DD")) {
                    startDateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (startDateField.getText().isEmpty()) {
                    startDateField.setText("YYYY-MM-DD");
                }
            }
        });
        startDatePanel.add(startDateField);

// Panel para End Date
        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        endDatePanel.add(new JLabel("Fecha de Fin (YYYY-MM-DD):"));
        endDateField = new JTextField(10);
        endDateField.setText("YYYY-MM-DD");
        endDateField.setToolTipText("Ingrese la fecha de fin en formato YYYY-MM-DD.");
        endDateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (endDateField.getText().equals("YYYY-MM-DD")) {
                    endDateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (endDateField.getText().isEmpty()) {
                    endDateField.setText("YYYY-MM-DD");
                }
            }
        });
        endDatePanel.add(endDateField);


        // Panel para Fetch Button
        JPanel fetchButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fetchButton = new JButton("Buscar");
        fetchButton.setToolTipText("Haga clic para buscar fotos según los parámetros ingresados.");
        fetchButtonPanel.add(fetchButton);

        // Agregar paneles al controlPanel
        controlPanel.add(roverPanel);
        controlPanel.add(cameraPanel);
        controlPanel.add(solPanel);
        controlPanel.add(startDatePanel);
        controlPanel.add(endDatePanel);
        controlPanel.add(fetchButtonPanel);

        add(controlPanel, BorderLayout.WEST); // Añadido a la izquierda

        photoPanel = new JPanel();
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        add(new JScrollPane(photoPanel), BorderLayout.CENTER);

        statusLabel = new JLabel();
        add(statusLabel, BorderLayout.SOUTH);

        fetchButton.addActionListener(e -> fetchPhotos());

        // Inicialización del controlador
        this.viewerController = new NasaApiViewerController(new NasaApiService());
    }

    private void fetchPhotos() {
        try {
            String rover = (String) roverComboBox.getSelectedItem();
            String camera = (String) cameraComboBox.getSelectedItem();
            String solText = solTextField.getText();
            String startDateText = startDateField.getText();
            String endDateText = endDateField.getText();

            if (solText.isEmpty() || startDateText.isEmpty() || endDateText.isEmpty()) {
                showErrorMessage("Por favor ingrese todos los datos requeridos.");
                return;
            }

            int sol = Integer.parseInt(solText);
            LocalDate startDate = parseDate(startDateText);
            LocalDate endDate = parseDate(endDateText);

            List<MarsPhoto> photos = viewerController.fetchPhotos(rover, sol, camera, startDate, endDate);
            displayPhotos(photos);
            statusLabel.setText("Fotos cargadas correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorMessage("Error al obtener las fotos: " + ex.getMessage());
        }
    }

    private LocalDate parseDate(String dateText) {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException e) {
            showErrorMessage("Formato de fecha inválido. Utilice el formato YYYY-MM-DD.");
            throw e;
        }
    }

    private void displayPhotos(List<MarsPhoto> photos) {
        photoPanel.removeAll(); // Limpiar el panel antes de agregar nuevas fotos

        for (MarsPhoto photo : photos) {
            JPanel singlePhotoPanel = new JPanel();
            singlePhotoPanel.setLayout(new BoxLayout(singlePhotoPanel, BoxLayout.Y_AXIS));
            singlePhotoPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear componentes a la izquierda

            JLabel dateLabel = new JLabel("Date: " + photo.getEarthDate().toString());
            JLabel cameraLabel = new JLabel("Camera: " + photo.getCameraName() + " (" + photo.getCameraFullName() + ")");
            JLabel roverLabel = new JLabel("Rover: " + photo.getRoverName() + " - Sol " + photo.getRoverMaxSol());

            dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            cameraLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            roverLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Obtener la URL de la imagen
            String imageUrlStr = photo.getImgSrc();

            // Crear un JLabel como enlace para abrir la URL en el navegador
            JLabel urlLabel = new JLabel("<html><a href=\"" + imageUrlStr + "\">" + imageUrlStr + "</a></html>");
            urlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cambiar el cursor al estilo de mano
            urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            urlLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(new URI(imageUrlStr));
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                            showErrorMessage("Error al abrir la URL: " + ex.getMessage());
                        }
                    } else {
                        showErrorMessage("No se puede abrir el navegador en este sistema.");
                    }
                }
            });

            // Agregar los detalles de la foto y el enlace al panel
            singlePhotoPanel.add(urlLabel);
            singlePhotoPanel.add(dateLabel);
            singlePhotoPanel.add(cameraLabel);
            singlePhotoPanel.add(roverLabel);

            photoPanel.add(singlePhotoPanel); // Agregar el panel de la foto al panel principal
        }

        photoPanel.revalidate(); // Revalidar el panel para actualizar la interfaz gráfica
        photoPanel.repaint(); // Repintar el panel para mostrar los cambios
    }




    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        statusLabel.setText(message);
    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this,
                "Instrucciones de uso:\n\n" +
                        "- Rover: Seleccione el explorador de Marte.\n" +
                        "- Cámara: Seleccione la cámara que capturó las fotos.\n" +
                        "- Sol: Ingrese el número de sol (día en Marte).\n" +
                        "- Fecha de Inicio y Fin: Ingrese el rango de fechas para la búsqueda de fotos.\n\n" +
                        "Para más detalles, consulte la documentación de ayuda.",
                "Ayuda",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MarsPhotoViewer viewer = new MarsPhotoViewer();
            viewer.setVisible(true);
        });
    }
}
