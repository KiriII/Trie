import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Trie {

    public static class Vertex {  // вершина

        public char label = '\u0000'  ; // значение в вершине
        public Vertex[] sons = new Vertex[0]; // массив сыновей в представлении вершин
        boolean wordEnd = false;

        public Vertex(char label) {
            this.label = label;
            this.sons = sons;
            this.wordEnd = wordEnd;
        }

        public void setLabel(char key) {label = key;}
        public void setVertex(Vertex vertex){
            vertex.setLabel(label);
            vertex.sons = sons;
            vertex.wordEnd = wordEnd;
        }
        public char getLabel() {
                return label;
        }
    }

    public Vertex f = new Vertex('\u0000'); // Vertex(null)

    // добавление элемента
    public void insertWord(String key){
        if (key == null) return;
        String key1 = key.toLowerCase();
        Vertex help = f;
        for (int i = 0; i <= key.length() - 1; i++ ) {
            int forRepeat = 0;
            int memorySon = 0;
            for (int k = 0; k <= help.sons.length - 1; k++){
                if (key1.charAt(i) != help.sons[k].getLabel()){
                    forRepeat ++;
                }
                else memorySon = k;
            }
            if (forRepeat == help.sons.length) {
                Vertex[] vertMass = new Vertex[help.sons.length + 1];
                vertMass[help.sons.length] = new Vertex(key1.charAt(i));
                for (int j = 0; j < help.sons.length; j++) vertMass[j] = help.sons[j];
                help.sons = vertMass;
                help = help.sons[help.sons.length - 1];
            }
            else help = help.sons[memorySon];
            // описывает ситуацию , когда одно из слов является частью другого
            if (i == key.length() - 1) help.wordEnd = true;
        }
    }

    // удаление элемента
    public void deleteWord(String key1){
        if (f.sons.length == 0) return;
        String key = key1.toLowerCase();
        if (!searchWord(key)) return;
        Vertex help = f;
        if (key == null) return;
        if (!searchWord(key)) return;
        int abc = 0; // число на месте которого в слове находится последнее расхождение (несколько детей)
        Vertex help1 = help;
        for (int i = 0; i < key.length(); i++) {
            if (help1.sons.length > 1) {
                abc = i;
                help = help1;
                for (int j = 0; j < help1.sons.length; j++ ){
                    if (help1.sons[j].getLabel() == key.charAt(i)) help1 = help1.sons[j];
                }
            }
            else {
                help1 = help1.sons[0];
                if ((i == key.length() - 1) && (help1.wordEnd) && (help1.sons.length > 0)) help = help1;
            }
        }
        Vertex help2 = help;
        for (int i = abc; i <= key.length() - 1; i ++){
            if ((help2.wordEnd) && (i == key.length() - 1) && (help2.sons.length != 0)) {
                help2.wordEnd = false;
                return;
            }
            if ((help2.wordEnd) && (i != key.length() - 1)){
                help = help2;
            }
            for (int j = 0; j <= help2.sons.length - 1; j++){
                if (help2.sons[j].getLabel() == key.charAt(i)) {help2 = help2.sons[j];}
            }
        }
        Vertex[] vertexArray = new Vertex[help.sons.length - 1];
        if (help.sons.length > 1) {
            for (int l = 0; l <= help.sons.length - 1; l++) {
                for (int k = 0; k <= help.sons.length - 2; k ++) {
                    if (help.sons[l].getLabel() != key.charAt(abc)) vertexArray[k] = help.sons[l];
                }
            }
        }
        help.sons = vertexArray;
    }


    // удостовериться что есть данное слово в дервеве
    public boolean searchWord (String key){
        key = key.toLowerCase();
        if (f.sons.length == 0) return false;
        Vertex help = f;
        if (key == null) return true;
        for (int i = 0; i <= key.length() - 1; i++){
            int forCheck = 0;
            for (int j = 0; j <= help.sons.length - 1; j++){
                if (help.sons.length != 0) {
                    if (key.charAt(i) == help.sons[j].label) {
                        forCheck++;
                        help = help.sons[j];
                    }
                }
            }
            if (forCheck == 0) break;
        }
        if ((help.wordEnd) && (help.getLabel() == key.charAt(key.length() - 1))) return true;
        return false;
    }

    // поиск всех слов по префиксу
    public String[] searchPrefix(String pref) {
        Vertex help = f;
        for (int i = 0; i <= pref.length() - 1; i++) {
            for (int j = 0; j <= help.sons.length - 1; j++) {
                if (pref.charAt(i) == help.sons[j].getLabel()) {
                    help = help.sons[j];
                }
            }
        }
        String[] ArrayString = new String[1];
        // создает очередь
        ArrayDeque<Vertex> q = new ArrayDeque<>();
        q.add(help);
        ArrayString[0] = (ArrayString[0] == null) ? "" + help.getLabel() : ArrayString[0] + help.getLabel();
        // обход в глубину
        while (!q.isEmpty()) {
            Vertex qurrent = q.poll();
            //Код <!--
            String[] helpString = Arrays.copyOf(ArrayString, ArrayString.length + qurrent.sons.length);
            for (int j = 0; j < qurrent.sons.length; j++) {
                for (int i = 0; i < ArrayString.length; i++) {
                    if (ArrayString[i].charAt(ArrayString[i].length() - 1) == qurrent.getLabel())
                        helpString[ArrayString.length + j] = ArrayString[i] + qurrent.sons[j].getLabel();
                }
            }
            ArrayString = helpString;
            //-->
            q.addAll(Arrays.asList(qurrent.sons));
        }
        String[] helpString1 = new String[0];
        for (int k = 0; k < ArrayString.length; k++) {
            if (pref.length() != 1) {
                String a = pref.substring(0 , pref.length() - 1) + ArrayString[0];
                if (searchWord(pref.substring(0, pref.length() - 1) + ArrayString[k])) {
                    String[] help1 = new String[helpString1.length + 1];
                    for (int l = 0; l < helpString1.length; l++) {
                        help1[l] = helpString1[l];
                    }
                    help1[help1.length - 1] = pref.substring(0, pref.length() - 1) + ArrayString[k];
                    helpString1 = help1;
                }
            } else if (searchWord(ArrayString[k])) {
                String[] help1 = new String[helpString1.length + 1];
                for (int l = 0; l < helpString1.length; l++) {
                    help1[l] = helpString1[l];
                }
                help1[help1.length - 1] = ArrayString[k];
                helpString1 = help1;
            }
        }
        return helpString1;
    }
}
