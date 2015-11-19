package es.uma.pfc.is.javafx;

import es.uma.pfc.is.bench.algorithms.results.treemodel.TreeResultModel;
import java.text.DecimalFormat;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 * Double TreeTableCell factory.<br/>
 * If the item value is 0, will not be shown.
 *
 * @author Dora Calderón
 */
public class NoZeroDoubleCellFactory implements Callback<TreeTableColumn, TreeTableCell> {
    private DecimalFormat decimalFormat = new DecimalFormat("#.###");

    @Override
    public TreeTableCell call(TreeTableColumn p) {
        
        TreeTableCell cell = new TreeTableCell<TreeResultModel, Double>() {
            @Override
            public void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
                setAlignment(Pos.CENTER_RIGHT);
            }

            private String getString() {
                Double item = getItem();
                return (item == null || item == 0)  ?  "" : decimalFormat.format(item);
            }
        };

        return cell;
    }
   
}
