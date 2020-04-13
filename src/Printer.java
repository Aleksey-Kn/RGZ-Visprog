import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class Printer extends JFrame{
    JTable table;

    Printer(JTable table){
        super("Print");
        setBounds(300, 200, 250, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.table = table;

        JButton printer = new JButton("Печать");
        printer.addActionListener(l -> {
            PrintJob pj = getToolkit().getPrintJob(this, "JobTitle", null);

            if (pj != null){
                Graphics pg = pj.getGraphics();
                if (pg != null){
                    table.print(pg);
                    pg.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Graphics's null");
                }
                pj.end();
            }else {
                JOptionPane.showMessageDialog(null, "Принтер отсутствует или не подключен");
            }
            dispose();
        });
        add(printer);

        JButton saver = new JButton("Сохранить по ссылке");
        add(saver);

        JTextField textField = new JTextField();
        add(textField);

        saver.addActionListener(l -> {
            String way = textField.getText();
            int row = table.getRowCount();
            Document document = new Document(row > 52? PageSize.A2.rotate(): (row > 37?PageSize.A3.rotate(): PageSize.A4.rotate()));
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(way.equals("")? "jTable.pdf": way + ".pdf")));

                document.open();
                PdfContentByte cb = writer.getDirectContent();

                cb.saveState();
                Graphics2D g2 = cb.createGraphicsShapes(800, row > 52? 1190: (row > 37?825: 595));

                Shape oldClip = g2.getClip();

                table.print(g2);
                g2.setClip(oldClip);

                g2.dispose();
                cb.restoreState();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            document.close();
            dispose();
        });

        setVisible(true);
    }
}