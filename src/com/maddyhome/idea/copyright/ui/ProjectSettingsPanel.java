package com.maddyhome.idea.copyright.ui;

/*
 * Copyright - Copyright notice updater for IDEA
 * Copyright (C) 2004-2005 Rick Maddy. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.SupportCode;
import com.maddyhome.idea.copyright.options.Options;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ProjectSettingsPanel
{
    public ProjectSettingsPanel(Project project, Options options)
    {
        setupControls(project, options);

        setOptions(options);

        setEnabled(cbEnable.isSelected());
    }

    public JComponent getMainComponent()
    {
        return mainPanel;
    }

    /**
     * @param config configuration parameters.
     */
    public void setOptions(Options config)
    {
        logger.debug("setOptions");

        cbEnable.setSelected(config.getState() == Options.STATE_PROJECT);
        projectTab.setOptions(config);
    }

    /**
     * @return the configuration.
     */
    public Options getOptions()
    {
        logger.debug("getOptions");

        Options res = projectTab.getOptions();
        res.setState(cbEnable.isSelected() ? Options.STATE_PROJECT : Options.STATE_DISABLE);

        return res;
    }

    public void validate() throws ConfigurationException
    {
        projectTab.validate();
        moduleTab.validate();
    }

    public void apply()
    {
        moduleTab.apply();
    }

    public boolean isModified(Options options)
    {
        logger.debug("isModified");

        return projectTab.isModified(options) || moduleTab.isModified() ||
            options.getState() != (cbEnable.isSelected() ? Options.STATE_PROJECT : Options.STATE_DISABLE);
    }

    public void setEnabled(boolean enable)
    {
        tabs.setEnabledAt(0, enable);
        projectTab.setEnabled(enable);
        tabs.setEnabledAt(1, enable);
        moduleTab.setEnabled(enable);
    }

    private void setupControls(Project project, Options options)
    {
        projectTab = new OptionsPanel(project, options, false);
        moduleTab = new ModuleSettingsPanel(project);

        tabs.addTab("Project Settings", projectTab.getMainComponent());
        tabs.addTab("Module Settings", moduleTab.getMainComponent());

        cbEnable.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setEnabled(cbEnable.isSelected());
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR call it in your
     * code!
     */
    private void $$$setupUI$$$()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
        tabs = new JTabbedPane();
        mainPanel.add(tabs, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200),
            null));
        cbEnable = new JCheckBox();
        cbEnable.setText("Enable Copyright Notice Plugin");
        cbEnable.setMnemonic(69);
        SupportCode.setDisplayedMnemonicIndex(cbEnable, 0);
        mainPanel.add(cbEnable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null));
    }

    private JPanel mainPanel;
    private JTabbedPane tabs;
    private JCheckBox cbEnable;

    private OptionsPanel projectTab;
    private ModuleSettingsPanel moduleTab;

    private static Logger logger = Logger.getInstance(ProjectSettingsPanel.class.getName());
}