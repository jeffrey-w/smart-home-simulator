package main.controller;

import main.model.Action;
import main.model.MultiValueManipulable;
import main.model.ValueManipulable;
import main.view.AwayLightChooser;
import main.view.Dashboard;
import main.view.ModuleView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class SecurityModuleController extends AbstractModuleController {

    private static final Set<String> CANCEL_KEYWORDS = new HashSet<>();

    static {
        CANCEL_KEYWORDS.add("Y");
        CANCEL_KEYWORDS.add("YES");
    }

    public SecurityModuleController(Controller parent, ModuleView view) {
        super(parent, view);
    }

    @Override
    public void control() {
        if (canAct()) {
            switch (view.getSelectedAction()) {
                case SET_AWAY_MODE_DELAY:
                    ValueManipulable<Integer> valueManipulable = null;
                    try {
                        valueManipulable = new ValueManipulable<>(Integer.parseInt(JOptionPane
                                .showInputDialog(parent.getDashboard(), "Enter an away mode delay.", "Away mode Delay",
                                        JOptionPane.PLAIN_MESSAGE)));
                    } catch (NumberFormatException e) {
                        parent.sendToConsole("Please enter a positive integer.", Dashboard.MessageType.ERROR);
                        return;
                    }
                    performCommand(valueManipulable, Action.SET_AWAY_MODE_DELAY);
                    break;
                case SET_AWAY_MODE_LIGHTS:
                    AwayLightChooser chooser = AwayLightChooser.of(parent.getHouse().getLocations());
                    chooser.addActionListener(f -> {
                        MultiValueManipulable multiValueManipulable =
                                new MultiValueManipulable(chooser.getSelectedLocations());
                        multiValueManipulable.addValue(chooser.getStart());
                        multiValueManipulable.addValue(chooser.getEnd());
                        performCommand(multiValueManipulable, Action.SET_AWAY_MODE_LIGHTS);
                        chooser.dispose();
                    });
                    chooser.pack();
                    chooser.setLocationRelativeTo(parent.getDashboard());
                    chooser.setVisible(true);
                case SET_AWAY_MODE:
                    performCommand(parent.getParameters().getAwayMode(), view.getSelectedAction());
                    parent.redrawHouse();
            }
        } else {
            parent.sendToConsole("Please select a permission to choose an action.", Dashboard.MessageType.ERROR);
        }
    }

    @Override
    public boolean canAct() {
        return parent.getParameters().getPermission() != null;
    }

    public void startAwayModeCountdown() {
        final boolean[] cancel = {false, false};
        Timer timer = new Timer(parent.getParameters().getAwayDelay(), e -> {
            parent.getDashboard().addConsoleListener(null, null);
            if (cancel[0]) {
                parent.sendToConsole("Crisis averted!", Dashboard.MessageType.WARNING);
            } else {
                parent.sendToConsole((cancel[1] ? "" : "\n") + "Intruder detected, the authorities have been alerted!",
                        Dashboard.MessageType.WARNING);
            }
        });
        parent.getDashboard().addConsoleListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (CANCEL_KEYWORDS.contains(parent.getDashboard().getLastConsoleMessage())) {
                        cancel[0] = true;
                        timer.setInitialDelay(0);
                        timer.restart();
                    }
                    cancel[1] = true;
                }
            }

        }, "Potential break in, do you want to disable the alarm [y/N]?");
        timer.setRepeats(false);
        timer.start();
    }

}
