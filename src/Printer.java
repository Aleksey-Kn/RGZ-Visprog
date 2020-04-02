import javax.swing.*;

public class Printer extends JFrame{
    Printer(JTable table){
        super("Print");
        setBounds(300, 200, 250, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton printer = new JButton("Печать");
        printer.addActionListener(l -> {});
        add(printer);

        JButton saver = new JButton("Сохранить по ссылке");
        add(saver);

        JTextField textField = new JTextField();
        add(textField);

        saver.addActionListener(l -> {});

        setVisible(true);
    }
}
