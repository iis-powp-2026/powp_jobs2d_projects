package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class MouseInteractionFeature implements IFeature {

    @Override
    public void setup(Application application) {
        JPanel panel = application.getFreePanel();

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {}
        });
    }

    @Override
    public String getName() {
        return "Mouse Interaction";
    }
}
