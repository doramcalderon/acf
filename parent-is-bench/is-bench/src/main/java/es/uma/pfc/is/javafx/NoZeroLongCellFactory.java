package es.uma.pfc.is.javafx;

import es.uma.pfc.is.bench.benchmarks.execution.BenchmarkResultsModel;
import javafx.geometry.Pos;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 * Long TreeTableCell factory.<br/>
 * If the item value is 0, will not be shown.
 *
 * @author Dora Calderón
 */
public class NoZeroLongCellFactory implements Callback<TreeTableColumn, TreeTableCell> {
    

    @Override
    public TreeTableCell call(TreeTableColumn p) {
        
        TreeTableCell cell = new TreeTableCell<BenchmarkResultsModel, Long>() {
            @Override
            public void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
                setAlignment(Pos.CENTER_RIGHT);
            }

            private String getString() {
                Long item = getItem();
                return (item == null || item == 0)  ?  "" : getItem().toString();
            }
        };

        return cell;
    }
   
}
