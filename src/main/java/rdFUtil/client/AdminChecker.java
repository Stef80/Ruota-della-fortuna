package rdFUtil.client;

public class AdminChecker {
    private static boolean isAdmin;

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        AdminChecker.isAdmin = isAdmin;
    }
}