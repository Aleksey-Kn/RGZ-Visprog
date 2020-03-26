import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Frame extends JFrame {
    private ResultSet rs;
    private String[] row = new String[8];

    Frame() throws SQLException {
        super("Frame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200, 100, 800, 700);
        setLayout(null);

        JRadioButton all = new JRadioButton("Без фильтра", true);
        all.setBounds(10, 10, 200, 20);
        add(all);
        JRadioButton questions = new JRadioButton("Вопросы по теме: ", false);
        questions.setBounds(10, 35, 200, 20);
        add(questions);
        JRadioButton task = new JRadioButton("Задачи", false);
        task.setBounds(10, 60, 200, 20);
        add(task);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(all);
        buttonGroup.add(questions);
        buttonGroup.add(task);

        JTextField textField = new JTextField();
        textField.setBounds(220, 35, 200, 20);
        add(textField);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Факультет", "Дисциплина", "Курс", "Семестр", "Тип задания",
                "Раздел", "Текст задания", "Ответ на задания"});
        JTable table = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(tableModel);
        table.setRowSorter(filter);
        JScrollPane firstScrollPane = new JScrollPane(table);
        firstScrollPane.setBounds(5, 90, 790, 300);
        add(firstScrollPane);

        Statement statement = Connector.createStatement();
        rs = statement.executeQuery("select * from Questions.Question");
        while (rs.next()){
            for(int i = 0; i < 8; i++){
                row[i] = rs.getString(i + 2);
            }
            tableModel.addRow(row);
        }

        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        new Frame();
    }
}
