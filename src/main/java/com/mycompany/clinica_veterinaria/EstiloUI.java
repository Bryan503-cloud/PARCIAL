package com.mycompany.clinica_veterinaria;

/**
 * Clase utilitaria que centraliza todo el estilo visual de la aplicación.
 * Paleta clásica azul oscuro — buen contraste, fuentes legibles.
 */
public class EstiloUI {

    // ── Paleta clásica ───────────────────────────────────────────────────────
    static final java.awt.Color AZUL_MENU    = new java.awt.Color(31,  78, 121);  // menú y headers
    static final java.awt.Color AZUL_OSCURO  = new java.awt.Color(20,  52,  90);  // bordes y sombras
    static final java.awt.Color AZUL_HOVER   = new java.awt.Color(52, 110, 165);  // hover en menú
    static final java.awt.Color AZUL_TABLA   = new java.awt.Color(31,  78, 121);  // cabecera tabla
    static final java.awt.Color FILA_PAR     = java.awt.Color.WHITE;
    static final java.awt.Color FILA_IMPAR   = new java.awt.Color(232, 241, 250); // contraste suave
    static final java.awt.Color SELECCION    = new java.awt.Color(155, 200, 235); // fila seleccionada
    static final java.awt.Color FONDO_FORM   = new java.awt.Color(248, 249, 251); // fondo formularios
    static final java.awt.Color FONDO_DESK   = new java.awt.Color(210, 225, 242); // escritorio MDI
    static final java.awt.Color TEXTO_OSCURO = new java.awt.Color(25,  25,  25);  // texto principal
    static final java.awt.Color TEXTO_LABEL  = new java.awt.Color(15,  55, 100);  // labels de form

