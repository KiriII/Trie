import org.junit.Assert;
import org.junit.Test;


public class Tests {
    Trie trie = new Trie();

    @Test
    public void testSearchWord() {
        Assert.assertEquals(false , trie.searchWord("hello"));
        Assert.assertEquals(false, trie.searchWord("key"));
        Assert.assertEquals(true, trie.searchWord(null));
    }

    @Test
    public void testInsertWord() {
        Assert.assertEquals(true,trie.searchWord(null));
        Assert.assertEquals(false,trie.searchWord("hello"));
        Assert.assertEquals(0 , trie.f.sons.length);
        trie.insertWord("hello");
        trie.insertWord("help");
        trie.insertWord("java");
        trie.insertWord("hel");
        Assert.assertEquals(false, trie.searchWord("Kirill"));
        Assert.assertEquals(true , trie.searchWord("hello"));
        Assert.assertEquals(true , trie.searchWord("help"));
        Assert.assertEquals(true , trie.searchWord("java"));
        Assert.assertEquals(false , trie.searchWord("he")); // проверка WordEnd
        // проверка , что дерево работает как надо(разветвления на месте).
        Assert.assertEquals(2, trie.f.sons.length);
        Assert.assertEquals(2, trie.f.sons[0].sons[0].sons[0].sons.length );
    }

    @Test
    public void testDeleteWord() {
        trie.insertWord("hello");
        trie.insertWord("help");
        trie.insertWord("java");
        trie.insertWord("hel");

        trie.deleteWord("hel");
        Assert.assertEquals(false , trie.searchWord("hel"));
        Assert.assertEquals(true , trie.searchWord("hello"));
        Assert.assertEquals(true , trie.searchWord("help"));
        Assert.assertEquals(true , trie.searchWord("java"));
        Assert.assertEquals(false , trie.searchWord("he"));
        trie.deleteWord("Steam");
        Assert.assertEquals(true , trie.searchWord("hello"));
        Assert.assertEquals(true , trie.searchWord("help"));
        Assert.assertEquals(true , trie.searchWord("java"));
        trie.deleteWord("hello");
        Assert.assertEquals(false, trie.searchWord("hello"));
        Assert.assertEquals(true , trie.searchWord("help"));
        Assert.assertEquals(true , trie.searchWord("java"));
        trie.deleteWord("help");
        Assert.assertEquals(false , trie.searchWord("help"));
        Assert.assertEquals(true , trie.searchWord("java"));
        trie.deleteWord("java");
        Assert.assertEquals(false , trie.searchWord("java"));

    }

    @Test
    public void testSearchPrefix(){
        trie.insertWord("hello");
        trie.insertWord("help");
        trie.insertWord("java");
        trie.insertWord("hel");

        Assert.assertArrayEquals(new String[]{"hel" , "help" ,  "hello" } ,trie.searchPrefix("hel"));
        Assert.assertArrayEquals(new String[]{"hel" , "help" , "hello" } ,trie.searchPrefix("h"));
        Assert.assertArrayEquals(new String[]{"java" } ,trie.searchPrefix("java"));
    }
}
