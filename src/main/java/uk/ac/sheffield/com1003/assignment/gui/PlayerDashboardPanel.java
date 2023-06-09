package uk.ac.sheffield.com1003.assignment.gui;
import uk.ac.sheffield.com1003.assignment.codeprovided.*;
import uk.ac.sheffield.com1003.assignment.codeprovided.Query;

import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.*;

/**
 * SKELETON IMPLEMENTATION
 */

public class PlayerDashboardPanel extends AbstractPlayerDashboardPanel
{
    // Constructor
    public PlayerDashboardPanel(AbstractPlayerCatalog playerCatalog) {
        super(playerCatalog);
    }
    //instace variables
    public static List<PlayerEntry> datalist;
    String userQueryString;
    List<PlayerProperty> category;
    String selectedCartegory = "general";//set the default
    String selectedLeague = (String) comboLeagueTypes.getSelectedItem();
    String selectedName = (String) comboPlayerNames.getSelectedItem();
    String selectedNation = (String) comboNations.getSelectedItem();
    String selectedPosition = (String) comboPositions.getSelectedItem();
    String SelectedTeam = (String) comboTeams.getSelectedItem();
    League myLeague = League.fromName(selectedLeague);
    boolean minimumChecked = false;
    boolean maximumChecked = false;
    boolean averageChecked = false;
    Query query;
    static List<Boolean> checked;

    

    @Override
    public void populatePlayerDetailsComboBoxes() {
        datalist = playerCatalog.getPlayerEntriesList(League.ALL);
        datalist = getPlayerDetails(League.ALL);
        playerNamesList.add(0,"");
        DefaultComboBoxModel<String> namesModel = (DefaultComboBoxModel<String>) comboPlayerNames.getModel();
        namesModel.addAll(playerNamesList);
       
        DefaultComboBoxModel<String> nationModel = (DefaultComboBoxModel<String>) comboNations.getModel();
        nationList.add(0,"");
        nationModel.addAll(nationList);

        DefaultComboBoxModel<String> positionModel = (DefaultComboBoxModel<String>) comboPositions.getModel();
        positionList.add(0,"");
        positionModel.addAll(positionList);

        DefaultComboBoxModel<String> teamModel = (DefaultComboBoxModel<String>) comboTeams.getModel();
        teamList.add(0,"");
        teamModel.addAll(teamList);
       
    }

