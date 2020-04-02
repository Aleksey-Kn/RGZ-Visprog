import javax.swing.*;
import java.awt.*;
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
                }else System.err.println("Graphics's null");
                pj.end();
            }else System.err.println("Принтер отсутствует или не подключен");
        });
        add(printer);

        JButton saver = new JButton("Сохранить по ссылке");
        add(saver);

        JTextField textField = new JTextField();
        add(textField);

        saver.addActionListener(l -> {});

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
        y += 15;
    }
}