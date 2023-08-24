package com.anura.view;

import com.anura.model.guientity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TopPanel extends JPanel {

    static JLabel inventoryLabel;
    Player player;
    static DefaultListModel<String> inventoryListModel;
    JList<String> inventoryList;

    public TopPanel(Player player){
        this.player = player;
        // Set the layout, bg color and properties
        setLayout(new BorderLayout());
        setBackground(Color.orange);

        inventoryLabel = new JLabel("Inventory");
        add(inventoryLabel, BorderLayout.NORTH);

        // Add the inventory to a scroll pane
        inventoryListModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryListModel);

        // Create custom cell renderer
        inventoryList.setCellRenderer(new CustomListCellRenderer());

        setOpaque(true);
        inventoryList.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(inventoryList);
//        scrollPane.getViewport().setBackground(Color.BLACK);
//        scrollPane.setBackground(Color.BLACK);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setOpaque(true);

        for(Component c : scrollPane.getViewport().getComponents()){
            if(c instanceof JComponent){
                ((JComponent) c).setOpaque(true);
            }
        }
    }

    public static void updateInventory(List<String> inventory){
        // clear current inventory
//        inventoryLabel.setText("Inventory");
        inventoryListModel.clear();

        for(String item : inventory){
//            inventoryLabel.setText(inventoryLabel.getText() + ", " + item);
            inventoryListModel.addElement(item);
        }
    }
}

class CustomListCellRenderer extends DefaultListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        //Set foreground (text) color to white and background to black
        renderer.setForeground(Color.WHITE);
        renderer.setBackground(Color.BLACK);

        return renderer;
    }
}
