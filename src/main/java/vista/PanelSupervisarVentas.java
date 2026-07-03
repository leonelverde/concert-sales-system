/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Venta;

/**
 *
 * @author leonel
 *
 * Panel del administrador para supervisar las ventas y cancelar (anular)
 * compras de los clientes.
 */
public class PanelSupervisarVentas extends javax.swing.JPanel {

    private final javax.swing.JTable tablaVentas = new javax.swing.JTable();
    private final javax.swing.JButton btnRefrescar = new javax.swing.JButton("Actualizar");
    private final javax.swing.JButton btnCancelar = new javax.swing.JButton("Cancelar Compra");
    private final javax.swing.JButton btnVolver = new javax.swing.JButton("Volver");

    public PanelSupervisarVentas() {
        construirInterfaz();
    }

    private void construirInterfaz() {
        javax.swing.JPanel panelFormulario = new javax.swing.JPanel();
        panelFormulario.setPreferredSize(new java.awt.Dimension(560, 440));
        panelFormulario.setLayout(null);

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("Supervisar Ventas");
        lblTitulo.setFont(new java.awt.Font("Nimbus Mono PS", 1, 24));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setBounds(20, 10, 520, 35);
        panelFormulario.add(lblTitulo);

        tablaVentas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Concierto", "Cliente", "Zona", "Entradas", "Monto", "Fecha", "Estado"}) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        });
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tablaVentas);
        scroll.setBounds(20, 60, 520, 300);
        panelFormulario.add(scroll);

        btnVolver.setBounds(60, 385, 130, 30);
        panelFormulario.add(btnVolver);

        btnCancelar.setBounds(215, 385, 170, 30);
        panelFormulario.add(btnCancelar);

        btnRefrescar.setBounds(410, 385, 130, 30);
        panelFormulario.add(btnRefrescar);

        this.setLayout(new java.awt.GridBagLayout());
        this.add(panelFormulario);
        this.revalidate();
        this.repaint();
    }

    public void poblarTabla(Venta[] ventas) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
        modelo.setRowCount(0);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Venta v : ventas) {
            String concierto = v.getNombreConcierto() != null ? v.getNombreConcierto() : "-";
            String nombreCliente = nombreDe(v.getCliente());
            String zona = v.getZona() != null ? v.getZona().getNombre() : "-";
            int entradas = v.getEntradas() != null ? v.getEntradas().size() : 0;
            String fecha = v.getFecha() != null ? formato.format(v.getFecha()) : "-";
            String estado = v.isAnulada() ? "ANULADA" : "Activa";

            modelo.addRow(new Object[]{
                concierto, nombreCliente, zona, entradas, "S/ " + v.getMonto(), fecha, estado
            });
        }
    }

    private String nombreDe(Cliente cliente) {
        if (cliente == null) {
            return "-";
        }
        return cliente.getNombres() + " " + cliente.getApellidos();
    }

    public javax.swing.JTable getTablaVentas() { return tablaVentas; }
    public javax.swing.JButton getBtnRefrescar() { return btnRefrescar; }
    public javax.swing.JButton getBtnCancelar() { return btnCancelar; }
    public javax.swing.JButton getBtnVolver() { return btnVolver; }
}
