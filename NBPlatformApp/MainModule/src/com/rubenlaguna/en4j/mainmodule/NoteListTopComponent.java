/*
 *  Copyright (C) 2010 Ruben Laguna <ruben.laguna@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rubenlaguna.en4j.mainmodule;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Property;
import org.jdesktop.observablecollections.ObservableCollections;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor.Task;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import com.rubenlaguna.en4j.interfaces.NoteFinder;
import com.rubenlaguna.en4j.interfaces.NoteRepository;
import com.rubenlaguna.en4j.noteinterface.Note;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import org.openide.util.RequestProcessor;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.rubenlaguna.en4j.mainmodule//NoteList//EN",
autostore = false)
public final class NoteListTopComponent extends TopComponent implements ListSelectionListener, PropertyChangeListener {

    private Logger LOG = Logger.getLogger(NoteListTopComponent.class.getName());
    private static final RequestProcessor RP = new RequestProcessor("search tasks", 2);
    private static NoteListTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "NoteListTopComponent";
    private final InstanceContent ic = new InstanceContent();
    private RequestProcessor.Task currentSearchTask = null;
    private RequestProcessor.Task currentRefreshTask = null;
    private String searchstring = "";
    private final CustomGlassPane customGlassPane = new CustomGlassPane();

    public NoteListTopComponent() {
        LOG.info("creating NoteListTopComponen " + this.toString());
        initComponents();
        setName(NbBundle.getMessage(NoteListTopComponent.class, "CTL_NoteListTopComponent"));
        setToolTipText(NbBundle.getMessage(NoteListTopComponent.class, "HINT_NoteListTopComponent"));
        associateLookup(new AbstractLookup(ic));
        jTable1.getSelectionModel().addListSelectionListener(this);
        putClientProperty(PROP_CLOSING_DISABLED, true);
        customGlassPane.setVisible(false);
        jLayeredPane1.add(customGlassPane, (Integer) (jLayeredPane1.DEFAULT_LAYER + 50));
    }

    public void refresh() {
        LOG.fine("refresh notelist " + new SimpleDateFormat("h:mm:ss a").format(new Date()));
        performSearch();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        list1 = getList();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLayeredPane1.setLayout(new OverlayLayout(jLayeredPane1));
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jScrollPane1.setBounds(jLayeredPane1.getVisibleRect());

        jTable1.setBounds(jScrollPane1.getVisibleRect());
        jTable1.setColumnSelectionAllowed(true);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list1, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${title}"));
        columnBinding.setColumnName("Title");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(NoteListTopComponent.class, "NoteListTopComponent.jTable1.columnModel.title0_2")); // NOI18N

        jScrollPane1.setBounds(0, 0, 450, -1);
        jLayeredPane1.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        searchTextField.setText(org.openide.util.NbBundle.getMessage(NoteListTopComponent.class, "NoteListTopComponent.searchTextField.text")); // NOI18N
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusLost(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(NoteListTopComponent.class, "NoteListTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(NoteListTopComponent.class, "NoteListTopComponent.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        performSearch();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void performSearch() {
        if (currentSearchTask != null) {
            currentSearchTask.cancel();
        }
        LOG.info(this.toString() + " searchstring " + searchstring);

        Runnable r = new Runnable() {

            @Override
            public void run() {

                final Task dimTask = postDimTask();
                final String text = searchstring;
                LOG.fine("searching in lucene...");
                Collection<Note> prelList = null;
                if (text.trim().isEmpty() || text.equals(org.openide.util.NbBundle.getMessage(NoteListTopComponent.class, "NoteListTopComponent.searchTextField.text"))) {
                    LOG.fine("no need to search the search box is empty " + text + " from thread " + Thread.currentThread().getName());
                    prelList = getList();
                } else {
                    NoteFinder finder = Lookup.getDefault().lookup(NoteFinder.class);
                    prelList = finder.find(text);
                    LOG.info("search for " + text + " returned " + prelList.size() + " results.");
                }
                final Collection<Note> list = prelList;
                try {
                    dimTask.cancel();
                    dimTask.waitFinished();
                    final int repSize = Lookup.getDefault().lookup(NoteRepository.class).size();
                    SwingUtilities.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            LOG.fine("Refreshing the list in the EDT");
                            list1.clear();
                            list1.addAll(list);
                            jLabel1.setText(list1.size()+"/"+repSize);
                            customGlassPane.setVisible(false);
                        }
                    });
                } catch (InterruptedException ex) {
                } catch (InvocationTargetException ex) {
                }
            }

            private Task postDimTask() {
                Runnable dimListRunnable = new Runnable() {

                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                customGlassPane.setVisible(true);
                            }
                        });
                    }
                };
                final Task dimTask = RP.post(dimListRunnable, 500);
                return dimTask;
            }
        };

        currentSearchTask = RP.post(r, 500);
        LOG.fine("currentSearchtask posted");
    }

    private void searchTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusGained
        // TODO add your handling code here:
        String initialText = org.openide.util.NbBundle.getMessage(NoteListTopComponent.class, "NoteListTopComponent.searchTextField.text");
        if (initialText.equals(searchTextField.getText())) {
            searchTextField.setText("");
        }
    }//GEN-LAST:event_searchTextFieldFocusGained

    private void searchTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusLost
        // TODO add your handling code here:
        if ("".equals(searchTextField.getText())) {
            LOG.info("searchTextField was empty so reset to the default text");
            searchTextField.setText(org.openide.util.NbBundle.getMessage(NoteListTopComponent.class, "NoteListTopComponent.searchTextField.text"));
        }
    }//GEN-LAST:event_searchTextFieldFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.util.List<Note> list1;
    public final javax.swing.JTextField searchTextField = new javax.swing.JTextField();
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized NoteListTopComponent getDefault() {
        if (instance == null) {
            instance = new NoteListTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the NoteListTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized NoteListTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(NoteListTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof NoteListTopComponent) {
            return (NoteListTopComponent) win;
        }
        Logger.getLogger(NoteListTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        NoteRepository rep = Lookup.getDefault().lookup(NoteRepository.class);
        rep.addPropertyChangeListener(this);
        Lookup.getDefault().lookup(NoteFinder.class).addPropertyChangeListener(this);
        LOG.info(this.toString() + " registered as listener to NoteRepositor and NoteFinder");
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {
                searchstring = searchTextField.getText();
                LOG.info("searchTextField changed: " + searchTextField.getText());
                performSearch();
            }
        });
        performSearch();
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
        NoteRepository rep = Lookup.getDefault().lookup(NoteRepository.class);
        rep.removePropertyChangeListener(this);
        Lookup.getDefault().lookup(NoteFinder.class).removePropertyChangeListener(this);
        LOG.info(this.toString() + " removed as listener to NoteRepositor and NoteFinder");
    }

    @Override
    protected void componentActivated() {
        searchTextField.requestFocusInWindow();
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        NoteListTopComponent singleton = NoteListTopComponent.getDefault();
        singleton.readPropertiesImpl(p);
        return singleton;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    private List<Note> getList() {
        List<Note> toReturn = ObservableCollections.observableList(new ArrayList<Note>());
        NoteRepository rep = Lookup.getDefault().lookup(NoteRepository.class);
        toReturn.addAll(rep.getAllNotes());
        Logger logger = Logger.getLogger(NoteListTopComponent.class.getName());
        logger.log(Level.INFO, "number of entries in db:" + toReturn.size());
        return toReturn;
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        if (!arg0.getValueIsAdjusting()) {
//            int sr = jTable1.getSelectedRow();
            Property<JTable, Object> p = BeanProperty.create("selectedElement");
            Object value = p.getValue(jTable1);

            //TODO clear this, move ic.set outside the if else block 
            if (value != null) {
                Logger.getLogger(getName()).log(Level.FINE, "selection changed: " + p.getValue(jTable1).toString());
                ic.set(Collections.singleton(value), null);

            } else {
                ic.set(Collections.emptySet(), null);
                Logger.getLogger(getName()).log(Level.FINE, "selection changed: nothing selected");
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LOG.fine("change in noterepository / index");
        if (null != currentRefreshTask) {
            boolean removed = currentRefreshTask.cancel();//cancel the last refresh so we only
            //two refresh task at a given moment
        }
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                NoteListTopComponent.this.refresh();
                try {
                    Thread.sleep(5000); //no more than 1 refresh every two seconds
                } catch (InterruptedException ex) {
                    //do nothing
                }
            }
        };
        currentRefreshTask = RP.post(runnable);
    }
}
