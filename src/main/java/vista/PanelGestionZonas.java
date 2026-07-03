package vista;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Concierto;
import modelo.EmisorTarjeta;
import modelo.Zona;

/**
 * Panel del administrador para gestionar las zonas de un concierto (agregar,
 * renombrar y cambiar precios) y configurar los descuentos por emisor de
 * tarjeta de forma independiente para cada concierto.
 */
public class PanelGestionZonas extends javax.swing.JPanel {

    private final javax.swing.JComboBox<String> cboConciertos = new javax.swing.JComboBox<>();
    private final javax.swing.JButton btnCargar = new javax.swing.JButton("Cargar");
    private final javax.swing.JTable tablaZonas = new javax.swing.JTable();

    private final javax.swing.JTextField txtNombreZona = new javax.swing.JTextField();
    private final javax.swing.JTextField txtCapacidadZona = new javax.swing.JTextField();
    private final javax.swing.JTextField txtPrecioZona = new javax.swing.JTextField();

    private final javax.swing.JButton btnAgregarZona = new javax.swing.JButton("Agregar Zona");
    private final javax.swing.JButton btnGuardarCambios = new javax.swing.JButton("Guardar Cambios Zona");

    private final javax.swing.JTextField txtDescVisa = new javax.swing.JTextField();
    private final javax.swing.JTextField txtDescMastercard = new javax.swing.JTextField();
    private final javax.swing.JTextField txtDescDiners = new javax.swing.JTextField();
    private final javax.swing.JTextField txtDescAmex = new javax.swing.JTextField();
    private final javax.swing.JButton btnGuardarDescuentos = new javax.swing.JButton("Guardar Descuentos");

    private final javax.swing.JButton btnVolver = new javax.swing.JButton("Volver");

    public PanelGestionZonas() {
        construirInterfaz();
    }

    private void construirInterfaz() {
        javax.swing.JPanel panelFormulario = new javax.swing.JPanel();
        panelFormulario.setPreferredSize(new java.awt.Dimension(560, 470));
        panelFormulario.setLayout(null);

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("Gestión de Zonas y Descuentos");
        lblTitulo.setFont(new java.awt.Font("Nimbus Mono PS", 1, 22));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setBounds(20, 5, 520, 30);
        panelFormulario.add(lblTitulo);

        javax.swing.JLabel lblConcierto = new javax.swing.JLabel("Concierto:");
        lblConcierto.setBounds(20, 45, 80, 25);
        panelFormulario.add(lblConcierto);

        cboConciertos.setBounds(100, 45, 250, 25);
        panelFormulario.add(cboConciertos);

        btnCargar.setBounds(360, 45, 110, 25);
        panelFormulario.add(btnCargar);

        tablaZonas.setModel(new DefaultTableModel(
                new Object[][]{}, new String[]{"Nombre", "Capacidad", "Precio"}) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tablaZonas);
        scroll.setBounds(20, 85, 520, 120);
        panelFormulario.add(scroll);

        javax.swing.JLabel lblNombre = new javax.swing.JLabel("Nombre:");
        lblNombre.setBounds(20, 215, 55, 25);
        panelFormulario.add(lblNombre);
        txtNombreZona.setBounds(75, 215, 120, 25);
        panelFormulario.add(txtNombreZona);

        javax.swing.JLabel lblCapacidad = new javax.swing.JLabel("Capacidad:");
        lblCapacidad.setBounds(205, 215, 70, 25);
        panelFormulario.add(lblCapacidad);
        txtCapacidadZona.setBounds(275, 215, 60, 25);
        panelFormulario.add(txtCapacidadZona);

        javax.swing.JLabel lblPrecio = new javax.swing.JLabel("Precio:");
        lblPrecio.setBounds(350, 215, 50, 25);
        panelFormulario.add(lblPrecio);
        txtPrecioZona.setBounds(400, 215, 70, 25);
        panelFormulario.add(txtPrecioZona);

        btnAgregarZona.setBounds(20, 250, 150, 28);
        panelFormulario.add(btnAgregarZona);

        btnGuardarCambios.setBounds(180, 250, 200, 28);
        panelFormulario.add(btnGuardarCambios);

        javax.swing.JLabel lblAyuda = new javax.swing.JLabel(
                "(Para editar: seleccione una fila, cambie Nombre/Precio y pulse Guardar Cambios Zona)");
        lblAyuda.setFont(new java.awt.Font("Liberation Sans", 0, 10));
        lblAyuda.setBounds(20, 280, 520, 18);
        panelFormulario.add(lblAyuda);

        javax.swing.JLabel lblDesc = new javax.swing.JLabel("Descuentos por emisor de tarjeta (%)");
        lblDesc.setFont(new java.awt.Font("Nimbus Mono PS", 1, 16));
        lblDesc.setBounds(20, 305, 520, 25);
        panelFormulario.add(lblDesc);

        javax.swing.JLabel lblVisa = new javax.swing.JLabel("Visa:");
        lblVisa.setBounds(20, 340, 80, 25);
        panelFormulario.add(lblVisa);
        txtDescVisa.setBounds(110, 340, 55, 25);
        panelFormulario.add(txtDescVisa);

        javax.swing.JLabel lblMaster = new javax.swing.JLabel("Mastercard:");
        lblMaster.setBounds(190, 340, 90, 25);
        panelFormulario.add(lblMaster);
        txtDescMastercard.setBounds(285, 340, 55, 25);
        panelFormulario.add(txtDescMastercard);

        javax.swing.JLabel lblDiners = new javax.swing.JLabel("Diners Club:");
        lblDiners.setBounds(20, 375, 90, 25);
        panelFormulario.add(lblDiners);
        txtDescDiners.setBounds(110, 375, 55, 25);
        panelFormulario.add(txtDescDiners);

        javax.swing.JLabel lblAmex = new javax.swing.JLabel("Amex:");
        lblAmex.setBounds(190, 375, 90, 25);
        panelFormulario.add(lblAmex);
        txtDescAmex.setBounds(285, 375, 55, 25);
        panelFormulario.add(txtDescAmex);

        btnGuardarDescuentos.setBounds(360, 340, 180, 28);
        panelFormulario.add(btnGuardarDescuentos);

        btnVolver.setBounds(360, 375, 180, 28);
        panelFormulario.add(btnVolver);

        this.setLayout(new java.awt.GridBagLayout());
        this.add(panelFormulario);
        this.revalidate();
        this.repaint();
    }

