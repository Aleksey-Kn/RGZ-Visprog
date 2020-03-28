import javax.swing.*;
import java.awt.*;

public class ForQuestion extends JPanel {
    private JLabel quest;

    ForQuestion(String question, String correct, String... uncorrect){
        setLayout(null);
        setPreferredSize(new Dimension(770, 100));

        quest = new JLabel(question);
        quest.setBounds(0, 0, 790, 20);
        add(quest);

        int index = (int)(Math.random() * 4);
        String[] strings = new String[4];
        for(int i = 0, it = 0; i < 4; i++){
            if(index == i){
                strings[i] = correct;
            }
            else {
                strings[i] = uncorrect[it++];
            }
        }

        JLabel one = new JLabel(strings[0]);
        one.setBounds(0, 20, 385, 20);
        add(one);

        JLabel two = new JLabel(strings[1]);
        two.setBounds(395, 20, 385, 20);
        add(two);

        JLabel three = new JLabel(strings[2]);
        three.setBounds(0, 60, 385, 20);
        add(three);

        JLabel four = new JLabel(strings[3]);
        four.setBounds(395, 60, 385, 20);
        add(four);
    }

    void recreateQuestion(String last, String now){
        if(quest.getText().equals(last)){
            quest.setText(now);
        }
    }
}
