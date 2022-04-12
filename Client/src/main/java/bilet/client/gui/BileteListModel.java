package bilet.client.gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BileteListModel extends AbstractListModel {
    private List<String> bilete;
    public BileteListModel() {
        bilete=new ArrayList<String>();
    }

    public int getSize() {
        return bilete.size();
    }

    public Object getElementAt(int index) {
        return bilete.get(index);
    }

//    public void newBilete(String idBuyer, String mesg){
//        String text="["+idBuyer+"]: "+mesg;
//        bilete.add(text);
//        fireContentsChanged(this, bilete.size()-1, bilete.size());
//    }

}
