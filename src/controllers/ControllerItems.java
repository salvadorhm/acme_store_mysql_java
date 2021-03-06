/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.JOptionPane;
import models.Modeltems;
import views.ViewItems;

/**
 *
 * @author sax
 */
public class ControllerItems {

    public Modeltems modeltems;
    public ViewItems viewItems;

    public ControllerItems(Modeltems modeltems, ViewItems viewItems) {
        this.modeltems = modeltems;
        this.viewItems = viewItems;

        this.viewItems.jbtnPrimero.addActionListener(actionListener);
        this.viewItems.jbtnSiguiente.addActionListener(actionListener);
        this.viewItems.jbtnAnterior.addActionListener(actionListener);
        this.viewItems.jbtnUltimo.addActionListener(actionListener);
        this.viewItems.jbtnAgregar.addActionListener(actionListener);
        this.viewItems.jbtnEliminar.addActionListener(actionListener);
        this.viewItems.jbtnEditar.addActionListener(actionListener);
        this.viewItems.jbtnBuscar.addActionListener(actionListener);
        this.viewItems.jbtnCancelar.addActionListener(actionListener);
        this.viewItems.jbtnReset.addActionListener(actionListener);

        this.viewItems.jtRegistros.setModel(modeltems.getTabla_productos());

        this.viewItems.jtfPrecioCompra.addKeyListener(keyAdapter);
        this.viewItems.jtfPrecioVenta.addKeyListener(keyAdapter);
        this.viewItems.jtfExistencias.addKeyListener(keyAdapter);

        initView();
    }

    private void initView() {
        showValues();
        viewItems.setVisible(true);
    }

    private void showValues() {
        viewItems.jtfId_producto.setText("" + modeltems.getId_producto());
        viewItems.jtfProducto.setText("" + modeltems.getProducto());
        viewItems.jtfExistencias.setText("" + modeltems.getExistencias());
        viewItems.jtaDescripcion.setText("" + modeltems.getDescripcion());
        viewItems.jtfPrecioCompra.setText("" + modeltems.getPrecio_compra());
        viewItems.jtfPrecioVenta.setText("" + modeltems.getPrecio_venta());
    }

    private void clearValues() {
        viewItems.jtfId_producto.setText("");
        viewItems.jtfProducto.setText("");
        viewItems.jtfExistencias.setText("");
        viewItems.jtaDescripcion.setText("");
        viewItems.jtfPrecioCompra.setText("");
        viewItems.jtfPrecioVenta.setText("");
    }