    // Fuentes
    private static final java.awt.Font FONT_NORMAL  = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13);
    private static final java.awt.Font FONT_BOLD    = new java.awt.Font("Segoe UI", java.awt.Font.BOLD,  13);
    private static final java.awt.Font FONT_MENU    = new java.awt.Font("Segoe UI", java.awt.Font.BOLD,  13);
    private static final java.awt.Font FONT_TABLA   = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13);
    private static final java.awt.Font FONT_HEADER  = new java.awt.Font("Segoe UI", java.awt.Font.BOLD,  13);

    // ════════════════════════════════════════════════════════════════════════
    // API pública
    // ════════════════════════════════════════════════════════════════════════

    /** Estilo completo para JInternalFrames con tabla + botones. */
    public static void aplicarVentana(java.awt.Container contentPane,
                                      javax.swing.JPanel header,
                                      javax.swing.JTable[] tablas,
                                      javax.swing.JButton[] botones) {
        contentPane.setBackground(FONDO_FORM);
        if (header != null)
            header.setBorder(javax.swing.BorderFactory.createMatteBorder(
                0, 0, 3, 0, AZUL_OSCURO));
        for (javax.swing.JTable t : tablas)   estilizarTabla(t);
        for (javax.swing.JButton b : botones) estilizarBoton(b);
        // Recorre todos los componentes y mejora combos, spinners y campos
        recorrerYEstilizar(contentPane);
    }

    /** Estilo para Login. */
    public static void aplicarLogin(java.awt.Container contentPane,
                                    javax.swing.JPanel header,
                                    javax.swing.JTextField[] campos,
                                    javax.swing.JButton[] botones) {
        contentPane.setBackground(new java.awt.Color(238, 243, 250));
        if (header != null)
            header.setBorder(javax.swing.BorderFactory.createMatteBorder(
                0, 0, 3, 0, AZUL_OSCURO));
        for (javax.swing.JTextField c : campos) {
            c.setBackground(java.awt.Color.WHITE);
            c.setForeground(TEXTO_OSCURO);
            c.setFont(FONT_NORMAL);
            c.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(160, 190, 220), 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 9, 5, 9)));
        }
        for (javax.swing.JButton b : botones) estilizarBoton(b);
        recorrerYEstilizar(contentPane);
    }

    /** Estilo para la ventana principal. */
    public static void aplicarMain(javax.swing.JDesktopPane desktop,
                                   javax.swing.JMenuBar menuBar,
                                   javax.swing.JLabel statusLabel,
                                   javax.swing.JPanel statusPanel,
                                   javax.swing.JMenu[] menus,
                                   javax.swing.JMenuItem[] items,
                                   javax.swing.JMenuItem itemPeligroso) {
        // Escritorio
        desktop.setBackground(FONDO_DESK);
        agregarPanelBienvenida(desktop);

        // Barra de estado
        statusPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(
            2, 0, 0, 0, AZUL_OSCURO));
        statusLabel.setFont(FONT_BOLD);
        statusLabel.setForeground(java.awt.Color.WHITE);

        // Barra de menú
        menuBar.setBackground(AZUL_MENU);
        menuBar.setBorder(javax.swing.BorderFactory.createMatteBorder(
            0, 0, 2, 0, AZUL_OSCURO));

        // Popup menus
        javax.swing.UIManager.put("PopupMenu.background",        java.awt.Color.WHITE);
        javax.swing.UIManager.put("MenuItem.background",         java.awt.Color.WHITE);
        javax.swing.UIManager.put("MenuItem.foreground",         TEXTO_OSCURO);
        javax.swing.UIManager.put("MenuItem.selectionBackground",new java.awt.Color(210, 228, 248));
        javax.swing.UIManager.put("MenuItem.selectionForeground",AZUL_OSCURO);

        for (javax.swing.JMenu m : menus)     estilizarMenu(m);
        for (javax.swing.JMenuItem i : items) estilizarMenuItem(i, TEXTO_OSCURO);
        if (itemPeligroso != null)
            estilizarMenuItem(itemPeligroso, new java.awt.Color(160, 30, 30));
    }

    // ════════════════════════════════════════════════════════════════════════
    // Helpers internos
    // ════════════════════════════════════════════════════════════════════════

    private static void estilizarTabla(javax.swing.JTable tabla) {
        tabla.setRowHeight(28);
        tabla.setFont(FONT_TABLA);
        tabla.setForeground(TEXTO_OSCURO);
        tabla.setSelectionBackground(SELECCION);
        tabla.setSelectionForeground(TEXTO_OSCURO);
        tabla.setGridColor(new java.awt.Color(200, 218, 235));
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new java.awt.Dimension(0, 1));

        // ── Cabecera: renderer propio para evitar que Windows L&F lo ignore ──
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        header.setPreferredSize(new java.awt.Dimension(0, 34));

        // Renderer que se pinta a sí mismo — no depende del L&F del sistema
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                // Construimos el label manualmente
                setOpaque(true);
                setBackground(AZUL_TABLA);
                setForeground(java.awt.Color.WHITE);
                setFont(FONT_HEADER);
                setText(value != null ? value.toString() : "");
                setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 1,
                        new java.awt.Color(15, 50, 95)),
                    javax.swing.BorderFactory.createEmptyBorder(4, 10, 4, 10)));
                return this;
            }
        });

        // ── Filas: renderer con colores y fuente forzados ────────────────────
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setFont(FONT_TABLA);
                setForeground(TEXTO_OSCURO);     // texto siempre oscuro
                setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 10, 3, 10));
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? FILA_PAR : FILA_IMPAR);
                } else {
                    setBackground(SELECCION);
                }
                return this;
            }
        });
    }

    private static void estilizarBoton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setFont(FONT_BOLD);
    }

    /**
     * Recorre en anchura todos los componentes del contenedor
     * y aplica estilo consistente a JComboBox, JSpinner, JTextField y JTextArea.
     * Omite las tablas (ya estilizadas) y el interior de paneles de cabecera.
     */
    private static void recorrerYEstilizar(java.awt.Container raiz) {
        java.util.Deque<java.awt.Container> cola = new java.util.ArrayDeque<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            java.awt.Container actual = cola.poll();
            for (java.awt.Component comp : actual.getComponents()) {
                if (comp instanceof javax.swing.JComboBox) {
                    estilizarCombo((javax.swing.JComboBox<?>) comp);
                } else if (comp instanceof javax.swing.JSpinner) {
                    estilizarSpinner((javax.swing.JSpinner) comp);
                } else if (comp instanceof javax.swing.JTextArea) {
                    comp.setFont(FONT_NORMAL);
                    comp.setForeground(TEXTO_OSCURO);
                } else if (comp instanceof javax.swing.JTextField
                        && !(comp instanceof javax.swing.JPasswordField)) {
                    comp.setFont(FONT_NORMAL);
                    comp.setForeground(TEXTO_OSCURO);
                    ((javax.swing.JTextField) comp).setMargin(new java.awt.Insets(3, 6, 3, 6));
                } else if (comp instanceof javax.swing.JPasswordField) {
                    comp.setFont(FONT_NORMAL);
                    comp.setForeground(TEXTO_OSCURO);
                } else if (comp instanceof javax.swing.JCheckBox) {
                    comp.setFont(FONT_NORMAL);
                    comp.setForeground(TEXTO_OSCURO);
                } else if (comp instanceof javax.swing.JLabel) {
                    // Solo si no está dentro de un header (fondo azul)
                    java.awt.Container padre = comp.getParent();
                    if (padre == null || !AZUL_MENU.equals(padre.getBackground())) {
                        comp.setFont(FONT_BOLD);
                        comp.setForeground(TEXTO_LABEL);
                    }
                }
                // Entrar en sub-contenedores, pero no dentro de JTable ni JComboBox
                if (comp instanceof java.awt.Container
                        && !(comp instanceof javax.swing.JTable)
                        && !(comp instanceof javax.swing.JComboBox)) {
                    cola.add((java.awt.Container) comp);
                }
            }
        }
    }

    /** JComboBox con fuente uniforme y renderer mejorado para el dropdown. */
    @SuppressWarnings("unchecked")
    private static void estilizarCombo(javax.swing.JComboBox<?> combo) {
        combo.setFont(FONT_NORMAL);
        combo.setBackground(java.awt.Color.WHITE);
        combo.setForeground(TEXTO_OSCURO);
        combo.setMaximumRowCount(10);
        combo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(FONT_NORMAL);
                setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
                if (isSelected) {
                    setBackground(new java.awt.Color(210, 228, 248));
                    setForeground(AZUL_OSCURO);
                } else {
                    setBackground(java.awt.Color.WHITE);
                    setForeground(TEXTO_OSCURO);
                }
                return this;
            }
        });
    }

    /** JSpinner con fuente uniforme en el campo interno de texto. */
    private static void estilizarSpinner(javax.swing.JSpinner spinner) {
        spinner.setFont(FONT_NORMAL);
        try {
            javax.swing.JComponent editor = spinner.getEditor();
            if (editor instanceof javax.swing.JSpinner.DefaultEditor) {
                javax.swing.JTextField tf =
                    ((javax.swing.JSpinner.DefaultEditor) editor).getTextField();
                tf.setFont(FONT_NORMAL);
                tf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            }
        } catch (Exception ignored) {}
    }

    private static void estilizarMenu(javax.swing.JMenu menu) {
        menu.setForeground(java.awt.Color.WHITE);
        menu.setFont(FONT_MENU);
        menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu.setOpaque(true);
        menu.setBackground(AZUL_MENU);
        // Reducir el padding interno para que los items queden más juntos
        menu.setMargin(new java.awt.Insets(4, 6, 4, 6));
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                menu.setBackground(AZUL_HOVER); menu.repaint();
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                menu.setBackground(AZUL_MENU); menu.repaint();
            }
        });
    }

    private static void estilizarMenuItem(javax.swing.JMenuItem item, java.awt.Color fg) {
        item.setFont(FONT_NORMAL);
        item.setBackground(java.awt.Color.WHITE);
        item.setForeground(fg);
        item.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 18, 7, 30));
        item.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    /** Panel de bienvenida centrado en el escritorio MDI. */
    private static void agregarPanelBienvenida(javax.swing.JDesktopPane desktop) {
        javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.GridBagLayout()) {
            @Override protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new java.awt.Color(255, 255, 255, 210));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new java.awt.Color(31, 78, 121, 80));
                g2.setStroke(new java.awt.BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new java.awt.Dimension(540, 210));

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("Clinica Veterinaria");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 38));
        lblTitulo.setForeground(AZUL_MENU);
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.JSeparator sep = new javax.swing.JSeparator();
        sep.setForeground(new java.awt.Color(130, 175, 220));
        sep.setPreferredSize(new java.awt.Dimension(420, 2));

        javax.swing.JLabel lblSub = new javax.swing.JLabel("Sistema de Gestion Integral");
        lblSub.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        lblSub.setForeground(new java.awt.Color(80, 130, 180));
        lblSub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.JLabel lblHint = new javax.swing.JLabel("Seleccione una opcion del menu para comenzar");
        lblHint.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 12));
        lblHint.setForeground(new java.awt.Color(130, 160, 195));
        lblHint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0; gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; gbc.insets = new java.awt.Insets(28, 40, 10, 40);
        card.add(lblTitulo, gbc);
        gbc.gridy = 1; gbc.insets = new java.awt.Insets(0, 40, 10, 40);
        card.add(sep, gbc);
        gbc.gridy = 2; gbc.insets = new java.awt.Insets(0, 40, 6, 40);
        card.add(lblSub, gbc);
        gbc.gridy = 3; gbc.insets = new java.awt.Insets(0, 40, 28, 40);
        card.add(lblHint, gbc);

        javax.swing.JPanel wrapper = new javax.swing.JPanel(new java.awt.GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, new java.awt.GridBagConstraints());

        desktop.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                wrapper.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
                wrapper.revalidate();
            }
        });
        wrapper.setBounds(0, 0, 1024, 650);
        desktop.add(wrapper, javax.swing.JLayeredPane.DEFAULT_LAYER);
    }
}