    /**
     * addListeners method - adds relevant actionListeners to the GUI components
     */
    @SuppressWarnings("")
    @Override
    public void addListeners() {

        comboLeagueTypes.addActionListener(e -> {
            // Code to be executed when a user selects an item in the combo box
            if(selectedLeague != null){
               
                String selectedLeague = (String) comboLeagueTypes.getSelectedItem();
                myLeague = League.fromName(selectedLeague);
                
                datalist = playerCatalog.getPlayerEntriesList(myLeague);
                getPlayerDetails(myLeague);
        
                updatePlayerCatalogDetailsBox();
                updateStatistics(); 
                updateRadarChart();
            }
        });
        comboPlayerNames.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedName = (String) comboPlayerNames.getSelectedItem();
                if (selectedName != null) {           
                    //datalist = newPlayersList(selectedName);
                    datalist = playerCatalog
                    .getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.PLAYER, selectedName);

                    updatePlayerCatalogDetailsBox();
                    updateStatistics(); 
                    updateRadarChart();
                }
            }
        });
        comboNations.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedNation = (String) comboNations.getSelectedItem();
                if (selectedNation != null) {           
                   // datalist = newPlayersList(selectedNation);
                   datalist = playerCatalog
                   .getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.NATION, selectedNation);
                    updatePlayerCatalogDetailsBox();
                    updateStatistics(); 
                    updateRadarChart();
                }
            }
        });
        comboPositions.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedPosition = (String) comboPositions.getSelectedItem();
                if (selectedPosition != null) {           
                    //datalist = newPlayersList(selectedPosition);
                    datalist = playerCatalog
                    .getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.POSITION, selectedPosition);
                    updatePlayerCatalogDetailsBox();
                    updateStatistics(); 
                    updateRadarChart();
                }
            }
        });
        comboTeams.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedTeam = (String) comboTeams.getSelectedItem();
                if (selectedTeam != null) {           
                   // datalist = newPlayersList(selectedTeam);
                   datalist = playerCatalog
                   .getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.TEAM, selectedTeam);
                    updatePlayerCatalogDetailsBox();
                    updateStatistics(); 
                   updateRadarChart();
                }
            }
        });
  
        buttonAddFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                
                if(datalist != null){
                    addFilter();
                }
               
            }
        });
        
        buttonClearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFilters();
                
            }
        });
        minCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                minimumChecked = true;
                 
            } else{
                minimumChecked = false;
            }
            updateRadarChart();
        });

        maxCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                maximumChecked = true;
              
               

            } else{
                maximumChecked = false;
               
            }
            updateRadarChart();
        });

        averageCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                averageChecked = true; 
                
            } else{
                averageChecked = false; 
            }
            updateRadarChart();

        });

        comboRadarChartCategories.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedCartegory = (String) comboRadarChartCategories.getSelectedItem();
                updateRadarChart();
            }
        });
  
    }
    /**
     * clearFilters method - clears all filters from the subQueryList ArrayList and updates
     * the relevant GUI components
     */
    @Override
    public void clearFilters() {
        subQueryList.clear();
       
        if(datalist!=null){
            updatePlayerCatalogDetailsBox(); // Add this line to update the player catalog details box
            // Add this line to update the statistics
              updateRadarChart();

        }

        SubQuery subQuery = new SubQuery(PlayerProperty.AGE, ">", 0);
        subQueryList.add(subQuery);
        query = new Query(subQueryList, myLeague);
        datalist = query.executeQuery(playerCatalog);
        subQueriesTextArea.setText("");
    }

    @Override
    public void updateRadarChart() {
        //get the selcted cartegory then update.

         checked = Arrays.asList(
            isMaxCheckBoxSelected(),
            isAverageCheckBoxSelected(),
            isMinCheckBoxSelected()
        );

       // System.out.println(checked.get(0) +" "+ checked.get(1) +" "+ checked.get(2));
        PlayerProperty[] newCartegory = Category.getCategoryFromName(selectedCartegory)
                                            .getProperties();
        category = new ArrayList<>();
        category.addAll(Arrays.asList(newCartegory));
        RadarChart radarChart = new RadarChart(playerCatalog, datalist, category);
        RadarChartPanel radarChartPanel = new RadarChartPanel(this, radarChart);
        // revalidate and repaint the panel
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        // Minimize and then maximize the parent JFrame
    // Slightly reduce the window size
    if (parentFrame != null) {
        Dimension originalSize = parentFrame.getSize();
        Dimension reducedSize = new Dimension(originalSize.width - 1, originalSize.height - 1);
        parentFrame.setSize(reducedSize);

        int delay = 30; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                parentFrame.setSize(originalSize);
            }
        };
        new Timer(delay, taskPerformer).start();
    }
        radarChartPanel.revalidate();
        radarChartPanel.repaint();

        
    }

    /**
     * updateStats method - updates the table with statistics after any changes which may
     * affect the JTable which holds the statistics
     */
    @Override
    public void updateStatistics() {
        if(datalist != null){
            statisticsTextArea.setText("");
            statisticsTextArea.append(String.format("%-55s %-55s %-55s %-55s\n", "Property","Maximum", "Average", "Minimum"));
    
            for (PlayerProperty property : PlayerProperty.values()) {
    
                statisticsTextArea.append(String.format("%-55s", property.getName()));
                double maxValue = playerCatalog.getMaximumValue(property, datalist);
                double avgValue = playerCatalog.getMeanAverageValue(property, datalist);
                double minValue = playerCatalog.getMinimumValue(property, datalist);
                statisticsTextArea.append(String.format("%-55s %-55s %-55s\n", String.format("%.2f", maxValue),
                        String.format("%.2f", avgValue), String.format("%.2f", minValue)));
    
            }
        }
       
    }

    /**
     * updatePlayerCatalogDetailsBox method - updates the list of players when changes are made
     */
    @Override
    public void updatePlayerCatalogDetailsBox() {
        filteredPlayerEntriesTextArea.setText("");
        filteredPlayerEntriesTextArea.append(String.format("%-16s %-26s %-34s %-38s %-32s %-34s",
                "ID", "League Type", "Player's Name", "Nation", "Position", "Team"));
        for (PlayerProperty property : PlayerProperty.values()) {
            filteredPlayerEntriesTextArea.append(String.format("%-48s", property.getName()));
        }
        filteredPlayerEntriesTextArea.append("\n");

        // Print the data rows
        for (PlayerEntry playerEntry : datalist) {
            filteredPlayerEntriesTextArea.append(String.format("%-16s %-26s %-34s %-38s %-32s %-34s",
                    playerEntry.getId(), playerEntry.getLeagueType().name(), playerEntry.getPlayerName(),
                    playerEntry.getNation(), playerEntry.getPosition(), playerEntry.getTeam()));

            for (PlayerProperty property : PlayerProperty.values()) {
                double entryValue = playerEntry.getProperty(property);

                filteredPlayerEntriesTextArea.append(String.format("%-74s", String.format("%.2f", entryValue)));
            }
            filteredPlayerEntriesTextArea.append("\n");
        }

    }

    /**
     * executeQuery method - applies chosen query to the relevant list
     */
    @Override
    public void executeQuery() {
        try{
            query = new Query(subQueryList, myLeague);
           
            datalist = query.executeQuery(playerCatalog);
            if(datalist!=null){
                updatePlayerCatalogDetailsBox(); 
                updateStatistics();
                updateRadarChart();
            }
          

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "no result.",
            "Error", JOptionPane.ERROR_MESSAGE);
        }
       
      
        
      
       
        String queryAsString = query+"";
        String query = queryAsString.substring(queryAsString.indexOf('{') + 1, queryAsString.indexOf('}'));
        String[] conditions = query.split(",");
        StringBuilder convertedQuery = new StringBuilder();

        // Extract the table name from the input string

        for (int i = 0; i < conditions.length; i++) {
            convertedQuery.append(conditions[i].trim());

            if (i < conditions.length - 1) {
                convertedQuery.append(" and ");
            }
        }
        subQueriesTextArea.setText("select from   "+myLeague+"  where  "+ convertedQuery+".");               
    }

    /**
     * addFilters method - adds filters input into GUI to subQueryList ArrayList
     */
    @Override
    public void addFilter() {
        executeQuery();
        String selectedProperty = (String) comboQueryProperties.getSelectedItem();
        String operator = (String) comboOperators.getSelectedItem();
        try{
            double Value = Double.parseDouble(value.getText());

            PlayerProperty property = PlayerProperty.fromPropertyName(selectedProperty);
    
            // Create a new SubQuery object for the selected property, operator, and value
            SubQuery subQuery = new SubQuery(property, operator, Value);
            subQueryList.add(subQuery);
         }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
       
    }

    @Override
    public boolean isMinCheckBoxSelected() {
        if(minimumChecked){
            return true;
        }
        return false;
    }

    @Override
    public boolean isMaxCheckBoxSelected() {
        if(maximumChecked){
            return true;
        }
        return false;
    }

    @Override
    public boolean isAverageCheckBoxSelected() {
        if(averageChecked){
            return true;
        }
        return false;
    }


    /*
     * This method gets all the player details and passes them to
     * the comboboxes
     */
    public List<PlayerEntry> getPlayerDetails(League league){
        //clear all the lists then update them
        List<String> namesList = new ArrayList<String>();namesList.clear();
        List<String> nationsList = new ArrayList<String>();namesList.clear();
        List<String> positionsList = new ArrayList<String>();namesList.clear();
        List<String> teamsList = new ArrayList<String>();namesList.clear();
        
        for (PlayerEntry playerEntry : datalist) {
            String playerName = playerEntry.getPlayerName();
            namesList.add(playerName);

            String playerNation = playerEntry.getNation();
            if (!nationsList.contains(playerNation)) {
                nationsList.add(playerNation);
            }

            String playerPosition = playerEntry.getPosition();
            if (!positionsList.contains(playerPosition)) {
                positionsList.add(playerPosition);
            }

            String playerTeam = playerEntry.getTeam();
            if (!teamsList.contains(playerTeam)) {
                teamsList.add(playerTeam);
            }
        }
        updateNamesComboBox(namesList);
        updateNationsComboBox(nationsList);
        updatePositionsComboBox(positionsList);
        updateTeamsComboBox(teamsList);
        return datalist;
    }


    private void updateNamesComboBox(List<String> newList) {
        newList.add(0,"");
        DefaultComboBoxModel<String> namesModel = (DefaultComboBoxModel<String>) comboPlayerNames.getModel();
        namesModel.removeAllElements();
        namesModel.addAll(newList);
       
    }

    private void updateNationsComboBox(List<String> newList) {
        DefaultComboBoxModel<String> nationModel = (DefaultComboBoxModel<String>) comboNations.getModel();
        newList.add(0,"");

        nationModel.removeAllElements();
        nationModel.addAll(newList);
       
    }
        
    private void updatePositionsComboBox(List<String> newList) {
    
        DefaultComboBoxModel<String> positionModel = (DefaultComboBoxModel<String>) comboPositions.getModel();
        newList.add(0,"");

        positionModel.removeAllElements();
        positionModel.addAll(newList);
    
    }

       
    private void updateTeamsComboBox(List<String> newList) {
        DefaultComboBoxModel<String> teamModel = (DefaultComboBoxModel<String>) comboTeams.getModel();
        newList.add(0,"");

        teamModel.removeAllElements();
        teamModel.addAll(newList);
    }

    public void styleGUIComponents() {
       

    }
    
}