    private boolean setValues() {
        try {
            if (viewItems.jtfProducto.getText().length() == 0) {
                throw new UnsupportedOperationException("Campo vacio");
            }
            if (viewItems.jtaDescripcion.getText().length() == 0) {
                throw new UnsupportedOperationException("Campo vacio");
            }
            //modeltems.setId_producto(Integer.parseInt(viewItems.jtfId_producto.getText()));
            modeltems.setProducto(viewItems.jtfProducto.getText());
            modeltems.setExistencias(Float.parseFloat(viewItems.jtfExistencias.getText()));
            modeltems.setDescripcion(viewItems.jtaDescripcion.getText());
            modeltems.setPrecio_compra(Float.parseFloat(viewItems.jtfPrecioCompra.getText()));
            modeltems.setPrecio_venta(Float.parseFloat(viewItems.jtfPrecioVenta.getText()));
            return true;
        } catch (NumberFormatException | UnsupportedOperationException e) {
            JOptionPane.showMessageDialog(viewItems, "Verificar los datos");
            System.err.println(e.getMessage());
        }
        return false;
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewItems.jbtnPrimero) {
                jbtnPrimeroActionPerformed();
            } else if (e.getSource() == viewItems.jbtnAnterior) {
                jbtnAnteriorActionPerformed();
            } else if (e.getSource() == viewItems.jbtnSiguiente) {
                jbtnSiguienteActionPerformed();
            } else if (e.getSource() == viewItems.jbtnUltimo) {
                jbtnUltimoActionPerformed();
            } else if (e.getSource() == viewItems.jbtnAgregar) {
                jbtnAgregarActionPerformed();
            } else if (e.getSource() == viewItems.jbtnCancelar) {
                jbtnCancelarActionPerformed();
            } else if (e.getSource() == viewItems.jbtnEditar) {
                jbtnEditarActionPerformed();
            } else if (e.getSource() == viewItems.jbtnEliminar) {
                jbtnEliminarActionPerformed();
            } else if (e.getSource() == viewItems.jbtnBuscar) {
                jbtnBuscarActionPerformed();
            } else if (e.getSource() == viewItems.jbtnReset) {
                jbtnResetActionPerformed();
            }
        }
    };

    private final KeyAdapter keyAdapter = new java.awt.event.KeyAdapter() {
        @Override
        public void keyTyped(java.awt.event.KeyEvent evt) {
            char c = evt.getKeyChar();
            if (((c < '0') || (c > '9')) && (c != '\b') && (c != '.')) {
                evt.consume();  // ignorar el evento de teclado
            }
        }
    };

    private void jbtnPrimeroActionPerformed() {
        modeltems.moveFirst();
        showValues();
    }

    private void jbtnAnteriorActionPerformed() {
        modeltems.movePrevious();
        showValues();
    }

    private void jbtnSiguienteActionPerformed() {
        modeltems.moveNext();
        showValues();
    }

    private void jbtnUltimoActionPerformed() {
        modeltems.moveLast();
        showValues();
    }

    private void jbtnAgregarActionPerformed() {
        if (viewItems.jbtnAgregar.getText().equals("Agregar")) {
            clearValues();
            componentsState(false);
            viewItems.jbtnAgregar.setEnabled(true);
            viewItems.jbtnAgregar.setText("Guardar");
        } else if (setValues()) {
            if (modeltems.add() == false) {
                JOptionPane.showMessageDialog(viewItems, "Error al insertar");
            } else {
                showValues();
                this.viewItems.jtRegistros.setModel(modeltems.getTabla_productos());
                JOptionPane.showMessageDialog(viewItems, "Registro insertado");
                viewItems.jbtnAgregar.setText("Agregar");
                componentsState(true);
            }
        }
    }

    private void componentsState(boolean state) {
        viewItems.jbtnPrimero.setEnabled(state);
        viewItems.jbtnAnterior.setEnabled(state);
        viewItems.jbtnSiguiente.setEnabled(state);
        viewItems.jbtnUltimo.setEnabled(state);
        viewItems.jbtnEliminar.setEnabled(state);
        viewItems.jbtnEditar.setEnabled(state);
        viewItems.jbtnBuscar.setEnabled(state);
        viewItems.jbtnAgregar.setEnabled(state);
        viewItems.jtfProducto.setEditable(!state);
        viewItems.jtfExistencias.setEditable(!state);
        viewItems.jtaDescripcion.setEditable(!state);
        viewItems.jtfPrecioCompra.setEditable(!state);
        viewItems.jtfPrecioVenta.setEditable(!state);
        viewItems.jbtnCancelar.setEnabled(!state);
        viewItems.jtRegistros.setEnabled(state);
        viewItems.jtfProducto.requestFocus();
    }

    private void jbtnEliminarActionPerformed() {
        if (viewItems.jbtnEliminar.getText().equals("Eliminar")) {
            componentsState(false);
            viewItems.jbtnEliminar.setEnabled(true);
            viewItems.jbtnEliminar.setText("Realizar");
        } else if (setValues()) {
            modeltems.setId_producto(Integer.parseInt(viewItems.jtfId_producto.getText()));
            if (modeltems.del() == false) {
                JOptionPane.showMessageDialog(viewItems, "Error al eliminar");
            } else {
                showValues();
                this.viewItems.jtRegistros.setModel(modeltems.getTabla_productos());
                JOptionPane.showMessageDialog(viewItems, "Registro eliminado");
                viewItems.jbtnEliminar.setText("Eliminar");
                componentsState(true);
            }
        }
    }

    private void jbtnEditarActionPerformed() {
        if (viewItems.jbtnEditar.getText().equals("Editar")) {
            componentsState(false);
            viewItems.jbtnEditar.setEnabled(true);
            viewItems.jbtnEditar.setText("Guardar");
        } else if (setValues()) {
            modeltems.setId_producto(Integer.parseInt(viewItems.jtfId_producto.getText()));
            if (modeltems.edit() == false) {
                JOptionPane.showMessageDialog(viewItems, "Error al editar");
            } else {
                showValues();
                this.viewItems.jtRegistros.setModel(modeltems.getTabla_productos());
                JOptionPane.showMessageDialog(viewItems, "Registro editado");
                viewItems.jbtnEditar.setText("Editar");
                componentsState(true);
            }
        }

    }

    private void jbtnBuscarActionPerformed() {
        if (viewItems.jbtnBuscar.getText().equals("Buscar")) {
            clearValues();
            componentsState(false);
            viewItems.jbtnBuscar.setEnabled(true);
            viewItems.jbtnBuscar.setText("Realizar");
        } else {
            modeltems.setProducto(viewItems.jtfProducto.getText());
            if (modeltems.search() == false) {
                JOptionPane.showMessageDialog(viewItems, "Registro no encontrado");
            } else {
                showValues();
                this.viewItems.jtRegistros.setModel(modeltems.getTabla_productos());
                JOptionPane.showMessageDialog(viewItems, "Registro encontrado");
                viewItems.jbtnBuscar.setText("Buscar");
                componentsState(true);
            }
        }
    }

    private void jbtnCancelarActionPerformed() {
        componentsState(true);
        showValues();
        this.viewItems.jtRegistros.setModel(modeltems.getTabla_productos());
        viewItems.jbtnAgregar.setText("Agregar");
        viewItems.jbtnEditar.setText("Editar");
        viewItems.jbtnEliminar.setText("Eliminar");
        viewItems.jbtnBuscar.setText("Buscar");
    }

    private void jbtnResetActionPerformed() {
        System.out.println("controllers.ControllerItems.jbtnResetActionPerformed()");
        showValues();
    }

}