    // --- Metodos de ayuda para el controlador ---

    public void poblarConciertos(Concierto[] conciertos) {
        cboConciertos.removeAllItems();
        for (Concierto c : conciertos) {
            cboConciertos.addItem(c.getNombre());
        }
    }

    public void poblarZonas(List<Zona> zonas) {
        DefaultTableModel modelo = (DefaultTableModel) tablaZonas.getModel();
        modelo.setRowCount(0);
        for (Zona z : zonas) {
            modelo.addRow(new Object[]{z.getNombre(), z.getCapacidad(), z.getPrecio()});
        }
    }

    public void cargarDescuentos(Concierto concierto) {
        txtDescVisa.setText(String.valueOf(concierto.getDescuento(EmisorTarjeta.VISA)));
        txtDescMastercard.setText(String.valueOf(concierto.getDescuento(EmisorTarjeta.MASTERCARD)));
        txtDescDiners.setText(String.valueOf(concierto.getDescuento(EmisorTarjeta.DINERS)));
        txtDescAmex.setText(String.valueOf(concierto.getDescuento(EmisorTarjeta.AMEX)));
    }

    public void limpiarCamposZona() {
        txtNombreZona.setText("");
        txtCapacidadZona.setText("");
        txtPrecioZona.setText("");
    }

    // --- Getters ---
    public javax.swing.JComboBox<String> getCboConciertos() { return cboConciertos; }
    public javax.swing.JButton getBtnCargar() { return btnCargar; }
    public javax.swing.JTable getTablaZonas() { return tablaZonas; }
    public javax.swing.JTextField getTxtNombreZona() { return txtNombreZona; }
    public javax.swing.JTextField getTxtCapacidadZona() { return txtCapacidadZona; }
    public javax.swing.JTextField getTxtPrecioZona() { return txtPrecioZona; }
    public javax.swing.JButton getBtnAgregarZona() { return btnAgregarZona; }
    public javax.swing.JButton getBtnGuardarCambios() { return btnGuardarCambios; }
    public javax.swing.JTextField getTxtDescVisa() { return txtDescVisa; }
    public javax.swing.JTextField getTxtDescMastercard() { return txtDescMastercard; }
    public javax.swing.JTextField getTxtDescDiners() { return txtDescDiners; }
    public javax.swing.JTextField getTxtDescAmex() { return txtDescAmex; }
    public javax.swing.JButton getBtnGuardarDescuentos() { return btnGuardarDescuentos; }
    public javax.swing.JButton getBtnVolver() { return btnVolver; }
}
