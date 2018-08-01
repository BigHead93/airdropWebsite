package airdrop.backend.util;

public enum ResponseEnum {

    SUCCESS("success", 1),
    OUTOFTIME("out of time", 2),
    NEEDNAME("need name", 3),
    NEEDPSW("need password", 4),
    NEEDINFO("need name | psw | email", 5),
    USERNOTEXIST("user not exist", 6),
    WRONGPSW("wrong password", 7),
    SAVEFAILED("failed to save", 8),
    WRONGVCODE("wrong verification code", 9),
    ;

    private int index;
    private String text;

    private ResponseEnum(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public String getText() {
        return this.text;
    }

}
