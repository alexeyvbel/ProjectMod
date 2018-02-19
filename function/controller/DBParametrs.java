package function.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by BELYANIN on 29.11.2017.
 */
public class DBParametrs {

    //Перечень необходимых таблиц
    ObservableList<String> itemsTables = FXCollections.observableArrayList("Abonents", "Blocks","Bst","Connectors","Eascycle","Fblocks",
            "Fbparameters","Functionpages","Infbport","Mechanics","Modules",
            "Outfbport","Pageleft","Pageright","Pages","Tabqrel","Tabzrel");


    public static void main(String[] args) {

    }
}

