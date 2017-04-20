import kotlin.reflect.jvm.internal.impl.util.ValueParameterCountCheck;

public class Trie {

    class Vertex {  // вершина

        private String label = " "; // значение в вершине
        public Vertex[] sons = new Vertex[0]; // массив сыновей в представлении вершин
        int count = sons.length; // колличество элементов
        boolean wordEnd = false;

        public Vertex(String label) {
            this.label = label;
            this.sons = sons;
            this.count = count;
            this.wordEnd = wordEnd;
        }

        public String getLabel() {
            return label;
        }
    }

    public Vertex f = new Vertex(" ");

    // добавление элемента
    public void InsertWord(String key){
        Vertex help = f;
        for (int i = 0; i < key.length() - 1; i++ ) {
            int forRepeat = 0;
            int memorySon = 0;
            for (int k = 0; k < help.count - 1; k++){
                if (key.substring(i,i) != help.sons[0].label){
                    forRepeat ++;
                    memorySon = k;
                }
            }
            if (forRepeat == help.count) {
                help.count++;
                Vertex[] vertMass = new Vertex[help.count];
                for (int j = 0; j < help.count; j++) vertMass[j] = help.sons[j];
                vertMass[help.count - 1].label = key.substring(i, i);
                help.sons = vertMass;
                help = help.sons[help.count];
            }
            else help = help.sons[memorySon];
            // описывает ситуацию , когда одно из слов является частью другого
            if ((i == key.length() - 1) && (help.count != 0)) help.wordEnd = true;
        }
    }

    // удаление элемента
    public void DeleteWord(String key){
        Vertex help = f;
        for (int i = 0; i < key.length() - 1; i ++) {
            if (help.count == 0) break;
            for (int g = 0; g < f.count - 1; g++) {
                if (key.substring(i, i) == f.sons[g].label) help = f.sons[g];
            }
        }
        // одно слово часть другого
        if (help.wordEnd) help.wordEnd = false;
        // всё слово или его часть - отдельная часть дерева
        else {
            for (int j = 0; j < key.length() - 1; j ++){
                for (int i = key.length() - 1; i > 0; i ++) {
                    int countForDelet = 0;
                    help = f;
                    for (int g = 0; g < help.count - 1; g++)
                        if (help.sons[g].label == key.substring(i, i)) countForDelet = g;
                    if (help.sons[countForDelet].sons.length == 0) {
                        help.count --;
                        Vertex[] helpMass = new Vertex[help.count];
                        for (int k = 0; k < help.count - 1;k ++ ){
                            for (int l = 0; l < help.count; l++){
                                if (l != countForDelet) helpMass[k] = help.sons[l];
                                else k--;
                            }
                        }
                        help.sons = helpMass;
                        break;
                    }
                }
            }
        }
    }


    // удостовериться что есть данное слово в дервеве
    public boolean SeartchWord (String key){
        Vertex help = f;
        for (int i = 0; i < key.length() - 1; i++){
            int forCheck = 0;
            for (int j = 0; j < help.sons.length - 1; j++){
                if (key.substring(i,i) == help.sons[j].label) {
                    forCheck ++;
                    if ( i == key.length() - 1 ) return true;
                    help = help.sons[j];
                }
            }
            if (forCheck == 0) break;
        }
        return false;
    }

    String[] abc = new String[0];

    // поиск всех дополнений к префиксу в SearchPrefix для образования слов
    public String[] SeartchWithoutPrefix(Vertex aaa){
        int count = abc.length;
        for (int i = 0; i <= aaa.count - 1; i++){
            if (abc.length == 0) {
                count = abc.length + 1;
                String[] help = abc;
                abc = new String[count];
                for (int j = 0; j <= help.length - 1; j++) abc[j] = help[j];
                abc[count - 1] = aaa.sons[i].label;
            }
            else {
                for (int j = 0; j <= abc.length - 1; j++){
                    if (aaa.label == abc[j].substring(abc[j].length() - 1)){
                        count++;
                        String[] helpMass = new String[count];
                        for (int k = 0; k <= count - 1; k++ ) helpMass[k] = abc[k];
                        helpMass[count - 1] = abc[j] + aaa.sons[i];
                    }
                }
            }
            SeartchWithoutPrefix(aaa.sons[i]);
        }

        String[] answer = new String[0];
        for (int l = 0; l < abc.length; l++){
            abc[l] = aaa.label + abc[l];
            if (SeartchWord(abc[l])){
                String[] massForCheck = new String[answer.length + 1];
                for (int f = 0; f < answer.length; f++) massForCheck[f] = answer[f];
                massForCheck[answer.length - 1] = abc[l];
                answer = massForCheck;
            }
        }
        abc = new String[0];
        return answer;
    }

    // поиск всех слов по префиксу
    public String[] SeartchPrefix(String pref){
        Vertex help = f;
        for (int i = 0 ; i <= pref.length() - 1; i ++){
            int forErorr = 0;
            for (int j = 0; j <= help.count - 1; j ++){
                if (pref.substring(i,i) == help.sons[j].label) {
                    forErorr ++;
                    help = help.sons[j];
                }
            }
            if (forErorr == 0) return new String[0];
        }
        return SeartchWithoutPrefix(help);
    }
}
