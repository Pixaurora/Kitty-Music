package net.pixaurora.kitten_heart.impl.scrobble;

public class ScrobblerId {
    private final String username;
    private final String scrobblerType;

    public ScrobblerId(String username, String scrobblerType) {
        this.username = username;
        this.scrobblerType = scrobblerType;
    }

    public String username() {
        return username;
    }

    public String scrobblerType() {
        return scrobblerType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int result = 1;

        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((scrobblerType == null) ? 0 : scrobblerType.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof ScrobblerId) {
            return this.equals0((ScrobblerId) other);
        } else {
            return false;
        }
    }

    private boolean equals0(ScrobblerId other) {
        return this.username.equals(other.username) && this.scrobblerType.equals(other.scrobblerType);
    }
}
