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
package com.rubenlaguna.en4j.sync;

import com.rubenlaguna.en4j.interfaces.SynchronizationService;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.util.Lookup;

/**
 *
 * @author Ruben Laguna <ruben.laguna@gmail.com>
 */
public class SyncToolbarJPanel extends javax.swing.JPanel {

    private final SynchronizationService sservice;

    /** Creates new form SyncToolbarJPanel */
    public SyncToolbarJPanel() {
        initComponents();

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                new SyncAction().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "SYNC"));
            }
        });
        sservice = getSyncService();


        //TODO superimpose an error exclamation
        sservice.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(SynchronizationServiceImpl.PROP_SYNCFAILED)) {

                    if (evt.getNewValue().equals(Boolean.TRUE)) {
                        rotatingLogo1.setFailed(true);
                    } else {
                        rotatingLogo1.setFailed(false);
                    }
                }
            }
        });
    }

    private SynchronizationService getSyncService() {
        return Lookup.getDefault().lookup(SynchronizationService.class);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        syncServiceBean1 = new com.rubenlaguna.en4j.sync.SyncServiceBean();
        syncLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(142, 70));

        syncLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        syncLabel.setText(org.openide.util.NbBundle.getMessage(SyncToolbarJPanel.class, "SyncToolbarJPanel.syncLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rotatingLogo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(syncLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(syncLabel)
                .addComponent(rotatingLogo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    static final com.rubenlaguna.en4j.sync.RotatingLogo rotatingLogo1 = new com.rubenlaguna.en4j.sync.RotatingLogo();
    javax.swing.JLabel syncLabel;
    com.rubenlaguna.en4j.sync.SyncServiceBean syncServiceBean1;
    // End of variables declaration//GEN-END:variables

    boolean  startAnimator() {
         return rotatingLogo1.startAnimator();
    }

    void stopAnimator() {
        rotatingLogo1.stopAnimator();
    }
}
