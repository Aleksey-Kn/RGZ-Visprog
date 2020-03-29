import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Frame extends JFrame implements Names{
    private ResultSet rs;
    private String[] row = new String[8];
    ForQuestion[] questionsOnMainPane = null;

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
        JTable table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<>(tableModel);
        table.setRowSorter(filter);
        JScrollPane firstScrollPane = new JScrollPane(table);
        firstScrollPane.setBounds(5, 90, 790, 200);
        add(firstScrollPane);

        Statement statement = Connector.createStatement();
        rs = statement.executeQuery("select * from Questions.Question");
        while (rs.next()){
            for(int i = 0; i < 8; i++){
                row[i] = rs.getString(i + 2);
            }
            tableModel.addRow(row);
        }

        all.addActionListener(l -> filter.setRowFilter(null));
        questions.addActionListener(l -> {
            if(textField.getText().equals("")){
                filter.setRowFilter(RowFilter.regexFilter("question", 4));
            }
            else {
                List<RowFilter<DefaultTableModel, Integer>> rowFilters = new ArrayList<>();
                rowFilters.add(RowFilter.regexFilter(textField.getText(), 5));
                rowFilters.add(RowFilter.regexFilter("question", 4));
                filter.setRowFilter(RowFilter.andFilter(rowFilters));
            }
        });
        task.addActionListener(l -> filter.setRowFilter(RowFilter.regexFilter("task", 4)));

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        JScrollPane secondScrollPane = new JScrollPane(mainPane);
        secondScrollPane.setBounds(5, 345, 790, 330);
        add(secondScrollPane);

        JPanel horisontalPanel = new JPanel();
        horisontalPanel.setBounds(5, 300, 790, 20);
        horisontalPanel.setLayout(new BoxLayout(horisontalPanel, BoxLayout.X_AXIS));
        JButton createTest = new JButton("Сгенерировать тест");
        horisontalPanel.add(createTest);
        horisontalPanel.add(new JLabel("Факультет:"));
        JTextField forFacultetFiled = new JTextField();
        horisontalPanel.add(forFacultetFiled);
        horisontalPanel.add(new JLabel("Дисциплина:"));
        JTextField forDisciplinFiled = new JTextField();
        horisontalPanel.add(forDisciplinFiled);
        horisontalPanel.add(new JLabel("Курс:"));
        JTextField forKursFiled = new JTextField();
        horisontalPanel.add(forKursFiled);
        horisontalPanel.add(new JLabel("Семестр:"));
        JTextField forSemestrFiled = new JTextField();
        horisontalPanel.add(forSemestrFiled);
        add(horisontalPanel);
        createTest.addActionListener(l -> {
            try {
                rs = statement.executeQuery("select " + question + ", " + answer + " from Questions.Question where " +
                        "type = 'question' and " + facultet + " = '" + forFacultetFiled.getText() + "' and " + disciplin + " = '"
                        + forDisciplinFiled.getText() + "' and " + kurs + " = '" + forKursFiled.getText() + "' and " +
                        semestr + " = '" + forSemestrFiled.getText() + "';");
                String[][] mas = new String[2][20];
                Random random = new Random();
                Vector<String[]> vector = new Vector<>(20);
                while (rs.next()){
                    vector.add(new String[]{rs.getString(1), rs.getString(2)});
                }
                if(vector.size() >= 20) {
                    if(questionsOnMainPane != null){
                        clearPane(mainPane);
                    }
                    LinkedHashSet<Integer> set = new LinkedHashSet<>(20);
                    while (set.size() < 20) {
                        set.add(random.nextInt(vector.size()));
                    }
                    Iterator<Integer> it = set.iterator();
                    for (int i = 0, now; i < 20; i++) {
                        now = it.next();
                        mas[0][i] = vector.get(now)[0];
                        mas[1][i] = vector.get(now)[1];
                    }
                    questionsOnMainPane = ForQuestion.creater(mas[0], mas[1]);
                    for (ForQuestion n : questionsOnMainPane) {
                        mainPane.add(n);
                    }
                    secondScrollPane.updateUI();
                }
                else JOptionPane.showMessageDialog(null, "Недостаточно вопросов для составления тестов");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });

        JButton answers = new JButton("Показать ответы");
        answers.setBounds(5, 322, 200, 20);
        answers.addActionListener(l -> {
            if(questionsOnMainPane != null && ForQuestion.isTest()){
                clearPane(mainPane);

                for(int i = 0; i < 20; i++){
                    mainPane.add(new ForQuestion(questionsOnMainPane[i].getData()));
                }
                secondScrollPane.updateUI();
            }
        });
        add(answers);

        //tableModel.addTableModelListener(event -> );

        setVisible(true);
    }

    private void clearPane(JPanel mainPane){
        for(int i = 19; i >= 0; i--){
            mainPane.remove(i);
        }
    }

    public static void main(String[] args) throws SQLException {
        new Frame();
    }
}
