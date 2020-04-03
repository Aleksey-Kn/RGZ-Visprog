import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

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
                    print(pg);
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
            int ColC = table.getColumnCount(); //Определяем кол-во столбцов
            int ItemC = table.getRowCount();  //и элементов (строк)
            StringBuilder sb = new StringBuilder();
            FileWriter fw = null;
            try {
                fw = new FileWriter(new File(textField.getText()));
            }catch (IOException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }
            for(int i = 0; i < table.getColumnCount(); i++){
                sb.append(table.getColumnName(i) + "\t");
            }
            sb.append("\r\n");
            for (int i = 0; i < ItemC; i++) { //проходим все строки
                for (int j = 0; j < ColC; j++) { //собираем одну строку из множества столбцов
                    sb.append(table.getValueAt(i, j));
                    if (j < ColC - 1) sb.append("\t\t");
                    if (j == ColC - 1) sb.append("\r\n");
                }
            }
            try { //Пытаемся писать в файл
                fw.write(sb.toString()); //записывем собранную строку в файл
                fw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            dispose();
        });

        setVisible(true);
    }

    public void paint(Graphics g){
        g.setFont(new Font("Serif", Font.PLAIN, 12));
        g.setColor(Color.black);

        StringBuilder workingString = new StringBuilder();
        int y = 40;

        for(int i = 0; i < table.getColumnCount(); i++) {
            workingString.append(table.getColumnName(i) + "\t");
        }
        g.drawString(workingString.toString(), 30, y);
        y += 20;

        for(int i = 0; i < table.getRowCount(); i++, y += 15){
            workingString = new StringBuilder();
            for(int j = 0; j < table.getColumnCount(); j++){
                workingString.append(table.getValueAt(i, j) + "\t");
            }
            g.drawString(workingString.toString(), 30, y);
        }
    }
}