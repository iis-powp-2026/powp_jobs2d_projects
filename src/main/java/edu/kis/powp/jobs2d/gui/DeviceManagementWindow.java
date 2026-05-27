package edu.kis.powp.jobs2d.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.drivers.DeviceUsageManager;
import edu.kis.powp.jobs2d.drivers.DeviceUsageSubscriber;

/**
 * Window that can manage multiple DeviceUsageManager instances.
 * It provides a selector (combo) for drivers and shows the selected manager's state.
 */
public class DeviceManagementWindow extends JFrame implements WindowComponent, DeviceUsageSubscriber {

    private DeviceUsageManager deviceUsageManager;
    private JProgressBar operationalUsageProgressBar;
    private JLabel usageLabel;
    private JComboBox<String> driverSelector;

    private final Map<String, DeviceUsageManager> managers = new LinkedHashMap<>();
    private String currentName = null;

    private static final long serialVersionUID = 1L;

    public DeviceManagementWindow() {
        this.setTitle("Device Management");
        this.setSize(400, 240);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;

        driverSelector = new JComboBox<>();
        driverSelector.addActionListener((ActionEvent e) -> {
            String selected = (String) driverSelector.getSelectedItem();
            if (selected != null && !selected.equals(currentName)) {
                DeviceUsageManager newMgr = managers.get(selected);
                setDeviceUsageManager(newMgr);
                currentName = selected;
            }
        });
        content.add(new JLabel("Select driver:"), c);
        content.add(driverSelector, c);

        content.add(new JLabel("Operational Usage Level:"), c);

        operationalUsageProgressBar = new JProgressBar(0, 10000);
        operationalUsageProgressBar.setValue(10000);
        operationalUsageProgressBar.setStringPainted(true);
        content.add(operationalUsageProgressBar, c);

        usageLabel = new JLabel("Total Usage: 0.0");
        content.add(usageLabel, c);

        JButton btnRefill = new JButton("Refill");
        btnRefill.addActionListener((ActionEvent e) -> {
            if (this.deviceUsageManager != null) {
                this.deviceUsageManager.refill();
            }
        });
        content.add(btnRefill, c);

        JButton btnService = new JButton("Service");
        btnService.addActionListener((ActionEvent e) -> {
            if (this.deviceUsageManager != null) {
                this.deviceUsageManager.service();
            }
        });
        content.add(btnService, c);
    }

    /**
     * Register a manager under given name; adds an entry to selector.
     * If it's the first manager, selects it automatically.
     */
    public void registerManager(DeviceUsageManager manager, String name) {
        if (manager == null || name == null) return;
        if (managers.containsKey(name)) return;

        managers.put(name, manager);
        driverSelector.addItem(name);

        if (managers.size() == 1) {
            driverSelector.setSelectedItem(name);
            setDeviceUsageManager(manager);
            currentName = name;
        }
    }

    public void setDeviceUsageManager(DeviceUsageManager newDeviceUsageManager) {
        if (this.deviceUsageManager != null) {
            this.deviceUsageManager.removeSubscriber(this);
        }
        this.deviceUsageManager = newDeviceUsageManager;
        if (this.deviceUsageManager != null) {
            this.deviceUsageManager.addSubscriber(this);
        } else {
            operationalUsageProgressBar.setValue(0);
            usageLabel.setText("Total Usage: 0.0");
        }
    }

    @Override
    public void update(String message) {
        if ("LOW_OPERATIONAL_USAGE".equals(message)) {
            operationalUsageProgressBar.setForeground(Color.RED);
        } else if ("REACHED_MAX_OPERATIONAL_USAGE".equals(message)) {
            operationalUsageProgressBar.setForeground(Color.DARK_GRAY);
        }
    }

    @Override
    public void onUsageUpdate(double operationalUsageLevel, double maxOperationalUsageLevel, double totalUsage) {
        if (operationalUsageProgressBar.getMaximum() != (int) maxOperationalUsageLevel) {
            operationalUsageProgressBar.setMaximum((int) maxOperationalUsageLevel);
        }
        operationalUsageProgressBar.setValue((int) operationalUsageLevel);
        usageLabel.setText(String.format("Total Usage: %.2f", totalUsage));

        if (operationalUsageLevel >= (maxOperationalUsageLevel * 0.1)) {
            operationalUsageProgressBar.setForeground(null);
        } else {
            operationalUsageProgressBar.setForeground(Color.RED);
        }
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }
}