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
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;
import org.openide.util.actions.Presenter;

public final class SyncAction implements ActionListener, Presenter.Toolbar {

    private final RequestProcessor RP = new RequestProcessor("sync task", 1, true);
    private final static SyncToolbarJPanel toolbarPresenter = new SyncToolbarJPanel();

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        final SynchronizationService sservice = Lookup.getDefault().lookup(SynchronizationService.class);

        Runnable task = new Runnable() {

            public void run() {
                toolbarPresenter.startAnimator();
                sservice.sync();
                toolbarPresenter.stopAnimator();
            }
        };
        RP.post(task);
    }

    public Component getToolbarPresenter() {
        return toolbarPresenter;
    }
}
