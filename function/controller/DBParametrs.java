package function.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by BELYANIN on 29.11.2017.
 */
public class DBParametrs {
    //Перечень схем
    ObservableList<String> itemsSchemas = FXCollections.observableArrayList("a260290338703446get",
            "ae613429596541854get",
            "b526745947807181get",
            "c2485043296429498get",
            "d769305054713560get",
            "e686181878568236get",
            "echsz_laes174110950795955get",
            "f7952415767822940get",
            "laes_2_blok_1_echsr_k_12008591657336291get",
            "sku_svo_laes4418715782306get",
            "skusb_2015_03_1186081100988665get",
            "stk_tg_nt6463275467591get");

    //Перечень необходимых таблиц
    ObservableList<String> itemsTables = FXCollections.observableArrayList("Abonents", "Blocks","Bst","Connectors","Eascycle","Fblocks",
            "Fbparameters","Functionpages","Infbport","Mechanics","Modules",
            "Outfbport","Pageleft","Pageright","Pages","Tabqrel","Tabzrel");


    public static void main(String[] args) {

    }
}

