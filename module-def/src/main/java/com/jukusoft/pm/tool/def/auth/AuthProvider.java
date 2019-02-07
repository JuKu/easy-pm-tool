package com.jukusoft.pm.tool.def.auth;

public interface AuthProvider extends Comparable<AuthProvider> {

    public AuthUser authenticate (String username, String password);

    public int priority();

    @Override
    default int compareTo(AuthProvider o) {
        return this.priority() == o.priority() ? 0 : this.priority() > o.priority() ? 1 : -1;
    }

}
