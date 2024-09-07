package crypto.helper;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class dragDrop_listener implements DropTargetListener {

    private helper lg;

    public dragDrop_listener(helper lang) {
        this.lg = lang;
    }

    @Override
    public void dragEnter(DropTargetDragEvent event) {
        event.acceptDrag(DnDConstants.ACTION_COPY);

        Transferable transferable = event.getTransferable();

        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        for (DataFlavor flavor : flavors) {

            try {

                if (flavor.isFlavorJavaFileListType()) {

                    List files = (List) transferable.getTransferData(flavor);

                    for (Iterator it = files.iterator(); it.hasNext();) {
                        File file = (File) it.next();
                        String file_path = file.getPath();
                        lg.check_store(file_path);
                    }

                }

            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent event) {
        lg.reset_store();
    }

    @Override
    public void drop(DropTargetDropEvent event) {

        event.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable transferable = event.getTransferable();

        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        for (DataFlavor flavor : flavors) {

            try {

                if (flavor.isFlavorJavaFileListType()) {

                    List files = (List) transferable.getTransferData(flavor);

                    for (Iterator it = files.iterator(); it.hasNext();) {
                        File file = (File) it.next();
                        String file_path = file.getPath();
                        lg.check_store(file_path);
                    }

                }

            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
        event.dropComplete(true);
    }
}
