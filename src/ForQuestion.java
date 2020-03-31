import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class ForQuestion extends JPanel {
    private JLabel quest;
    private String question;
    private String answer;
    private static boolean test;

    ForQuestion(String[] data){
        setLayout(null);
        setPreferredSize(new Dimension(770, 55));
        test = false;
        question = data[0];

        quest = new JLabel(data[0] + ":");
        quest.setBounds(0, 0, 770, 20);
        add(quest);

        JLabel ans = new JLabel(data[1]);
        ans.setBounds(0, 25, 770, 20);
        add(ans);
    }

    ForQuestion(String questionn, String correct, String... uncorrect){
        setLayout(null);
        setPreferredSize(new Dimension(770, 100));

        question = questionn;
        answer = correct;
        test = true;

        quest = new JLabel(question + ":");
        quest.setBounds(0, 0, 770, 20);
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
        if(index == 0){
            one.setForeground(Color.RED);
        }
        add(one);

        JLabel two = new JLabel(strings[1]);
        two.setBounds(395, 20, 385, 20);
        if(index == 1){
            two.setForeground(Color.RED);
        }
        add(two);

        JLabel three = new JLabel(strings[2]);
        three.setBounds(0, 60, 385, 20);
        if(index == 2){
            three.setForeground(Color.RED);
        }
        add(three);

        JLabel four = new JLabel(strings[3]);
        four.setBounds(395, 60, 385, 20);
        if(index == 3){
            four.setForeground(Color.RED);
        }
        add(four);
    }

    void recreateQuestion(String last, String now){
        if(question.equals(last)){
            question = now;
            quest.setText(now + ":");
        }
    }

    String[] getData(){
        return new String[]{question, answer};
    }

    static ForQuestion[] creater(String[] questions, String[] answer){
        ForQuestion[] result = new ForQuestion[20];
        Random random = new Random();
        HashSet<Integer> set = new HashSet<>();
        Integer[] mas = new Integer[3];
        for(int i = 0; i < 20; i++){
            set.add(i);
            while (set.size() < 4){
                set.add(random.nextInt(20));
            }
            set.remove(i);
            set.toArray(mas);
            result[i] = new ForQuestion(questions[i], answer[i], answer[mas[0]], answer[mas[1]], answer[mas[2]]);
            set.clear();
        }
        return result;
    }

    static boolean isTest(){
        return test;
    }
}
