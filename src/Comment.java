public class Comment {
    private static String text = "";
    public static void addComment(String comment) {
        text += comment + "\n";
    }
    public static String getText() {
        return text;
    }
    public static void clearComment () {
        text = "";
    }
}
